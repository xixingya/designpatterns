package tech.xixing.elasticsearch.io;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


/**
 * @author liuzhifei
 * @since 1.0
 */
public class GossipProtocol {
    private static final int GOSSIP_INTERVAL = 1000;
    private static final int MAX_CLUSTER_SIZE = 10;
    private static final int MAX_MEMBER_AGE = 5 * GOSSIP_INTERVAL;

    private String nodeName;
    private List<Member> cluster;

    public GossipProtocol(String nodeName,int port) throws UnknownHostException {
        this.nodeName = nodeName;
        this.cluster = new ArrayList<>();
        this.cluster.add(new Member(nodeName, InetAddress.getLocalHost().getHostAddress(),port));
    }

    public void start() {
        Thread gossipThread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                Member member = selectRandomMember();
                if (member != null) {
                    Member peer = selectRandomPeer(member);
                    if (peer != null) {
                        exchangeGossipInfo(member, peer);
                    }
                }
                sleep(GOSSIP_INTERVAL + random.nextInt(GOSSIP_INTERVAL));
                cleanup();
            }
        });
        gossipThread.start();
    }

    public void join(String nodeName, String ipAddress,int port) {
        Member member = new Member(nodeName, ipAddress,port);
        cluster.add(member);
        System.out.println(this.nodeName + " joined " + nodeName + " at " + ipAddress);
    }

    private Member selectRandomMember() {
        if (cluster.size() > 1) {
            return cluster.get(new Random().nextInt(cluster.size()));
        } else {
            return null;
        }
    }

    private Member selectRandomPeer(Member member) {
        List<Member> peers = new ArrayList<>(cluster);
        peers.remove(member);
        if (peers.size() > 0) {
            return peers.get(new Random().nextInt(peers.size()));
        } else {
            return null;
        }
    }

    private void exchangeGossipInfo(Member member1, Member member2) {
        if (isYounger(member1, member2)) {
            mergeMember(member1, member2);
        } else {
            mergeMember(member2, member1);
        }
    }

    private boolean isYounger(Member member1, Member member2) {
        return member1.getAge() < member2.getAge();
    }

    private void mergeMember(Member younger, Member older) {
        older.setIpAddress(younger.getIpAddress());
        older.setPort(younger.getPort());
        older.setState(mergeStates(younger.getState(), older.getState()));
        older.setAge(0);
    }
    private Map<String, Object> mergeStates(Map<String, Object> state1, Map<String, Object> state2) {
        Map<String, Object> mergedState = new HashMap<>(state1);
        mergedState.putAll(state2);
        return mergedState;
    }

    private void cleanup() {
        List<Member> deadMembers = new ArrayList<>();
        for (Member member : cluster) {
            member.incrementAge(GOSSIP_INTERVAL);
            if (member.getAge() > MAX_MEMBER_AGE) {
                deadMembers.add(member);
            }
        }
        for (Member member : deadMembers) {
            cluster.remove(member);
            System.out.println(nodeName + " removed dead member " + member.getName() + " at " + member.getIpAddress());
        }
        if (cluster.size() > MAX_CLUSTER_SIZE) {
            int removeCount = cluster.size() - MAX_CLUSTER_SIZE;
            List<Member> toRemove = new ArrayList<>();
            for (int i = 0; i < removeCount; i++) {
                Member member = selectRandomMember();
                if (member != null) {
                    toRemove.add(member);
                }
            }
            for (Member member : toRemove) {
                cluster.remove(member);
                System.out.println(nodeName + " removed excess member " + member.getName() + " at " + member.getIpAddress());
            }
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        GossipProtocol node1 = new GossipProtocol("node1",6379);
        node1.start();

        GossipProtocol node2 = new GossipProtocol("node2",6379);
        node2.join("node1", InetAddress.getLocalHost().getHostAddress(),6379);
        node2.start();
    }

    public static class Member {
        private String name;
        private String ipAddress;
        private int port;
        private long age;
        private Map<String, Object> state;

        public Member(String name, String ipAddress, int port) {
            this.name = name;
            this.ipAddress = ipAddress;
            this.port = port;
            this.age = 0;
            this.state = new HashMap<>();
        }

        public String getName() {
            return name;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public long getAge() {
            return age;
        }

        public void setAge(long age) {
            this.age = age;
        }

        public void incrementAge(long delta) {
            this.age += delta;
        }

        public Map<String, Object> getState() {
            return state;
        }

        public void setState(Map<String, Object> state) {
            this.state = state;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Member member = (Member) o;
            return Objects.equals(name, member.name) && Objects.equals(ipAddress, member.ipAddress) && port == member.port;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, ipAddress, port);
        }

        @Override
        public String toString() {
            return "Member{" +
                    "name='" + name + '\'' +
                    ", ipAddress='" + ipAddress + '\'' +
                    ", port=" + port +
                    ", age=" + age +
                    '}';

        }
    }
}