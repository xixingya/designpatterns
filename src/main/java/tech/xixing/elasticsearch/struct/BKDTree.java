package tech.xixing.elasticsearch.struct;

import java.util.*;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class BKDTree {
    private Data[] data; // 数据点数组
    private Node root; // BKD 树根节点
    private int k; // 排序维度数目

    // 构造函数
    public BKDTree(Data[] data, int k) {
        this.data = data.clone();
        this.root = build(0, data.length - 1, 0, k);
        this.k = k;
    }

    // 构建 BKD 树
    private Node build(int start, int end, int depth, int k) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        int axis = depth % k;
        Arrays.sort(data, start, end + 1, Comparator.comparing(d -> d.getPoint().get(axis)));
        Node node = new Node(data[mid], k);
        List<Integer> leftIntervals = findIntervals(start, mid - 1, node);
        List<Integer> rightIntervals = findIntervals(mid + 1, end, node);
        for (int i : leftIntervals) {
            node.addChild(build(i, node.getRight(i), depth + 1, k));
        }
        for (int i : rightIntervals) {
            node.addChild(build(node.getLeft(i), i, depth + 1, k));
        }
        return node;
    }

    // 查找区间
    // 查找区间
    private List<Integer> findIntervals(int start, int end, Node node) {
        List<Integer> intervals = new ArrayList<>();
        for (int i = start; i <= end;) {
            if (data[i].getPoint().get(node.getAxis()) <= node.getData().getPoint().get(node.getAxis())) {
                int j = i;
                while (j <= end && data[j].getPoint().get(node.getAxis()) == data[i].getPoint().get(node.getAxis())) {
                    j++;
                }
                intervals.add(j - 1);
                i = j;
            } else {
                intervals.add(i);
                i++;
            }
        }
        return intervals;
    }

    // 搜索最近的 k 个数据点
    public PriorityQueue<Data> search(Point query, int k) {
        PriorityQueue<Data> nearestData = new PriorityQueue<Data>(k, new Comparator<Data>() {
            public int compare(Data d1, Data d2) {
                double dist1 = d1.getPoint().distance(query);
                double dist2 = d2.getPoint().distance(query);
                return Double.compare(dist2, dist1);
            }
        });
        search(root, query, k, nearestData);
        return nearestData;
    }

    // 递归搜索 BKD 树
    private void search(Node node, Point query, int k, PriorityQueue<Data> nearestData) {
        if (node == null) {
            return;
        }
        double dist = node.getData().getPoint().distance(query);
        if (nearestData.size() < k || dist < nearestData.peek().getPoint().distance(query)) {
            nearestData.offer(node.getData());
            if (nearestData.size() > k) {
                nearestData.poll();
            }
        }
        double splitDist = query.get(node.getAxis()) - node.getData().getPoint().get(node.getAxis());
        if (splitDist < 0) {
            for (int i = 0; i < node.childCount(); i++) {
                Node child = node.getChild(i);
                if (child != null && child.getRangeMin() <= Math.abs(splitDist)) {
                    search(child, query, k, nearestData);
                }
            }
        } else {
            for (int i = 0; i < node.childCount(); i++) {
                Node child = node.getChild(i);
                if (child != null && child.getRangeMax() >= Math.abs(splitDist)) {
                    search(child, query, k, nearestData);
                }
            }
        }
    }

    // 查找半径内的数据点
    // 查询最近多少距离内的数据点
    public List<Point> search(Point query, double radius) {
        List<Point> result = new ArrayList<>();
        search(root, query, radius, result);
        return result;
    }

    // 递归搜索 BKD 树
    private void search(Node node, Point query, double radius, List<Point> result) {
        if (node == null) {
            return;
        }
        double dist = node.getData().getPoint().distance(query);
        if (dist <= radius) {
            result.add(node.getData().getPoint());
        }
        double splitDist = query.get(node.getAxis()) - node.getData().getPoint().get(node.getAxis());
        if (splitDist < 0) {
            for (int i = 0; i < node.childCount(); i++) {
                Node child = node.getChild(i);
                if (child != null && child.getRangeMin() <= Math.abs(splitDist) + radius) {
                    search(child, query, radius, result);
                }
            }
        } else {
            for (int i = 0; i < node.childCount(); i++) {
                Node child = node.getChild(i);
                if (child != null && child.getRangeMax() >= Math.abs(splitDist) - radius) {
                    search(child, query, radius, result);
                }
            }
        }
    }

    // 节点类
    private static class Node {
        private Data data; // 数据
        private int axis; // 排序维度
        private List<Node> children; // 子节点

        public Node(Data data, int k) {
            this(data, 0, new ArrayList<>(k));
        }

        public Node(Data data, int axis, List<Node> children) {
            this.data = data;
            this.axis = axis;
            this.children = children;
        }

        public Data getData() {
            return data;
        }

        public int getAxis() {
            return axis;
        }

        public int childCount() {
            return children.size();
        }

        public Node getChild(int index) {
            return children.get(index);
        }

        public void addChild(Node child) {
            children.add(child);
        }

        // 计算区间范围
        public double getRangeMin() {
            if (children.isEmpty()) {
                return 0;
            }
            return children.get(0).getData().getPoint().get(axis) - data.getPoint().get(axis);
        }

        public double getRangeMax() {
            if (children.isEmpty()) {
                return 0;
            }
            return children.get(children.size() - 1).getData().getPoint().get(axis) - data.getPoint().get(axis);
        }

        // 计算区间范围
        // 获取子节点右边界
        public int getRight(int index) {
            if (index >= children.size() - 1) {
                return data.getIndex();
            }
            return children.get(index + 1).getData().getIndex() - 1;
        }

        // 获取子节点左边界
        public int getLeft(int index) {
            if (index <= 0) {
                return 0;
            }
            return children.get(index - 1).getData().getIndex();
        }

        // 比较器
        public static Comparator<Node> comparator(int axis) {
            return new Comparator<Node>() {
                public int compare(Node n1, Node n2) {
                    return Double.compare(n1.getData().getPoint().get(axis), n2.getData().getPoint().get(axis));
                }
            };
        }
    }

    // 数据类
    // 数据类
    private static class Data {
        private Point point; // 空间点
        private String label; // 标签
        private double weight; // 权重
        private int index; // 在输入数据中的索引

        public Data(Point point, String label, double weight, int index) {
            this.point = point;
            this.label = label;
            this.weight = weight;
            this.index = index;
        }

        public Point getPoint() {
            return point;
        }

        public String getLabel() {
            return label;
        }

        public double getWeight() {
            return weight;
        }

        public int getIndex() {
            return index;
        }
    }

    public static class Point {
        private double[] coordinates; // 坐标数组

        public Point(double... coordinates) {
            this.coordinates = coordinates.clone();
        }

        public double get(int index) {
            return coordinates[index];
        }

        public int dimension() {
            return coordinates.length;
        }

        // 计算两点之间的距离
        public double distance(Point other) {
            double sum = 0;
            for (int i = 0; i < dimension(); i++) {
                double diff = get(i) - other.get(i);
                sum += diff * diff;
            }
            return Math.sqrt(sum);
        }
    }

    public static void main(String[] args) {
        // 创建一个包含 10 个随机点的数组
        Data[] datas = new Data[10];
        for (int i = 0; i < datas.length; i++) {
            Data data = new Data(new Point(Math.random(), Math.random()), "label", 1, i);
            datas[i] = data;
        }
        datas[9] = new Data(new Point(100, 100), "label", 1, 0);

        // 创建 BKD 树
        BKDTree tree = new BKDTree(datas,2);

        List<Point> search = tree.search(new Point(100, 101), 2d);

        System.out.println(search.size());


    }
}
