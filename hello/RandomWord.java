import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String [] args) {    
        double n = 1;
        String championWord = StdIn.readString();
        while (!StdIn.isEmpty()) {
            n++;
            String currentWord = StdIn.readString();
            boolean p = StdRandom.bernoulli(1/n);
            if (p) {
                championWord = currentWord;
            }
        }
        StdOut.println(championWord);
    }
}
