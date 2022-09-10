import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int itemsize;
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        itemsize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return itemsize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return itemsize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (checknull(item)) {
            throw new IllegalArgumentException("input shoud not be null");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;
              
        if (itemsize == 0) {
            last = first;
        }
        else {        
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        itemsize++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (checknull(item)) {
            throw new IllegalArgumentException("input shoud not be null");
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;       
        last.next = null;
        
        if (itemsize == 0) {
            first = last;
        }
        else {
            last.previous = oldlast;
            oldlast.next = last;
        }
        itemsize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("no element in the queue");
        }
        Item item = first.item;
        itemsize--;
        if (itemsize == 0) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.previous = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("no element in the queue");
        }
        Item item = last.item;
        itemsize--;
        if (itemsize == 0) {
            last = null;
            first = null;
        } else {
            last = last.previous;
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null;   }
        public void remove() { throw new UnsupportedOperationException();    }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("no element in the queue");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }   

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private boolean checknull(Item item) {
        return item == null;
    }

    // unit testing (required)
    public static void main(String[] args) {
        
        Deque<Integer> testDeq = new Deque<Integer>();
        StdOut.println("Is this test Deque Empty: " + testDeq.isEmpty());
        StdOut.println("Add test integer in test Deque...");
        for (int i = 0; i < 11; i++) {
            if ((i*i+1) % 5 == 1) {
                testDeq.addFirst(i*i+1);
            } else {
                testDeq.addLast(i*i+1);
            }
        }
        StdOut.println("Is this test Deque Empty: " + testDeq.isEmpty());
        
        StdOut.println("The element in the test Deque are: ");
        Iterator<Integer> i = testDeq.iterator();
        while (i.hasNext()) {
            int tmpi = i.next();
            StdOut.print(tmpi + "\t");
        }

        StdOut.println("\nTest removeFirst and the result is: " + testDeq.removeFirst());
        StdOut.println("Test removeLast and the result is: " + testDeq.removeLast());
        StdOut.println("Test Size and the result is: " + testDeq.size());
    }

}