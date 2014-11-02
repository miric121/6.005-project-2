package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import physics.LineSegment;
import physics.Vect;
import pingball.*;
public class WallTest {
    private static List<Ball> emptyBall;
    private static List<Gadget> emptyGadget;
    /**
     * Wall():
     *     Walltype: Top, left, right, bottom;
     *     visible: true, false;
     *     Triggers: NONE according to specs;
     * GetCollisionTime(ball):
     *          Wall: the corners, top , bottom, left, right
     *          Ball: negative/positive velocity
     * reflect():
     *          Ball: negative/positive velocity
     *          Wall: Top, right, bottom, left
     */
    @BeforeClass
    public static void setUpBeforeClass(){
        emptyBall = new ArrayList<Ball>();
        emptyGadget = new ArrayList<Gadget>();
    }
    /////////TEST GET WALL////////////////////
    @Test
    public void testGetWallTop() {
        Board b = new Board(emptyBall,emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);

        assertTrue(top.getWall().equals(new LineSegment(0,20,20,20)));
    }
    @Test
    public void testGetWallBottom() {
        Board b = new Board(emptyBall,emptyGadget);
        Wall wall = new Wall(b, WallType.BOTTOM, false, emptyGadget);

        assertTrue(wall.getWall().equals(new LineSegment(0,0,20,0)));
    }
    @Test
    public void testGetWallLeft() {
        Board b = new Board(emptyBall,emptyGadget);
        Wall wall = new Wall(b, WallType.LEFT, true, emptyGadget);
        assertTrue(wall.isVisible());
        assertTrue(wall.getWall().equals(new LineSegment(0,0,0,20)));
    }  
    @Test
    public void testGetWallRight() {
        Board b = new Board(emptyBall,emptyGadget);
        Wall wall = new Wall(b, WallType.RIGHT, false, emptyGadget);

        assertTrue(wall.getWall().equals(new LineSegment(20,0,20,20)));
        assertFalse(wall.isVisible());
    }
    /////////TEST COLLISION////////////////////
    @Test
    public void testGetCollisionTimeTopLeftCorner(){
        Ball ball = new Ball("b1",1,1,-1,-1);
        Board b = new Board(emptyBall,emptyGadget);
        Wall left = new Wall(b, WallType.LEFT, false, emptyGadget);
        Wall bottom = new Wall(b, WallType.BOTTOM, false, emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);
        Wall right = new Wall(b, WallType.RIGHT, false, emptyGadget);
        Double tTC = left.getCollisionTime(ball);
        Double tTC2 = bottom.getCollisionTime(ball);
        Double tTC3 = top.getCollisionTime(ball);
        Double tTC4 = right.getCollisionTime(ball);
        assertTrue(tTC < Double.POSITIVE_INFINITY);
        assertTrue(tTC2 < Double.POSITIVE_INFINITY);
        assertTrue(tTC3 == Double.POSITIVE_INFINITY);
        assertTrue(tTC4 == Double.POSITIVE_INFINITY);
    }
    @Test
    public void testGetCollisionTimeTopRightCorner(){
        Ball ball = new Ball("b1",19,1,1,-1);
        Board b = new Board(emptyBall,emptyGadget);
        Wall left = new Wall(b, WallType.LEFT, false, emptyGadget);
        Wall bottom = new Wall(b, WallType.BOTTOM, false, emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);
        Wall right = new Wall(b, WallType.RIGHT, false, emptyGadget);
        Double tTC = left.getCollisionTime(ball);
        Double tTC2 = bottom.getCollisionTime(ball);
        Double tTC3 = top.getCollisionTime(ball);
        Double tTC4 = right.getCollisionTime(ball);
        assertTrue(tTC == Double.POSITIVE_INFINITY);
        assertTrue(tTC2 < Double.POSITIVE_INFINITY);
        assertTrue(tTC3 == Double.POSITIVE_INFINITY);
        assertTrue(tTC4 < Double.POSITIVE_INFINITY);
    }
    @Test
    public void testGetCollisionTimeBottomLeftCorner(){
        Ball ball = new Ball("b1",1,19,-1,1);
        Board b = new Board(emptyBall,emptyGadget);
        Wall left = new Wall(b, WallType.LEFT, false, emptyGadget);
        Wall bottom = new Wall(b, WallType.BOTTOM, false, emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);
        Wall right = new Wall(b, WallType.RIGHT, false, emptyGadget);
        Double tTC = left.getCollisionTime(ball);
        Double tTC2 = bottom.getCollisionTime(ball);
        Double tTC3 = top.getCollisionTime(ball);
        Double tTC4 = right.getCollisionTime(ball);
        assertTrue(tTC < Double.POSITIVE_INFINITY);
        assertTrue(tTC2 == Double.POSITIVE_INFINITY);
        assertTrue(tTC3 < Double.POSITIVE_INFINITY);
        assertTrue(tTC4 == Double.POSITIVE_INFINITY);
    }
    @Test
    public void testGetCollisionTimeBottomRightCorner(){
        Ball ball = new Ball("b1",19,19,1,1);
        Board b = new Board(emptyBall,emptyGadget);
        Wall left = new Wall(b, WallType.LEFT, false, emptyGadget);
        Wall bottom = new Wall(b, WallType.BOTTOM, false, emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);
        Wall right = new Wall(b, WallType.RIGHT, false, emptyGadget);
        Double tTC = left.getCollisionTime(ball);
        Double tTC2 = bottom.getCollisionTime(ball);
        Double tTC3 = top.getCollisionTime(ball);
        Double tTC4 = right.getCollisionTime(ball);
        assertTrue(tTC == Double.POSITIVE_INFINITY);
        assertTrue(tTC2 == Double.POSITIVE_INFINITY);
        assertTrue(tTC3 < Double.POSITIVE_INFINITY);
        assertTrue(tTC4 < Double.POSITIVE_INFINITY);
    }
    @Test
    public void testGetCollisionTimeLeftCorner(){
        Ball ball = new Ball("b1",5,5,-5,0);
        Board b = new Board(emptyBall,emptyGadget);
        Wall left = new Wall(b, WallType.LEFT, false, emptyGadget);
        Wall bottom = new Wall(b, WallType.BOTTOM, false, emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);
        Wall right = new Wall(b, WallType.RIGHT, false, emptyGadget);
        Double tTC = left.getCollisionTime(ball);
        Double tTC2 = bottom.getCollisionTime(ball);
        Double tTC3 = top.getCollisionTime(ball);
        Double tTC4 = right.getCollisionTime(ball);
        assertTrue(tTC < Double.POSITIVE_INFINITY);
        assertTrue(tTC2 == Double.POSITIVE_INFINITY);
        assertTrue(tTC3 == Double.POSITIVE_INFINITY);
        assertTrue(tTC4 == Double.POSITIVE_INFINITY);
    }
    @Test
    public void testGetCollisionTimeRight(){
        Ball ball = new Ball("b1",5,5,1,0);
        Board b = new Board(emptyBall,emptyGadget);
        Wall left = new Wall(b, WallType.LEFT, false, emptyGadget);
        Wall bottom = new Wall(b, WallType.BOTTOM, false, emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);
        Wall right = new Wall(b, WallType.RIGHT, false, emptyGadget);
        Double tTC = left.getCollisionTime(ball);
        Double tTC2 = bottom.getCollisionTime(ball);
        Double tTC3 = top.getCollisionTime(ball);
        Double tTC4 = right.getCollisionTime(ball);
        assertTrue(tTC == Double.POSITIVE_INFINITY);
        assertTrue(tTC2 == Double.POSITIVE_INFINITY);
        assertTrue(tTC3 == Double.POSITIVE_INFINITY);
        assertTrue(tTC4 < Double.POSITIVE_INFINITY);
    }
    @Test
    public void testGetCollisionTimeBottom(){
        Ball ball = new Ball("b1",5,5,0,10);
        Board b = new Board(emptyBall,emptyGadget);
        Wall left = new Wall(b, WallType.LEFT, false, emptyGadget);
        Wall bottom = new Wall(b, WallType.BOTTOM, false, emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);
        Wall right = new Wall(b, WallType.RIGHT, false, emptyGadget);
        Double tTC = left.getCollisionTime(ball);
        Double tTC2 = bottom.getCollisionTime(ball);
        Double tTC3 = top.getCollisionTime(ball);
        Double tTC4 = right.getCollisionTime(ball);
        assertTrue(tTC == Double.POSITIVE_INFINITY);
        assertTrue(tTC2 == Double.POSITIVE_INFINITY);
        assertTrue(tTC3 < Double.POSITIVE_INFINITY);
        assertTrue(tTC4 == Double.POSITIVE_INFINITY);
    }
    @Test
    public void testGetCollisionTimeTop(){
        Ball ball = new Ball("b1",5,5,0,-10);
        Board b = new Board(emptyBall,emptyGadget);
        Wall left = new Wall(b, WallType.LEFT, false, emptyGadget);
        Wall bottom = new Wall(b, WallType.BOTTOM, false, emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);
        Wall right = new Wall(b, WallType.RIGHT, false, emptyGadget);
        Double tTC = left.getCollisionTime(ball);
        Double tTC2 = bottom.getCollisionTime(ball);
        Double tTC3 = top.getCollisionTime(ball);
        Double tTC4 = right.getCollisionTime(ball);
        assertTrue(tTC == Double.POSITIVE_INFINITY);
        assertTrue(tTC2 < Double.POSITIVE_INFINITY);
        assertTrue(tTC3 == Double.POSITIVE_INFINITY);
        assertTrue(tTC4 == Double.POSITIVE_INFINITY);
    }
    /////////TEST REFLECT////////////////////
    @Test
    public void testReflectTopAndBottom(){
        Ball ball = new Ball("b1",5,5,5,-10);
        Ball ball2 = new Ball("b2",6,6,6,10);
        Board b = new Board(emptyBall,emptyGadget);
        Wall bottom = new Wall(b, WallType.BOTTOM, false, emptyGadget);
        Wall top = new Wall(b, WallType.TOP, false, emptyGadget);
        top.reflect(ball);
        bottom.reflect(ball2);
        assertFalse(ball.getVelocity().equals(new Vect(5,-10)));
        assertFalse(ball2.getVelocity().equals(new Vect(6,10)));
    }
    @Test
    public void testReflectLeftAndRight(){
        Ball ball = new Ball("b1",5,5,-10,4);
        Ball ball2 = new Ball("b2",6,6,10,3);
        Board b = new Board(emptyBall,emptyGadget);
        Wall left = new Wall(b, WallType.LEFT, false, emptyGadget);
        Wall right = new Wall(b, WallType.RIGHT, false, emptyGadget);
        right.reflect(ball);
        left.reflect(ball2);
        assertFalse(ball.getVelocity().equals(new Vect(-10,4)));
        assertFalse(ball2.getVelocity().equals(new Vect(10,3)));
    }


}
