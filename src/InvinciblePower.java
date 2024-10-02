import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to InvinciblePower
 * @author Alysha Thean Student ID: 1495768
 */
public class InvinciblePower extends GameEntity{
    /**
     * The taxi's vertical speed
     */
    public final int TAXI_MOVE_FRAME_Y;
    /**
     * The radius of invincible power
     */
    public final double RADIUS;
    /**
     * The maximum frames of invincible power
     */
    public final int MAX_FRAMES;

    private boolean isCollided;
    private int frameLeft;

    /**
     * Constructor for InvinciblePower class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of InvinciblePower
     * @param coorY y-coordinate of InvinciblePower
     */
    public InvinciblePower(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.invinciblePower.image"));
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.invinciblePower.radius"));
        this.MAX_FRAMES = Integer.parseInt(gameProps.getProperty("gameObjects.invinciblePower.maxFrames"));
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));

        this.isCollided = false;
        this.frameLeft = 0;
    }

    /**
     * Gets whether invincible power is collided
     * @return true if collided, false otherwise
     */
    public boolean getIsCollided() {
        return isCollided;
    }

    /**
     * Sets whether invincible power is collided
     * @param collided true if collided, false otherwise
     */
    public void setIsCollided(boolean collided) {
        isCollided = collided;
    }

    /**
     * Gets frames left of invincible power
     * @return invincible power's frame left
     */
    public int getFrameLeft() {
        return frameLeft;
    }

    /**
     * Sets frames left of invincible power
     * @param frameLeft invincible power's frame left
     */
    public void setFrameLeft(int frameLeft) {
        this.frameLeft = frameLeft;
    }

    /**
     * Moves InvinciblePower down based on Taxi's move frame speed
     */
    public void moveDown(){
        setCoorY(getCoorY() + TAXI_MOVE_FRAME_Y);
    }

    /**
     * Checks if InvinciblePower has collided with Taxi
     * @param taxi Taxi object
     * @return true if InvinciblePower collided with Taxi, false otherwise
     */
    public boolean hasCollided(Taxi taxi){
        double sumOfRadius = RADIUS + taxi.RADIUS;
        if (GamePlayScreen.calcDist(this, taxi) < sumOfRadius) {
            frameLeft = MAX_FRAMES;
            setIsCollided(true);
            return true;
        }
        return false;
    }

    /**
     * Checks if InvinciblePower has collided with Driver
     * @param driver Driver object
     * @return true if InvinciblePower collided with Driver, false otherwise
     */
    public boolean hasCollided(Driver driver){
        double sumOfRadius = RADIUS + driver.RADIUS;
        if (GamePlayScreen.calcDist(this, driver) < sumOfRadius) {
            frameLeft = MAX_FRAMES;
            setIsCollided(true);
            return true;
        }
        return false;
    }

    /**
     * Decrements frame left
     */
    public void decrementFrameLeft(){
        if (frameLeft > 0){
            frameLeft--;
        }
    }

    /**
     * Returns the state of InvinciblePower object
     * @return String of states of InvinciblePower object
     */
    public String toString(){
        return "INVINCIBLE POWER\n____________" +
                "\nradius: " + RADIUS +
                "\nmax frames: " + MAX_FRAMES +
                "\ncoor x: " + getCoorX() +
                "\ncoor y:" + getCoorY();
    }
}
