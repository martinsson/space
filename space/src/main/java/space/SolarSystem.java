package space;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class SolarSystem extends Space implements MouseWheelListener, MouseMotionListener{

    private static final long serialVersionUID = 1L;
    public SolarSystem() {
        super(AppConfiguration.IS_BREAKOUT);
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        setScale(getScale() + getScale() * (Math.min(9, e.getWheelRotation())) / 10 + 0.0001);
        getGraphics().clearRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        if (lastDrag == null) {
            lastDrag = e.getPoint();
        }
        setCentrex(getCentrex() - ((e.getX() - lastDrag.x) * getScale()));
        setCentrey(getCentrey() - ((e.getY() - lastDrag.y) * getScale()));
        lastDrag = e.getPoint();
        getGraphics().clearRect(0, 0, getWidth(), getHeight());
    }
    protected static Point lastDrag = null;
    public void mouseMoved(MouseEvent e) {
        lastDrag = null;
    }

    @Override
    public double newYpos(double y) {
        return (y - getCentrey()) / getScale() + getSize().height / 2;
    }

    @Override
    public double newXpos(double x) {
        return (x - getCentrex()) / getScale() + getSize().width / 2;
    }

}
