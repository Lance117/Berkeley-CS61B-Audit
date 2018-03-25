/**
 * Array deque implementation
 * @items: array of type T objects
 * @size: size of array
 * @nextFirst: index of first element of array
 * @nextLast: index of last element of array
 */
public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int first;
    private int last;
    private int nextFirst;
    private int nextLast;
    private int capacity = 8;
    public static final int RFACTOR = 2;

    /* Resizes array to target capacity */
    private void resize(int capacity, int oldCap) {
        int i, j;
        int newPos = capacity / 4;
        T[] a = (T[]) new Object[capacity];
        for (i = first, j = newPos; ; i = (i + 1) % oldCap, j++) {
            a[j] = items[i];
            if (i == last) {
                break;
            }
        }
        items = a;
        first = newPos;
        last = j;
        nextFirst = newPos - 1;
        nextLast = j + 1;
    }

    /* Creates an empty array deque */
    public ArrayDeque() {
        items = (T[]) new Object[capacity];
        nextFirst = capacity / 2;
        nextLast = nextFirst + 1;
        first = last = nextFirst;
        size = 0;
    }

    /* Adds item T to front of deque */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(capacity * RFACTOR, capacity);
            capacity *= RFACTOR;
        }
        items[nextFirst] = item;
        first = nextFirst;
        nextFirst = (nextFirst - 1) % capacity;
        if (nextFirst < 0) {
            nextFirst += capacity;
        }
        size++;
    }

    /* Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (size == items.length) {
            resize(capacity * RFACTOR, capacity);
            capacity *= RFACTOR;
        }
        items[nextLast] = item;
        last = nextLast;
        nextLast = (nextLast + 1) % capacity;
        size++;
    }

    /* Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /* Prints the items in the deque from nextFirst to nextLast, separated by a space */
    public void printDeque() {
        int i;
        for (i = first; ; i = (i + 1) % capacity) {
            System.out.print(items[i]);
            if (i != last) {
                System.out.print(" ");
            }
            if (i == last) {
                break;
            }
        }
        System.out.println();
    }

    /* Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        double R = (double) size / items.length;
        if (size >= 16 && R < 0.25) {
            capacity *= ((double) 1 / RFACTOR);
            resize(capacity, capacity * RFACTOR);
        }
        T x = items[first];
        items[first] = null;
        nextFirst = first;
        first = (first + 1) % capacity;
        size--;
        return x;
    }

    /* Removes and returns the item at the back of the deque. If no such item exists, returns null */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        double R = size / items.length;
        if (R < 0.25) {
            capacity *= (1 / RFACTOR);
            resize(capacity, capacity * RFACTOR);
        }
        T x = items[last];
        items[last] = null;
        nextLast = last;
        last = (last - 1) % capacity;
        if (last < 0) {
            last += capacity;
        }
        size--;
        return x;
    }

    /* Gets the item at the given index */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(index + first) % capacity];
    }
}