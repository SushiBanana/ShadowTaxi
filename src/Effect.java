import java.util.Properties;

/**
 * This Java abstract class contains attributes and methods related to Effect
 * @author Alysha Thean Student ID: 1495768
 */
public abstract class Effect extends GameEntity{
    /**
     * The taxi's vertical speed
     */
    public final int TAXI_MOVE_FRAME_Y;

    private int currentFrame;
    private boolean isActive;

    /**
     * Constructor for Effect class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of effect
     * @param coorY y-coordinate of effect
     */
    public Effect(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));

        this.currentFrame = 0;
        this.isActive = false;
    }

    /**
     * Gets the current frame of effect
     * @return the effect's current frame
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Sets the current frame of effect
     * @param currentFrame the effect's current frame
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Gets whether the effect is active
     * @return true if active, false otherwise
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets whether the effect is active
     * @param active true if active, false otherwise
     */
    public void setIsActive(boolean active) {
        isActive = active;
    }

    /**
     * Moves effect down based on Taxi's speed
     */
    public void moveDown(){
        setCoorY(getCoorY() + TAXI_MOVE_FRAME_Y);
    }

    /**
     * Activates effect
     * @param coorX x-coordinate of effect
     * @param coorY y-coordinate of effect
     */
    public void activate(int coorX, int coorY){
        currentFrame = 0;
        isActive = true;
        setCoorX(coorX);
        setCoorY(coorY);
    }
}
