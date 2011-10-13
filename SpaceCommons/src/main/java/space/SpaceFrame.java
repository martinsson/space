package space;

import java.awt.Color;

public interface SpaceFrame {

    public static final double EARTH_WEIGHT = 5.9736e24;

    double newXposBreakout(double x);

    double newYposBreakout(double y);

    double newXpos(double x);

    double newYpos(double y);

    double getSeconds();

    boolean isBreakout();

    boolean isBouncingBalls();

    Color weightToColor(double weight);

}