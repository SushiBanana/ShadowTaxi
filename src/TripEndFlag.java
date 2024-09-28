import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Trip End Flag
 * @author Alysha Thean Student ID: 1495768
 */
public class TripEndFlag extends GameEntity{
    public final int TAXI_MOVE_FRAME_Y = 5;

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
        this.RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.tripEndFlag.radius"));

        this.isVisible = false;
    }


    /**
     * Getter method for whether trip end flag is visible
     * @return true if visible, false otherwise
     */
    public boolean getIsVisible() {
        return isVisible;
    }

    /**
     * Setter method for whether trip end flag is visible
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
     * Returns the state of trip end flag
     * @return String of states in trip end flag
     */
    public String toString() {
        return "Trip End Flag \n ___________\n" + "IMAGE: " + IMAGE + "\nRADIUS: " + RADIUS + "\nx-coordinate: " +
                getCoorX() + "\ny-coordinate: " + getCoorY() + "\nisVisible: " + isVisible + "\n";
    }

}
