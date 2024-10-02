import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to TripEndFlag
 * @author Alysha Thean Student ID: 1495768
 */
public class TripEndFlag extends GameEntity{

    /**
     * The taxi's vertical movement speed
     */
    public final int TAXI_MOVE_FRAME_Y;
    /**
     * The radius of trip end flag
     */
    public final int RADIUS;

    private boolean isVisible;

    /**
     * Constructor for Trip End Flag class
     * @param gameProps properties file for values of various attributes
     * @param coorX integer of trip end flag's x-coordinate
     * @param coorY integer of trip end flag's y-coordinate
     */
    public TripEndFlag(Properties gameProps,int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.tripEndFlag.image"));
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        this.RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.tripEndFlag.radius"));

        this.isVisible = false;
    }

    /**
     * Gets whether the trip end flag is visible
     * @return true if visible, false otherwise
     */
    public boolean getIsVisible() {
        return isVisible;
    }

    /**
     * Sets whether the trip end flag is visible
     * @param isVisible true if visible, false otherwise
     */
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Moves trip end flag down by incrementing its y-coordinates based on MOVE_FRAME
     */
    public void moveDown(){
        setCoorY(getCoorY() + TAXI_MOVE_FRAME_Y);
    }

    /**
     * Returns the state of TripEndFlag object
     * @return String of states in TripEndFlag
     */
    public String toString() {
        return "Trip End Flag \n ___________" +
                "\nIMAGE: " + IMAGE +
                "\nRADIUS: " + RADIUS +
                "\nx-coordinate: " + getCoorX() +
                "\ny-coordinate: " + getCoorY() +
                "\nisVisible: " + isVisible;
    }
}
