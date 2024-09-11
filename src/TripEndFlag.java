import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Trip End Flag
 * @author Alysha Thean Student ID: 1495768
 */
public class TripEndFlag {
    public final int MOVE_FRAME = 5;

    public final Image IMAGE;
    public final int RADIUS;

    private int coorX;
    private int coorY;
    private boolean isVisible;

    /**
     * Constructor for Trip End Flag class
     * @param gameProps properties file for values of various attributes
     * @param coorX integer of trip end flag's x-coordinate
     * @param coorY integer of trip end flag's y-coordinate
     */
    public TripEndFlag(Properties gameProps,int coorX, int coorY){
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.tripEndFlag.image"));
        this.RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.tripEndFlag.radius"));

        this.coorX = coorX;
        this.coorY = coorY;
        this.isVisible = false;
    }

    /**
     * Getter method of trip end flag's x-coordinate
     * @return integer of trip end flag's x-coordinate
     */
    public int getCoorX() {
        return coorX;
    }

    /**
     * Setter method of trip end flag's x-coordinate
     * @param coorX integer of trip end flag's new x-coordinate
     */
    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    /**
     * Getter method of trip end flag's y-coordinate
     * @return integer of trip end flag's y-coordinate
     */
    public int getCoorY() {
        return coorY;
    }

    /**
     * Setter method of trip end flag's y-coordinate
     * @param coorY integer of trip end flag's new y-coordinate
     */
    public void setCoorY(int coorY) {
        this.coorY = coorY;
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
        coorY += MOVE_FRAME;
    }

    /**
     * Returns the state of trip end flag
     * @return String of states in trip end flag
     */
    public String toString() {
        return "Trip End Flag \n ___________\n" + "IMAGE: " + IMAGE + "\nRADIUS: " + RADIUS + "\nx-coordinate: " + coorX
                + "\ny-coordinate: " + coorY + "\nisVisible: " + isVisible + "\n";
    }

}
