import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Taxi
 * @author Alysha Thean Student ID: 1495768
 */
public class Taxi {

    public final Image IMAGE;
    public final double RADIUS;
    public final Properties GAME_PROPS;
    public final int MOVE_FRAME;

    private int coorX;
    private int coorY;
    private Passenger currentPassenger;

    /**
     * Constructor for Taxi class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of taxi
     * @param coorY y-coordinate of taxi
     */
    public Taxi(Properties gameProps, int coorX, int coorY){
        this.GAME_PROPS = gameProps;
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.taxi.image"));
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.taxi.radius"));
        this.MOVE_FRAME = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedX"));

        this.coorX = coorX;
        this.coorY = coorY;
        this.currentPassenger = null;
    }

    /**
     * Getter method for taxi's x-coordinate
     * @return integer of taxi's x-coordinate
     */
    public int getCoorX() {
        return coorX;
    }

    /**
     * Setter method for taxi's x-coordinate
     * @param coorX integer of taxi's new x-coordinate
     */
    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    /**
     * Getter method for taxi's y-coordinate
     * @return integer of taxi's y-coordinate
     */
    public int getCoorY() {
        return coorY;
    }

    /**
     * Setter method for taxi's y-coordinate
     * @param coorY integer of taxi's new y-coordinate
     */
    public void setCoorY(int coorY) {
        this.coorY = coorY;
    }

    /**
     * Getter method for current passenger in taxi
     * @return Passenger object in taxi
     */
    public Passenger getCurrentPassenger() {
        return currentPassenger;
    }

    /**
     * Setter method for current passenger in taxi
     * @param currentPassenger new Passenger in taxi
     */
    public void setCurrentPassenger(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;
    }

    /**
     * Decrements the x-coordinate of taxi when moving left based on MOVE_FRAME
     */
    public void moveLeft(){
        coorX -= MOVE_FRAME;
    }

    /**
     * Increments the x-coordinate of taxi when moving right based on MOVE_FRAME
     */
    public void moveRight(){
        coorX += MOVE_FRAME;
    }

    /**
     * Calculates the distance of taxi's coordinates with another set of coordinates
     * @param targetCoorX x-coordinates of target
     * @param targetCoorY y-coordinates of target
     * @return double of distance between taxi's and target's coordinates
     */
    public double calcDist(double targetCoorX, double targetCoorY){
        return Math.sqrt(Math.pow(coorX - targetCoorX, 2) + Math.pow(coorY - targetCoorY, 2));
    }

    /**
     * Calculates the distance between taxi and current passenger's trip end flag
     * @return double of distance between taxi and trip end flag
     */
    public double calcTaxiFlagDist(){
        return calcDist(currentPassenger.TRIP_END_FLAG.getCoorX(), currentPassenger.TRIP_END_FLAG.getCoorY());
    }

    /**
     * Calculates the distance between taxi and current passenger's trip end flag y-coordinates
     * @return double of distance between taxi and trip end flag y-coordinates
     */
    public double calcTaxiFlagCoorY(){
        return currentPassenger.TRIP_END_FLAG.getCoorY() - coorY;
    }

    /**
     * Returns the state of taxi class
     * @return String of states in taxi class
     */
    public String toString(){
        return "Taxi\n" + "_____________\nIMAGE: " + IMAGE + "\nRADIUS: " + RADIUS + "\nMOVE_FRAME: " + MOVE_FRAME +
                "\nx" + "-coordinate: " + coorX + "\ny-coordinate: " + coorY + "\n";
    }

}
