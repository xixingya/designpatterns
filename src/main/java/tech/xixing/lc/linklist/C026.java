package tech.xixing.lc.linklist;

import tech.xixing.lc.ListNode;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author liuzhifei
 * @date 2022/8/31 5:22 下午
 */
public class C026 {

    public static void reorderList(ListNode head) {
        Deque<ListNode> deque = new LinkedBlockingDeque<>();
        int size = 0;
        while (head!=null){
            size++;
            ListNode temp = head;
            head = head.next;
            temp.next = null;
            deque.add(temp);
        }
        ListNode res = new ListNode();
        ListNode temp = res;
        while (!deque.isEmpty()){
            ListNode listNode = deque.pollFirst();
            ListNode listNode1 = deque.pollLast();
            temp.next =listNode;
            temp = temp.next;
            if(listNode1!=null){
                temp.next = listNode1;
                temp = temp.next;
            }else {
                break;
            }
        }
        head = res.next;
    }

    public static void main(String[] args) {
        reorderList(new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4)))));
    }
}
