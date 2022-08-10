import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
    private double [] samples;
    private int sampleNum;
    private double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trial <= 0");
        }

        samples = new double [trials];
        sampleNum = trials;
        for (int trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            int[] perm = StdRandom.permutation(n*n);

            for (int i = 0; i < perm.length; i++) {
                int val = perm[i];
                int tmpRow = val / n + 1;
                int tmpCol = val % n + 1;
                if ((!perc.isOpen(tmpRow, tmpCol))) {
                    perc.open(tmpRow, tmpCol);
                }                
                
                if (perc.percolates()) {
                    samples[trial] = (double) perc.numberOfOpenSites() / (double) (n * n);
                    break;
                }
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(samples);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(samples);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(samples) - CONFIDENCE_95 * StdStats.stddev(samples) / Math.sqrt(sampleNum);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(samples) + CONFIDENCE_95 * StdStats.stddev(samples) / Math.sqrt(sampleNum);
    }

   // test client (see below)
   public static void main(String[] args) {
    if (args.length != 2) {
        return;
    }
    int n = Integer.parseInt(args[0]);
    int trialNum = Integer.parseInt(args[1]);

    PercolationStats stats = new PercolationStats(n, trialNum);
    StdOut.println("mean =                      " + stats.mean());
    StdOut.println("stddev =                    " + stats.stddev());
    StdOut.println("95/% confidence interval = [" + stats.confidenceLo() + " , " + stats.confidenceHi() + "]");

   }

}