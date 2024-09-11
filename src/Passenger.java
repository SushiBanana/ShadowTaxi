import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Passenger
 * @author Alysha Thean Student ID: 1495768
 */
public class Passenger {

    public final static int MOVE_FRAME = 5;

    public final Properties GAME_PROPS;
    public final Image IMAGE;
    public final TripEndFlag TRIP_END_FLAG;
    public final int ADJACENT_DIST;
    public final int WALK_SPEED_X;
    public final int WALK_SPEED_Y;
    public final double RATE_PER_Y;
    public final int RATE_PRIORITY_1;
    public final int RATE_PRIORITY_2;
    public final int RATE_PRIORITY_3;

    private int coorX;
    private int coorY;
    private int priority;
    private int endCoorX;
    private int yDist;
    private double earnings;
    private boolean isPickedUp;
    private boolean isDroppedOff;

    /**
     * Constructor for Passenger class
     * @param gameProps properties file for values of various attributes
     * @param coorX integer of passenger x-coordinate
     * @param coorY integer of passenger y-coordinate
     * @param priority integer of passenger priority
     * @param endCoorX integer of passenger end x-coordinate
     * @param yDist integer of distance travelled vertically during trip
     */
    public Passenger(Properties gameProps, int coorX, int coorY, int priority, int endCoorX, int yDist){
        this.GAME_PROPS = gameProps;
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.passenger.image"));
        this.TRIP_END_FLAG = new TripEndFlag(gameProps, endCoorX, coorY - yDist);
        this.ADJACENT_DIST = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.passenger.taxiDetectRadius"));
        this.WALK_SPEED_X = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.passenger.walkSpeedX"));
        this.WALK_SPEED_Y = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.passenger.walkSpeedY"));
        this.RATE_PER_Y = Double.parseDouble(gameProps.getProperty("trip.rate.perY"));
        this.RATE_PRIORITY_1 = Integer.parseInt(gameProps.getProperty("trip.rate.priority1"));
        this.RATE_PRIORITY_2 = Integer.parseInt(gameProps.getProperty("trip.rate.priority2"));
        this.RATE_PRIORITY_3 = Integer.parseInt(gameProps.getProperty("trip.rate.priority3"));

        this.coorX = coorX;
        this.coorY = coorY;
        this.priority = priority;
        this.endCoorX = endCoorX;
        this.yDist = yDist;
        this.earnings = calcExpEarnings(yDist);
        this.isPickedUp = false;
        this.isDroppedOff = false;

    }

    /**
     * Getter method for passenger's x-coordinate
     * @return integer of passenger's x-coordinate
     */
    public int getCoorX() {
        return coorX;
    }

    /**
     * Setter method for passenger's x-coordinate
     * @param coorX integer of passenger's new x-coordinate
     */
    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    /**
     * Getter method for passenger's y-coordinate
     * @return integer of passenger's y-coordinate
     */
    public int getCoorY() {
        return coorY;
    }

    /**
     * Setter method for passenger's y-coordinate
     * @param coorY integer of passenger's new y-coordinate
     */
    public void setCoorY(int coorY) {
        this.coorY = coorY;
    }

    /**
     * Getter method for passenger's priority
     * @return integer of passenger's priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Setter method for passenger's priority
     * @param priority integer of passenger's new priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Getter method for whether passenger is picked up
     * @return true if passenger has been picked up, false otherwise
     */
    public boolean getIsPickedUp(){
        return isPickedUp;
    }

    /**
     * Setter method for whether passenger is picked up
     * @param isPickedUp true if passenger has been picked up, false otherwise
     */
    public void setIsPickedUp(boolean isPickedUp){
        this.isPickedUp = isPickedUp;
    }

    /**
     * Getter method for passenger's earnings
     * @return double of passenger's earnings
     */
    public double getEarnings() {
        return earnings;
    }

    /**
     * Setter method for passenger's earnings
     * @param earnings double of passenger's new earnings
     */
    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    /**
     * Getter method of vertical distance passenger intends to travel
     * @return integer of vertical distance passenger intends to travel
     */
    public int getYDist() {
        return yDist;
    }

    /**
     * Setter method of vertical distance passenger intends to travel
     * @param yDist integer of vertical distance passenger intends to travel
     */
    public void setYDist(int yDist) {
        this.yDist = yDist;
    }

    /**
     * Getter method of passenger's end of trip x-coordinate
     * @return integer of passenger's end of trip x-coordinate
     */
    public int getEndCoorX() {
        return endCoorX;
    }

    /**
     * Setter method of passenger's end of trip x-coordinate
     * @param endCoorX integer of passenger's end of trip x-coordinate
     */
    public void setEndCoorX(int endCoorX) {
        this.endCoorX = endCoorX;
    }

