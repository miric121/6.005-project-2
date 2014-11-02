package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import physics.Vect;
import physics.Geometry.DoublePair;
import pingball.*;

public class CircleBumperTest {

    //Trigger: 
    //          if the trigger is generated or not when the ball hits it; triggering different gadgets
    //Ball's velocity: 
    //          zero, slow, fast; negative, positive
    //Number of balls: 
    //          none, one, more than one 
    //Type of ball movements:
    //          individual, collision 
    //action: 
    //          no action
    //ball hitting location: 
    //          each four corners, each line segments
    //location of the square bumpers: 
    //          none, one, multiple in a row, multiple seperated
    //Reflection of Coefficient: 
    //          if ball's velocity kept the same
    //Collision time
    //Reflect 

    private Ball slowBall = new Ball("slowBall", 10, 10, 5, 5 );
    private Ball fastBall = new Ball("fastBall", 10, 10, 20, 20 );
    private Ball stableBall = new Ball("stableBall", 19, 19, 0, 0 );
    private Ball negativeBall = new Ball("negativeBall", 15, 15, -20, -20);

    private static List<Ball> emptyBalls;
    private static List<Gadget> emptyGadget;

    @BeforeClass
    public static void setUpBeforeClass(){
        emptyBalls = new ArrayList<Ball>();
        emptyGadget = new ArrayList<Gadget>();
    }        

  //Tests if the ball keeps the same speed but in different direction with slow, positive velocity
    @Test
    public void testVelocityAfterSlow() {
        Vect expected = new Vect(5, 5);
        assertTrue(slowBall.getVelocity().equals(expected));
    }
    //Tests if the ball keeps the same speed but in different direction with fast, positive velocity
    @Test
    public void testVelocityAfterFast() {
        Vect expected = new Vect(20, 20);
        assertTrue(fastBall.getVelocity().equals(expected));
    }
    //Tests if the ball keeps the same speed but in different direction with zero velocity
    @Test
    public void testVelocityAfterStable() {
        Vect expected = new Vect(0, 0);
        assertTrue(stableBall.getVelocity().equals(expected));
    }
    //Tests if the ball keeps the same speed but in different direction with fast, negative velocity
    @Test
    public void testVelocityAfterNegative() {
        Vect expected = new Vect(-20, -20);
        assertTrue(negativeBall.getVelocity().equals(expected));
    }

    @Test
    public void testSetPosition() {
        stableBall.setPosition(5, 1);
        DoublePair expected = new DoublePair(5,1);
        assertTrue(stableBall.getCurrentPosition().equals(expected));
    }

    @Test
    public void testGetSpeed() {
        double expected = (20*2+20*2)*0.5;
        assertTrue( fastBall.getSpeed() - expected < 0.1 );
    }

    ///////////////*******TEST REFLECT*********/////////////////
    @Test
    public void testReflectSlow(){
        Ball ball = new Ball("edge", 7,7, 0, -1);
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        List<Gadget> gadgets = new ArrayList<Gadget>();
        gadgets.add(bumper);
        bumper.reflect(ball);
        assertTrue(ball.getSpeed() == 1.0);
    }

   
    @Test
    public void testReflectFast(){
        Ball ball = new Ball("edge", 7,7, 0, -20);
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        List<Gadget> gadgets = new ArrayList<Gadget>();
        gadgets.add(bumper);
        bumper.reflect(ball);
        assertTrue(ball.getSpeed() == 20.0);
    }
    
    @Test
    public void testReflectMultiple(){
        Ball ball = new Ball("edge", 7,7, 0, 20);
        Ball ball1 = new Ball("edge1", 7,7, 0, 10);
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        List<Gadget> gadgets = new ArrayList<Gadget>();
        gadgets.add(bumper);
        bumper.reflect(ball);
        bumper.reflect(ball1);
        assertTrue(ball.getSpeed() == 20.0);
        assertTrue(ball1.getSpeed() == 10.0);
    }

    
    @Test
    public void testReflectRandom(){
        Ball ball = new Ball("fixed point", 5,5,0,3);
        Ball ball2 = new Ball("moveable", 15,15,0,-3);
        double ballSpeedBefore = ball.getSpeed();
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        bumper.reflect(ball);
        bumper.reflect(ball2);
        assertFalse(ball.getVelocity().equals(new Vect(0,3)));
        assertFalse(ball2.getVelocity().equals(new Vect(0,-3)));
        assertTrue(Math.abs(ball.getVelocity().x()) > 0);
        assertTrue(Math.abs(ball2.getVelocity().x()) > 0);
        assertTrue(Math.abs(ballSpeedBefore - ball.getSpeed()) < .15);
        assertTrue(Math.abs(ballSpeedBefore - ball2.getSpeed()) < .15);
    }



