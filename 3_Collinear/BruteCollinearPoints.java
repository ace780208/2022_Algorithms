import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
    
        if (points == null) throw new IllegalArgumentException("no point in the input");
              
        for (int i = 0; i < points.length; i++)
            {
                if (points[i] == null) throw new IllegalArgumentException("point is null");
            }
        // avoid mutate input points
        Point[] tmppoints = points.clone();
        Arrays.sort(tmppoints);     
        for (int i = 1; i < tmppoints.length; i++)
        {
            if (tmppoints[i].compareTo(tmppoints[i-1]) == 0) throw new IllegalArgumentException("found duplicate points");
        }
        
        if (tmppoints.length < 4) return; 
        for (int i = 0; i < tmppoints.length - 3; i++)
        {
            Point p = tmppoints[i];
            for (int j = i + 1; j < tmppoints.length - 2; j++)
            {
                Point q = tmppoints[j];
                for (int k = j + 1; k < points.length - 1; k++)
                {
                    Point r = tmppoints[k];
                    for (int m = k + 1; m < points.length; m++)
                    {
                        Point s = tmppoints[m];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s) && p.slopeTo(q) == p.slopeTo(s))
                        {
                            segments.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
    }
    public int numberOfSegments()        // the number of line segments
    {
        return segments.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
