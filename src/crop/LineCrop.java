package crop;

import model.Line;
import model.Point2D;
import model.Rectangle;

public class LineCrop {

    public static boolean CropLine(Line line, Rectangle rect)
    {
        if (line == null) return false;
        if (rect == null) return false;
        rect.orientate(); // ořezávací ploše se přiřadí nejmenší x a y na x1 a y1

        // algorytmus se opakuje (maximálně 5x), dokud úsečka není správně oříznutá
        for (int i = 0; i < 5; i++)
        {
            Point2D point1 = line.getStart();
            Point2D point2 = line.getEnd();
            byte val1 = comparePos(point1,rect);
            byte val2 = comparePos(point2,rect);

            if ((val1 | val2) == 0) return false; // úsečka leží uvnitř
            if ((val1 & val2) != 0) return true; // úsečka leží kompletně mimo

            // úsečka potřebuje být oříznutá
            setPointPos(point1, val1, line, rect);
            setPointPos(point2, val2, line, rect);
        }
        return false;
    }

    // metoda na porovnání a ohodnocení pozice bodu
    private static byte comparePos(Point2D point, Rectangle rect)
    {
        int xMin = rect.getX1();
        int xMax = rect.getX2();
        int yMin = rect.getY1();
        int yMax = rect.getY2();

        byte val = 0;
        // bod je...
        if (point.getX() < xMin) // vlevo
            val += 0b1000;
        else if (point.getX() > xMax) // vpravo
            val += 0b0100;
        if (point.getY() < yMin) // nahoře
            val += 0b0001;
        else if (point.getY() > yMax) // dole
            val += 0b0010;
        return val;
    }

    // upravení pozice bodu podle jeho hodnocení
    private static void setPointPos(Point2D point, byte val, Line line, Rectangle rect)
    {
        switch (val)
        {
            // bod se nachází...
            case 0b1001: // vlevo nahoře
            case 0b0101: // vpravo nahoře
            case 0b0001: // nahoře
                point.set(line.getInterceptionY(rect.getY1()), rect.getY1());
                break;
            case 0b1010: // vlevo dole
            case 0b0110: // vpravo dole
            case 0b0010: // dole
                point.set(line.getInterceptionY(rect.getY2()), rect.getY2());
                break;
            case 0b1000: // vlevo
                point.set(rect.getX1(), line.getInterceptionX(rect.getX1()));
                break;
            case 0b0100: // vpravo
                point.set(rect.getX2(), line.getInterceptionX(rect.getX2()));
                break;
        }
    }
}
