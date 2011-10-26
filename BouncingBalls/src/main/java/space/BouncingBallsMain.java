package space;

import java.lang.reflect.InvocationTargetException;

class BouncingBallsMain {
    
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        Space space;
        space = configureBouncingBalls();
        Space.startSpace(space);
    }
    private static Space configureBouncingBalls() {
        BouncingSpace space = new BouncingSpace(AppConfiguration.IS_BREAKOUT);
//        space.addMouseWheelListener(space);
//        space.addMouseMotionListener(space);
        space.addKeyListener(space);
        space.setSize(800, 820);
        Space.nrOfObjects = 50;
        space.setStepSize(1); // One second per iteration
        for (int i = 0; i < Space.nrOfObjects; i++) {
            // radius,weight in [1,20]
            double radiusAndWeight = 1 + 19 * Math.random();
            BouncingBalls physicalObject = new BouncingBalls(radiusAndWeight, 20 + 760 * Math.random(), 20 + 760 * Math.random(),
                    3 - 6 * Math.random(), 3 - 6 * Math.random(), radiusAndWeight, space);
            Space.add(physicalObject);
            //x,y in [max radius, width or height - max radius]

        }
        space.setScale(1);
        space.setCentrex(400);
        space.setCentrey(390); //Must compensate for title bar
        return space;
    }
}