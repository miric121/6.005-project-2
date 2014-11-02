package pingball;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javafx.util.Pair;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/*
 * 
 *Board is a framework of the game and a play field, containing gadgets and balls.
 *Each board has size of 20L * 20L, having top, left, bottom and right walls.
 *The origin of a board is in the upper left-hand corner with coordinates increasing to the right and down.
 *
 *Boards could be connected if they are neighbors and share an invisible wall. 
 *
 *RI:
 *
 */
public class Board {
	private final List<Ball> balls; 
	private final List<Gadget> gadgetSet; //all gadgets excluding balls and walls
	private List<Wall> walls = new ArrayList<Wall>();
	
	private final double rate = 0.05; //(20 frames per second)
	private double gravity = 25;// (L/sec2) could be changed
	private double mu = 0.025;  //(per second) could be changed
	private double mu2 = 0.025; //(per L) could be changed
	
	private final Wall topWall = new Wall(this, WallType.TOP, true, new ArrayList<Gadget>(Arrays.asList()));
	private final Wall bottomWall = new Wall(this, WallType.BOTTOM, true,new ArrayList<Gadget>(Arrays.asList()));
	private final Wall leftWall = new Wall(this, WallType.LEFT, true,new ArrayList<Gadget>(Arrays.asList()));
	private final Wall rightWall = new Wall(this, WallType.RIGHT, true,new ArrayList<Gadget>(Arrays.asList()));
	
	private String[][] matrix = new String[22][22];
		
    /**
     * The constructor for a board
     * 
     * Create a new board, having four solid walls, gadgets, balls and
     * 						default values of frameRate, frameRate, mu and mu2.
     * 					 
     * @param balls all the balls that are to be included in the board at creation
     * @param gadgets all the gadgets (neither ball nor wall) that are to be included in the board
     */
	public Board(List<Ball> balls, List<Gadget> gadgetsNotBallNotWall ){
		this.balls = balls;
		this.gadgetSet = gadgetsNotBallNotWall;
		this.walls.addAll(Arrays.asList(topWall, bottomWall, leftWall, rightWall));
		emptyMatrix();
		updateMatrix();
	}

	
    /**
     * creates empty field for board
     */
    public void emptyMatrix(){
		for (int i = 0; i<22; i++){
			for (int j = 0; j<22; j++){
					matrix[i][j] = " ";
				}
			}
		}
    
    
    /**
     * @Returns a String representation of this Board, including its all gadgets.
     */
    @Override public String toString(){
    	String matrixString = "";
    	for (int r = 0; r < 22; r++){
    		String line ="";
    		for(int c = 0; c < 22; c++){
    			line += matrix[c][r];
    		}
    		line +="\n";
    		matrixString += line;
    	}
    	return matrixString;
    }
    	
    
    /**
     * Updates the matrix to current gadgets.
     */
    @SuppressWarnings("rawtypes")
    public void updateMatrix(){
    	emptyMatrix();
    	for (Gadget gadget: gadgetSet){
			List<Pair> coords = gadget.coordinates();
			for (Pair coord: coords){
				matrix[(int) coord.getKey()+1][(int) coord.getValue()+1] = gadget.toString();
			}
		}
    	
    	for (Gadget ball: balls){
			List<Pair> coords = ball.coordinates();
			for (Pair coord: coords){
				matrix[(int) coord.getKey()+1][(int) coord.getValue()+1] = ball.toString();
			}
		}
    	
    	for (Gadget wall: walls){
			List<Pair> coords = wall.coordinates();
			for (Pair coord: coords){
				matrix[(int) coord.getKey()][(int) coord.getValue()] = wall.toString();
			}
		}
    }
	
	/**
	 * Set other board neighbor to this board whose particular wall would be connection
	 * 
	 * @param other which is going to be neighbor to this board
	 * @param wall which this board's wall
	 */
	public void setNeighorTo(Board other, WallType wall){
		throw new NotImplementedException();
	}
	
	/**
	 * Connect this board to its neighbor board
	 * @param wall which is this board's wall
	 * @return true if this board has a valid neighbor board by given wall and connects successfully. 
	 * 				Otherwise, false
	 */
	public boolean connectTo( WallType wall){
		throw new NotImplementedException();
	}
	
	
	/**
	 * Set the gravity at the given value
	 * @param amount the value to be set
	 */
	public void setGravity(double amount){
	    this.gravity = amount;
	}
	
	/**
	 * Set the mu at the given value
	 * @param amount the value to be set
	 */
	public void setMU(double amount){
	    this.mu = amount;
	}
	
	/**
	 * Set the mu2 at the given value
	 * @param amount the value to be set
	 */
	public void setMU2(double amount){
	    this.mu2 = amount;
	}
	
	
	public void action(Gadget gadget){
		gadget.action( balls);
	}
	
	
	
	/**
	 * Updates all balls positions and velocities for a particular time duration.
	 * During that time, any collision must not happen for the list balls. 
	 * @param balls to be updated
	 * @param time; expected time duration
	 */
	public void moveBalls( HashSet<Ball> balls, double time){
		for (Ball ball: balls){
			ball.move(time,gravity,mu,mu2);
		}
	}
	

	public void simulate(double timeToRate){
		if (balls.isEmpty()){
			return;
		}
		
		//find first collision
		double timeToCollision = Double.POSITIVE_INFINITY;
		Ball ballTo = balls.get(0);        //ball to be in collision first (default)
		Gadget toGadget = this.rightWall;  //gadget to be in collision first  (default)
		HashSet<Ball> ballsNotInCollision = new HashSet<Ball>();  
		
		for (Ball ball: this.balls){
			//check all gadgets
			for (Gadget gadget: gadgetSet){
				if ( gadget.getCollisionTime(ball) < timeToCollision){
					timeToCollision = gadget.getCollisionTime(ball);
					ballTo = ball; 
					toGadget = gadget;
				}
			}
			//check all walls	
			for (Wall wall: walls){
				if ( wall.getCollisionTime(ball) < timeToCollision){
					timeToCollision = wall.getCollisionTime(ball);
					ballTo = ball; 
					toGadget = wall;
				}
			}
			//check all pair of balls //could be improved later
			for (Ball otherBall: balls){
				if (! (ballsNotInCollision.contains(otherBall)|| ball == otherBall)){
					if (otherBall.getCollisionTime(ball) > timeToCollision){ 
						ballsNotInCollision.add(otherBall);
					}else{
						timeToCollision = otherBall.getCollisionTime(ball);
						ballTo = ball; 
						toGadget = otherBall; 
					}
				}
			}
		}
		
		if ( timeToCollision < timeToRate ){
			//collision should happen
			toGadget.reflect(ballTo);
			toGadget.triggers(balls);
			moveBalls(ballsNotInCollision, timeToCollision );
			this.simulate( timeToRate - timeToCollision);
			
		}else{
			moveBalls(new HashSet<Ball>(balls), timeToRate );
		}
	}
	
	/**
	 * Finds text mode of this board at the current time
	 * @return representation of this board, including its gadgets, balls and walls
	 * @throws InterruptedException 
	 */
	public void TextMode() throws InterruptedException{
		while (true){
			simulate(this.rate);
			updateMatrix();
			System.out.println( this.toString());
			Thread.sleep((long) 50);
		}
	
		}
}
