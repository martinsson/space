package space;

import java.awt.Graphics2D;
import java.util.List;

public class StellarBody extends PhysicalObject{

    public StellarBody(double weightKilos, double x, double y, double vx, double vy, double radius, SpaceFrame space) {
        super(weightKilos, x, y, vx, vy, radius, space);
    }

    protected void update(List<PhysicalObject> objects, double seconds) {
        double fx = 0;
        double fy = 0;
        for (PhysicalObject oth : objects) {
            if (this == oth)
                continue;
            double[] d = new double[]{x - oth.x, y - oth.y};
            double r2 = Math.pow(d[0], 2) + Math.pow(d[1], 2);
            double f = SpaceFrame.G * mass * oth.mass / r2;
            double sqrtOfR2 = Math.sqrt(r2);
            fx += f * d[0] / sqrtOfR2;
            fy += f * d[1] / sqrtOfR2;
        }
        double ax = fx / mass;
        double ay = fy / mass;
        x = x - ax * Math.pow(seconds, 2) / 2 + vx * seconds;
        y = y - ay * Math.pow(seconds, 2) / 2 + vy * seconds;
        vx = vx - ax * seconds;
        vy = vy - ay * seconds;
    }

    @Override
    public PhysicalObject handleCollision(PhysicalObject other) {
        PhysicalObject toRemove;
        if (Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2)) < 5e9) {
            absorb(other);
            toRemove = other;
        } else 
            toRemove = null;

        return toRemove;
    }

    public StellarBody absorb(PhysicalObject other) {
        double totalMass = mass + other.mass;
        x = (x * mass + other.x * other.mass) / totalMass;
        y = (y * mass + other.y * other.mass) / totalMass;
        vx = (vx * mass + other.vx * other.mass) / totalMass;
        vy = (vy * mass + other.vy * other.mass) / totalMass;
        mass = totalMass;
        return this;
    }

    @Override
    public PhysicalObject collideWithWalls() {
        return null;
    }

    @Override
    public void paintPhysicalObject(Graphics2D graphics) {
        graphics.setColor(space.weightToColor(mass));
        int diameter = mass >= SpaceFrame.EARTH_WEIGHT * 10000 ? 7 : 2;
        int xtmp = (int) (space.newXpos(x));
        int ytmp = (int) (space.newYpos(y));
        graphics.fillOval(
                xtmp-diameter/2,
                ytmp-diameter/2,
                diameter,
                diameter);
    }
    

}