    ///////////////*******TEST GET COLLISION*********/////////////////
    @Test
    public void testGetCollisionTimeNoCollision(){
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        Ball ball = new Ball("notCollide", 7,7, 0, -1);
        Double tTC = bumper.getCollisionTime(ball);
        assertTrue(tTC.equals(Double.POSITIVE_INFINITY));
    }

    @Test
    public void testGetCollisionTimeCollision(){
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        Ball ball = new Ball("notCollide", 1, 11,2,0);
        Double tTC = bumper.getCollisionTime(ball);
        assertTrue(tTC.equals(Double.POSITIVE_INFINITY));
    }
    ///////////////*******TEST TRIGGER*********/////////////////
    @Test
    public void testTriggerOneGadget(){
        CircleBumper bumper = new CircleBumper("bumper",5,5, emptyGadget);
        CircleBumper bumper1 = new CircleBumper("bumper1",5,5, new ArrayList<Gadget>(Arrays.asList(bumper)));
        bumper1.triggers(emptyBalls);
        assertEquals(5,bumper.getX());
        assertEquals(5,bumper.getY());    
    }

    @Test
    public void testTriggerMultipleGadget(){
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        CircleBumper bumper1 = new CircleBumper("bumper2",7,7, emptyGadget);
        CircleBumper bumper2 = new CircleBumper("bumper3",10,10, new ArrayList<Gadget>(Arrays.asList(bumper, bumper1)));
        bumper2.triggers(emptyBalls);
        assertEquals(5,bumper.getX());
        assertEquals(5,bumper.getY());    
        assertEquals(7,bumper1.getX());
        assertEquals(7,bumper1.getY());
    }

    @Test
    public void testTriggerNonEmptyBallList(){
        Ball ball = new Ball("center", 6,6, 1, 0);
        Ball ball2 = new Ball("out", 7.1,7.1,0,20);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball,ball2));
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        CircleBumper bumper2 = new CircleBumper("bumper3",10,10, new ArrayList<Gadget>(Arrays.asList(bumper)));
        bumper2.triggers(ballInCenter);
        assertEquals(5,bumper.getX());
        assertEquals(5,bumper.getY());
        assertTrue(ball.getSpeed() == 1 );
        assertTrue(ball2.getSpeed() ==20);
    }
    ///////////////*******TEST ACTION*********/////////////////
    @Test
    public void testCircleBumperActionOneBall(){
        Ball ball = new Ball("center", 6,6, 0, 0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball));
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        bumper.action(ballInCenter);
        assertEquals(5,bumper.getX());
        assertEquals(5,bumper.getY());
        assertTrue(ball.getSpeed() == 0 );

    }
    @Test
    public void testCircleBumperActionEdgeCase(){
        Ball ball = new Ball("edge", 7,7, 5, 0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball));
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        bumper.action(ballInCenter);
        assertEquals(5,bumper.getX());
        assertEquals(5,bumper.getY());
        assertTrue(ball.getSpeed() > 0 );
    }
    @Test
    public void testCircleBumperActionOneBallInOneBallOut(){
        Ball ball = new Ball("center", 6,6, 5, 0);
        Ball ball2 = new Ball("out", 7.1,7.1,0,0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball,ball2));
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        bumper.action(ballInCenter);
        assertEquals(5,bumper.getX());
        assertEquals(5,bumper.getY());
        assertTrue(ball.getSpeed() > 0 );
        assertTrue(ball2.getSpeed() ==0);
    }
    @Test
    public void testCircleBumperActionMultipleBallIn(){
        Ball ball = new Ball("center", 6,6, 5, 0);
        Ball ball2 = new Ball("edge", 7,7,0,0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball,ball2));
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        bumper.action(emptyBalls);
        bumper.action(ballInCenter);
        assertEquals(5,bumper.getX());
        assertEquals(5,bumper.getY());
        assertTrue(ball.getSpeed() > 0 );
        assertTrue(ball2.getSpeed() == 0);
    }
    @Test
    public void testCircleBumperActionNoBallIn(){
        Ball ball = new Ball("edge2", 8,8, 0, 0);
        Ball ball2 = new Ball("edge", 9,9,0,0);
        List<Ball> ballInCenter = new ArrayList<Ball> (Arrays.asList(ball,ball2));
        CircleBumper bumper = new CircleBumper("bumper1",5,5, emptyGadget);
        bumper.action(emptyBalls);
        bumper.action(ballInCenter);
        assertEquals(5,bumper.getX());
        assertEquals(5,bumper.getY());
        assertTrue(ball.getSpeed() == 0 );
        assertTrue(ball2.getSpeed() == 0);
    }


}
