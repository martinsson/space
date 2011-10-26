package space;


public class BouncingSpace extends Space {

    private static final long serialVersionUID = 1L;
    private boolean isBreakout;

    public BouncingSpace(boolean isBreakout) {
        this.isBreakout = isBreakout;
    }

    public double newYpos(double y) {
        return (y - getCentrey())  + getSize().height / 2;
    }

    public double newXpos(double x) {
        return (x - getCentrex())  + getSize().width / 2;
    }

    public boolean isBreakout() {
        return isBreakout;
    }


}
