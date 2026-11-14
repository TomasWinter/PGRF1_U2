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

    public int getInterceptionY(int y)
    {
        double k = (double)(end.getX() - start.getX()) / (end.getY() - start.getY());
        double q = -k * start.getY() + start.getX();

        return (int)Math.round((k * y) + q);
    }

    public int getInterceptionX(int x)
    {
        double k = (double)(end.getY() - start.getY()) / (end.getX() - start.getX());
        double q = -k * start.getX() + start.getY();

        return (int)Math.round((k * x) + q);
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
