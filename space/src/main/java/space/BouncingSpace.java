package space;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class BouncingSpace extends Space {

    private static final long serialVersionUID = 1L;

    public BouncingSpace(boolean isBreakout) {
        super(isBreakout);
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
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
