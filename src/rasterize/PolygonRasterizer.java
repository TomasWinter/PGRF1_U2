package rasterize;

import fill.ScanlineFiller;
import model.Point2D;
import model.Polygon;

public class PolygonRasterizer {
    private final LineRasterizer lineRasterizer;
    private final ScanlineFiller filler;

    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
        this.filler = new ScanlineFiller(lineRasterizer.getRaster());
    }

    public void rasterize(Polygon polygon) {
        if (polygon == null) return;
        if (polygon.getSize() == 0) return;

        if (polygon.getSize() > 2 && polygon.isFilled()) // vyplnění polygonu pokud polygon má mít vyplnění
        {
            filler.setPolygon(polygon);
            filler.fill();
        }

        if (polygon.getSize() == 1) //vykreslení bodu, když polygon má jen jeden bod
        {
            Point2D point = polygon.getPoint(0);
            lineRasterizer.getRaster().setPixel(point.getX(), point.getY(), polygon.getEdgeColor());
            return;
        }

        int originalColor = lineRasterizer.getLineColor();
        lineRasterizer.setLineColor(polygon.getEdgeColor());
        for (int i = 0; i < polygon.getSize(); i++) {
            Point2D pointA = polygon.getPoint(i);
            Point2D pointB = polygon.getPoint((i + 1) % polygon.getSize());
            lineRasterizer.rasterize(pointA.getX(), pointA.getY(), pointB.getX(), pointB.getY());
        }
        lineRasterizer.setLineColor(originalColor);
    }
}
