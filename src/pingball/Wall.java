package pingball;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import physics.LineSegment;
import physics.Vect;



/*
 *The wall is a line boarder of the play field which could be either horizontal (y = 0L or 20L) or 
 * a vertical (x = 0L or 20L). Each wall may be either solid or invisible. 
 */



public class Wall implements Gadget{
	private final Board board;     //the board which the wall stands for
    private final WallType location; //location restricted to be: "top", "bottom", "right" or "left"
    private boolean visible;         // if the wall could be either solid or visible
    private double ReflectionCoefficient = 1.0;
    private List<Gadget> toTrigger;

    
    /**
     * 
     * @param board the board to be place
     * @param name type of wall, top, bottom, left, or right
     * @param visible, invisible or not
     * @param toTrigger, list of gadget to trigger
     */
	public Wall(Board board, WallType name, Boolean visible,List<Gadget> toTrigger){
		this.board = board;
		this.location = name;
		this.visible = visible;
		this.toTrigger = toTrigger;

	}
	
	/**
	 * Finds appropriate line segment depending wall type
     * @return the line segment to be used
     */
	public LineSegment getWall(){
	    LineSegment segment = null;
	    switch (this.location){
	        case TOP: segment = new LineSegment(0,20,20,20);
	                    break;
	        case BOTTOM: segment= new LineSegment(0,0,20,0);
	                     break;
	        case LEFT: segment = new LineSegment(0,0,0,20);
	                    break;
	        case RIGHT: segment = new LineSegment(20,0,20,20);
	                    break;
	        }
	    return segment;
	       
	}
	
    /**
     * Returns the visibility of this wall
     * @return true if this wall is visible, otherwise false
     */
	public boolean isVisible(){
		return visible;
	}
	
	
	/**
     * @effect Updates this wall's coefficient of reflection
     */
	public void setReflectionCoeff(double reflectCoeff){
		this.ReflectionCoefficient = reflectCoeff;
	}
	
	/**
     * @effect Updates this wall's visibility
     */
	public void setVisibility(boolean visibility){
	    this.visible = visibility;
	}
	/**
     * Finds the time until the given ball collides with the wall.
     * @param ball the ball whose collision time is to be calculated.
     * @return the double time until the ball collides with the wall.
     */
	@Override
	public double getCollisionTime(Ball ball){
	    return physics.Geometry.timeUntilWallCollision(this.getWall(), ball.getCircle(), ball.getVelocity());
	}
	
	/**
     * @returns the name of the wall
     */
	@Override
	public String getName() {
	    return this.location.location;
	}
    /**
     *@return x coordinate of wall's upper left corner 
     */
	@Override
	public int getX() {
	    return (int) this.getWall().p1().x();
	}

	/**
     * @return y coordinate of wall's upper left corner  
     */
	@Override
	public int getY() {
	    return (int) this.getWall().p1().y();
	}
	/**
     * @returns 0 since there is no orientation for the wall
     */
	@Override
	public int getOrientation() {
		return 0;
	}
	/**
     * @returns int of the wall's width, which is 20
     */
	@Override
	public int getWidth() {
	    return 20;
	}
	/**
     * @returns int of the wall's height, which is 20
     */
	@Override
	public int getHeight() {
		return 20;
	}
	/**
     * @returns the value of the coefficient of reflection of the wall
     */
	@Override
	public double getReflectionCoefficient() {
	    return this.ReflectionCoefficient;
	}
	/**
     * @effect Updates the velocity and the position of the ball right when the ball hits the wall.
     * @param ball that triggers the wall by hitting the wall
     * 
     */
	@Override
	public void reflect(Ball ball) {
	    Vect newVect = physics.Geometry.reflectWall(this.getWall(), ball.getVelocity());
	    ball.setVelocity(newVect);
	}

	/**
	 * @effect does nothing
	 */
	@Override
	public void action(List<Ball> listOfBalls) {		
	}
	/**
     * The event where the ball collides with the wall
     * @param gadget the wall
     */
	@Override

	/**
     * @returns the list of coordinates of the corners of the wall
     */

    public void triggers(List<Ball> listOfBall) {
        for (Gadget gad: this.toTrigger){
            gad.action(listOfBall);
        }
        
    }

	/**
	 * @return coordinates pairs of the walls
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Pair> coordinates() {
		
		List<Pair> coords = new ArrayList<Pair>();
		
        if ( !this.isVisible()){
        	return coords;
        	
        }else if( this.location.equals(WallType.TOP)){
        	int y = 0;
        	for (int x = 0; x < 22; x++){
        		coords.add( new Pair( x, y));
        	}
        }else if(this.location.equals(WallType.RIGHT)){
        	int x = 21;
        	for (int y = 1; y < 21; y++){
        		coords.add( new Pair( x, y));
        	}
        }else if(this.location.equals(WallType.BOTTOM)){
        	int y = 21;
        	for (int x = 0; x < 22; x++){
        		coords.add( new Pair( x, y));
        	}
        }else{
        	int x = 0;
        	for (int y = 1; y < 21; y++){
        		coords.add( new Pair( x, y));
        	}
        }
        return coords;
	}
	/**
     * @returns String of the wall's representation
     */
    @Override public String toString(){
    	return ".";
    }
	
	
}
