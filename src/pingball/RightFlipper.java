package pingball;


import java.util.List;
import java.util.ArrayList;

import javafx.util.Pair;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;


public class RightFlipper extends Flipper {

	
    /**
     * Constructor for the Right Flipper Class
     * @param name; the name of this flipper
     * @param x; the x coordinate of the square bumper in the board
     * @param y; the y coordinate of the square bumper in the board
     * @param orientation; the orientation of the right flipper, 0, 90, 180, 270 degrees
     * @param toTrigger; list of gadgets to be triggered

     */
	public RightFlipper(String name, int x, int y, Orientation orientation, List<Gadget> toTrigger){
		this.name = name;
        this.newName = new String(name);
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.center = new Vect(x+1,y+1);
		this.rotating = false;
        if(orientation.rotation == 0){
        	this.fixedEnd = new Circle(x+2, y, 0);
        	this.movableEnd = new Circle(x+2, y+2, 0);
        	this.segment = new LineSegment(x+2, y, x+2, y+2);
        }else if(orientation.rotation == 90){
        	this.fixedEnd = new Circle(x+2, y+2, 0);
        	this.movableEnd = new Circle(x, y+2, 0);
        	this.segment = new LineSegment(x, y+2, x+2, y+2);
        }else if(orientation.rotation == 180){
        	this.fixedEnd = new Circle(x, y+2, 0);
        	this.movableEnd = new Circle(x, y, 0);
        	this.segment = new LineSegment(x, y+2, x, y);
        }else{
        	this.fixedEnd = new Circle(x, y, 0);
        	this.movableEnd = new Circle(x+2, y, 0);
        	this.segment = new LineSegment(x, y, x+2, y);
        }
        this.toTrigger = toTrigger;
    }
    
	
	/**
     * @return return the name of the flipper
     */
	@Override
	public String getName() {
		return this.name;
	}
	/**
     * Returns the right flipper's upper left corner position from the board's origin(0,0).
     * @return integer x coordinate representing the upper left corner
     */

    @Override
    public int getX() {
        return this.x;
    }
    /**
     *Returns the right flipper's left corner position from the board's origin(0,0).
     * @return integer y coordinate representing the upper left corner
     */
    @Override
    public int getY() {
        return this.y;
    }
    /**
     * @return the clockwise rotation in degrees around the center of the bounding box of the gadget 
     * 0, 90, 180, 270 degrees
     * 
     */
	@Override
	public int getOrientation() {
	    return this.orientation.rotation;
	}
	/**
     * @return the width of the right flipper
     */
	@Override
	public int getWidth() {
	    return 2;
	}
	/**
     * @return the height of the right flipper
     */
	@Override
	public int getHeight() {
	    return 2;
	}

	/**
	 * @return the line segment representing the right flipper
	 */
	public LineSegment getLineSegment() {
	    return segment;
	}


	/**
     * @return the value of the coefficient of reflection
     * decreases the velocity of the ball after the ball hits the flipper
     */
	@Override
	public double getReflectionCoefficient() {
	    return this.reflection;
	}


	/**
     * @effect rotates 90 degrees when the ball hits the right flipper
     * direction of initial rotation is clockwise direction
     */
    @Override
    public void action(List<Ball> listOfBalls) {
        checkRep();
        rotating = true;
        final double flipSpeed= -18.8495559;
        for (Ball ball: listOfBalls){
            boolean xDi = ball.getX() <= (this.getX() + this.getWidth());
            boolean xDi2 = ball.getX() >= this.getX();
            boolean yDi = (ball.getY() >= this.getY());
            boolean yDi2 =(ball.getY() <= (this.getY()+this.getHeight()));
           if (xDi && xDi2 && yDi && yDi2){ 
               double angularVel = flipSpeed;
               if (rotated){
                  angularVel = -angularVel;
               }
               Vect newVect = physics.Geometry.reflectRotatingWall(segment, fixedEnd.getCenter(), angularVel, ball.getCircle(), ball.getVelocity(),this.reflection);
               ball.setVelocity(newVect);
           }
        }
        if (!rotated){
            if(orientation.rotation == 0){
                this.movableEnd = new Circle(x, y, 0);
                this.segment = new LineSegment(x+2, y, x, y);
            }else if(orientation.rotation == 90){
                this.movableEnd = new Circle(x+2, y, 0);
                this.segment = new LineSegment(x+2, y+2, x+2, y);
            }else if(orientation.rotation == 180){
                this.movableEnd = new Circle(x+2, y+2, 0);
                this.segment = new LineSegment(x, y+2, x+2, y+2);
            }else{
                this.movableEnd = new Circle(x, y+2, 0);
                this.segment = new LineSegment(x, y+2, x, y);
            }
        }
        else if (rotated){
            if(orientation.rotation == 0){
                this.movableEnd = new Circle(x+2, y+2, 0);
                this.segment = new LineSegment(x+2, y, x+2, y+2);
            }else if(orientation.rotation == 90){
                this.movableEnd = new Circle(x, y+2, 0);
                this.segment = new LineSegment(x, y+2, x+2, y+2);
            }else if(orientation.rotation == 180){
                this.movableEnd = new Circle(x, y, 0);
                this.segment = new LineSegment(x, y, x, y+2);
            }else{
                this.movableEnd = new Circle(x+2, y, 0);
                this.segment = new LineSegment(x, y, x+2, y);
            }
        }
        rotated = !rotated;
        rotating = false;
    }
    /**
     * @effect The event where the ball collides with the right flipper
     * @param gadget the right flipper
     */
	@Override

    public void triggers(List<Ball> listOfBall) {
	    checkRep();
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
        assert (orientation.rotation == 0)|| (orientation.rotation == 90) ||(orientation.rotation == 180) ||(orientation.rotation == 270) ;
    }
	

    /**
     * @return the list of coordinates of the upper left corner of the box in which the right flipper is occupied by
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Pair> coordinates() {
		
		List<Pair> coords = new ArrayList<Pair>();
		
		if (!rotated){	
			if(orientation.rotation == 0){
				coords.add( new Pair( x+1, y));
				coords.add( new Pair( x+1, y+1));
			}else if(orientation.rotation == 90){ 
				coords.add( new Pair( x, y+1));
				coords.add( new Pair( x+1,y+1));
			}else if(orientation.rotation == 180){
				coords.add( new Pair( x, y) );
				coords.add( new Pair( x+1, y));
			}else{
				coords.add( new Pair( x, y));
				coords.add( new Pair( x+1, y));
			}
		}else{
			if(orientation.rotation == 0){
				coords.add( new Pair( x, y));
				coords.add( new Pair( x+1, y));
			}else if(orientation.rotation == 90){ 
				coords.add( new Pair( x+1, y));
				coords.add( new Pair( x+1,y+1));
			}else if(orientation.rotation == 180){
				coords.add( new Pair( x, y+1) );
				coords.add( new Pair( x+1, y+1));
			}else{
				coords.add( new Pair( x, y+1));
				coords.add( new Pair( x, y));
			}
		}
        return coords;
	}
	/**
     * @return String of the right flipper's representation
     */
    @Override public String toString(){
    	if ( (orientation.rotation == 0 && (!rotated) )
    			|| (orientation.rotation == 90 && (rotated))
    			|| (orientation.rotation == 180 && (!rotated))
    			|| (orientation.rotation == 270 && (rotated))){
    		return "|";
    	}
    	return "-";
    }
}
