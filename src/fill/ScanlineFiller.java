package fill;

import model.Edge;
import model.Point2D;
import model.Polygon;
import raster.Raster;
import sort.SelectionSort;

import java.util.ArrayList;
import java.util.List;

// třída pro implementaci scanline algorytmu
public class ScanlineFiller implements Filler, PatternFill {
    private Pattern pattern = null;
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
            Point2D pointB = polygon.getPoint((i + 1) % polygon.getSize());

            // překonvertování bodů na hrany
            Edge edge = new Edge(pointA, pointB);
            if (edge.isHorizontal()) continue; // ignorování horizontálních stran
            edge.orientate(); // srovnání hrany, aby zařatek byl první

            // najití nejnižšího a nejvyššího bodu
            if (edge.getY2() > yMax) yMax = edge.getY2();
            if (edge.getY1() < yMin) yMin = edge.getY1();

            edges.add(edge);
        }

        // seznam průsečíků hran
        List<Double> intersections = new ArrayList<>();

        for (int y = yMin; y < yMax; y++) {
            intersections.clear();
            for (Edge edge : edges) { // detekce průsečíků na každém Y
                if (y < edge.getY1() || y >= edge.getY2()) continue;

                double x = edge.getInterceptionY(y);
                intersections.add(x);
            }

            SelectionSort.sort(intersections); // vlastní implementace selection sort
            if (intersections.size() < 2) continue;

            // mezi každou dvojicí průsečíků se vybarví plocha
            for (int i = 0; i + 1 < intersections.size(); i += 2) {
                int startX = (int) Math.floor(intersections.get(i));
                int endX = (int) Math.ceil(intersections.get(i + 1));

                for (int x = startX; x < endX; x++)
                    setPixel(x, y);
            }
        }
    }

    private void setPixel(int x, int y) {
        int color = fillColor;

        if (pattern != null)
            color = pattern.getPixel(x, y);
        raster.setPixel(x, y, color);
    }

    @Override
    public void changePattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public void setFillColor(int color) {
        this.fillColor = color;
    }
}
