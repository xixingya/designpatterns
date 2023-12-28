package tech.xixing.lc.linklist;

import tech.xixing.lc.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author liuzhifei
 * @date 2022/8/26 7:45 下午
 */
public class TwoAdd {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Deque<Integer> deque1 = new ArrayDeque<>();
        Deque<Integer> deque2 = new ArrayDeque<>();
        ListNode l1Copy = l1;
        ListNode l2Copy = l2;
        while (l1!=null){
            deque1.push(l1.val);
            l1 = l1.next;
        }
        while (l2!=null){
            deque2.push(l2.val);
            l2 = l2.next;
        }
        int inNext = 0;
        Deque<ListNode> deque3 = new ArrayDeque<>();
        while (!deque1.isEmpty()&&!deque2.isEmpty()){
            Integer i1 = deque1.pop();
            Integer i2 = deque2.pop();
            int val = (i1+i2+inNext)%10;
            if(i1+i2+inNext>10){
                inNext = 1;
            }else {
                inNext = 0;
            }
            deque3.push(new ListNode(val));
        }
        Deque<Integer> bigger = deque1.isEmpty()?deque2:deque1;
        while (!bigger.isEmpty()){
            Integer i1 = bigger.pop();
            int val = (i1+inNext)%10;
            if(i1+inNext>10){
                inNext = 1;
            }else {
                inNext = 0;
            }
            deque3.push(new ListNode(val));
        }
        if(inNext ==1){
            deque3.push(new ListNode(1));
        }
        ListNode listNode = deque3.pop();
        ListNode res  = listNode;
        while (!deque3.isEmpty()){
            listNode.next = deque3.pop();
            listNode = listNode.next;
        }
        return res;
    }

    public static void main(String[] args) {
        addTwoNumbers(new ListNode(2,new ListNode(4,new ListNode(3))),new ListNode(5,new ListNode(6,new ListNode(4))));
    }
}
