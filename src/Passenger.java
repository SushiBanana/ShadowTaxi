import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Passenger
 * @author Alysha Thean Student ID: 1495768
 */
public class Passenger extends GameEntity implements Damageable{

    public final static int MOVE_FRAME = 5;
    
    public final TripEndFlag TRIP_END_FLAG;
    public final int ADJACENT_DIST;
    public final int WALK_SPEED_X;
    public final int WALK_SPEED_Y;
    public final double RATE_PER_Y;
    public final int RATE_PRIORITY_1;
    public final int RATE_PRIORITY_2;
    public final int RATE_PRIORITY_3;
    public final Blood BLOOD;

    private int currentPriority;
    private int originalPriority;
    private int endCoorX;
    private int yDist;
    private double earnings;
    private boolean isPickedUp;
    private boolean isDroppedOff;

    //
    public final static int EJECTION_COOR_X_MINUS = 100;
    public static final int MOMENTUM = 10;
    public final static int COOR_Y_MOVEMENT_STEP = 2;
    public final static int COLLISION_TIMEOUT = 200;


    public final int RADIUS;
    public final int TAXI_GET_IN_RADIUS;

    private double health;
    private boolean hasUmbrella;
    private boolean isEjected;
    private int collisionTimeoutLeft;
    private int momentumCurrentFrame;
    private int rainPriority;

    /**
     * Constructor for Passenger class
     * @param gameProps properties file for values of various attributes
     * @param coorX integer of passenger x-coordinate
     * @param coorY integer of passenger y-coordinate
     * @param priority integer of passenger priority
     * @param endCoorX integer of passenger end x-coordinate
     * @param yDist integer of distance travelled vertically during trip
     */
    public Passenger(Properties gameProps, int coorX, int coorY, int priority, int endCoorX, int yDist,
                     boolean hasUmbrella){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.passenger.image"));
        this.TRIP_END_FLAG = new TripEndFlag(gameProps, endCoorX, coorY - yDist);
        this.ADJACENT_DIST = Integer.parseInt(gameProps.getProperty("gameObjects.passenger.taxiDetectRadius"));
        this.WALK_SPEED_X = Integer.parseInt(gameProps.getProperty("gameObjects.passenger.walkSpeedX"));
        this.WALK_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.passenger.walkSpeedY"));
        this.RATE_PER_Y = Double.parseDouble(gameProps.getProperty("trip.rate.perY"));
        this.RATE_PRIORITY_1 = Integer.parseInt(gameProps.getProperty("trip.rate.priority1"));
        this.RATE_PRIORITY_2 = Integer.parseInt(gameProps.getProperty("trip.rate.priority2"));
        this.RATE_PRIORITY_3 = Integer.parseInt(gameProps.getProperty("trip.rate.priority3"));

        this.currentPriority = priority;
        this.originalPriority = priority;
        this.endCoorX = endCoorX;
        this.yDist = yDist;
        this.earnings = calcExpEarnings(yDist);
        this.isPickedUp = false;
        this.isDroppedOff = false;

        //
        this.hasUmbrella = hasUmbrella;
        this.isEjected = false;

        this.RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.passenger.radius"));
        this.TAXI_GET_IN_RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.passenger.taxiGetInRadius"));
        this.health = Double.parseDouble(gameProps.getProperty("gameObjects.passenger.health")) * 100;
        this.BLOOD = new Blood (gameProps, getCoorX(), getCoorY());

    }

    /**
     * Getter method for passenger's priority
     * @return integer of passenger's priority
     */
    public int getCurrentPriority() {
        return currentPriority;
    }

