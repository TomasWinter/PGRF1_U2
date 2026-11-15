package model;

public class Edge {
    private int x1, y1, x2, y2;

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Edge(Point2D p1, Point2D p2) {
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
    }

    public boolean isHorizontal()
    {
        return y1 == y2;
    }

    // vrací relativní pozici bodu k úsečce
    // -1 = vlevo, 1 = vpravo
    public int getRelativePos(int x, int y)
    {
        Vector2 tVector = new Vector2(x2 - x1, y2 - y1);
        Vector2 normalToT = tVector.getNormal();
        Vector2 vectorToPoint = new  Vector2((x - x1), (y - y1));

        Vector2 normalToTNormalized = normalToT.getNormalized();
        Vector2 vectorToPointNormalized = vectorToPoint.getNormalized();

        float scalar = normalToTNormalized.getX() * vectorToPointNormalized.getX() + normalToTNormalized.getY() * vectorToPointNormalized.getY();

        if (scalar > 0)
            return -1;
        else if (scalar < 0)
            return 1;
        else
            return 0;
    }

    // Metoda na srovnání souřadnic aby x1 a y1 byly nejmenšími body
    public void orientate()
    {
        if (y1 <= y2) return;
        int temp = y1;
        y1 = y2;
        y2 = temp;

        temp = x1;
        x1 = x2;
        x2 = temp;
    }

    // vrací prušečík na ose y
    public double getInterceptionY(int y)
    {
        double k = (double)(x2 - x1) / (y2 - y1);
        double q = -k * y1 + x1;

        return (k * y) + q;
    }

    // vrací prušečík mezi čárama
    public Point2D getInterceptionLine(Point2D start, Point2D end)
    {
        double k1 = (double)(y2 - y1) / (x2 - x1);
        double q1 = -k1 * x1 + y1;

        int xl1 = start.getX();
        int yl1 = start.getY();
        int xl2 = end.getX();
        int yl2 = end.getY();
        double k2 = (double)(yl2 - yl1) / (xl2 - xl1);
        double q2 = -k2 * xl1 + yl1;

        double x = (q2 - q1) / (k1 - k2);
        double y = k1 * x + q1;

        return new Point2D((int)x,(int)y);
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}
