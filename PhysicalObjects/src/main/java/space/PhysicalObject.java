package space;

import java.awt.Graphics2D;
import java.util.List;

public abstract class PhysicalObject<T extends PhysicalObject<? super T>> {

    protected double mass;
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected final double radius;
    protected final SpaceFrame space;

    public PhysicalObject(double weightKilos, double x, double y, double vx,
                          double vy, double radius, SpaceFrame space) {
        this.mass = weightKilos;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.space = space;
    }


    @Override
    public String toString() {
        return "x=" + x + ",y=" + y + ",vx=" + vx + ",vy=" + vy + ",mass="
                + mass + ",radius=" + radius;
    }

    public abstract void paint(Graphics2D graphics);

    protected abstract void update(List<T> objects, double seconds);

    public abstract T handleCollision(T other);


    public abstract T collideWithWalls();
}
