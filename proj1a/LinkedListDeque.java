/**
 * Deque implementation
 *
 * @sentinel: keeps track of the first and last nodes
 * @size: keeps track of deque size
 */
public class LinkedListDeque<T> {

    /**
     * GodNode class
     *
     * @item: value the node holds
     * @next: pointer to next node
     */
    public class GodNode {
        private T item;
        private GodNode next;
        private GodNode prev;

        /* GodNode constructor */
        public GodNode(T i, GodNode n, GodNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    /* first item at sentinel.next, last at sentinel.prev */
    private GodNode sentinel;
    private int size;

    /* Creates an empty linked list deque */
    public LinkedListDeque() {
        sentinel = new GodNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /* Adds item of type T to front of deque */
    public void addFirst(T item) {
        sentinel.next = new GodNode(item, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    /* Adds item of type T to back of deque */
    public void addLast(T item) {
        sentinel.prev = new GodNode(item, sentinel, sentinel.prev);
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }

    /* Gets an item given an index */
    public T get(int index) {
        int i;
        GodNode p = sentinel;
        if (sentinel.next == sentinel) {
            return null;
        }
        for (i = 0; p.next != sentinel && i < index; i++) {
            p = p.next;
        }
        return p.next.item;
    }

    /* Same as get, but recursive */
    public T getHelp(int index, GodNode head) {
        if (head.next == sentinel) {
            return null;
        }
        if (index == 0) {
            return head.next.item;
        }
        return getHelp(index - 1, head.next);
    }

    public T getRecursive(int index) {
        return getHelp(index, sentinel);
    }

    /* Returns true if deque is empty, false otherwise */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Prints items of deque from first to last, separated by space */
    public void printDeque() {
        GodNode p = sentinel;
        while (p.next != sentinel) {
            System.out.print(p.next.item);
            if (p.next.next != sentinel) {
                System.out.print(" ");
            }
            p = p.next;
        }
        System.out.println();
    }

    /* Removes and returns front item; return null if doesn't exist */
    public GodNode removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        sentinel.next = sentinel.next.next;
        size--;
        return sentinel.next;
    }

    /* Removes and returns back item; return null if doesn't exist */
    public GodNode removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        sentinel.prev = sentinel.prev.prev;
        size--;
        return sentinel.prev;
    }

    /* Returns size of deque */
    public int size() {
        return size;
    }
}
