import java.util.*;

class SqList<E> {
    final int initcapacity = 10;
    int size;
    int capacity;
    E arr[];

    @SuppressWarnings("unchecked")
    public SqList() {
        this.arr = (E[]) new Object[this.initcapacity];
        this.size = 0;
        this.capacity = this.initcapacity;
    }

    @SuppressWarnings("unchecked")
    public SqList(int capa) {
        this.arr = (E[]) new Object[capa];
        this.size = 0;
        this.capacity = capa;
    }

    public SqList(E ar[]) {
        this.arr = ar.clone();
        this.size = ar.length;
        if (this.size >= this.initcapacity) 
            this.capacity = size * 2;
        else 
            this.capacity = this.initcapacity;
    }

    public boolean add(E elem) {
        if (this.size == this.capacity) {
            this.updateCapacity(2 * this.capacity);
        }
        this.arr[size] = elem;
        return true;
    }

    public boolean add(E elem, int index) {
        if (index < 0 || index > size) return false;
        if (this.size == this.capacity) {
            this.updateCapacity(2 * this.capacity);
        }
        for (int i = index; i < size; i++) {
            this.arr[i] = this.arr[i+1];
        } 
        this.arr[index] = elem;
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean updateCapacity(int capa) {
        if (this.size > capa) return false;
        E newArr[] = (E[]) new Object[capa];
        for (int i = 0; i < this.size; i++) {
            newArr[i] = this.arr[i];
        }
        this.arr = newArr;
        this.capacity = capa;
        return true;
    }

    public int Size() {
        return this.size;
    }

    public E getElem(int index) {
        return this.arr[index];
    }

    public boolean setElem(E elem, int index) {
        if (index < 0 || index >= this.size) return false;
        this.arr[index] = elem;
        return true;
    }

    public void swap(int index1, int index2) {
        E tmp = this.arr[index1];
        this.arr[index1] = this.arr[index2];
        this.arr[index2] = tmp;
    }

    public boolean setSize(int newSize) {
        if (newSize < 0 || newSize > this.capacity) return false; 
        this.size = newSize;
        return true;
    }

    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(this.arr, 0, size));
    }
}


public class Homework_SequeneTable {
    public static void delete_neg(SqList<Integer> L) {
        int i=0, j=0;
        // i is the pointer of the first element in unclassified section
        // j is the pointer of the first element in negative element section
        while (i < L.Size()) {  
            if (L.getElem(i) > 0) {
                if (i != j)
                    L.swap(i, j);  // exchange two elements
                j++;
            }
            i++;
        }
        L.setSize(j);  // change the size of L
    }

    public static void delete_between_xy(SqList<Integer> L, int x, int y) {
        int i=0, j=0;
        int tmp;
        // i is the pointer of the first element in unclassified section
        // j is the pointer of the first element in unwanted element section
        while (i < L.Size()) {  
            tmp = L.getElem(i);
            if (tmp > y || tmp < x) {
                if (i != j)
                    L.swap(i, j);  // exchange two elements
                j++;
            }
            i++;
        }
        L.setSize(j);
    }    
    
    public static void put_neg_ahead(SqList<Integer> L) {
        int i=0, j=0;
        // i is the pointer of the first element in unclassified section
        // j is the pointer of the first element in non-negative element section
        while (i < L.Size()) {  
            if (L.getElem(i) < 0) {
                if (i != j)
                    L.swap(i, j);  // exchange two elements
                j++;
            }
            i++;
        }
    }

    public static void main(String[] args) {
        Integer mpt[] = {-1, -2, 3, -1, -2, -3, 0, -5, 1, 0, 2, 5, 7};
        var l = new SqList<Integer>(mpt);
        System.out.print("Original L: ");
        System.out.println(l.toString());
        delete_neg(l);
        System.out.print("After calling delete_neg(l): ");
        System.out.println(l.toString());
        l = new SqList<Integer>(mpt);
        delete_between_xy(l, -1 , 1);
        System.out.print("After calling delete_between_xy(l, -1 , 1): ");
        System.out.println(l.toString());
        l = new SqList<Integer>(mpt);
        put_neg_ahead(l);
        System.out.print("After calling put_neg_ahead(l): ");
        System.out.println(l.toString());
    }
}
