package pingball;

import java.util.List;

import javafx.util.Pair;



/**
*
* The standard gadget could either be a CircleBumper, SquareBumper, TriangularBumper, LeftFlipper, RightFlipper,
* or Absorber. The gadget consists of Circles and LineSegments.
* 
*  Each gadget have a location, width, height and a coefficient of reflection, which is a multiplier applied to the 
* magnitude of the ball’s velocity after it bounces off the gadget. 
*  Also, the gadget may have a trigger and an action. 
*  A trigger is an event that happens at the gadget, such as a ball colliding with it. 
*  An action is a response that a gadget can make to a trigger happening somewhere on the board.
* 
* ADT : CircleBumper + SquareBumber + TriangleBumber + Flipper
* Rep Invariant: 
* 		0 =< Any point in Gadget's bounding box =< 20
* 		Any two gadgets must not be overlapped
* 
*/

public interface Gadget {
	
    /**
     * Returns the name of this gadget.
     * @return the String name
     */
	public String getName();
	
    /**
     * @return x coordinate of this gadget’s origin 
     */
	public int getX(); //don't change!
    /**
     * @return y coordinate of this gadget’s origin 
     */
	public int getY(); //don't change!
	
    /**
     * Returns the orientation of this gadget. Could be either 270, 180, 90 or defaulted 0. 
     * @return the orientation.
     */
    public int getOrientation(); //don't change!
	
    /**
     * @return Width of this gadget's bounding box
     */

	public int getWidth(); //don't change!
	
	/**
	 * @return Height of this gadget's bounding box
	 */
	public int getHeight(); //don't change!

	
	
	/**
	 * @return coefficient of this gadget's reflection
	 */
	public double getReflectionCoefficient();
	
	
    /**
     * Finds the time until the given ball collides with this gadget.
     * @param ball the ball whose collision time is to be calculated.
     * @return the time until the ball collides with this gadget.
     */
    public double getCollisionTime(Ball ball);
	
    /**
     * Updates the velocity and the position of the ball right when the ball hits this gadget.
     * if the gadget is ball, its velocity and position would be updated.
     * @param ball the ball that triggered this gadget by hitting it. 
     */
	public void reflect(Ball ball);
	
	
	/**
	 * Returns pair of coordinates row and column in Pingball board. 
	 */
	@SuppressWarnings("rawtypes")
	public List<Pair> coordinates();
	
	
    /**
     * Triggers this gadget. If this gadget is a flipper, it rotates 90 degrees, or if this 
     * gadget is an absorber shoots out an already stored ball, otherwise does nothing.
     */  
	public void action(List<Ball> listOfBalls); 
	
	/**
	 * An event that happens at this gadget when a ball collides with it.
	 */
	public void triggers(List<Ball> listOfBall);
	
}