package tech.xixing.lc.linklist;

import tech.xixing.lc.ListNode;

/**
 * @author liuzhifei
 * @date 2022/9/1 3:35 下午
 */
public class PalindromeList {

    public static boolean isPalindrome(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode reverse = reverse(slow);
        while (reverse!=null){
            if(head.val != reverse.val){
                return false;
            }
            head = head.next;
            reverse = reverse.next;
        }
        return true;
    }

    public static ListNode reverse(ListNode root) {
        ListNode r = new ListNode();
        ListNode p  = root;
        ListNode q;
        while (p!=null){
            q = p.next;
            p.next = r.next;
            r.next = p;
            p = q;
        }
        return r.next;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(1))))));
    }
}
