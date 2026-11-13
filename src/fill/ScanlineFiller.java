package fill;

import model.Edge;
import model.Point2D;
import model.Polygon;
import raster.Raster;

import java.util.ArrayList;
import java.util.List;

public class ScanlineFiller implements Filler {

    private int fillColor = 0x00A0A0;
    private Polygon polygon;
    private final Raster raster;

    public ScanlineFiller(Raster raster) {
        this.raster = raster;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public void fill() {
        if (polygon == null) return;
        if (polygon.getSize() < 3) return;

        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        List<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < polygon.getSize(); i++) {
            Point2D pointA = polygon.getPoint(i);
            Point2D pointB = polygon.getPoint((i+1)%polygon.getSize());

            Edge edge = new Edge(pointA, pointB);
            if (edge.isHorizontal()) continue;
            edge.orientate();

            if (edge.getY2() > yMax) yMax = edge.getY2();
            if (edge.getY1() < yMin) yMin = edge.getY1();

            edges.add(edge);
        }

        List<Double> intersections = new ArrayList<>();

        for (int y = 0; y < yMax; y++ ) {
            intersections.clear();
            for(Edge edge : edges){
                if(y < edge.getY1() || y >= edge.getY2()) continue;

                double x = edge.getInterceptionY(y);
                intersections.add(x);
            }

            intersections.sort(null);
            if (intersections.size() < 2) continue;

            boolean fillIn = true;
            int nextIndex = 1;
            for (int x = (int)Math.floor(intersections.getFirst()); x <= (int)Math.ceil(intersections.getLast()); x++)
            {
                if (Math.ceil(intersections.get(nextIndex)) == x)
                {
                    nextIndex++;
                    fillIn = !fillIn;
                }
                if (fillIn)
                    raster.setPixel(x,y, fillColor);
            }
        }
    }

    @Override
    public void setFillColor(int color) {
        this.fillColor = color;
    }
}
