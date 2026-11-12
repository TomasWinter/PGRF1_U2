package model;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 getNormal()
    {
        return new Vector2(y,-x);
    }

    public Vector2 getNormalized()
    {
        float magnitude = getMagnitude();
        return new Vector2(x/magnitude,y/magnitude);
    }

    public float getMagnitude()
    {
        return (float)Math.sqrt(x*x + y*y);
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
