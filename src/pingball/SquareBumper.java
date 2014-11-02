package pingball;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class SquareBumper implements Gadget {
    /*
    Size and shape: a square shape with edge length 1L

    Orientation: not applicable (symmetric to 90 degree rotations)

    Coefficient of reflection: 1.0

    Trigger: generated whenever the ball hits it

    Action: none
    */
    
    /*
     * REP INVARIANT:
     * x, y and name must be non-null
     * 0 <= x <= 19
     * 0 <= y <= 19
     * 
     * String name has to be immutable
     * width and height should be immutable
     * Coefficient of reflection has to be immutable
     * Location of the gadget has to be immutable
     */
	
	private final String name;
	private final String newName;
	private final int width;
	private final int height;
    private final int x;
    private final int y;
	private final double reflectionCoefficient;
	private final LineSegment segment1;
	private final LineSegment segment2;
	private final LineSegment segment3;
	private final LineSegment segment4;
    private final Circle fixedEnd1;
    private final Circle fixedEnd2;
    private final Circle fixedEnd3;
    private final Circle fixedEnd4;
    private List<Gadget> toTrigger;
	
	/**
	 * Constructor for the SquareBumper Class
	 * @param name
	 * @param x; the x coordinate of the square bumper in the board
	 * @param y; the y coordinate of the square bumper in the board
	 */
	public SquareBumper(String name, int x, int y, List<Gadget> toTrigger){
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = 1;
		this.height = 1;
		this.reflectionCoefficient = 1.0;
        this.segment1 = new LineSegment(x,y,x+1,y);
        this.segment2 = new LineSegment(x,y,x,y+1);
        this.segment3 = new LineSegment(x+1,y,x+1,y+1);
        this.segment4 = new LineSegment(x, y+1, x+1, y+1);
        this.fixedEnd1 = new Circle(x, y, 0);
        this.fixedEnd2 = new Circle(x+1, y, 0);
        this.fixedEnd3 = new Circle(x, y+1, 0);
        this.fixedEnd4 = new Circle(x+1, y+1, 0);
        this.toTrigger = toTrigger;
        this.newName = new String(name);
	}
	/**
	 * @return String; name of the square bumper
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the square bumper's upper left corner position from the board's origin(0,0).
     * @return integer x coordinate representing the upper left corner
     */

	public int getX() {
		return this.x;
	}
	
	/**
     *Returns the square bumper's left corner position from the board's origin(0,0).
     * @return integer y coordinate representing the upper left corner
     */
	public int getY() {
		return this.y;
	}
	
	/**
	 * @return 0 since the rotation is not applicable to square 
	 * clockwise rotation in degrees around the center of the bounding box of the gadget
	 * 
	 */
	@Override
	public int getOrientation() {
		return 0;
	}
	
	/**
	 * @return the width of the square bumper
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * @return the height of the square bumper
	 */
	public int getHeight() {
		return this.height;
	}
	/**
	 * @return the value of the coefficient of reflection
	 */
	@Override
	public double getReflectionCoefficient() {
		return this.reflectionCoefficient;
	}

	/**
	 * Finds the time until the given ball collides with the square bumper.
     * @param ball the ball whose collision time is to be calculated.
     * @return the double time until the ball collides with the square bumper.
	 */
	@Override
	public double getCollisionTime(Ball ball) {
	    checkRep();
	    List<LineSegment> segments = new ArrayList<LineSegment>();
        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment4);
        List<Circle> points = new ArrayList<Circle>();
        points.add(fixedEnd1);
        points.add(fixedEnd2);
        points.add(fixedEnd3);
        points.add(fixedEnd4);
        
        
        double t = Double.POSITIVE_INFINITY;

        for (LineSegment segment: segments){
        	double timeToCollision = physics.Geometry.timeUntilWallCollision(segment, ball.getCircle(), ball.getVelocity());
        	if (timeToCollision < t){
        		t = timeToCollision;
        	}
        }
        
        for (Circle point: points){
        	double timeToCollision = physics.Geometry.timeUntilCircleCollision(point, ball.getCircle(), ball.getVelocity());
        	if (timeToCollision < t){
        		t = timeToCollision;
        	}
        }
        return t;

	}
	
	/**
	 * @effect Updates the velocity and the position of the ball right when the ball hits the square gadget.
     * @param ball that triggers the square bumper by hitting the square bumper
     * Hitting the circle at the corner gets the priority than hitting the line segment
	 */
	@Override
	public void reflect(Ball ball) { 
	    checkRep();
        List<LineSegment> segments = new ArrayList<LineSegment>();
        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment4);
        List<Circle> points = new ArrayList<Circle>();
        points.add(fixedEnd1);
        points.add(fixedEnd2);
        points.add(fixedEnd3);
        points.add(fixedEnd4);
        
        double tSegment = Double.POSITIVE_INFINITY;
        LineSegment closestSegment = segment1; 
        for (LineSegment segment: segments){
        	double timeToCollision = physics.Geometry.timeUntilWallCollision(segment, ball.getCircle(), ball.getVelocity());
        	if (timeToCollision < tSegment){
        		tSegment = timeToCollision;
        		closestSegment = segment;
        	}
        }
        	
        double tCircle = Double.POSITIVE_INFINITY;
        Circle closestEnd = fixedEnd1; 
        for (Circle point: points){
        	double timeToCollision = physics.Geometry.timeUntilCircleCollision(point, ball.getCircle(), ball.getVelocity());
        	if (timeToCollision < tCircle){
        		tCircle = timeToCollision;
        		closestEnd = point;
        	}
        }
        
        if (tSegment < tCircle ){
            Vect newVect = physics.Geometry.reflectWall(closestSegment,ball.getVelocity(),this.reflectionCoefficient);
            ball.setVelocity(newVect);
        }else{
        	Vect newVect= physics.Geometry.reflectCircle(closestEnd.getCenter(), ball.getCircle().getCenter(), ball.getVelocity(), this.reflectionCoefficient);
            ball.setVelocity(newVect);
        }
    }
	 /**
     * @effect Does nothing. 
     */  
	@Override
	public void action(List<Ball> listOfBalls) {
	}
	/**
     * @effect The event where the ball collides with the square gadget
     * @param gadget the square bumper
     */
	@Override

    public void triggers(List<Ball> listOfBall) {
        for (Gadget gad: this.toTrigger){
            gad.action(listOfBall);
        }
        
    }
	
	/**
     * @effect checks rep invariant for the SquareBumper Class
     * 
     * @throws RuntimeException
     */
    private void checkRep() throws RuntimeException{
    
    
        assert newName.equals(name);
        assert x >= 0;
        assert x <= 19;
        assert y >= 0;
        assert y <= 19;
    }
    
    
    /**
     * @return the list of coordinates of the upper left corner of the box in which the square bumper is occupied by
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Pair> coordinates() {
		List<Pair> coords = new ArrayList<Pair>();
		coords.add( new Pair( this.x, this.y));
		return coords;
	}
	/**
     * @return String of the square bumper's representation
     */
    @Override public String toString(){
    	return "#";
    }

}
