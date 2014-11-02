package pingball;

import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public abstract class Flipper implements Gadget {
	/*
	 * Flipper represents a right and left flipper in the pingball board.
	 * 
     * REP INVARIANT:
     * x, y and name must be non-null.
     * 0 <= x <= 19
     * 0 <= y <= 19
     * orientation should be either 0, 90, 180 or 270
     */
	
    protected double angularVelocity = 1080;
    protected String name;
    protected String newName;
    protected final int width = 2;
	protected final int height = 2;
	protected int x;
	protected int y;
	protected Orientation orientation;
	protected double reflection = 0.95;
	protected boolean rotated = false;
    protected boolean rotating = false;
	protected Vect center;
	protected Circle fixedEnd;
	protected Circle movableEnd;
	protected LineSegment segment;
	protected List<Gadget> toTrigger;
	
	/**
	 * Constructor for flipper
	 * 
	 * 
	 * @param name the name of this flipper
     * @param x the x coordinate of this flipper in the board.
     * @param y the y coordinate of this flipper in the board
     * @param orientation of this flipper.
     * @param list of triggers
	 */
	
	
	
	/**
     * Finds the time until the given ball collides with the left flipper.
     * @param ball the ball whose collision time is to be calculated.
     * @return the double time until the ball collides with the left flipper.
     */
	@Override
	public double getCollisionTime(Ball ball) {
		
        List<Circle> points = new ArrayList<Circle>();
        points.add(fixedEnd);
        points.add(movableEnd);        
        
        double t = physics.Geometry.timeUntilWallCollision(segment, ball.getCircle(), ball.getVelocity());
        
        for (Circle point: points){
        	double timeToCollision = physics.Geometry.timeUntilCircleCollision(point, ball.getCircle(), ball.getVelocity());
        	if (timeToCollision < t){
        		t = timeToCollision;
        	}
        }
        return t;
	}
	
	/**
     * Updates the velocity and the position of the ball right when the ball hits the left flipper gadget.
     * @param ball that triggers the left flipperby hitting the line segment of the left flipper bumper
     * 
     */
	@Override
	public void reflect(Ball ball) {
	
		double minTime = physics.Geometry.timeUntilCircleCollision(fixedEnd, ball.getCircle(), ball.getVelocity()) ;
		Circle endPoint = fixedEnd;
		
		double timeToMovableEnd = physics.Geometry.timeUntilCircleCollision(movableEnd, ball.getCircle(), ball.getVelocity()) ;
		double timeToSegment = physics.Geometry.timeUntilWallCollision(segment, ball.getCircle(), ball.getVelocity()) ;
        
		if ( minTime > timeToMovableEnd) {
			minTime = timeToMovableEnd;
			endPoint = movableEnd;
        }
		
		if (minTime > timeToSegment){
		     Vect newVect = physics.Geometry.reflectWall(segment,ball.getVelocity(),this.reflection);
		     ball.setVelocity(newVect);
		}else{
			Vect newVect =  physics.Geometry.reflectCircle( endPoint.getCenter() ,ball.getCircle().getCenter(),ball.getVelocity(), this.reflection);
			ball.setVelocity(newVect);
		}
	}
	

}
