package crop;

import model.Edge;
import model.Point2D;
import model.Polygon;

import java.util.ArrayList;
import java.util.List;

public class PolygonCrop {

    public static void cropPolygon(Polygon cropVictim, Polygon cropper)
    {
        List<Edge> cropEdges = new ArrayList<>();
        boolean isCCW = cropper.isCounterClockwise();

        for (int i = 0; i < cropper.getSize(); i++) {
            Point2D pointA = cropper.getPoint(i);
            Point2D pointB = cropper.getPoint((i + 1) % cropper.getSize());

            if (isCCW)
                cropEdges.add(new Edge(pointA, pointB));
            else
                cropEdges.add(new Edge(pointB, pointA));
        }

        for (Edge cropEdge : cropEdges)
        {
            List<Point2D> slaughterSurvivors = new ArrayList<>();
            for (int i = 0; i < cropVictim.getSize(); i++) {
                Point2D start = cropVictim.getPoint(i);
                Point2D end = cropVictim.getPoint((i + 1) % cropVictim.getSize());

                boolean startInside = cropEdge.getRelativePos(start.getX(), start.getY()) <= 0;
                boolean endInside = cropEdge.getRelativePos(end.getX(), end.getY()) <= 0;

                if (startInside && endInside){
                    slaughterSurvivors.add(new Point2D(end.getX(), end.getY()));
                }
                else if (startInside && !endInside) {
                    slaughterSurvivors.add(cropEdge.getInterceptionLine(start, end));
                }
                else if (!startInside && endInside) {
                    slaughterSurvivors.add(cropEdge.getInterceptionLine(start, end));
                    slaughterSurvivors.add(new Point2D(end.getX(), end.getY()));
                }
            }
            cropVictim.setPoints(slaughterSurvivors);
        }
    }

}
