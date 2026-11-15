package fill;

import model.Point2D;
import raster.Raster;
import raster.RasterBufferedImage;

import java.util.Stack;

public class SeedFiller implements Filler, PatternFill {
    private static final boolean USE_RECURSIVE = false;

    private Pattern pattern = null;
    private final RasterBufferedImage filledRaster;
    private final Raster raster;
    private Point2D seed;
    private final int backgroundColor = -16777216;
    private int fillColor = 0xFFFF00;

    public SeedFiller(Raster raster) {
        this.raster = raster;
        this.filledRaster = new RasterBufferedImage(raster.getWidth(),raster.getHeight());
    }

    @Override
    public void fill() {
        if (seed == null) return;

        if (USE_RECURSIVE) { // rekurzivní implementace
            try {
                fill_recursive(seed.getX(), seed.getY());
            } catch (StackOverflowError e) { // zachycení stackOverflow
                e.printStackTrace();
            }
        }
        else { // stack implementace
            fill_stack(seed);
        }
    }

    // seedfill využívající stack
    void fill_stack(Point2D seed)
    {
        Stack<Point2D> stack = new Stack<>();
        stack.push(seed);
        while (!stack.empty())
        {
            Point2D currentPoint = stack.pop();
            int x = currentPoint.getX();
            int y = currentPoint.getY();

            if (x >= raster.getWidth() || x < 0) continue;
            if (y >= raster.getHeight() || y < 0) continue;
            if (raster.getPixel(x, y) != backgroundColor) continue;

            setPixel(x,y);

            stack.push(new Point2D(x+1,y));
            stack.push(new Point2D(x,y+1));
            stack.push(new Point2D(x,y-1));
            stack.push(new Point2D(x-1,y));
        }
    }

    // rekurzivní verze seedfillu (zúůsobuje stackOverflow)
    void fill_recursive(int x, int y) throws StackOverflowError {
        if (x >= raster.getWidth() || x < 0) return ;
        if (y >= raster.getHeight() || y < 0) return ;
        if (raster.getPixel(x, y) != backgroundColor) return;

        setPixel(x,y);

        fill_recursive(x+1,y);
        fill_recursive(x,y+1);
        fill_recursive(x,y-1);
        fill_recursive(x-1,y);
    }

    private void setPixel(int x, int y)
    {
        int color = fillColor;

        // získání pixelu patternu
        if (pattern != null)
            color = pattern.getPixel(x,y);

        raster.setPixel(x, y, color); // vykreslení pixelu
        filledRaster.setPixel(x,y,color); // uložení pixelu
    }

    @Override
    public void changePattern(Pattern pattern) {
        this.pattern = pattern;
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

    // vyrenderování uloženého rastru
    public void paint() {
        for (int x = 0; x < raster.getWidth(); x++)
        {
            for (int y = 0; y < raster.getHeight(); y++)
            {
                int color = filledRaster.getPixel(x,y);
                if (color == backgroundColor) continue;
                raster.setPixel(x,y,color);
            }
        }
    }
}
