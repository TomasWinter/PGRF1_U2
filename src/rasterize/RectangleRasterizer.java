package rasterize;

import model.Rectangle;
import raster.Raster;

public class RectangleRasterizer {
    int lineColor = 0xffffff;
    Raster raster;

    public RectangleRasterizer(Raster raster) {
        this.raster = raster;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public void rasterize(Rectangle rect)
    {
        if (rect == null) return;

        int xMin = rect.getX1();
        int xMax = rect.getX2();
        int yMin = rect.getY1();
        int yMax = rect.getY2();

        if (xMin > xMax)
        {
            int temp = xMin;
            xMin = xMax;
            xMax = temp;
        }
        if (yMin > yMax)
        {
            int temp = yMin;
            yMin = yMax;
            yMax = temp;
        }

        for (int y = yMin; y <= yMax; y++)
        {
            raster.setPixel(xMin,y,lineColor);
            raster.setPixel(xMax,y,lineColor);
        }
        for (int x = xMin; x <= xMax; x++)
        {
            raster.setPixel(x,yMin,lineColor);
            raster.setPixel(x,yMax,lineColor);
        }
    }
}
