import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Coin
 * @author Alysha Thean Student ID: 1495768
 */
public class Coin extends GameEntity{
    /**
     * The taxi's vertical speed
     */
    public final int TAXI_MOVE_FRAME_Y;
    /**
     * The x-coordinate of coin's display
     */
    public final int DISPLAY_FRAME_COOR_X;
    /**
     * The y-coordinate of coin's display
     */
    public final int DISPLAY_FRAME_COOR_Y;
    /**
     * The radius of coin
     */
    public final double RADIUS;
    /**
     * The maximum frames of coin
     */
    public final int MAX_FRAMES;

    private boolean isCollided;
    private int currentFrame;

    /**
     * Constructor for Coin class
     * @param gameProps properties file for values of various attributes
     * @param coorX integer of coin's x-coordinate
     * @param coorY integer of coin's y-coordinate
     */
    public Coin(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);

        this.IMAGE = new Image(gameProps.getProperty("gameObjects.coin.image"));
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        this.DISPLAY_FRAME_COOR_X = Integer.parseInt(gameProps.getProperty("gameplay.coin.x"));
        this.DISPLAY_FRAME_COOR_Y = Integer.parseInt(gameProps.getProperty("gameplay.coin.y"));
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.coin.radius"));
        this.MAX_FRAMES = Integer.parseInt(gameProps.getProperty("gameObjects.coin.maxFrames"));

        this.isCollided = false;
        this.currentFrame = 0;
    }

    /**
     * Gets whether coin is collided
     * @return true if collided, false otherwise
     */
    public boolean getIsCollided() {
        return isCollided;
    }

    /**
     * Sets whether coin is collided
     * @param isCollided true if collided, false otherwise
     */
    public void setIsCollided(boolean isCollided) {
        this.isCollided = isCollided;
    }

    /**
     * Gets the current frame of coin
     * @return coin's current frame
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Sets the current frame of coin
     * @param currentFrame coin's current frame
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Moves coin down by incrementing its y-coordinates based on Taxi's speed
     */
    public void moveDown(){
        setCoorY(getCoorY() + TAXI_MOVE_FRAME_Y);
    }

    /**
     * Increments coin's current frame
     */
    public void incrementFrames(){
        currentFrame++;
    }

    /**
     * Checks if Taxi has collided with Coin
     * @param taxi Taxi object collided with Coin
     * @return true if Taxi has collided with a Coin, false otherwise
     */
    public boolean hasCollided(Taxi taxi){
        boolean flag = false;

        double currentDist = calcDist(taxi.getCoorX(), taxi.getCoorY());
        double currentRange = calcRange(taxi.RADIUS);

        if (currentDist <= currentRange){
            flag = true;
            setIsCollided(true);

        }
        return flag;
    }

    /**
     * Checks if Driver has collided with Coin
     * @param driver Driver object collided with Coin
     * @return true if Driver has collided with a Coin, false otherwise
     */
    public boolean hasCollided(Driver driver){
        boolean flag = false;

        double currentDist = calcDist(driver.getCoorX(), driver.getCoorY());
        double currentRange = calcRange(driver.RADIUS);

        if (currentDist <= currentRange){
            flag = true;
            setIsCollided(true);
        }
        return flag;
    }

    /**
     * Calculates range of coin with target's radius
     * @param targetRadius double of target's radius
     * @return double of range calculated
     */
    public double calcRange(double targetRadius){
        return RADIUS + targetRadius;
    }

    /**
     * Calculates distance between coin and target's coordinates
     * @param targetCoorX double of target's x-coordinate
     * @param targetCoorY double of target's y-coordinate
     * @return double of distance between coin and target
     */
    public double calcDist(double targetCoorX, double targetCoorY){
        return Math.sqrt(Math.pow(getCoorX() - targetCoorX, 2) + Math.pow(getCoorY() - targetCoorY, 2));
    }

    /**
     * Returns the state of coin
     * @return String of states of coin
     */
    public String toString(){
        return "Coin" +
                "\n_____________ "+
                "\nx-coordinate: " + getCoorX() +
                "\ny-coordinate: " + getCoorY() +
                "\nis collided: " + isCollided +
                "\ncurrent frame:" + currentFrame;
    }
}
