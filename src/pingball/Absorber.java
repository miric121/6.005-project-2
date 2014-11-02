package pingball;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import physics.*;


public class Absorber implements Gadget{
    /*
    Size and shape: A rectangle kL × mL where k and m are positive integers <= 20

            Orientation: not applicable (only one orientation is allowed)

            Coefficient of reflection: not applicable; the ball is captured

            Trigger: generated whenever the ball hits it

            Action: shoots out a stored ball (see below)
            */
    
    /*
     * REP INVARIANT:
     * x, y and name must be non-null
     * 
     * The length of the absorber + x-position <= 20
     * 
     * String name has to be immutable
     * the rectangular size of the absorber should be immutable
     * Location of the gadget has to be immutable
     * width and height should be immutable
     * 
     */
    
    private final String name;
    private final String newName;
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final int reflectionCoef;
    private final List<Gadget> toTrigger;
    private List<Ball> absored;
    private final boolean selfTrigger;
    /**
     * Constructor for the Absorber Class
     * @param name
     * @param x coordinate
     * @param y coordinate
     * @param width  the width coordinate of the square bumper in the board
     * @param height the height coordinate of the square bumper in the board
     * @param toTrigger list of gadgets to triggers
     * @param self if the absorber is self trigger or not
     */
    public Absorber(String name, int x, int y, int width, int height, List<Gadget> toTrigger, boolean self){
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.reflectionCoef = 1;
        this.absored = new ArrayList<Ball>();
        this.toTrigger = toTrigger;
        this.selfTrigger = self;
        this.newName = new String(name);
    }
    /**
     * @returns String; name of the absorber
     */
    @Override
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the absorber's left corner position from the board's origin(0,0).
     * @return integer x coordinate representing the absorber's upper left corner
     */
    @Override
    public int getX() {
        return this.x;
    }
    /**
     *Returns the absorber's left corner position from the board's origin(0,0).
     * @return integer y coordinate representing the absorber's upper left corner
     */
    @Override
    public int getY() {
        return this.y;
    }
    /**
     * Returns the integer width of the absorber
     * @return integer representing the width of the absorber
     */
    @Override
    public int getWidth() {
        return this.width;
    }
    /**
     * Returns the integer height of the absorber
     * @return integer representing the height of the absorber
     */
    @Override
    public int getHeight() {
        return this.height;
    }


    /**
     * @returns the degree orientation of the absorber
     * clockwise rotation in degrees around the center of the bounding box of the gadget
     * 
     */
    @Override
    public int getOrientation() {
        return 0;
    }
    
    
    /**
     * @returns the value of the coefficient of reflection
     */
    @Override
    public double getReflectionCoefficient() {
        return this.reflectionCoef;
    }

    /**
     * Finds the time until the given ball collides with the absorber.
     * @param ball the ball whose collision time is to be calculated.
     * @return the double time until the ball collides with the absorber.
     */
    @Override
    public double getCollisionTime(Ball ball) {
        checkRep();
        return physics.Geometry.timeUntilWallCollision(new LineSegment(x,y,x+width,y), ball.getCircle(), ball.getVelocity());
    }
    
    /**
     * Updates the velocity and the position of the ball right when the ball hits the absorber.
     * @param ball that triggers the square bumper by hitting the absorber
     * @effect reflect or store the ball. If no ball are stored then store the ball. Else reflect it.
     */
    @Override
    public void reflect(Ball ball) {
        checkRep();
        if(this.absored.size() ==0){
            ball.setPosition(x+width-.25, y+height-.25);
            ball.setVelocity(new Vect(0,0));   
            this.absored.add(ball);
        }
        else {
            Vect newVect = physics.Geometry.reflectWall(new LineSegment(x,y,x+width,y), ball.getVelocity());
            ball.setVelocity(newVect);
        }
     }
     /**
      * @param listOfBalls list of ball in the board
     *@effect Shoots out an already stored ball.
     *If the absorber is not holding the ball, or if the previously ejected ball has not yet left the absorber, 
     *then the absorber takes no action when it receives a trigger 
     */  
    @Override
    public void action(List<Ball> listOfBalls) {
        if(this.absored.size()==1){
            Ball ballToRelease = this.absored.get(0);
            ballToRelease.setPosition(x+width-.25, y-.5);
            ballToRelease.setVelocity(new Vect(0,-50));
            this.absored.remove(0);
        }
    }
    /**
     * The event where the ball collides with the absorber
     * May self-triggering by connecting its trigger to its own action. When the ball hits a self-triggering absorber, 
     * it should be moved to the bottom right-hand corner as described above, and then be shot upward as described above. 
     * There may or may not be a short delay between these events, at your discretion.
     * @param gadget the absorber
     * @effect triggers all the gadgets
     */
    @Override

    public void triggers(List<Ball> listOfBall) {
        for (Gadget gad: this.toTrigger){
            gad.action(listOfBall);
        }
        if (this.selfTrigger){
            this.action(listOfBall);
        }
        

    }
    
    /**
     * checks rep invariant for the Absorber Class
     * 
     * @throws RuntimeException
     */
    private void checkRep() throws RuntimeException{
        assert newName.equals(name);
        assert (this.width + x) <= 20;
        assert (this.height + y) <= 20;
    }
    
    
    /**
     * @returns the list of coordinates of the upper left corner of the box in which the absorber is occupied by
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Pair> coordinates() {
		List<Pair> coords = new ArrayList<Pair>();
		for (int r = 0; r < height; r++){
			for (int c = 0; c < width; c++){
				coords.add( new Pair(x + c, y + r));
			}
		}
		return coords;
	}
	
	/**
	 * @returns String of the absorber's representation
	 */
    @Override public String toString(){
    	return "=";
    }

    
}
