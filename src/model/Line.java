package model;

//Třída pro ukládaní čar
public class Line {
    Point2D start;
    Point2D end;

    public Point2D getStart() {
        return start;
    }

    public void setStart(int x, int y) {
        if (start == null) start = new Point2D(x, y);
        else start.set(x, y);
    }

    public Point2D getEnd() {
        return end;
    }

    public void setEnd(int x, int y) {
        if (end == null) end = new Point2D(x, y);
        else end.set(x, y);
    }

    public int getRelativePos(int x, int y)
    {
        Vector2 tVector = new Vector2(end.getX() - start.getX(), end.getY() - start.getY());
        Vector2 normalToT = tVector.getNormal();
        Vector2 vectorToPoint = new  Vector2((x - start.getX()), (y - start.getY()));

        Vector2 normalToTNormalized = normalToT.getNormalized();
        Vector2 vectorToPointNormalized = vectorToPoint.getNormalized();

        float skalar =normalToTNormalized.getX() * vectorToPointNormalized.getX() + normalToTNormalized.getY() * vectorToPointNormalized.getY();

        System.out.println("skalar = " + skalar);

        if (skalar > 0)
            return -1;
        else if (skalar < 0)
            return 1;
        else
            return 0;
    }


    // Metoda vrátí konec úsečky který je nejblíž vstupnímu bodu
    public Point2D getClosestPoint(int x, int y) {
        double magnitudeToStart = Point2D.getMagnitudeBetween(x, y, start.getX(), start.getY());
        double magnitudeToEnd = Point2D.getMagnitudeBetween(x, y, end.getX(), end.getY());

        if (magnitudeToEnd <= magnitudeToStart)
            return end;
        else
            return start;
    }
}