    /**
     * Moves passenger and passenger's trip end flag by incrementing its y-coordinates based on MOVE_FRAME
     */
    public void moveDown(){
        coorY += MOVE_FRAME;
        TRIP_END_FLAG.moveDown();
    }

    /**
     * Checks if passenger is adjacent to a target's coordinates based on ADJACENT_DIST
     * @param targetCoorX double of target's x-coordinate
     * @param targetCoorY double of target's y-coordinate
     * @return true if passenger is adjacent to target, false otherwise
     */
    public boolean isAdjacent(double targetCoorX, double targetCoorY){
        boolean flag = false;
        double dist = Math.sqrt(Math.pow((coorX - targetCoorX), 2) + Math.pow((coorY - targetCoorY), 2));
        if (dist <= ADJACENT_DIST){
            flag = true;
        }
        return flag;
    }

    /**
     * Increments one of passenger's x or y-coordinates to target's coordinates based on WALK_SPEED_X and
     * WALK_SPEED_Y respectively
     * @param targetCoorX double of target's x-coordinates
     * @param targetCoorY double of target's y-coordinates
     */
    public void incrementCoorXY(double targetCoorX, double targetCoorY){

        if (sameCoor(targetCoorX, targetCoorY)){
            return;
        }

        if (coorX < targetCoorX){
            coorX += WALK_SPEED_X;

        } else if (coorX > targetCoorX){
            coorX -= WALK_SPEED_X;

        } else if (coorY < targetCoorY){
            coorY += WALK_SPEED_Y;

        } else if (coorY > targetCoorY){
            coorY -= WALK_SPEED_Y;

        }

    }

    /**
     * Checks if passenger's coordinates are the same with target
     * @param targetCoorX double of target's x-coordinates
     * @param targetCoorY double of target's y-coordinates
     * @return true if passenger and target coordinates are same, false otherwise
     */
    public boolean sameCoor(double targetCoorX, double targetCoorY){
        boolean flag = false;

        if (coorX == targetCoorX && coorY == targetCoorY){
            flag = true;
        }
        return flag;
    }

    /**
     * Setter method for whether passenger is dropped off
     * @return true if passenger has been dropped off, false otherwise
     */
    public boolean isDroppedOff() {
        return isDroppedOff;
    }

    /**
     * Setter method for whether passenger is dropped off
     * @param droppedOff boolean of whether passenger is dropped off
     */
    public void setIsDroppedOff(boolean droppedOff) {
        isDroppedOff = droppedOff;
    }

    /**
     * Increments one of passenger's x or y-coordinates towards their trip end flag based on WALK_SPEED_X and
     * WALK_SPEED_Y respectively
     */
    public void moveTowardsFlag(){

        if (sameCoor(TRIP_END_FLAG.getCoorX(), TRIP_END_FLAG.getCoorY())){
            TRIP_END_FLAG.setIsVisible(false);
            return;
        }


        if (coorX < TRIP_END_FLAG.getCoorX()){
            coorX += WALK_SPEED_X;

        } else if (coorX > TRIP_END_FLAG.getCoorX()){
            coorX -= WALK_SPEED_X;

        }

        if (coorY < TRIP_END_FLAG.getCoorY()){
            coorY += WALK_SPEED_Y;

        } else if (coorY > TRIP_END_FLAG.getCoorY()){
            coorY -= WALK_SPEED_Y;

        }

    }

    /**
     * Decrements passenger's priority
     */
    public void decrementPriority(){
        if (priority < 1){
            return;
        }
        priority--;
    }

    /**
     * Calculates passenger's expected earnings
     * @param yDist integer of vertical distance passenger intends to travel
     * @return double of passenger's expected earnings
     */
    public double calcExpEarnings(int yDist){
        int rate;
        double res;

        switch (priority) {
            case 1:
                rate = RATE_PRIORITY_1;
                break;


            case 2:
                rate = RATE_PRIORITY_2;
                break;


            case 3:
                rate = RATE_PRIORITY_3;
                break;

            default:
                rate = 0;
                break;

        }
        res = yDist * RATE_PER_Y + priority * rate;
        this.earnings = res;
        return res;
    }

    /**
     * Returns the state of passenger class
     * @return String of states in passenger class
     */
    public String toString(){
        return "Passenger\n" + "_____________\n" + "IMAGE: " + IMAGE + "\nx-coordinate: " + coorX + "\n" +"y" +
                "-coordinate: " + coorY + "\n" +
                "priority: " + priority + "\n" + TRIP_END_FLAG;
    }
}