    /**
     * Setter method for passenger's priority
     * @param currentPriority integer of passenger's new priority
     */
    public void setCurrentPriority(int currentPriority) {
        this.currentPriority = currentPriority;
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
     * Getter method of whether passenger has umbrella
     * @return boolean of whether passenger has umbrella
     */
    public boolean getHasUmbrella() {
        return hasUmbrella;
    }

    /**
     * Setter method of whether passenger has umbrella
     * @param hasUmbrella boolean of whether passenger has umbrella
     */
    public void setHasUmbrella(boolean hasUmbrella) {
        this.hasUmbrella = hasUmbrella;
    }

    /**
     * Getter method of passenger's health
     * @return double of passenger's health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Setter method of passenger's health
     * @param health double of passenger's health
     */
    public void setHealth(double health) {
        this.health = health;
    }


    /**
     * Moves passenger and passenger's trip end flag by incrementing its y-coordinates based on MOVE_FRAME
     */
    public void moveDown(){
        setCoorY(getCoorY() + MOVE_FRAME);
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
        double dist = Math.sqrt(Math.pow((getCoorX() - targetCoorX), 2) + Math.pow((getCoorY() - targetCoorY), 2));
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

        if (getCoorX() < targetCoorX){
            setCoorX(getCoorX()+ WALK_SPEED_X);

        } else if (getCoorX() > targetCoorX){
            setCoorX(getCoorX() - WALK_SPEED_X);

        } else if (getCoorY() < targetCoorY){
            setCoorY(getCoorY() + WALK_SPEED_Y);

        } else if (getCoorY() > targetCoorY){
            setCoorY(getCoorY() - WALK_SPEED_Y);

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

        if (getCoorX() == targetCoorX && getCoorY() == targetCoorY){
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
     * @param isDroppedOff boolean of whether passenger is dropped off
     */
    public void setIsDroppedOff(boolean isDroppedOff) {
        this.isDroppedOff = isDroppedOff;
    }

    public boolean getIsEjected() {
        return isEjected;
    }

    public void setIsEjected(boolean isEjected) {
        this.isEjected = isEjected;
    }

    public int getCollisionTimeoutLeft() {
        return collisionTimeoutLeft;
    }

    public void setCollisionTimeoutLeft(int collisionTimeoutLeft) {
        this.collisionTimeoutLeft = collisionTimeoutLeft;
    }

    public int getMomentumCurrentFrame() {
        return momentumCurrentFrame;
    }

    public void setMomentumCurrentFrame(int momentumCurrentFrame) {
        this.momentumCurrentFrame = momentumCurrentFrame;
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


        if (getCoorX() < TRIP_END_FLAG.getCoorX()){
            setCoorX(getCoorX() + WALK_SPEED_X);

        } else if (getCoorX() > TRIP_END_FLAG.getCoorX()){
            setCoorX(getCoorX() - WALK_SPEED_X);

        } else if (getCoorY() < TRIP_END_FLAG.getCoorY()){
            setCoorY(getCoorY() + WALK_SPEED_Y);

        } else if (getCoorY() > TRIP_END_FLAG.getCoorY()){
            setCoorY(getCoorY() - WALK_SPEED_Y);

        }

    }

    /**
     * Decrements passenger's priority
     */
    public void decrementPriority(){
        if (currentPriority < 1 || originalPriority < 1){
            return;
        }
        currentPriority--;
        originalPriority--;
    }

    /**
     * Calculates passenger's expected earnings
     * @param yDist integer of vertical distance passenger intends to travel
     * @return double of passenger's expected earnings
     */
    public double calcExpEarnings(int yDist){
        int rate;
        double res;

        switch (currentPriority) {
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
        res = yDist * RATE_PER_Y + currentPriority * rate;
        this.earnings = res;
        return res;
    }

    /**
     * Returns the state of passenger class
     * @return String of states in passenger class
     */
    public String toString(){
        return "Passenger\n" + "_____________\n" + "IMAGE: " + IMAGE + "\nx-coordinate: " + getCoorX() + "\n" +"y" +
                "-coordinate: " + getCoorY() + "\n" +
                "current priority: " + currentPriority + "\n" + TRIP_END_FLAG +
                "has umbrella: " + hasUmbrella +
                "\nis ejected: " + isEjected +
                "\nis picked up: " + isPickedUp +
                "\nis dropped off: " + isDroppedOff+
                "\nmomentum current frame: " + momentumCurrentFrame;
    }

    public void eject(int coorX, int coorY){
        setIsEjected(true);
        setCoorX(coorX);
        setCoorY(coorY);
    }

    public void followDriver(Driver driver){
        if (getCoorX() < driver.getCoorX()){
            setCoorX(getCoorX() + WALK_SPEED_X);

        } else if (getCoorX() > driver.getCoorX()){
            setCoorX(getCoorX() - WALK_SPEED_X);

        } else if (getCoorY() < driver.getCoorY()){
            setCoorY(getCoorY() + WALK_SPEED_Y);

        } else if (getCoorY() > driver.getCoorY()){
            setCoorY(getCoorY() - WALK_SPEED_Y);

        }

    }

    public void handleMomentum(){
        if (momentumCurrentFrame == 0){
            return;
        }
        if (momentumCurrentFrame > 0){
            setCoorY(getCoorY() + 1);
            momentumCurrentFrame = momentumCurrentFrame - COOR_Y_MOVEMENT_STEP;
        } else {
            setCoorY(getCoorY() - 1);
            momentumCurrentFrame = momentumCurrentFrame + COOR_Y_MOVEMENT_STEP;
        }
    }

    public void decrementCollisionTimeoutLeft(){
        if (collisionTimeoutLeft > 0){
            collisionTimeoutLeft--;
        }
    }

    public void checkHealth(){
        if (getHealth() <= 0){
            activateBlood();
        }
    }


    public void activateBlood(){
        BLOOD.setIsActive(true);
        BLOOD.setCoorX(getCoorX());
        BLOOD.setCoorY(getCoorY());
    }

    @Override
    public void takeDamage(DamageDealer damageDealer) {
        if (getHealth() > 0 && getCollisionTimeoutLeft() == 0){

            setHealth(getHealth() - damageDealer.getDamagePoints());
            setCollisionTimeoutLeft(COLLISION_TIMEOUT);
            checkHealth();

        }
    }

    public void changePriorityWhenRaining(){
        if (!hasUmbrella){
            currentPriority = 1;
        }
    }

    public void revertPriorityWhenSunny(){
        currentPriority = originalPriority;
    }
}
