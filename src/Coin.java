import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Coin
 * @author Alysha Thean Student ID: 1495768
 */
public class Coin {

    public final static int MOVE_FRAME = 5;

    public final Properties GAME_PROPS;
    public final Image IMAGE;
    public final int DISPLAY_FRAME_COOR_X;
    public final int DISPLAY_FRAME_COOR_Y;
    public final double RADIUS;
    public final int MAX_FRAMES;

    private int coorX;
    private int coorY;
    private boolean isCollided;
    private int currentFrame;

    /**
     * Constructor for Coin class
     * @param gameProps properties file for values of various attributes
     * @param coorX integer of coin's x-coordinate
     * @param coorY integer of coin's y-coordinate
     */
    public Coin(Properties gameProps, int coorX, int coorY){
        this.GAME_PROPS = gameProps;
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.coin.image"));
        this.DISPLAY_FRAME_COOR_X = Integer.parseInt(gameProps.getProperty("gameplay.coin.x"));
        this.DISPLAY_FRAME_COOR_Y = Integer.parseInt(gameProps.getProperty("gameplay.coin.y"));
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.coin.radius"));
        this.MAX_FRAMES = Integer.parseInt(gameProps.getProperty("gameObjects.coin.maxFrames"));

        this.coorX = coorX;
        this.coorY = coorY;
        this.isCollided = false;
        this.currentFrame = 0;
    }

    /**
     * Getter method of coin's x-coordinate
     * @return integer of coin's x-coordinate
     */
    public int getCoorX() {
        return coorX;
    }

    /**
     * Setter method of coin's x-coordinate
     * @param coorX integer of coin's x-coordinate
     */
    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    /**
     * Getter method of coin's y-coordinate
     * @return integer of coin's y-coordinate
     */
    public int getCoorY() {
        return coorY;
    }

    /**
     * Setter method of coin's y-coordinate
     * @param coorY integer of coin's y-coordinate
     */
    public void setCoorY(int coorY) {
        this.coorY = coorY;
    }

    /**
     * Getter method of whether coin is collided
     * @return true if collided, false otherwise
     */
    public boolean getIsCollided() {
        return isCollided;
    }

    /**
     * Setter method of whether coin is collided
     * @param isCollided true if collided, false otherwise
     */
    public void setIsCollided(boolean isCollided) {
        this.isCollided = isCollided;
    }

    /**
     * Getter method of coin's current frame
     * @return integer of coin's current frame
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Setter method of coin's current frame
     * @param currentFrame integer of coin's new current frame
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Moves coin down by incrementing its y-coordinates based on MOVE_FRAME
     */
    public void moveDown(){
        coorY += MOVE_FRAME;
    }

    /**
     * Increments coin's current frame
     */
    public void incrementFrames(){
        currentFrame++;
    }

    /**
     * Checks if taxi has collided with coin
     * @param taxi Taxi object collided with coin
     * @return true if taxi has collided with a coin, false otherwise
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
        return Math.sqrt(Math.pow(coorX - targetCoorX, 2) + Math.pow(coorY - targetCoorY, 2));
    }

    /**
     * Returns the state of coin
     * @return String of states of coin
     */
    public String toString(){
        return "Coin\n" + "_____________\nx-coordinate: " + coorX + "\ny-coordinate: " + coorY + "\nis collided: " +
                isCollided + "\ncurrent frame:" + currentFrame;
    }

}
