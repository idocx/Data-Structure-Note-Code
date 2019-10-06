import java.util.Scanner;

class DoubleNode {
    int num;
    DoubleNode next;
    DoubleNode previous;
    public DoubleNode() {
        num = 0;
        next = null;
        previous = null;
    } 

    public DoubleNode(int _num, DoubleNode _next, DoubleNode _previous) {
        num = _num;
        next = _next;
        previous = _previous;
    }

    @Override
    public String toString() {
        return num + "";
    }
}

class DoubleLinkList {
    DoubleNode head;

    public DoubleLinkList() {
        head = new DoubleNode();
        head.next = head;
        head.previous = head;
    }

    public DoubleLinkList(String numStr) {
        head = new DoubleNode();
        head.next = head;
        head.previous = head;
        for (int i = 0; i < numStr.length(); i++) {
            addFromHead((int)numStr.charAt(i) - (int)'0');
        }
    }

    public void addFromHead(int num) {
        DoubleNode tmp = new DoubleNode(num, head.next, head);
        head.next.previous = tmp;
        head.next = tmp;
    }

    public void addToTail(int num) {
        DoubleNode tmp = new DoubleNode(num, head, head.previous);
        head.previous.next = tmp;
        head.previous = tmp;
    }

    public DoubleNode jumpTo(int index) {
        DoubleNode tmp = head;
        for (int i = 0; i < index; i++) {
            if (tmp.next == head) {
                addToTail(0);
            }
            tmp = tmp.next;
        }
        return tmp;
    }

    @Override
    public String toString() {
        boolean start = false;
        String num_str = "";
        DoubleNode tmp = head.previous;
        while (tmp != head) {
            if (tmp.num != 0)
                start |= true;
            if (start)
                num_str += tmp.num;
            tmp = tmp.previous;
        }
        if (!start)
            num_str += "0";
        return num_str;
    }
}

class TwoBigNum {
    DoubleLinkList n1, n2;
    
    public TwoBigNum(DoubleLinkList _n1, DoubleLinkList _n2) {
        n1 = _n1;
        n2 = _n2;
    }

    public DoubleLinkList multiple() {
        DoubleLinkList result = new DoubleLinkList();
        DoubleNode nn1 = n1.head.next;
        DoubleNode nn2 = n2.head.next;
        DoubleNode nnr;
        int overflow = 0;
        int multi;
        int count = 0;

        while (nn1 != n1.head) {
            nnr = result.jumpTo(count);
            while (nn2 != n2.head) {
                if (nnr.next == result.head) {
                    result.addToTail(0);
                }
                nnr = nnr.next;
                multi = nn1.num * nn2.num;
                nnr.num += (multi % 10 + overflow);
                overflow = 0;
                overflow += (Math.floorDiv(multi, 10) + Math.floorDiv(nnr.num, 10));
                nnr.num %= 10;
                nn2 = nn2.next;
            }

            if (overflow != 0) {
                if (nnr.next == result.head)
                result.addToTail(0);
                nnr.next.num += overflow;
                overflow = 0;
            }

            nn2 = n2.head.next;
            nn1 = nn1.next;
            count++;
        }
        return result;
    }
}

public class POJ_2389 {
    public static int abs(int x) {
        if (x >= 0) return x;
        return -x;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String nn1 = input.nextLine();
        String nn2 = input.nextLine();
        input.close();

        DoubleLinkList n1 = new DoubleLinkList(nn1);
        DoubleLinkList n2 = new DoubleLinkList(nn2);
        TwoBigNum tb = new TwoBigNum(n1, n2);
        System.out.println(tb.multiple().toString());
    }
}
