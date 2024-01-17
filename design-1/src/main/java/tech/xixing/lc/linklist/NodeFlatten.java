package tech.xixing.lc.linklist;

import tech.xixing.lc.Node;

/**
 * @author liuzhifei
 * @date 2022/9/5 4:46 下午
 */
public class NodeFlatten {

    public static Node flatten(Node head) {
        Node node = head;
        while (node!=null){
            if(node.child!=null){
                Node fltDfs = flatten(node.child);
                node.child = null;
                Node next = node.next;
                fltDfs.prev = node;
                node.next = fltDfs;
                while (fltDfs.next!=null){
                    fltDfs =fltDfs.next;
                }
                next.prev = fltDfs;
                fltDfs.next = next;
                node = next;
            }else {
                node = node.next;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        Node node = new Node();
        node.val = 1;
        Node node1 = new Node(2);
        node1.prev = node;
        node.next = node1;
        Node child = new Node(3);
        Node child2 = new Node(4);
        child2.prev = child;
        child.next = child2;
        node1.child = child;
        Node node2 = new Node(6);
        node2.prev = node1;
        node1.next = node2;
        Node flatten = flatten(node);
        System.out.println(flatten);
    }
}
