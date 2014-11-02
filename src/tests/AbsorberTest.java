package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import pingball.*;
import physics.*;

public class AbsorberTest {
    private static List<Gadget> emptyGadget;
    private static List<Ball> emptyBall;
    /**
     * getHeight, getWidth:
     *     width and height are positive integers <= 20
     * getCollisionTime(ball):
     *      ball: never collides, collides, positive/negative velocity, edge cases
     *      Absorber: different height, width
     * reflect(ball):
     *      Absorber: hold no ball, holding 1 ball
     *      Ball: edge of absorber, neg/positive velocity
     *action(List<Ball>)
     *      Absorber: hold no ball, holding 1 ball
     *      List<Ball>: empty, non empty;
     *trigger(List<Ball>):
     *      Gadgets: No gadgets, multiple, 1 gadget
     *      List<Ball>: One ball, multiple balls, empty
     *      Absorber: self trigger, not sefl triggered
     * 
     * 
     * 
     */
    @BeforeClass
    public static void setUpBeforeClass(){
        emptyBall = new ArrayList<Ball>();
        emptyGadget = new ArrayList<Gadget>(); 
    }

	@Test
	public void testAbsorberWidthHeight(){
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,emptyGadget,false);
	    assertEquals(20, abs.getWidth());
	    assertEquals(3,abs.getHeight());
	}
    //////////TEST GET COLLISION////////////////

	@Test
	public void testAbsorberGetCollisionTimeDifferentHeight(){
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,emptyGadget,false);
	    Absorber abs2 = new Absorber("ab2",0,10,20,10,emptyGadget,false);
	    Ball ball = new Ball("b1", 19.75, 5, -1, 3);
	    Double tTC1 = abs.getCollisionTime(ball);
	    Double tTC2 = abs2.getCollisionTime(ball);
	    assertTrue(tTC1 > 0);
	    assertTrue(tTC2 > 0);
	    assertTrue(tTC1 < Double.POSITIVE_INFINITY);
	    assertTrue(tTC2 < Double.POSITIVE_INFINITY);
	    assertTrue(tTC2 <tTC1);
	}
	@Test
	public void testAbsorberGetCollisionTimeNeverCollide(){
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,emptyGadget,false);
	    Ball ball = new Ball("b1", 5, 5, -2, 3);
	    Double tTC = abs.getCollisionTime(ball);
	    assertTrue(tTC.equals(Double.POSITIVE_INFINITY));
	}
	@Test
	public void testAbsorberGetCollisionTimeEdges(){
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,emptyGadget,false);
	    Ball ball = new Ball("b1", 19.75, 5, 0, 3);
	    Ball ball2 = new Ball("b1", .25, 5, 0, 3);
	    Double tTC1 = abs.getCollisionTime(ball);
	    Double tTC2 = abs.getCollisionTime(ball2);
	    assertTrue(tTC1 > 0);
	    assertTrue(tTC2 > 0);
	    assertTrue(tTC1 < Double.POSITIVE_INFINITY);
	    assertTrue(tTC2 < Double.POSITIVE_INFINITY);
	}
	@Test
	public void testAbsorberGetCollisionTimeOneCollidesOneDoesnt(){
	    Absorber abs = new Absorber("ab1", 3, 17, 5,3,emptyGadget,false);
	    Ball ball = new Ball("b1", 0.25, 5, 0, 3);
	    Ball ball2 = new Ball("b1", 19.75, 5, 0, 3);
	    Double tTC1 = abs.getCollisionTime(ball);
	    Double tTC2 = abs.getCollisionTime(ball2);
	    assertTrue(tTC2 > 0);
        assertTrue(tTC1.equals(Double.POSITIVE_INFINITY));
	    assertFalse(tTC2 < Double.POSITIVE_INFINITY);
	}
    //////////TEST REFLECT////////////////

	@Test
	public void testReflectHoldNoBallEdgeCase(){
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,emptyGadget,false);
	    Ball ball = new Ball("b1", .25, 5, 0, -3);
	    abs.reflect(ball);
	    assertTrue(ball.getCurrentPosition().d1 == 19.75);
	    assertTrue(ball.getCurrentPosition().d2 == 19.75);
	    assertTrue(ball.getVelocity().equals(new Vect(0,0)));
	}
	@Test
	public void testReflectHoldOneBall(){
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,emptyGadget,false);
	    Ball ball = new Ball("b1", 3, 5, 0, 3);
	    Ball ball2 = new Ball("b2",4,5,0,-3);
	    abs.reflect(ball);
	    abs.reflect(ball2);
	    assertTrue(ball2.getVelocity().y()>0);
	    assertTrue(ball2.getCurrentPosition().d1 < 19.75);
	    assertTrue(ball2.getCurrentPosition().d2 < 19.75);
	    assertTrue(ball.getCurrentPosition().d1 == 19.75);
	    assertTrue(ball.getCurrentPosition().d2 == 19.75);
	    assertTrue(ball.getVelocity().equals(new Vect(0,0)));
	}
    //////////TEST ACTION////////////////

	@Test
	public void testActionHoldNoBallSingleBallList(){
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,emptyGadget,false);
	    Ball ball = new Ball("b1", 20, 5, 0, 3);
	    abs.action(new ArrayList<Ball>(Arrays.asList(ball)));
	    assertTrue(ball.getVelocity().equals(new Vect(0,3)));
	    assertTrue(ball.getCircle().getCenter().equals(new Vect(20,5)));
	}
	@Test
	public void testActionHold1BallMultipleBallList(){
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,emptyGadget,false);
	    Ball ball = new Ball("b1", 19, 5, 0, 3);
	    Ball ball2 = new Ball("b2", 5,5,0,-3);
	    abs.reflect(ball);
	    abs.action(new ArrayList<Ball>(Arrays.asList(ball,ball2)));
	    assertTrue(ball.getVelocity().equals(new Vect(0,-50)));
	    assertFalse(ball.getCircle().getCenter().equals(new Vect(19,5)));
	    assertTrue(ball2.getVelocity().equals(new Vect(0,-3)));
	}
    //////////TEST TRIGGER////////////////

	@Test
	public void testTrigger2GadgetNoSelfEmptyBallList(){
	    LeftFlipper flip1 = new LeftFlipper("flip1",5,5,Orientation.rotate0,emptyGadget);
	    LeftFlipper flip2 = new LeftFlipper("flip1",10,10,Orientation.rotate0,emptyGadget);
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,new ArrayList<Gadget>(Arrays.asList(flip1,flip2)),false);
	    abs.triggers(emptyBall);
	    assertTrue(new LineSegment(5,5,7,5).equals(flip1.getLineSegment()));
	    assertTrue(new LineSegment(10,10,12,10).equals(flip2.getLineSegment()));
	}
	@Test
	public void testTrigger1GadgetNoSelf1BallList(){
        Ball ball = new Ball("center", 6,6, 0, 0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball));
        LeftFlipper flipper = new LeftFlipper("flipper1", 5, 5, Orientation.rotate0,emptyGadget);
        Absorber abs = new Absorber("ab1", 0, 17, 20,3,new ArrayList<Gadget>(Arrays.asList(flipper)),false);
        abs.triggers(ballInCenter);
        assertTrue(new LineSegment(5,5,7,5).equals(flipper.getLineSegment()));
        assertEquals(5,flipper.getX());
        assertEquals(5,flipper.getY());
        assertTrue(ball.getSpeed() > 0 );
	}
	
	@Test
	public void testTriggerNoGadgetHold1BallSelfTriggers(){
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,emptyGadget,true);
	    Ball ball = new Ball("b1", 19, 5, 0, 3);
	    Ball ball2 = new Ball("b2", 5,5,0,-3);
	    abs.reflect(ball);
	    abs.triggers(new ArrayList<Ball>(Arrays.asList(ball,ball2)));
	    assertTrue(ball.getVelocity().equals(new Vect(0,-50)));
	    assertFalse(ball.getCircle().getCenter().equals(new Vect(19,5)));
	    assertTrue(ball2.getVelocity().equals(new Vect(0,-3)));
	}
	@Test
	public void testTriggerGadgetsHold1BallSelfTriggers(){
	    LeftFlipper flip1 = new LeftFlipper("flip1",5,5,Orientation.rotate0,emptyGadget);
        LeftFlipper flip2 = new LeftFlipper("flip1",10,10,Orientation.rotate0,emptyGadget);
	    Absorber abs = new Absorber("ab1", 0, 17, 20,3,new ArrayList<Gadget>(Arrays.asList(flip1,flip2)),true);
	    Ball ball = new Ball("b1", 19, 5, 0, 3);
	    Ball ball2 = new Ball("b2", 5,5,0,-3);
	    abs.reflect(ball);
	    abs.triggers(new ArrayList<Ball>(Arrays.asList(ball,ball2)));
	    assertTrue(ball.getVelocity().equals(new Vect(0,-50)));
	    assertFalse(ball.getCircle().getCenter().equals(new Vect(19,5)));
	    assertTrue(ball2.getVelocity().equals(new Vect(0,-3)));
	    assertTrue(new LineSegment(5,5,7,5).equals(flip1.getLineSegment()));
	    assertTrue(new LineSegment(10,10,12,10).equals(flip2.getLineSegment()));
	}
	
}
