package fill;

import java.util.Arrays;

public class Pattern {
    private final int[][] raster;
    private final int width;
    private final int height;

    public Pattern(int width, int height) {
        this.height = height;
        this.width = width;
        this.raster = new int[width][height];
    }

    public int getPixel(int x,int y)
    {
        if (x < 0 || y < 0) return 0;
        // vratí barvu pixelu pomocí modula
        return raster[x % width][y % height];
    }

    public void setPixel(int x,int y,int color)
    {
        if (x >= width | x < 0) return;
        if (y >= height | y < 0) return;

        raster[x][y] = color;
    }

    public void fillRect(int xMin,int yMin,int xMax,int yMax,int color)
    {
        if (xMax >= width | xMin < 0) return;
        if (yMax >= height | yMin < 0) return;
        // vyplní čtyřúhelník určitou barvou
        for (int x = xMin; x <= xMax; x++)
        {
            for (int y = yMin; y <= yMax; y++)
            {
                raster[x][y] = color;
            }
        }
    }

    public void fillRaster(int color)
    {
        // vyplní celý rastr
        for (int[] xs : raster) {
            Arrays.fill(xs, color);
        }
    }

    public void fillRow(int y,int color)
    {
        // vyplní řádek
        for (int[] xs : raster) {
            xs[y] = color;
        }
    }

    public void fillColumn(int x,int color)
    {
        // vyplní sloupec
        Arrays.fill(raster[x], color);
    }


    // metoda na vrácení předpřipraveného patternu
    public static Pattern getGreenFace()
    {
        Pattern pattern = new Pattern(8,8);
        int black = 1;
        pattern.fillRaster(0x31881E);
        pattern.fillRect(1,1,2,2,black);
        pattern.fillRect(5,1,6,2,black);
        pattern.fillRect(3,3,4,5,black);
        pattern.fillRect(2,4,2,6,black);
        pattern.fillRect(5,4,5,6,black);
        return pattern;
    }

    // metoda na vrácení předpřipraveného patternu
    public static Pattern getRedGuy()
    {
        Pattern pattern = new Pattern(20,20);
        int background = 0x3A0C0C;
        int body = 0xff0000;
        int visor = 0x2A89C7;
        pattern.fillRaster(background);
        pattern.fillRect(4,2,15,18,body);
        pattern.fillRect(8,16,10,18,background);
        pattern.fillRect(4,2,5,3,background);
        pattern.fillRect(14,2,15,3,background);
        pattern.fillRect(16,6,17,13,body);
        pattern.fillRect(2,6,8,8,visor);
        return pattern;
    }
}
