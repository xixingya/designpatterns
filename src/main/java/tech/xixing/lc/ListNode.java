package tech.xixing.lc;

import java.util.Arrays;
import java.util.TimeZone;

/**
 * @author liuzhifei
 * @date 2022/8/26 7:45 下午
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }


    public static void main(String[] args) {
        Long current = System.currentTimeMillis();  //当前时间的时间戳
        System.out.println(Arrays.toString(TimeZone.getAvailableIDs()));
        long todayZero = current - (current + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        System.out.println(todayZero);
    }

}
