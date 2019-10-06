import java.util.*;

class LinkNode<E> {
    E _data;
    LinkNode<E> _next;

    public LinkNode() {
        _data = null;
        _next = null;
    }

    public LinkNode(E data, LinkNode<E> next) {
        _data = data;
        _next = next;
    }

    public LinkNode<E> getNextNode() {
        return _next;
    }

    public boolean setNextNode(LinkNode<E> next) {
        _next = next;
        return true;
    }

    public E getContent() {
        return _data;
    }

    public String toString() {
        return _data.toString();
    }
}

class SingleLinkList<E> {
    LinkNode<E> head;

    public SingleLinkList() {
        head = new LinkNode<E>();
    }

    public SingleLinkList(E [] ar) {
        head = new LinkNode<E>();
        for (int i = ar.length-1; i >= 0; i--)
            add(ar[i]);
    }

    public boolean add(E elem) {
        LinkNode<E> tmp = head.getNextNode();
        var p = new LinkNode<E>(elem, tmp);
        head.setNextNode(p);
        return true;
    }

    public String toString() {
        String target = "[";
        LinkNode<E> p = head.getNextNode();
        while (p != null) {
            if (p != head.getNextNode()) target += ", ";
            target += p.toString();
            p = p.getNextNode();
        }
        target += "]";
        return target;
    }
}

public class Homework_LinkedList {
    public static void put_neg_ahead(SingleLinkList<Integer> L) {
        LinkNode<Integer> node = L.head.getNextNode();
        LinkNode<Integer> prior = L.head;
        LinkNode<Integer> last_neg = L.head;

        while (node != null) {
            if (node.getContent() < 0) {
                prior.setNextNode(node.getNextNode());
                node.setNextNode(last_neg.getNextNode());
                last_neg.setNextNode(node);

                last_neg = node;
                node = prior;
            }
            prior = node;
            node = node.getNextNode();
        }
    }

    public static SingleLinkList<Integer> unranked_union(
        SingleLinkList<Integer> L1, SingleLinkList<Integer> L2) {
        LinkNode<Integer> node1=L1.head.getNextNode();
        LinkNode<Integer> node2=L2.head.getNextNode();

        SingleLinkList<Integer> result = new SingleLinkList<Integer>();
        LinkNode<Integer> result_last = result.head;
        int content1;

        while (node1 != null) {
            content1 = node1.getContent();

            while (node2 != null) {  // look up target value in L2
                if (node2.getContent() == content1) {
                    var tmp_node = new LinkNode<Integer>(content1, null);
                    result_last.setNextNode(tmp_node);
                    result_last = tmp_node;
                    break;
                }
                node2 = node2.getNextNode();
            }
            node2 = L2.head.getNextNode();  // reset L2
            node1 = node1.getNextNode();
        }
        return result;
    }

    public static SingleLinkList<Integer> ranked_union(
        SingleLinkList<Integer> L1, SingleLinkList<Integer> L2) {

        SingleLinkList<Integer> result = new SingleLinkList<Integer>();
        LinkNode<Integer> node1=L1.head.getNextNode();
        LinkNode<Integer> node2=L2.head.getNextNode();
        LinkNode<Integer> tmp_node, result_last=result.head;
        int content1, content2;
        
        while (node1 != null && node2 != null) {
            content1 = node1.getContent();
            content2 = node2.getContent();

            if (content1 > content2)
                node2 = node2.getNextNode();
            else if (content1 < content2)
                node1 = node1.getNextNode();
            else {
                tmp_node = new LinkNode<Integer>(content1, null);
                result_last.setNextNode(tmp_node);
                result_last = tmp_node;
                node1 = node1.getNextNode();
                node2 = node2.getNextNode();
            }
        }
        return result;
    }

    public static SingleLinkList<Integer> unranked_diff(
        SingleLinkList<Integer> L1, SingleLinkList<Integer> L2) {

        LinkNode<Integer> node1=L1.head.getNextNode();
        LinkNode<Integer> node2=L2.head.getNextNode();
        SingleLinkList<Integer> result = new SingleLinkList<Integer>();
        boolean raux2[];  // auxiliary array
        LinkNode<Integer> result_last = result.head;
        int content1;
        int count = 0;
        boolean isIn = false;

        // get the length of L2
        while (node2 != null) {
            node2 = node2.getNextNode();
            count++;
        }
        raux2 = new boolean[count];
        // reset node2 and count
        node2 = L2.head.getNextNode();
        count = 0;

        // get the same element in L1 and L2
        while (node1 != null) {
            content1 = node1.getContent();
            // look up target value in L2, 
            // if not in, isIn variable will be false
            while (node2 != null) {  
                if (node2.getContent() == content1){
                    isIn = true;
                    raux2[count] = true;
                }
                count++;
                node2 = node2.getNextNode();
            }
            // decide whether add the node to result or not
            if (!isIn) {
                var tmp_node = new LinkNode<Integer>(content1, null);
                result_last.setNextNode(tmp_node);
                result_last = tmp_node;
            }

            isIn = false;  // reset isIn flag
            node2 = L2.head.getNextNode();  // reset L2
            count = 0;
            node1 = node1.getNextNode();
        }

        while (node2 != null) {
            if (!raux2[count]) {
                var tmp_node = new LinkNode<Integer>(node2.getContent(), null);
                result_last.setNextNode(tmp_node);
                result_last = tmp_node;
            }
            count++;
            node2 = node2.getNextNode();
        }
        return result;
    }

