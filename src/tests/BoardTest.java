package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pingball.*;

public class BoardTest {

	/*
	 * Test cases for Board (the game field)
	 * Partition:
	 * 	1. Create empty Board
	 * 	2. Create Board with 
	 * 			Ball
	 * 			All kind of gadgets	
	 *  3. Set gravity and friction 
	 *  4. Ball under Gravity
	 *  5. Ball under Friction
	 */

	private final String[][] emptyBoard = new String[22][22];
	
    public String getString( String[][] matrix){
    	String matrixString = "";
    	for (int r = 0; r < 22; r++){
    		String line ="";
    		for(int c = 0; c < 22; c++){
    			if( c == 0 || c == 21 || r == 0 || r == 21){
    				line += ".";
    			}else{
    				line += matrix[c][r];
    			}
    		}
    		line +="\n";
    		matrixString += line;
    	}
    	return matrixString;
    }
	
	
	

	/*------------------ Test Cases for Constructor ---------------------
	 */
	
	@Test
	public void testEmptyBoard(){
		Board board = new Board( new ArrayList<Ball>(),  new ArrayList<Gadget>());
		for (int i = 0; i<22; i++){
			for (int j = 0; j<22; j++){
					emptyBoard[i][j] = " ";
				}
			}
		
		assertTrue(board.toString().equals(getString(emptyBoard)));
		}
	
	
	@Test
	public void testBoardWithGadgets(){
		
		List<Gadget> empty = new ArrayList<Gadget>();
		CircleBumper c = new CircleBumper("c", 0, 0, empty);
		TriangleBumper t = new TriangleBumper("t", 19,19 ,Orientation.rotate180, empty);
		SquareBumper s = new SquareBumper( "s", 10, 10,empty);
		Ball b = new Ball("b",2,2,5,0);
	    Absorber a = new Absorber("a1", 0, 18, 20, 1, empty,false);

	
		Board board = new Board( new ArrayList<Ball>(),  new ArrayList<Gadget>(Arrays.asList(c,t,s,b,a)));
		for (int i = 0; i<22; i++){
			for (int j = 0; j<22; j++){
					emptyBoard[i][j] = " ";
				}
			}
		
		
		emptyBoard[1][1] = "O";
		emptyBoard[2][2] = "*";  
		emptyBoard[20][20]  = "/";
		emptyBoard[11][11] = "#";
		
		for (int i= 0; i <21; i++){
			emptyBoard[i][19] = "=";
		}

		assertTrue(board.toString().equals(getString(emptyBoard)));
		}
	
	
	@Test
	public void testBoardWithFlippers(){
		List<Gadget> empty = new ArrayList<Gadget>();
		LeftFlipper l1 = new LeftFlipper("l1", 0,0, Orientation.rotate180,empty);
		LeftFlipper l2 = new LeftFlipper("l2", 0,2, Orientation.rotate90,empty);
		RightFlipper r1 = new RightFlipper("r1", 0,4, Orientation.rotate0,empty);
		RightFlipper r2 = new RightFlipper("r2", 0,6, Orientation.rotate0,empty);

	
		Board board = new Board( new ArrayList<Ball>(),  new ArrayList<Gadget>(Arrays.asList(l1,l2,r1,r2)));
		for (int i = 0; i<22; i++){
			for (int j = 0; j<22; j++){
					emptyBoard[i][j] = " ";
				}
			}
		
		
		emptyBoard[2][1] = "|";
		emptyBoard[2][2] = "|";  
		
		emptyBoard[1][3]  = "-";
		emptyBoard[2][3] = "-";
		

		emptyBoard[2][5] = "|";
		emptyBoard[2][6]  = "|";
		emptyBoard[2][7] = "|";
		emptyBoard[2][8]  = "|";
	
		assertTrue(board.toString().equals(getString(emptyBoard)));
		}
	

	/*------------------ Test Cases for Gravity ---------------------
	 */

	@Test
	public void testBoardGravity(){
		Ball b = new Ball("b",10,10,5,0);
		Board board = new Board( new ArrayList<Ball>(Arrays.asList(b)),  new ArrayList<Gadget>());
		board.setGravity(25);
		board.setMU(0);
		board.setMU2(0);
		board.simulate(2);
		assertTrue(b.getSpeed() > 5 );
	}
	
	
	/*------------------ Test Cases for Friction ---------------------
	 */

	@Test
	public void testBoardFriction(){
		Ball b = new Ball("b",10,10,5,0);
		Board board = new Board( new ArrayList<Ball>(Arrays.asList(b)),  new ArrayList<Gadget>());
		board.setGravity(0);
		board.setMU(2);
		board.setMU2(2);
		board.simulate(2);
		assertTrue(b.getSpeed() < 5 );
	}

}