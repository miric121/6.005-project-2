package pingball;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import physics.Circle;
import physics.Geometry.VectPair;
import physics.Vect;
import physics.Geometry.DoublePair;


public class Ball implements Gadget{
	
    /*
     * The ball is a Ping ball. It has velocity and can leave its current board through invisible wall.  
     *  
     * REP INVARIANT:
     * radius =< ball.x =< 20 - radius 
     * ball's velocity should be O or range between 0.01 and 200.
     */
	
	
	
	
    private final String name; 
    private final String newName;
    private final double radius = 0.25; //default
    private Circle circle;
    private DoublePair position;
    private Vect velocity;
    
    /**
     * Constructor function for ball 
     * 
     * @param name 
     * @param xCoord which this ball's x coordinate of location to be placed
     * @param yCoord which this ball's y coordinate of location to be placed
     * @param xVel which rate this ball's x coordinate of velocity to be set
     * @param yVel which rate this ball's y coordinate of velocity to be set
     */
    public Ball(String name, double xCoord, double yCoord, double xVel, double yVel){
    	this.name = name;
    	this.position = new DoublePair(xCoord, yCoord);
    	this.velocity = new Vect(xVel, yVel);
    	this.circle = new Circle(xCoord, yCoord, this.radius);
        this.newName = new String(name);

    }
    
	
	/**
     * Finds the time until the given ball collides with the other ball.
     * @param ball the ball whose collision time is to be calculated.
     * @return the double time until the ball collides with the other ball.
     */
	@Override
	public double getCollisionTime(Ball ball) {
	    checkRep() ;
	    return physics.Geometry.timeUntilBallBallCollision(this.circle, this.velocity, ball.circle, ball.velocity);
	    
	}

	/**
     * Updates the velocity and the position of the ball and of the other ball right when the ball hits the other ball.
     * @param ball the other ball 
     * 
     */
	@Override
	public void reflect(Ball ball) {
	    checkRep();
	    VectPair newVect = physics.Geometry.reflectBalls(this.circle.getCenter(), .5, this.velocity, ball.circle.getCenter(), .5, ball.velocity);
	    this.setVelocity(newVect.v1);
	    ball.setVelocity(newVect.v2);
	}
	
	
	/**
	 * move this ball for time, assuming hitting nothing
	 * @param time
	 */
	public void move(double time, double gravity, double mu, double mu2) {
	    checkRep() ;
		double newX = this.position.d1 +velocity.x()*time;
		double newY = this.position.d2 +velocity.y()*time;
		if (newX < .25){
		    newX = .25;
		} else if (newX > 19.75){
		    newX = 19.75;
		}
	    if (newY < .25){
	        newY = .25;
	    } else if (newY > 19.75){
	        newY = 19.75;
	    }
	    double newVy = this.getVelocity().y() + gravity * time;
	    newVy = newVy * (1 - mu *time - mu2 * Math.abs(newVy)*time);
	    double newVx = this.velocity.x() * (1 - mu *time - mu2 * Math.abs(this.velocity.x())*time);
		this.setPosition(newX, newY);
		this.setVelocity(new Vect(newVx, newVy));
		  
	}
	
	
    
    /**
     * @return coordinate of this ball's center
     */
    public DoublePair getCurrentPosition(){
        return this.position;
    }
       
    
    /**
     * 
     * @return current velocity of this ball
     */
    public Vect getVelocity(){
        return this.velocity;
    }

    /**
     * Sets this ball's velocity at the given value
     * @param newValue value to be set 
     */
    public void setVelocity(Vect newValue) {
        this.velocity = newValue;
    }
    
    /**
     * Sets this ball's location at the given value
     * @param x new x coordinate of this ball's location 
     * @param y new y coordinate of this ball's location 
     */
    public void setPosition(double x,double y){
    	this.position = new DoublePair(x,y);
    	this.circle = new Circle(x,y,this.radius);
    }
    
    
    /**
     * Calculates this ball's speed 
     * @return current speed of the ball
     */
    public double getSpeed(){
        double xDir = velocity.x();
        double yDir = velocity.y();
        return Math.sqrt(Math.pow(xDir, 2)+ Math.pow(yDir,2));
    }

    /**
     * returns the name of the ball
     */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * x coordinate of ball's container board cell's upper left corner 
	 */
	@Override
	public int getX() {
	    return (int) Math.floor(this.centerX() - this.radius);
	}

	/**
	 * y coordinate of ball's container board cell's upper right corner 
	 */
	@Override
	public int getY() {
	    return (int) Math.floor(
	    		this.centerY() - this.radius);
	}
	
	/**
	 * returns the x-coordinate of the center of the ball
	 * @return double; x-coordinate of the center of the ball 
	 */
	public double centerX(){
		return this.position.d1;
	}
	
	/**
     * returns the y-coordinate of the center of the ball
     * @return double; y-coordinate of the center of the ball 
     */
	public double centerY(){
		return this.position.d2;
	}

	/**
	 * returns 0 since there is no orientation for the ball
	 */
	@Override
	public int getOrientation() {
		return 0;
	}

	/**
	 * returns int of the ball's width, which is a diameter
	 */
	@Override
	public int getWidth() {
		return (int) Math.ceil( 2*this.radius);
	}

	/**
	 * 
	 * @return circle representing the ball containing its x-coord, y-coord, and radius
	 */
	public Circle getCircle(){
	    return this.circle;
	}
	/**
     * returns int of the ball's height, which is a diameter
     */
	@Override
	public int getHeight() {
	    return (int) Math.ceil( 2*this.radius);
	}

	/**
     * returns the value of the coefficient of reflection, which is 1, keeping the same velocity
     */
	@Override
	public double getReflectionCoefficient() {
	    return 1;
	}

	/**
	 * does nothing
	 */
	@Override
	public void action(List<Ball> ListOfBalls) {
	}

	/**
	 * does nothing
	 */
	@Override
    public void triggers(List<Ball> listOfBall) {
    }
	
	/**
     * checks rep invariant for the Absorber Class
     * 
     * @throws RuntimeException
     */
    private void checkRep() throws RuntimeException{
        assert newName.equals(name);
        assert  ((this.getSpeed() <= 200) && (0.00 <= this.getSpeed()));
    }
    
    
	
	/**
     * returns the list of coordinates of the upper left corner of the box in which the ball is occupied by
     */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Pair> coordinates() {
		List<Pair> coords = new ArrayList<Pair>();
		coords.add( new Pair( (int) Math.floor( this.getX()) , (int) Math.floor( this.getY()) ));
		return coords;
	}
	
	/**
     * returns String of the Ball's representation
     */
    @Override public String toString(){
    	return "*";
    }
}
