package crop;

import model.Edge;
import model.Point2D;
import model.Polygon;

import java.util.ArrayList;
import java.util.List;

// třída na ořezávání polygonu konvexním polygonem
public class PolygonCrop {

    public static void cropPolygon(Polygon cropVictim, Polygon cropper)
    {
        List<Edge> cropEdges = new ArrayList<>();
        boolean isCCW = cropper.isCounterClockwise(); // zjištění orientace

        // sestavení všech hran
        for (int i = 0; i < cropper.getSize(); i++) {
            Point2D pointA = cropper.getPoint(i);
            Point2D pointB = cropper.getPoint((i + 1) % cropper.getSize());

            // hrana se otočí pokud polygon není counter clockwise
            if (isCCW)
                cropEdges.add(new Edge(pointA, pointB));
            else
                cropEdges.add(new Edge(pointB, pointA));
        }

        // polygon se postupně ořízne každou hranou
        for (Edge cropEdge : cropEdges)
        {
            List<Point2D> survivors = new ArrayList<>();
            for (int i = 0; i < cropVictim.getSize(); i++) {
                Point2D start = cropVictim.getPoint(i);
                Point2D end = cropVictim.getPoint((i + 1) % cropVictim.getSize());

                // jsou body uvnitř polygonu?
                boolean startInside = cropEdge.getRelativePos(start.getX(), start.getY()) <= 0;
                boolean endInside = cropEdge.getRelativePos(end.getX(), end.getY()) <= 0;

                if (startInside && endInside){ // pokud ano, přidá se konec
                    survivors.add(new Point2D(end.getX(), end.getY()));
                }
                else if (startInside && !endInside) { // pokud je konec venku, přidá se průsečík
                    survivors.add(cropEdge.getInterceptionLine(start, end));
                }
                else if (!startInside && endInside) { // pokud je začatek venku, přidá se průsečík a konec
                    survivors.add(cropEdge.getInterceptionLine(start, end));
                    survivors.add(new Point2D(end.getX(), end.getY()));
                }
                // jinak se body ignorují
            }
            // z nového seznamu hran se stane polygon, algorytmus se zopakuje, pokud zbývají hrany
            cropVictim.setPoints(survivors);
        }
    }

}
