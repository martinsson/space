package space;

public interface SpaceFrame {
    public static final double EARTH_WEIGHT = 5.9736e24;
    public static final double G = 6.67428e-11; // m3/kgs2

    double newXpos(double x);

    double newYpos(double y);

    double getSeconds();

}