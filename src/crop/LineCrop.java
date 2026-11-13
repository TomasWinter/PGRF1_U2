package crop;

import model.Line;
import model.Point2D;
import model.Polygon;
import model.Rectangle;

public class LineCrop {

    public static boolean CropLine(Line line, Rectangle rect)
    {
        if (line == null) return false;
        if (rect == null) return false;

        rect.orientate();

        int xMin = rect.getX1();
        int xMax = rect.getX2();
        int yMin = rect.getY1();
        int yMax = rect.getY2();

        Point2D point1 = line.getStart();
        Point2D point2 = line.getEnd();
        byte val1 = 0;
        byte val2 = 0;

        if (point1.getX() <= xMin)
            val1 += 0b1000;
        else if (point1.getX() >= xMax)
            val1 += 0b0100;
        if (point1.getY() <= yMin)
            val1 += 0b0001;
        else if (point1.getY() >= yMax)
            val1 += 0b0010;

        if (point2.getX() <= xMin)
            val2 += 0b1000;
        else if (point2.getX() >= xMax)
            val2 += 0b0100;
        if (point2.getY() <= yMin)
            val2 += 0b0001;
        else if (point2.getY() >= yMax)
            val2 += 0b0010;

        String s1 = String.format("%8s", Integer.toBinaryString(val1 & 0xFF)).replace(' ', '0');
        String s2 = String.format("%8s", Integer.toBinaryString(val2 & 0xFF)).replace(' ', '0');
        System.out.println(s1 + "..." + s2);

        if ((val1 | val2) == 0) return false;
        if ((val1 & val2) != 0) return true;

        setPointPos(point1, val1, line, rect);
        setPointPos(point2, val2, line, rect);

        return  false;
    }

    private static void setPointPos(Point2D point, byte val, Line line, Rectangle rect)
    {
        switch (val)
        {
            // TODO: nefunkční rohy
            case 0b1001: // levý horní
                point.set(line.getInterceptionY(rect.getY1()), line.getInteraceptionX(rect.getX1()));
                break;
            case 0b0101: // pravý horní
                point.set(line.getInterceptionY(rect.getY1()), line.getInteraceptionX(rect.getX2()));
                break;
            case 0b1010: // levý dolní
                point.set(line.getInterceptionY(rect.getY2()), line.getInteraceptionX(rect.getX1()));
                break;
            case 0b0110: // pravý dolní
                point.set(line.getInterceptionY(rect.getY2()), line.getInteraceptionX(rect.getX2()));
                break;
            case 0b1000: // levý
                point.set(rect.getX1(), line.getInteraceptionX(rect.getX1()));
                break;
            case 0b0100: // pravý
                point.set(rect.getX2(), line.getInteraceptionX(rect.getX2()));
                break;
            case 0b0001: // horní
                point.set(line.getInterceptionY(rect.getY1()), rect.getY1());
                break;
            case 0b0010: // dolní
                point.set(line.getInterceptionY(rect.getY2()), rect.getY2());
                break;
        }
    }
}
