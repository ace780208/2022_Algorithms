import java.util.Comparator;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Stack<Board> solutions;
    private boolean solvable = true;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        if (initial == null) throw new IllegalArgumentException("initial board is null");
        MinPQ<SearchNode> mainpq = new MinPQ<>(new ManhattanOrder());
        MinPQ<SearchNode> twinpq = new MinPQ<>(new ManhattanOrder());
        mainpq.insert(new SearchNode(initial, 0, null));
        Board twinBoard = initial.twin();
        twinpq.insert(new SearchNode(twinBoard, 0, null));
        solutions = new Stack<Board>();
        while (true)
        {
            SearchNode mainSearchNode = mainpq.delMin();
            SearchNode twinSearchNode = twinpq.delMin();

            if (mainSearchNode.board.isGoal())
            {
                while (mainSearchNode.previous != null)
                {
                solutions.push(mainSearchNode.board);
                mainSearchNode = mainSearchNode.previous;
                }            
                break;
            }

            if (twinSearchNode.board.isGoal())
            {
                solvable = false;
                break;
            }

            for (Board ngbr: mainSearchNode.board.neighbors())
            {
                if (mainSearchNode.previous == null)
                {
                    mainpq.insert(new SearchNode(ngbr, mainSearchNode.moves+1, mainSearchNode));
                }
                else
                {
                    if (!ngbr.equals(mainSearchNode.previous.board))
                    {
                        mainpq.insert(new SearchNode(ngbr, mainSearchNode.moves+1, mainSearchNode));
                    }
                }
                          
            }

            for (Board ngbr: twinSearchNode.board.neighbors())
            {
                if (twinSearchNode.previous == null)
                {
                    twinpq.insert(new SearchNode(ngbr, twinSearchNode.moves+1, twinSearchNode));
                }
                else
                {
                    if (!ngbr.equals(twinSearchNode.previous.board))
                    {
                        twinpq.insert(new SearchNode(ngbr, twinSearchNode.moves+1, twinSearchNode));
                    }
                }

            }
        }
        solutions.push(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {   
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        return solvable ? solutions.size()-1 : -1;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {   
        return solvable ? solutions : null;
    }

    private class SearchNode
    {
        Board board;
        int moves;
        SearchNode previous;
        int priority;

        public SearchNode(Board board, int moves, SearchNode previous)
        {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            priority = board.manhattan() + moves;
        }

    }

    private class ManhattanOrder implements Comparator<SearchNode>
    {
        @Override
        public int compare(SearchNode a, SearchNode b)
        {
            if (a.priority < b.priority) return -1;
            else if (a.priority > b.priority) return 1;
            else return 0;
        }
    }

    // test client (see below) 
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
    
        // solve the puzzle
        Solver solver = new Solver(initial);
    
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
