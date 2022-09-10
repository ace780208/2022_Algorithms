import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (checknull(item)) {
            throw new IllegalArgumentException("input shoud not be null");
        }       
        if (n == q.length) {
            resize(2*q.length);
        }
        n++;
        q[n-1] = item;
   
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("no element in the queue");
        }
        if (n > 0 && n == q.length/4) {
            resize(q.length/2);
        }
        int randIdx = StdRandom.uniformInt(n);
        Item tmpItem = q[randIdx];
        q[randIdx] = q[n-1];
        q[n-1] = null;
        n--;
        return tmpItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {        
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("no element in the queue");
        }
        Item sampleItem = q[StdRandom.uniformInt(n)];
        return sampleItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new RandomIterator(); }

    private class RandomIterator implements Iterator<Item> {

        private int[] idx;
        private int counter;

        RandomIterator() {
            idx = StdRandom.permutation(n);
            counter = n-1;
        }

        public boolean hasNext() { return counter >= 0;  }
        public void remove() {
            throw new UnsupportedOperationException("not support remove in iterator");
        }
        public Item next() {                
            if (counter < 0) {
                    throw new java.util.NoSuchElementException("no element in the queue");
            }
            return q[idx[counter--]];
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    private boolean checknull(Item item) {
        return item == null;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> testRQ = new RandomizedQueue<Integer>();
        StdOut.println("Is this test queue Empty: " + testRQ.isEmpty());
        StdOut.println("Add test integer in test queue...");
        for (int i = 0; i < 11; i++) {
            testRQ.enqueue(i*i+1);
        }
        StdOut.println("Is this test queue Empty: " + testRQ.isEmpty());

        StdOut.println("The dequeue element: " + testRQ.dequeue()); 

        StdOut.println("The random sample: " + testRQ.sample());
        
        StdOut.println("The element in the random queue are: ");
        Iterator<Integer> i = testRQ.iterator();
        while (i.hasNext()) {
            int tmpi = i.next();
            StdOut.print(tmpi + "\t");
        }

        StdOut.println("\nTest Size and the result is: " + testRQ.size());
    }
}