package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private int open = 0;
    private WeightedQuickUnionUF uf;    // structure to check for percolation
    private WeightedQuickUnionUF ufB;
    private boolean[][] grid;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be a positive, non-zero integer.");
        }
        n = N;
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufB = new WeightedQuickUnionUF(n * n + 1);
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
                if (i == 0) {
                    uf.union(n * n, xyTo1D(i, j));  // union top row and virtual top
                    ufB.union(n * n, xyTo1D(i, j));
                }
                if (i == n - 1) {
                    uf.union(n * n + 1, xyTo1D(i, j));  // union bottom row and virtual bottom
                }
            }
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!grid[row][col]) {
            open++;
            grid[row][col] = true;
        }
        unionSites(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return ufB.connected(xyTo1D(row, col), n * n) && isOpen(row, col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(n * n, n * n + 1);
    }

    private int xyTo1D(int row, int col) {
        validate(row, col);
        return row * n + col;
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException("Row or column not between 0 and " + (n - 1));
        }
    }

    private void unionSites(int row, int col) {
        if (row < n - 1 && grid[row + 1][col]) {
            uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            ufB.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (row > 0 && grid[row - 1][col]) {
            uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            ufB.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (col < n - 1 && grid[row][col + 1]) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            ufB.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        if (col > 0 && grid[row][col - 1]) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            ufB.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
    }
}