    public static SingleLinkList<Integer> ranked_diff(
        SingleLinkList<Integer> L1, SingleLinkList<Integer> L2) {

        SingleLinkList<Integer> result = new SingleLinkList<>();
        LinkNode<Integer> node1=L1.head.getNextNode();
        LinkNode<Integer> node2=L2.head.getNextNode();
        LinkNode<Integer> tmp_node, last_result=result.head;
        int content1, content2;

        while (node1 != null && node2 != null) {
            content1 = node1.getContent();
            content2 = node2.getContent();
            if (content1 > content2) {
                tmp_node = new LinkNode<>(content2, last_result.getNextNode());
                last_result.setNextNode(tmp_node);
                last_result = tmp_node;
                node2 = node2.getNextNode();
            }
            else if (content1 < content2) {
                tmp_node = new LinkNode<>(content1, last_result.getNextNode());
                last_result.setNextNode(tmp_node);
                last_result = tmp_node;
                node1 = node1.getNextNode();
            }
            else {
                node1 = node1.getNextNode();
                node2 = node2.getNextNode();
            }
        }
        while (node1 != null) {
            content1 = node1.getContent();
            tmp_node = new LinkNode<>(content1, last_result.getNextNode());
            last_result.setNextNode(tmp_node);
            last_result = tmp_node;
            node1 = node1.getNextNode();
        }

        while (node2 != null) {
            content2 = node2.getContent();
            tmp_node = new LinkNode<>(content2, last_result.getNextNode());
            last_result.setNextNode(tmp_node);
            last_result = tmp_node;
            node2 = node2.getNextNode();
        }
        return result;
    }

    public static SingleLinkList<Integer> rank_two_list(
        SingleLinkList<Integer> L1, SingleLinkList<Integer> L2) {

        SingleLinkList<Integer> result = new SingleLinkList<>();
        LinkNode<Integer> node1=L1.head.getNextNode();
        LinkNode<Integer> node2=L2.head.getNextNode();
        LinkNode<Integer> tmp;
        int content1, content2;

        while (node1 != null && node2 != null) {
            content1 = node1.getContent();
            content2 = node2.getContent();
            if (content1 > content2) {
                tmp = result.head.getNextNode();
                var p = new LinkNode<Integer>(content2, tmp);
                result.head.setNextNode(p);
                node2 = node2.getNextNode();
            }
            else {
                tmp = result.head.getNextNode();
                var p = new LinkNode<Integer>(content1, tmp);
                result.head.setNextNode(p);
                node1 = node1.getNextNode();
            }
        }
        while (node1 != null) {
            tmp = result.head.getNextNode();
            var p = new LinkNode<Integer>(node1.getContent(), tmp);
            result.head.setNextNode(p);
            node1 = node1.getNextNode();
        }

        while (node2 != null) {
            tmp = result.head.getNextNode();
            var p = new LinkNode<Integer>(node2.getContent(), tmp);
            result.head.setNextNode(p);
            node2 = node2.getNextNode();
        }

        return result;
    }

        public static void main(String[] args) {
        Integer mpt[] = {1, -1, -2, 3, 5, -5, 6, -7};
        Integer mpt1[] = {1, 3, 6, 5, 4, -2};
        Integer mpt2[] = {1, -1, -2, -3, 2};
        var l = new SingleLinkList<Integer>(mpt);
        System.out.print("Origin : ");
        System.out.println(l.toString());
        put_neg_ahead(l);
        System.out.print("After calling put_neg_ahead(l): ");
        System.out.println(l.toString());
        System.out.println("-----------------------------------------------------------------");
        Arrays.sort(mpt1);
        Arrays.sort(mpt2);
        var l1 = new SingleLinkList<Integer>(mpt1);
        var l2 = new SingleLinkList<Integer>(mpt2);
        System.out.print("Original L1: ");
        System.out.println(l1.toString());
        System.out.print("Original L2: ");
        System.out.println(l2.toString());
        System.out.print("call unranked_union(l1, l2): ");
        System.out.println(unranked_union(l1, l2).toString());
        System.out.print("call unranked_diff(l1, l2): ");
        System.out.println(unranked_diff(l1, l2).toString());
        System.out.println("-----------------------------------------------------------------");
        Arrays.sort(mpt1);
        Arrays.sort(mpt2);
        l1 = new SingleLinkList<Integer>(mpt1);
        l2 = new SingleLinkList<Integer>(mpt2);
        System.out.print("call ranked_union(l1, l2): ");
        System.out.println(ranked_union(l1, l2).toString());
        System.out.print("call ranked_diff(l1, l2): ");
        System.out.println(ranked_diff(l1, l2).toString());
        System.out.print("call rank_two_list(l1, l2): ");
        System.out.println(rank_two_list(l1, l2).toString());
    }
}
