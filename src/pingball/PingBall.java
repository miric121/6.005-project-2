package pingball;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PingBall {
	public static void main(String[] args) throws InterruptedException {
	    if (args.length == 0){
	        textModeDefault();
	    }
	    String boardName = args[0].toLowerCase();
	    if (boardName.equals("flipper")){
	        textModeFlipper();
	    } else if (boardName.equals("default")){
	        textModeDefault();
	    } else if (boardName.equals("absorber")){
	        textModeAbsorber();
	    }
//		textModeDefault();
//		textModeFlipper();
//		textModeAbsorber();
//	    textModeBallcollision();
	}
	
	public static void textModeFlipper() throws InterruptedException{
		//		Ball @ (0.25,3.25) 
		//		Ball @ (5.25,3.25) 
		//		Ball @ (10.25,3.25) 
		//		Ball @ (15.25,3.25) 
		//		Ball @ (19.25,3.25) 
		//		LeftFlipper1 @ (0,8), Orientation: 90° 
		//		LeftFlipper2 @ (4,10), Orientation: 90° 
		//		LeftFlipper3 @ (9,8), Orientation: 90° 
		//		LeftFlipper4 @ (15,8), Orientation: 90° 
		//		Circle Bumper @ (5,18) 
		//		Circle Bumper @ (7,13) 
		//		Circle Bumper @ (0,5), Triggered Gadgets: LeftFlipper1 
		//		Circle Bumper @ (5,5) 
		//		Circle Bumper @ (10,5), Triggered Gadgets: LeftFlipper3 
		//		Circle Bumper @ (15,5), Triggered Gadgets: LeftFlipper4 
		//		Triangle Bumper @ (19,0), Orientation: 90° 
		//		Triangle Bumper @ (10,18), Orientation: 180° 
		//		RightFlipper1 @ (2,15), Orientation: 0° 
		//		RightFlipper2 @ (17,15), Orientation: 0° 
		//		Absorber @ (0,19), k = 20, m = 1, Triggered Gadgets: RightFlipper1, RightFlipper2, Absorber

	
		List<Gadget> empty = new ArrayList<Gadget>();
		

		Ball b1 = new Ball("b1", .25, 3.25, 0, 0 );
		Ball b2 = new Ball("b2", 5.25,3.25, 0, 0 );
		Ball b3 = new Ball("b3", 10.25, 3.25, 0, 0 );
		Ball b4 = new Ball("b4", 15.25, 3.25, 0, 0 );
		Ball b5 = new Ball("b5", 19.25, 3.25, 0, 0 );
		
		LeftFlipper l1 = new LeftFlipper("l1", 0,8, Orientation.rotate90,empty);
		LeftFlipper l2 = new LeftFlipper("l2", 4,10, Orientation.rotate90,empty);
		LeftFlipper l3 = new LeftFlipper("l3", 9,8, Orientation.rotate90,empty);
		LeftFlipper l4 = new LeftFlipper("l4", 15,8, Orientation.rotate90,empty);
		RightFlipper r1 = new RightFlipper("r1", 2,15, Orientation.rotate0,empty);
		RightFlipper r2 = new RightFlipper("r2", 17,15, Orientation.rotate0,empty);
		
		
	
		List<Gadget> Flipp = new ArrayList<Gadget>(Arrays.asList(l1));
		List<Gadget> Flipp3 = new ArrayList<Gadget>(Arrays.asList(l3));
		List<Gadget> Flipp4 = new ArrayList<Gadget>(Arrays.asList(l4));
		List<Gadget> Flipp1_2 = new ArrayList<Gadget>(Arrays.asList(r1,r2));
		CircleBumper c1 = new CircleBumper("c1", 5, 18,empty);
		CircleBumper c2 = new CircleBumper("c2", 7, 13,empty);
		CircleBumper c3 = new CircleBumper("c3", 0, 5,Flipp);
		CircleBumper c4 = new CircleBumper("c4", 5, 5,empty);
		CircleBumper c5 = new CircleBumper("c5", 10, 5, Flipp3);
		CircleBumper c6 = new CircleBumper("c6", 15, 5,Flipp4);
		
		TriangleBumper t1 = new TriangleBumper("t1", 19, 0, Orientation.rotate90,empty);
		TriangleBumper t2 = new TriangleBumper("t2", 10, 18, Orientation.rotate180,empty);
		
		Absorber a1 = new Absorber("a1", 0, 19, 20, 1,Flipp1_2,true);
		List<Ball> balls = new ArrayList<Ball>(Arrays.asList(b1,b2,b3,b4,b5));
		List<Gadget> gadgets = new ArrayList<Gadget>(Arrays.asList(l1, l2,l3, l4, c1, c2, c3, c4, c5, c6, t1, t2, a1,r1,r2));
		Board board = new Board(balls, gadgets);
		System.out.println(board.toString());
		
		board.TextMode();
		

	}
	
	
	public static void textModeAbsorber() throws InterruptedException{
		//		Ball @ (10.25,15.25) 
		//		Ball @ (19.25,3.25) 
		//		Ball @ (1.25,5.25) 
		//		Absorber @ (0,18), k = 20, m = 2 
		//		Triangle Bumper @ (19,0), Orientation: 90° 
		//		Circle Bumper @ (1,10), Triggered Gadgets: Absorber 
		//		Circle Bumper @ (2,10), Triggered Gadgets: Absorber 
		//		Circle Bumper @ (3,10), Triggered Gadgets: Absorber 
		//		Circle Bumper @ (4,10), Triggered Gadgets: Absorber 
		//		Circle Bumper @ (5,10), Triggered Gadgets: Absorber
		
//		Ball b1 = new Ball("b1", 10.25, 15, 0, 3 );
//		Ball b2 = new Ball("b1", 19.5, 3.25, 10, 5 );
//		Ball b3 = new Ball("b1", 1.25, 5, 0, 4 );
//		Ball b4 = new Ball("b1", 19.5, 16.25, 10, 5 );
		
		Ball b1 = new Ball("b1", 10.25, 15.25, 0,0 );
		Ball b2 = new Ball("b2", 19.25, 3.25, 0, 0 );
		Ball b3 = new Ball("b3", 1.25, 5.25, 0, 0 );
		
	    List<Gadget> empty = new ArrayList<Gadget>();
	    Absorber a1 = new Absorber("a1", 0, 18, 20, 2, empty,false);
	    List<Gadget> abs = new ArrayList<Gadget>(Arrays.asList(a1));
		CircleBumper c1 = new CircleBumper("c1", 1, 10,abs);
		CircleBumper c2 = new CircleBumper("c2", 2, 10,abs);
		CircleBumper c3 = new CircleBumper("c3", 3, 10,abs);
		CircleBumper c4 = new CircleBumper("c4", 4, 10,abs);
		CircleBumper c5 = new CircleBumper("c5", 5, 10,abs);
		CircleBumper c6 = new CircleBumper("c6", 5, 10,abs);
		
		TriangleBumper t1 = new TriangleBumper("t1", 19, 0, Orientation.rotate90,empty);
		
		
		
		
		List<Ball> balls = new ArrayList<Ball>(Arrays.asList(b1,b2,b3));
//		balls.addAll(Arrays.asList(stableBall));
		List<Gadget> gadgets = new ArrayList<Gadget>(Arrays.asList(c1, c2, c3, c4, c5, c6, t1, a1));
		Board board = new Board(balls, gadgets);
		System.out.println(board.toString());
		
		board.TextMode();

	}
	
	
	public static void textModeDefault() throws InterruptedException{
		//		Ball @ (1.25,1.25) 
		//		Circle Bumper @ (1,10) 
		//		Triangle Bumper @ (12,15), Orientation: 180° 
		//		Square Bumper @ (0,17) 
		//		Square Bumper @ (1,17) 
		//		Square Bumper @ (2,17) 
		
		//		Circle Bumper @ (7,18) 
		//		Circle Bumper @ (8,18) 
		//		Circle Bumper @ (9,18)
	    List<Gadget> empty = new ArrayList<Gadget>();
		CircleBumper c1 = new CircleBumper("c1", 1, 10,empty);
		CircleBumper c2 = new CircleBumper("c2", 7, 18,empty);
		CircleBumper c3 = new CircleBumper("c3", 8, 18,empty);
		CircleBumper c4 = new CircleBumper("c4", 9, 18,empty);
		
		TriangleBumper t1 = new TriangleBumper("t1",12,15,Orientation.rotate180,empty);
		SquareBumper s1 = new SquareBumper( "s1", 0, 17,empty);
		SquareBumper s2 = new SquareBumper( "s2", 1, 17,empty);
		SquareBumper s3 = new SquareBumper( "s3", 2, 17,empty);
	
		
		
		Ball b1 = new Ball("b1", 1.25, 1.25, 0, 0 );

		
		List<Ball> balls = new ArrayList<Ball>(Arrays.asList(b1));
		List<Gadget> gadgets = new ArrayList<Gadget>(Arrays.asList(s1,s2,s3,c1,c2,c3,c4,t1));
		Board board = new Board(balls, gadgets);
		
		board.TextMode();

	}
	public static void textModeBallcollision() throws InterruptedException{
	    Ball b1 = new Ball("b1",2,5,5,0);
	    Ball b2 = new Ball("b2",17,5,-2,0);
	    List<Ball> balls = new ArrayList<Ball>(Arrays.asList(b1,b2));
	    List<Gadget> empty = new ArrayList<Gadget>();
	    Board board = new Board(balls, empty);
	    board.setGravity(0);
	    board.setMU(0);
	    board.setMU2(0);
	   
	    board.TextMode();
	}

}
