package fill;

import model.Edge;
import model.Point2D;
import model.Polygon;

import java.util.ArrayList;
import java.util.List;

public class ScanlineFiller implements Filler {

    private int fillColor = 0xff00ff;
    private Polygon polygon;


    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public void fill() {
        if (polygon == null) return;
        if (polygon.getSize() < 3) return;

        List<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < polygon.getSize(); i++) {
            Point2D pointA = polygon.getPoint(i);
            Point2D pointB = polygon.getPoint((i+1)%polygon.getSize());

            Edge edge = new Edge(pointA, pointB);
            if (edge.isHorizontal()) continue;
            edge.orientate();
            edges.add(edge);
        }

        // TODO: Najit min max x a y
        int yMin = 0;
        int yMax = 600;
        for (int y = 0; y <= yMax; y++ ) {
            // TODO: pro danou scanline, existuje prusecik s jakoukoliv hranou
        }
    }

    @Override
    public void setFillColor(int color) {
        this.fillColor = color;
    }
}
