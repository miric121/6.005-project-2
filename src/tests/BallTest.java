package tests;

import static org.junit.Assert.*;


import physics.Vect;
import physics.Geometry.DoublePair;
import pingball.*;

import org.junit.Test;

public class BallTest {
	
	/*
	 * Test cases for ball
	 * 	1. position, velocity features (collision would be checked in board)
	 * 	2. modifier setPosition
	 * 	3. observer currentSpeed
	 * 	4. get expected time until collision (close, infinity)
	 * 	5. Collisions between balls (collisions with a gadget in gadget test cases)
	 */
	
	private Ball fastBall = new Ball("fastBall", 10, 10, 20, 20 );
	private Ball stableBall = new Ball("stableBall", 19, 19, 0, 0 );
	

	@Test
	public void testVelocity() {
		Vect expected = new Vect(20, 20);
		assertTrue(fastBall.getVelocity().equals(expected));
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
	
	@Test
	public void testCollisionsBetweenBalls() {
		
		Ball b1 = new Ball("b1", 10, 1, 1, 0 );
		Ball b2 = new Ball("b2", 10.5, 1, -1, 0 );
		b1.reflect(b2);		

		assertTrue( b1.getVelocity().equals(new Vect(-1,0)));
		assertTrue( b2.getVelocity().equals(new Vect(1,0)));
	}
	
	@Test
	public void testCollisionTime() {
		
		Ball b1 = new Ball("b1", 10, 1, 1, 0 );
		Ball b2 = new Ball("b2", 10.5, 1, -1, 0 );
		double time = b1.getCollisionTime(b2);		

		assertTrue( time == 0);
	}
	
	@Test
	public void testCollisionTimeInfinity() {
		
		Ball b1 = new Ball("b1", 10, 1, -1, 0 );
		Ball b2 = new Ball("b2", 10.5, 1, 1, 0 );
		double time = b1.getCollisionTime(b2);		
		assertTrue( time == Double.POSITIVE_INFINITY);
	}
	
	
}
