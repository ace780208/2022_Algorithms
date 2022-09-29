import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null) throw new IllegalArgumentException("no point in the input");
              
        for (int i = 0; i < points.length; i++)
            {
                if (points[i] == null) throw new IllegalArgumentException("point is null");
            }
        Point[] tmppoints = points.clone();
        Arrays.sort(tmppoints);
        if (points.length > 1)
        {   
            for (int i = 1; i < tmppoints.length; i++)
            {
                if(tmppoints[i].compareTo(tmppoints[i-1]) == 0) throw new IllegalArgumentException("found duplicate points");
            }
        }

        if (points.length < 4) return;
        
        Point[] tmppoints2 = tmppoints.clone();
        for (Point p : tmppoints)
        {
            Arrays.sort(tmppoints2, p.slopeOrder());

            ArrayList<Point> collinearPoints = new ArrayList<Point>();
            collinearPoints.add(p);

            double slope = p.slopeTo(tmppoints2[1]);

            for (int j = 2; j < tmppoints2.length; j++)
            {
                double tmpSlope = p.slopeTo(tmppoints2[j]);
                if (Double.compare(slope, tmpSlope) != 0)
                {
                    if (collinearPoints.size() > 3)
                    {
                      collinearPoints.sort(null);
                      if (p.compareTo(collinearPoints.get(0)) == 0) segments.add(new LineSegment(p, collinearPoints.get(collinearPoints.size()-1)));
                    }
                    slope = tmpSlope;
                    collinearPoints = new ArrayList<Point>();
                    collinearPoints.add(p);
                } 
                else
                {
                    if (collinearPoints.size() == 1) collinearPoints.add(tmppoints2[j-1]);
                    collinearPoints.add(tmppoints2[j]);
                }
            }
            if (collinearPoints.size() > 3)
            {
                collinearPoints.sort(null);
                if (p.compareTo(collinearPoints.get(0)) == 0) segments.add(new LineSegment(p, collinearPoints.get(collinearPoints.size()-1)));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
