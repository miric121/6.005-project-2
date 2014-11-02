package warmup;
import physics.*;

public class Ball {
    /** 
     * 
     * Mutable class representing a ball with velocity
     * 
     */
    private Circle ball;
    private Vect velocity;
    
    /**
     * 
     * @param ball
     * @param vel
     */
    public Ball(Circle ball, Vect vel){
        this.ball = ball;
        this.velocity = vel;
    }
    
    /**
     * 
     * @return location of the ball
     */
    public Circle getBall(){
        return this.ball;
    }
    
    /**
     * 
     * @return a Vect representing the velocity
     */
    public Vect getVelocity(){
        return this.velocity;
    }
    
    /**
     * 
     * @param newVelocity
     * @effect update the ball velocity
     */
    public void updateVelocity(Vect newVelocity){
        this.velocity = newVelocity;
    }
    
    /**
     * 
     * @param dt time in seconds
     * @effect update the location of the ball accordingly to the time
     */
    public void updateLocation(double dt){
        double dx = this.velocity.x() * dt;
        double dy = this.velocity.y() * dt;
        double newX = dx+this.ball.getCenter().x();
        double newY = dy+this.ball.getCenter().y();
        
        if ( newX > 19.5 ){
        	newX = 19.5;
        }else if( newX < 0.5){
        	newX = .5;
        }
        
        if ( newY > 19.5){
        	newY = 19.5;
        }else if( newY <0.5){
        	newY = .5;
        }
        this.ball = new Circle(newX,newY,.1);
    }
}
