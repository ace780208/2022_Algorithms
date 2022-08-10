import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    final int vtop;
    final int vbottom;
    private WeightedQuickUnionUF wufGrid;
    private int gridSize;
    private int opensites;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should > 0");
        }
        gridSize = n;
        grid = new boolean[gridSize][gridSize];
        vtop = gridSize * gridSize;
        vbottom = vtop + 1;
        wufGrid = new WeightedQuickUnionUF(gridSize*gridSize+2);
        opensites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!indexCheck(row, col)) {
            indexError();
        }
        
        if (isOpen(row, col)) {
            return;
        }
        
        grid[row-1][col-1] = true;
        opensites++;

        if (row == 1) {
            wufGrid.union(vtop, gridSize*(row-1)+(col-1));
        }
        if (row == gridSize) {
            wufGrid.union(vbottom, gridSize*(row-1)+(col-1));
        }

        // check top grid
        if (indexCheck(row-1, col) && isOpen(row-1, col)) {
            wufGrid.union(gridSize*(row-1)+(col-1), gridSize*(row-2)+(col-1));
        }

        // check bottom grid
        if (indexCheck(row+1, col) && isOpen(row+1, col)) {
            wufGrid.union(gridSize*(row-1)+(col-1), gridSize*row+(col-1));
        }

        // check left grid
        if (indexCheck(row, col-1) && isOpen(row, col-1)) {
            wufGrid.union(gridSize*(row-1)+(col-1), gridSize*(row-1)+(col-2));
        }

        // check right grid
        if (indexCheck(row, col+1) && isOpen(row, col+1)) {
            wufGrid.union(gridSize*(row-1)+(col-1), gridSize*(row-1)+col);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!indexCheck(row, col)) {
            indexError();
        }
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return wufGrid.find(gridSize*(row-1)+(col-1)) == wufGrid.find(vtop);
    }

    // return the number of open sites
    public int numberOfOpenSites() {
        return opensites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wufGrid.find(vtop) == wufGrid.find(vbottom);
    }

    private boolean indexCheck(int row, int col) {
        return (row > 0 && row <= gridSize && col > 0 && col <= gridSize);
        
    }

    private void indexError() {
        throw new IllegalArgumentException("index is out of the range of gridSize");
    }

    // test client (optional)
    public static void main(String[] args) {
        // Percolation are used for PercolationStats  
    }

}