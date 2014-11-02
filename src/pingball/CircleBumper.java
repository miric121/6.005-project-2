package pingball;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import physics.Circle;
import physics.Vect;

public class CircleBumper implements Gadget{
    /*
    Size and shape: a circular shape with diameter 1L

    Coefficient of reflection: 1.0

    Orientation: not applicable (symmetric to 90 degree rotations)

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
     * circle's diameter has to immutable
     * width and height should be immutable
     * Coefficient of reflection has to be immutable
     * Location of the gadget has to be immutable
     */
    
    private final String name;
    private final String newName;
    private double radius;
    private Circle circle;
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final double reflectionCoefficient;
    private final List<Gadget> toTrigger;
    
    /**
     * Constructor for the CircleBumper Class
     * @param circle; the Circle class taking x and y coordinates of the center point of the circle, and its radius
     * @param name the name of this circle bumper
     */
    public CircleBumper(String name, int x, int y, List<Gadget> toTriggers){
        this.name = name;
        this.radius = 0.5;
        this.circle = new Circle( x + this.radius, y + this.radius, radius );
        this.x = x;
        this.y = y;
        this.width =  this.height = (int) Math.ceil(radius*2);
        this.reflectionCoefficient = 1.0;
        this.toTrigger = toTriggers;
        this.newName = new String(name);

    }
   
    /**
     * @return String; name of the circle bumper
     */
    @Override
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the circle bumper's box upper left corner position from the board's origin(0,0).
     * @return integer x coordinate representing the box's upper left corner
     */
    @Override
    public int getX() {
        return this.x;
    }
    /**
     * Returns the circle bumper's box upper left corner position from the board's origin(0,0).
     * @return integer y coordinate representing the box's upper left corner
     */
    @Override
    public int getY() {
        return this.y;
    }
    /**
     * Returns the integer width of the circle bumper's box
     * @return integer representing the width of the circle bumper
     */
    @Override
    public int getWidth() {
        return this.width;
    }
    /**
     * Returns the integer height of the circle bumper's box
     * @return integer representing the height of the circle bumper
     */
    @Override
    public int getHeight() {
        return this.height;
    }
    /**
     * @return 0, the degree orientation of the circle bumper
     * clockwise rotation in degrees around the center of the bounding box of the gadget
     * 
     */
    @Override
    public int getOrientation() {
        return 0;
    }
    
    
    /**
     * @return the value of the coefficient of reflection
     */
    @Override
    public double getReflectionCoefficient() {
        return this.reflectionCoefficient;
    }

    /**
     * Finds the time until the given ball collides with the circle bumper.
     * @param ball the ball whose collision time is to be calculated.
     * @return the double time until the ball collides with the circle bumper.
     */
    @Override
    public double getCollisionTime(Ball ball) {
        checkRep();
        return physics.Geometry.timeUntilCircleCollision(this.circle, ball.getCircle(), ball.getVelocity());
    }
    
    /**
     * @effect Updates the velocity and the position of the ball right when the ball hits the circle gadget.
     * @param ball that triggers the circle bumper by hitting the circle bumper
     */
    @Override
    public void reflect(Ball ball) {
        checkRep();
        Vect newVect = physics.Geometry.reflectCircle(this.circle.getCenter(), ball.getCircle().getCenter(), ball.getVelocity(),this.reflectionCoefficient);
        ball.setVelocity(newVect);
    }

     /**
     * @effect Does nothing. 
     */  
    @Override
    public void action(List<Ball> listOfBalls) {
    }
    /**
     * The event where the ball collides with the circle gadget
     * @param gadget the circle bumper
     */
    @Override
    public void triggers(List<Ball> listOfBall) {
        for (Gadget gad: this.toTrigger){
            gad.action(listOfBall);
        }
        
    }
    /**
     * @effect checks rep invariant for the CircleBumper Class
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
     * @return the list of coordinates of the upper left corner of the box in which the circle bumper is occupied by
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Pair> coordinates() {
		List<Pair> coords = new ArrayList<Pair>();
		coords.add( new Pair( this.x, this.y));
		return coords;
	}
	/**
     * @return String of the circle bumper's representation
     */
    @Override public String toString(){
    	return "O";
    }

    
}