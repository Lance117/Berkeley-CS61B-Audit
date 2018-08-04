package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int trials;
    private Percolation grid;
    private double[] x;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be a positive, non-zero integer.");
        }
        x = new double[T];
        trials = T;
        while (T > 0) {
            grid = pf.make(N);
            while (!grid.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                grid.open(row, col);
            }
            double threshold = (double)grid.numberOfOpenSites() / (N * N);
            x[T - 1] = threshold;
            T--;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);

    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int T = 400;
        if (args.length == 1) {
            T = Integer.parseInt(args[0]);
        }
        PercolationFactory pf = new PercolationFactory();
        PercolationStats stats = new PercolationStats(20, T, pf);
        System.out.println(stats.mean());
        System.out.println(stats.stddev());
    }
}
