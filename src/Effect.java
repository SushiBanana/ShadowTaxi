import java.util.Properties;

/**
 * This Java abstract class contains attributes and methods related to Effect
 * @author Alysha Thean Student ID: 1495768
 */
public abstract class Effect extends GameEntity{

    public final int TAXI_MOVE_FRAME_Y;

    private int currentFrame;
    private boolean isActive;

    /**
     * Constructor for Effect class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of Effect
     * @param coorY y-coordinate of Effect
     */
    public Effect(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));

        this.currentFrame = 0;
        this.isActive = false;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    /**
     * Moves Effect down based on Taxi's speed
     */
    public void moveDown(){
        setCoorY(getCoorY() + TAXI_MOVE_FRAME_Y);
    }

    /**
     * Activates effect
     * @param coorX x-coordinate of Effect
     * @param coorY y-coordinate of Effect
     */
    public void activate(int coorX, int coorY){
        currentFrame = 0;
        isActive = true;
        setCoorX(coorX);
        setCoorY(coorY);
    }
}
