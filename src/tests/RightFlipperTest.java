package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import physics.LineSegment;
import physics.Vect;
import pingball.*;

public class RightFlipperTest {
    /**
     * 
     * RightFlipper():
     *       x,y: has to be <20 and >0
     *       Orientation: 0, 90,180,270
     * action(List<Ball> listOfBall): trigger once, trigger more than once;
     *      x,y : ha to be <20 and >0
     *      Orientation: 0, 90, 180, 270
     *      List<Ball> listOfBall = no balls, ball(s) in the center while flipping, ball(s) on the edge, no bals near flipper
     *      Flipper: orientations, rotated, not rotated;
     *trigger(List<Balls> listOfBall):
     *      List<Ball> : empty, 1, >1;
     *      toTriggers: empty, 1 gadget, mulitple gadgets
     *      Flipper: orientations, rotated, not rotated;
     *reflect(Ball ball):
     *      ball at end points, ball in segment
     *      Flipper: orientations, rotated, not rotated;
     *timeTilCollision(Ball ball):
     *      ball heading toward linesegment, end points
     *      ball not heading toward linesegment, endpoints
     *      ball velocity positive/negative, at angle
     *      Flipper: orientations, rotated, not rotated;
     * 
     * 
     */
    private static List<Ball> emptyBalls;
    private static List<Gadget> emptyGadget;
    @BeforeClass
    public static void setUpBeforeClass(){
        emptyBalls = new ArrayList<Ball>();
        emptyGadget = new ArrayList<Gadget>();
    }
    //////////TEST ROTATION////////////////

