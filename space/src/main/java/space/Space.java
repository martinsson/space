package space;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public abstract class Space<T extends PhysicalObject<T>> extends  JFrame implements  KeyListener, SpaceFrame {
    private static final long serialVersionUID = 1532817796535372081L;
    private double seconds = 1;
    private List<T> objects = new ArrayList<T>();
    private double centrex = 0.0;
    private double centrey = 0.0;
    private double scale = 10;

    private static boolean showWake = false;
    private static int step = 0;
    private static int frameRate = 25;

    static int nrOfObjects = 75;
    public  void add(T physicalObject) {
        objects.add(physicalObject);
    }

    public static <O extends PhysicalObject<O>> void  startSpace(final Space<O> space) throws InterruptedException, InvocationTargetException {
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

    @Override
    public void paint(Graphics original) {
        if (original != null) {
            BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = buffer.createGraphics();

            if (!showWake) {
                graphics.clearRect(0, 0, getWidth(), getHeight());
            }
            for (T po : objects) {
                po.paintPhysicalObject(graphics);
                String string = "Objects:" + objects.size() + " scale:" + getScale() + " steps:" + step + " frame rate: " + frameRate;
                setTitle(string);
            }
            original.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
        }

    }

    public void setStepSize(double seconds) {
        this.seconds = seconds;
    }
    
    public void step() {
        for (T o : objects) {
            o.update(objects, getSeconds());
        }
        step++;
        paint(getGraphics());

    }

    private void collide() {
        List<T> remove = new ArrayList<T>();
        for (T one : objects) {
            if (remove.contains(one))
                continue;
            for (T other : objects) {
                if (one == other || remove.contains(other))
                    continue;
                T toRemove = one.handleCollision(other);
                remove.add(toRemove);
            }
            // Wall collision reverses speed in that direction
            T toRemove = one.collideWithWalls();
            remove.add(toRemove);
        }
        objects.removeAll(remove);
    }

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
