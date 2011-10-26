package space;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public abstract class Space extends  JFrame implements  KeyListener, SpaceFrame {

    
    private static final long serialVersionUID = 1532817796535372081L;

    private double seconds = 1;
    protected boolean isBreakout;

    private static List<PhysicalObject> objects = new ArrayList<PhysicalObject>();
    private double centrex = 0.0;
    private double centrey = 0.0;
    private double scale = 10;

    private static boolean showWake = false;
    private static int step = 0;
    private static int frameRate = 25;

    static int nrOfObjects = 75;
    public static void add(PhysicalObject physicalObject) {
        objects.add(physicalObject);
    }

    public static void startSpace(final Space space) throws InterruptedException, InvocationTargetException {
        space.setVisible(true);
        while (true) {
            final long start = System.currentTimeMillis();
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    space.collide();
                    space.step();
                }
            });
            try {
                long ahead = 1000 / frameRate - (System.currentTimeMillis() - start);
                if (ahead > 50) {
                    Thread.sleep(ahead);
                    if(frameRate<25) frameRate++;
                } else {
                    Thread.sleep(50);
                    frameRate--;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    protected Space(boolean isBreakout) {
        this.isBreakout = isBreakout;
    }

    @Override
    public void paint(Graphics original) {
        if (original != null) {
            BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = buffer.createGraphics();

            if (!showWake) {
                graphics.clearRect(0, 0, getWidth(), getHeight());
            }
            for (PhysicalObject po : objects) {
                po.paintPhysicalObject(graphics);
                String string = "Objects:" + objects.size() + " scale:" + getScale() + " steps:" + step + " frame rate: " + frameRate;
                setTitle(string);
            }
            original.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
        }

    }

    @Override
    public Color weightToColor(double weight) {
        if (weight < 1e10) return Color.GREEN;
        if (weight < 1e12) return Color.CYAN;
        if (weight < 1e14) return Color.MAGENTA;
        if (weight < 1e16) return Color.BLUE;
        if (weight < 1e18) return Color.GRAY;
        if (weight < 1e20) return Color.RED;
        if (weight < 1e22) return Color.ORANGE;
        if (weight < 1e25) return Color.PINK;
        if (weight < 1e28) return Color.YELLOW;
        return Color.WHITE;
    }


    public void setStepSize(double seconds) {
        this.seconds = seconds;
    }

    
    
    public void step() {
        for (PhysicalObject o : objects) {
            o.update(objects, getSeconds());
        }
        step++;
        paint(getGraphics());

    }

    private void collide() {
        List<PhysicalObject> remove = new ArrayList<PhysicalObject>();
        for (PhysicalObject one : objects) {
            if (remove.contains(one))
                continue;
            for (PhysicalObject other : objects) {
                if (one == other || remove.contains(other))
                    continue;
                PhysicalObject toRemove = one.handleCollision(other);
                remove.add(toRemove);
            }
            // Wall collision reverses speed in that direction
            PhysicalObject toRemove = one.collideWithWalls();
            remove.add(toRemove);
        }
        objects.removeAll(remove);
    }

    public abstract void mouseWheelMoved(final MouseWheelEvent e);
    public abstract void mouseDragged(final MouseEvent e);



    public void keyPressed(KeyEvent e) {
    }


    public void keyReleased(KeyEvent e) {
    }


    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'w')
            showWake = !showWake;
    }

    @Override
    public double getSeconds() {
        return seconds;
    }

    protected void setCentrex(double centrex) {
        this.centrex = centrex;
    }

    protected double getCentrex() {
        return centrex;
    }

    protected void setCentrey(double centrey) {
        this.centrey = centrey;
    }

    protected double getCentrey() {
        return centrey;
    }

    protected void setScale(double scale) {
        this.scale = scale;
    }

    protected double getScale() {
        return scale;
    }




}
