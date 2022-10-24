import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class Board {
    private final int[][] board;
    private final int n;
    private int emptyRow;
    private int emptyCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        this.board = new int[tiles.length][tiles[0].length];
        n = tiles.length;
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                this.board[row][col] = tiles[row][col];
                if (board[row][col] == 0)
                {
                    emptyRow = row;
                    emptyCol = col;
                }
            }  
        }
    }
                                           
    // string representation of this board
    public String toString()
    {
        StringBuilder boardString = new StringBuilder();
        boardString.append(String.valueOf(n) + "\n");
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                boardString.append(" " + String.valueOf(board[row][col]));
                if (col == n-1) boardString.append("\n");
            }
        }
        return boardString.toString();
    }

    // board dimension n
    public int dimension()
    {
        return n;
    }

    // number of tiles out of place
    public int hamming()
    {   
        int hamDist = 0;
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                if (board[row][col] == 0) continue;
                if (board[row][col] != row*n + col+1) hamDist++;
            }
        }
        return hamDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {   
        int mahDist = 0;
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                if (board[row][col] == 0) continue;             
                int targetCol = board[row][col] % n == 0 ? n - 1 : board[row][col] % n - 1;
                int targetRow = targetCol == n - 1 ? board[row][col] / n - 1 : board[row][col] / n;
                mahDist += (Math.abs(row - targetRow) + Math.abs(col - targetCol));
            }
        }
    
        return mahDist;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        int val = 1;
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                
                if (row == n-1 && col == n-1)
                {
                    if (board[row][col] != 0) return false;
                }
                else 
                {
                    if (board[row][col] != val) return false;
                    val++;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        if (this.n != ((Board) y).dimension()) return false;

        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                if (this.board[row][col] != ((Board) y).board[row][col]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        ArrayList<Board> neighborlist = new ArrayList<Board>();
        int[][] tmpArr = board.clone();

        if (emptyRow != 0)
        {
            int tmp = tmpArr[emptyRow-1][emptyCol];
            tmpArr[emptyRow-1][emptyCol] = 0;
            tmpArr[emptyRow][emptyCol] = tmp;
            neighborlist.add(new Board(tmpArr));
            tmpArr[emptyRow][emptyCol] = 0;
            tmpArr[emptyRow-1][emptyCol] = tmp;
        }
        if (emptyRow != n-1)
        {
            int tmp = tmpArr[emptyRow+1][emptyCol];
            tmpArr[emptyRow+1][emptyCol] = 0;
            tmpArr[emptyRow][emptyCol] = tmp;
            neighborlist.add(new Board(tmpArr));
            tmpArr[emptyRow][emptyCol] = 0;
            tmpArr[emptyRow+1][emptyCol] = tmp;
        }
        if (emptyCol != 0)
        {
            int tmp = tmpArr[emptyRow][emptyCol-1];
            tmpArr[emptyRow][emptyCol-1] = 0;
            tmpArr[emptyRow][emptyCol] = tmp;
            neighborlist.add(new Board(tmpArr));
            tmpArr[emptyRow][emptyCol-1] = tmp;
            tmpArr[emptyRow][emptyCol] = 0;
        }
        if (emptyCol != n-1)
        {
            int tmp = tmpArr[emptyRow][emptyCol+1];
            tmpArr[emptyRow][emptyCol+1] = 0;
            tmpArr[emptyRow][emptyCol] = tmp;
            neighborlist.add(new Board(tmpArr));
            tmpArr[emptyRow][emptyCol+1] = tmp;
            tmpArr[emptyRow][emptyCol] = 0;
        }
        return neighborlist;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        int[][] tmpArr = board.clone();
        int fromRow = 0;
        int fromCol = 0;
        int toRow = 0;
        int toCol = 1;
        if (tmpArr[fromRow][fromCol] == 0)
        {
            for (int row = 0; row < n; row++)
            {
                for (int col = 0; col < n; col++)
                {
                    if (tmpArr[row][col] != 0 && (row != toRow || col != toCol))
                    {
                        fromRow = row;
                        fromCol = col;
                        break;
                    }
                }
            }
        }

        if (tmpArr[toRow][toCol] == 0)
        {
            for (int row = 0; row < n; row++)
            {
                for (int col = 0; col < n; col++)
                {
                    if (tmpArr[row][col] != 0 && (row != fromRow || col != fromCol))
                    {
                        toRow = row;
                        toCol = col;
                        break;
                    }
                }
            }
        }

        int tmp = tmpArr[toRow][toCol];
        tmpArr[toRow][toCol] = tmpArr[fromRow][fromCol];
        tmpArr[fromRow][fromCol] = tmp;
        Board twinBoard = new Board(tmpArr);
        tmp = tmpArr[toRow][toCol];
        tmpArr[toRow][toCol] = tmpArr[fromRow][fromCol];
        tmpArr[fromRow][fromCol] = tmp;
        return twinBoard; 
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Board compare = new Board(tiles);
        StdOut.println(initial.toString());
        StdOut.println("Goal? " + initial.isGoal());
        StdOut.println("dimension: " + initial.dimension());
        StdOut.println("hamming distance: " + initial.hamming());
        StdOut.println("manhattan distance: " + initial.manhattan());
        
        for (Board ngbr: initial.neighbors())
        {
            StdOut.println(ngbr.toString());
            StdOut.println("equal to initial? " + ngbr.equals(initial));
        }

        StdOut.println("compare: " + compare.toString());
        StdOut.println("equal to compare? " + initial.equals(compare));
        StdOut.println("equal to self? " + initial.equals(initial));
        StdOut.println("twin: " + initial.twin());
        
    }

}
