package warmup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import physics.*;


/**
 * 
 * 
 * TODO: put documentation for your class here
 */
public class Main {
	public static String[][] createStaticEnvironment(){
		String[][] board=  new String[21][21];


		for (int i = 0; i<21; i++){
			for (int j = 0; j<21; j++){
				if (i == 0 || i == 20 || j == 0 || j == 20){
					board[i][j] = ".";
				}else{
					board[i][j] = " ";
				}
			}
		}
		return board;
	}

	public static void createEnvironment(){
		Angle angle = new Angle(1.0);
		Vect ball = new Vect(angle, 20.0);}

	public static void displayEnvironment(double x, double y){
		String[][] room = createStaticEnvironment();
		int cellX = (int) Math.ceil(x);
		int cellY = (int) Math.ceil(y);
		
		room[cellX][cellY] = "*";
		
		//Print matrix
		for (int i = 0; i<21; i++){
			String line ="";
			for (int j = 0; j<21; j++){
				line += room[i][j];
			}
			System.out.println(line);
		}
	}

    /**
     * TODO: describe your main function's command line arguments here
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        double budg = 19.100000000000005;
        LineSegment ceil = new LineSegment(0, 0 , 20, 0);
        LineSegment floor = new LineSegment(0, 20 , 20, 20);
        LineSegment rightWall = new LineSegment(20, 0 , 20, 20);
        LineSegment leftWall = new LineSegment(0, 0 , 0, 20);
        Circle topRight = new Circle(20,20,0);
        Circle topLeft = new Circle(0,0,0);
        Circle bottomLeft = new Circle(0,20,0);
        Circle bottomRight = new Circle(20,0,0);
        List<Circle> corners= new ArrayList<Circle>(Arrays.asList(topRight,topLeft,bottomLeft,bottomRight));
        List<LineSegment> boundaries = new ArrayList<LineSegment>();
        boundaries.add(ceil);
        boundaries.addAll(Arrays.asList(floor,rightWall,leftWall));
        Ball ball = new Ball( new Circle( new Vect(10,10.0), .1), new Vect(-5,-7));
        Map<Double, LineSegment> timeTilWallCollisions;
        Map<Double, Circle> timeTilCornerCollisions;

        displayEnvironment(ball.getBall().getCenter().x(),ball.getBall().getCenter().y());
        while (true){
            timeTilWallCollisions = new HashMap<Double, LineSegment>();
            timeTilCornerCollisions = new HashMap<Double, Circle>();
            double timeTilNextCollision;
            Vect newVel = new Vect(0,0);
            Circle nextCornerCollision = null;
            boolean cornerC= false;
            for (LineSegment boundary: boundaries){
                double tc = physics.Geometry.timeUntilWallCollision(boundary, ball.getBall(), ball.getVelocity());
                System.out.println(ball.getBall().getCenter());
                System.out.println("Velocity is: " + ball.getVelocity());
                System.out.println(tc+ " "+ " "+ boundary);
                timeTilWallCollisions.put(tc, boundary);
            }
            for (Circle corner: corners){
                double tc = physics.Geometry.timeUntilCircleCollision(corner, ball.getBall(), ball.getVelocity());
                System.out.println(tc+ " "+ " "+ corner);
                timeTilCornerCollisions.put(tc, corner);
            }
            Double timeTilNextWallCollision = Collections.min(timeTilWallCollisions.keySet());
            Double timeTilNextCornerCollision = Collections.min(timeTilCornerCollisions.keySet());
            if (timeTilNextWallCollision < timeTilNextCornerCollision){
                timeTilNextCollision = timeTilNextWallCollision;
                LineSegment nextCollision = timeTilWallCollisions.get(timeTilNextWallCollision);
                newVel = physics.Geometry.reflectWall(nextCollision, ball.getVelocity());
            } else {
                timeTilNextCollision = timeTilNextCornerCollision;
                nextCornerCollision = timeTilCornerCollisions.get(timeTilNextCornerCollision);
                cornerC = true;

            }
            if (!Double.isInfinite(timeTilNextCollision)){
            long expectedTime= (long) (System.currentTimeMillis() + timeTilNextCollision*1000);
            while (System.currentTimeMillis() < expectedTime){
                ball.updateLocation(.05);
                displayEnvironment(ball.getBall().getCenter().x(),ball.getBall().getCenter().y());
                System.out.println(timeTilNextCollision);
                System.out.println(ball.getBall().getCenter().x()+ "--"+ ball.getBall().getCenter().y());
                System.out.println("Velocity is: "+ball.getVelocity());
                Thread.sleep(50);     
            }
            timeTilWallCollisions.clear();
            timeTilCornerCollisions.clear();
            if (cornerC){
                newVel = physics.Geometry.reflectCircle(nextCornerCollision.getCenter(), ball.getBall().getCenter(), ball.getVelocity());
            }
            ball.updateVelocity(newVel);

        }
        
      }   
    }
}
    