    @Test
    public void testRightFlipper0(){
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate0,emptyGadget);
        assertTrue(new LineSegment(7,5,7,7).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipper90(){
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate90,emptyGadget);
       assertTrue(new LineSegment(5,7,7,7).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipper180(){
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate180,emptyGadget);
        assertTrue(new LineSegment(5,7, 5,5).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipper270(){
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate270,emptyGadget);
        assertTrue(new LineSegment(5,5, 7,5).equals(flipper.getLineSegment()));
    }
    //////////TEST TRIGGER////////////////

    @Test
    public void testRightFlipper0Triggered(){
        RightFlipper flipper = new RightFlipper("flipper1",5,5, Orientation.rotate0,emptyGadget);
        flipper.action(emptyBalls);
        assertTrue(new LineSegment(7,5,5,5).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipper90Triggered(){
        RightFlipper flipper = new RightFlipper("flipper1",5,5, Orientation.rotate90,emptyGadget);
        flipper.action(emptyBalls);
        assertTrue(new LineSegment(7,7,7,5).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipper180Triggered(){
        RightFlipper flipper = new RightFlipper("flipper1",5,5,Orientation.rotate180,emptyGadget);
        flipper.action(emptyBalls);
        assertTrue(new LineSegment(5,7,7,7).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipper270Triggered(){
        RightFlipper flipper = new RightFlipper("flipper1",5,5,Orientation.rotate270,emptyGadget);
        flipper.action(emptyBalls);
        assertTrue(new LineSegment(5,7,5,5).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipperTriggerTwice0(){
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate0,emptyGadget);
        flipper.action(emptyBalls);
        flipper.action(emptyBalls);
        assertTrue(new LineSegment(7,5,7,7).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipperTriggerTwice90(){
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate90,emptyGadget);
        flipper.action(emptyBalls);
        flipper.action(emptyBalls);
        assertTrue(new LineSegment(5,7,7,7).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipperTriggerTwice180(){
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate180,emptyGadget);
        flipper.action(emptyBalls);
        flipper.action(emptyBalls);
        assertTrue(new LineSegment(5,5, 5,7).equals(flipper.getLineSegment()));
    }
    @Test
    public void testRightFlipperTriggerTwice270(){
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate270,emptyGadget);
        flipper.action(emptyBalls);
        flipper.action(emptyBalls);
        assertTrue(new LineSegment(5,5, 7,5).equals(flipper.getLineSegment()));
    }
    //////////TEST ACTION////////////////

    @Test
    public void testRightFlipperActionOneBall(){
        Ball ball = new Ball("center", 6,6, 0, 0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball));
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate0,emptyGadget);
        flipper.action(ballInCenter);
        assertTrue(new LineSegment(7,5,5,5).equals(flipper.getLineSegment()));
        assertEquals(5,flipper.getX());
        assertEquals(5,flipper.getY());
        assertTrue(ball.getVelocity().x() >0 );
        assertTrue(ball.getSpeed() > 0 );
        
    }
    @Test
    public void testRightFlipperActionEdgeCase(){
        Ball ball = new Ball("edge", 7,7, 0, 0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball));
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate0,emptyGadget);
        flipper.action(ballInCenter);
        assertTrue(new LineSegment(7,5,5,5).equals(flipper.getLineSegment()));
        assertEquals(5,flipper.getX());
        assertEquals(5,flipper.getY());
        assertTrue(ball.getSpeed() > 0 );
    }
    @Test
    public void testRightFlipperActionOneBallInOneBallOut(){
        Ball ball = new Ball("center", 6,6, 0, 0);
        Ball ball2 = new Ball("out", 7.1,7.1,0,0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball,ball2));
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate0,emptyGadget);
        flipper.action(ballInCenter);
        assertTrue(new LineSegment(7,5,5,5).equals(flipper.getLineSegment()));
        assertEquals(5,flipper.getX());
        assertEquals(5,flipper.getY());
        assertTrue(ball.getSpeed() > 0 );
        assertTrue(ball2.getSpeed() ==0);
    }
    @Test
    public void testRightFlipperActionMultipleBallIn(){
        Ball ball = new Ball("center", 6,6, 0, 0);
        Ball ball2 = new Ball("edge", 6,7,0,0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball,ball2));
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate0,emptyGadget);
        flipper.action(emptyBalls);
        flipper.action(ballInCenter);
        assertTrue(new LineSegment(7,5,7,7).equals(flipper.getLineSegment()));
        assertEquals(5,flipper.getX());
        assertEquals(5,flipper.getY());
        assertTrue(ball.getSpeed() > 0 );
        assertTrue(ball2.getSpeed() > 0);
    }
    @Test
    public void testRightFlipperActionNoBallIn(){
        Ball ball = new Ball("edge2", 8,8, 0, 0);
        Ball ball2 = new Ball("edge", 9,9,0,0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball,ball2));
        RightFlipper flipper = new RightFlipper("flipper1", 5, 5, Orientation.rotate0,emptyGadget);
        flipper.action(emptyBalls);
        flipper.action(ballInCenter);
        assertTrue(new LineSegment(7,5,7,7).equals(flipper.getLineSegment()));
        assertEquals(5,flipper.getX());
        assertEquals(5,flipper.getY());
        assertTrue(ball.getSpeed() == 0 );
        assertTrue(ball2.getSpeed() == 0);
    }
    //////////TEST REFLECT////////////////

    @Test
    public void testReflectEndPoint(){
        Ball ball = new Ball("fixed point", 5,5,0,3);
        Ball ball2 = new Ball("moveable", 15,15,0,-3);
        double ballSpeedBefore = ball.getSpeed();
        RightFlipper flipper = new RightFlipper("flipper1", 10,10, Orientation.rotate0,emptyGadget);
        flipper.reflect(ball);
        flipper.reflect(ball2);
        assertFalse(ball.getVelocity().equals(new Vect(0,3)));
        assertFalse(ball2.getVelocity().equals(new Vect(0,-3)));
        assertTrue(Math.abs(ball.getVelocity().x()) > 0);
        assertTrue(Math.abs(ball2.getVelocity().x()) > 0);
        assertTrue(Math.abs(ballSpeedBefore - ball.getSpeed()) < .15);
        assertTrue(Math.abs(ballSpeedBefore - ball2.getSpeed()) < .15);
    }
    @Test
    public void testReflectSegment(){
        Ball ball = new Ball("b", 5,5,0,3);
        Ball ball2 = new Ball("b2", 5,15,0,-3);
        double ballSpeedBefore = ball.getSpeed();
        RightFlipper flipper = new RightFlipper("flipper1",4,10,Orientation.rotate90,emptyGadget);
        flipper.reflect(ball);
        flipper.reflect(ball2);
        assertTrue(Math.abs(ballSpeedBefore - ball.getSpeed()) < .2);
        assertTrue(Math.abs(ballSpeedBefore - ball2.getSpeed()) < .2);
        assertTrue(ball.getVelocity().y() < 0 );
        assertTrue(ball2.getVelocity().y() >0);
    }
    //////////TEST GET COLLISION////////////////

    @Test
    public void testGetCollisionTimeNoCollision(){
        RightFlipper flipper = new RightFlipper("flipper1",4,10,Orientation.rotate180,emptyGadget);
        Ball ball = new Ball("notCollide", 5, 5,0,3);
        Double tTC = flipper.getCollisionTime(ball);
        assertTrue(tTC.equals(Double.POSITIVE_INFINITY));
    }
    @Test
    public void testGetCollisionTimeCollision(){
        RightFlipper flipper = new RightFlipper("flipper1",4,10,Orientation.rotate180,emptyGadget);
        Ball ball = new Ball("notCollide", 1, 11,2,0);
        Double tTC = flipper.getCollisionTime(ball);
        assertFalse(tTC.equals(Double.POSITIVE_INFINITY));
        assertTrue(tTC >0);
    }
    
    //////////TEST TRIGGER GADGETS////////////////
    @Test
    public void testTriggerOneGadget(){
        RightFlipper trig = new RightFlipper("trig",5,5,Orientation.rotate270,emptyGadget);
        RightFlipper flipper = new RightFlipper("flipper1",4,10,Orientation.rotate180,new ArrayList<Gadget>(Arrays.asList(trig)));
        flipper.triggers(emptyBalls);
        assertTrue(new LineSegment(5,7,5,5).equals(trig.getLineSegment()));
        assertEquals(5,trig.getX());
        assertEquals(5,trig.getY());    
    }
    @Test
    public void testTriggerMultipleGadget(){
        RightFlipper trig = new RightFlipper("trig",5,5,Orientation.rotate270,emptyGadget);
        RightFlipper trig2 = new RightFlipper("flipper1",5,5, Orientation.rotate90,emptyGadget);
        RightFlipper flipper = new RightFlipper("flipper1",4,10,Orientation.rotate180,new ArrayList<Gadget>(Arrays.asList(trig,trig2)));
        flipper.triggers(emptyBalls);
        assertTrue(new LineSegment(5,7,5,5).equals(trig.getLineSegment()));
        assertEquals(5,trig.getX());
        assertEquals(5,trig.getY());    
        assertTrue(new LineSegment(7,7,7,5).equals(trig2.getLineSegment()));
        assertEquals(5,trig2.getX());
        assertEquals(5,trig2.getY());
    }
    @Test
    public void testTriggerNonEmptyBallList(){
        Ball ball = new Ball("center", 6,6, 0, 0);
        Ball ball2 = new Ball("out", 7.1,7.1,0,0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball,ball2));
        RightFlipper trig = new RightFlipper("flipper1", 5, 5, Orientation.rotate0,emptyGadget);
        RightFlipper flipper = new RightFlipper("flipper1",4,10,Orientation.rotate180,new ArrayList<Gadget>(Arrays.asList(trig)));
        flipper.triggers(ballInCenter);
        assertTrue(new LineSegment(7,5,5,5).equals(trig.getLineSegment()));
        assertEquals(5,trig.getX());
        assertEquals(5,trig.getY());
        assertTrue(ball.getSpeed() > 0 );
        assertTrue(ball2.getSpeed() ==0);
    }

}
