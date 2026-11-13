package fill;

import model.Point2D;
import raster.Raster;
import raster.RasterBufferedImage;

public class SeedFiller implements Filler {

    private RasterBufferedImage filledRaster;
    private Raster raster;
    private Point2D seed;
    private int backgroundColor = 0x000000;
    private int fillColor = 0xffff00;

    public SeedFiller(Raster raster) {
        this.raster = raster;
        this.filledRaster = new RasterBufferedImage(raster.getWidth(),raster.getHeight());
    }

    @Override
    public void fill() {
        if (seed == null) return;
        backgroundColor = raster.getPixel(seed.getX(), seed.getY());
        fill(seed.getX(), seed.getY());
    }

    void fill(int x, int y) {
        if (x >= raster.getWidth() || x < 0) return ;
        if (y >= raster.getHeight() || y < 0) return ;
        if (raster.getPixel(x, y) != backgroundColor) return;

        raster.setPixel(x, y, fillColor);
        filledRaster.setPixel(x,y,fillColor);

        fill(x+1,y);
        fill(x,y+1);
        fill(x,y-1);
        fill(x-1,y);
    }

    @Override
    public void setFillColor(int color) {
        fillColor = color;
    }

    public void setSeed(Point2D seed) {
        this.seed = seed;
    }

    public void clearFill()
    {
        filledRaster.clear();
    }

    public void paint() {
        for (int x = 0; x < raster.getWidth(); x++)
        {
            for (int y = 0; y < raster.getHeight(); y++)
            {
                raster.setPixel(x,y,filledRaster.getPixel(x,y));
            }
        }
    }
}
