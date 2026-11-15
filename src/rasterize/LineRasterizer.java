package rasterize;

import model.Line;
import raster.Raster;

public abstract class LineRasterizer {
    protected Raster raster;
    int lineColor = 0xFFFFFF;

    public LineRasterizer(Raster raster) {
        this.raster = raster;
    }

    public abstract void rasterize(int x1, int y1, int x2, int y2);

    public void rasterize(Line line) {
        if (line == null || line.getStart() == null || line.getEnd() == null) return;
        rasterize(line.getStart().getX(), line.getStart().getY(), line.getEnd().getX(), line.getEnd().getY());
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public Raster getRaster() {
        return raster;
    }
}
