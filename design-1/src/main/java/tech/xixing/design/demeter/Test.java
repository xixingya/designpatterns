package tech.xixing.design.demeter;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 10:30
 */
public class Test {
    public static void main(String[] args) {
        Boss boss=new Boss();
        TeamLeader teamLeader=new TeamLeader();

        boss.commandCheckNum(teamLeader);
    }
}
