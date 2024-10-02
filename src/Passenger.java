import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Passenger
 * @author Alysha Thean Student ID: 1495768
 */
public class Passenger extends GameEntity implements Damageable{
    /**
     * The x-coordinate offset of passenger's ejection
     */
    public final static int EJECTION_COOR_X_MINUS = 100;
    /**
     * The momentum frames of passenger
     */
    public static final int MOMENTUM = 10;
    /**
     * The movement step of passenger's y-coordinate
     */
    public final static int COOR_Y_MOVEMENT_STEP = 2;
    /**
     * The collision timeout of passenger
     */
    public final static int COLLISION_TIMEOUT = 200;
    /**
     * The taxi's vertical speed
     */
    public final int TAXI_MOVE_FRAME_Y;
    /**
     * The trip end flag of passenger
     */
    public final TripEndFlag TRIP_END_FLAG;
    /**
     * The distance between passenger and another entity to be considered "adjacent"
     */
    public final int ADJACENT_DIST;
    /**
     * The horizontal walking speed of passenger
     */
    public final int WALK_SPEED_X;
    /**
     * The vertical walking speed of passenger
     */
    public final int WALK_SPEED_Y;
    /**
     * The passenger's rate per each y-coordinate travelled
     */
    public final double RATE_PER_Y;
    /**
     * The rate of priority 1 of passenger
     */
    public final int RATE_PRIORITY_1;
    /**
     * The rate of priority 2 of passenger
     */
    public final int RATE_PRIORITY_2;
    /**
     * The rate of priority 3 of passenger
     */
    public final int RATE_PRIORITY_3;
    /**
     * The blood of passenger
     */
    public final Blood BLOOD;
    /**
     * The radius of passenger
     */
    public final int RADIUS;
    /**
     * The taxi get in radius of passenger
     */
    public final int TAXI_GET_IN_RADIUS;

    private int currentPriority;
    private int originalPriority;
    private int endCoorX;
    private int yDist;
    private double earnings;
    private boolean isPickedUp;
    private boolean isDroppedOff;
    private double health;
    private boolean hasUmbrella;
    private boolean isEjected;
    private int collisionTimeoutLeft;
    private int momentumCurrentFrame;

    /**
     * Constructor for Passenger class
     * @param gameProps properties file for values of various attributes
     * @param coorX integer of passenger x-coordinate
     * @param coorY integer of passenger y-coordinate
     * @param priority integer of passenger priority
     * @param endCoorX integer of passenger end x-coordinate
     * @param yDist integer of distance travelled vertically during trip
     * @param hasUmbrella boolean of whether passenger has umbrella
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
        this.RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.passenger.radius"));
        this.TAXI_GET_IN_RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.passenger.taxiGetInRadius"));
        this.health = Double.parseDouble(gameProps.getProperty("gameObjects.passenger.health")) * 100;
        this.BLOOD = new Blood (gameProps, getCoorX(), getCoorY());
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));

        this.currentPriority = priority;
        this.originalPriority = priority;
        this.endCoorX = endCoorX;
        this.yDist = yDist;
        this.earnings = calcExpEarnings(yDist);
        this.isPickedUp = false;
        this.isDroppedOff = false;
        this.hasUmbrella = hasUmbrella;
        this.isEjected = false;

    }

    /**
     * Gets the current priority of passenger
     * @return passenger's current priority
     */
    public int getCurrentPriority() {
        return currentPriority;
    }

    /**
     * Sets the current priority of passenger
     * @param currentPriority passenger's current priority
     */
    public void setCurrentPriority(int currentPriority) {
        this.currentPriority = currentPriority;
    }

    /**
     * Gets the original priority of passenger
     * @return passenger's original priority
     */
    public int getOriginalPriority() {
        return originalPriority;
    }

    /**
     * Sets the original priority of passenger
     * @param originalPriority passenger's original priority
     */
    public void setOriginalPriority(int originalPriority) {
        this.originalPriority = originalPriority;
    }

    /**
     * Gets whether the passenger is picked up
     * @return true if picked up, false otherwise
     */
    public boolean getIsPickedUp(){
        return isPickedUp;
    }

    /**
     * Sets whether the passenger if picked up
     * @param isPickedUp true if picked up, false otherwise
     */
    public void setIsPickedUp(boolean isPickedUp){
        this.isPickedUp = isPickedUp;
    }

    /**
     * Gets the earnings of passenger
     * @return passenger's earnings
     */
    public double getEarnings() {
        return earnings;
    }

    /**
     * Sets the earnings of passenger
     * @param earnings passenger's earnings
     */
    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    /**
     * Gets the vertical distance to travel by passenger
     * @return passenger's vertical distance to travel
     */
    public int getYDist() {
        return yDist;
    }

    /**
     * Sets the vertical distance to travel by passenger
     * @param yDist passenger's vertical distance to travel
     */
    public void setYDist(int yDist) {
        this.yDist = yDist;
    }

    /**
     * Gets the end of the trip x-coordinate of passenger
     * @return passenger's end of the trip x-coordinate
     */
    public int getEndCoorX() {
        return endCoorX;
    }

    /**
     * Sets the end of the trip x-coordinate of passenger
     * @param endCoorX passenger's end of the trip x-coordinate
     */
    public void setEndCoorX(int endCoorX) {
        this.endCoorX = endCoorX;
    }

    /**
     * Gets whether the passenger has an umbrella
     * @return true if passenger has umbrella, false otherwise
     */
    public boolean getHasUmbrella() {
        return hasUmbrella;
    }

    /**
     * Sets whether the passenger has an umbrella
     * @param hasUmbrella true if passenger has umbrella, false otherwise
     */
    public void setHasUmbrella(boolean hasUmbrella) {
        this.hasUmbrella = hasUmbrella;
    }

    /**
     * Gets the health of passenger
     * @return passenger's health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the health of passenger
     * @param health passenger's health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Gets whether the passenger is dropped off
     * @return true if passenger is dropped off, false otherwise
     */
    public boolean isDroppedOff() {
        return isDroppedOff;
    }

    /**
     * Sets whether the passenger is dropped off
     * @param isDroppedOff true if passenger is dropped off, false otherwise
     */
    public void setIsDroppedOff(boolean isDroppedOff) {
        this.isDroppedOff = isDroppedOff;
    }

    /**
     * Gets whether the passenger is ejected from taxi
     * @return true if passenger is ejected, false otherwise
     */
    public boolean getIsEjected() {
        return isEjected;
    }

    /**
     * Sets whether the passenger is ejected from taxi
     * @param isEjected true if passenger is ejected, false otherwise
     */
    public void setIsEjected(boolean isEjected) {
        this.isEjected = isEjected;
    }

    /**
     * Gets the collision timeout remaining of passenger
     * @return passenger's collision timeout remaining
     */
    public int getCollisionTimeoutLeft() {
        return collisionTimeoutLeft;
    }

    /**
     * Sets the collision timeout remaining of passenger
     * @param collisionTimeoutLeft passenger's collision timeout remaining
     */
    public void setCollisionTimeoutLeft(int collisionTimeoutLeft) {
        this.collisionTimeoutLeft = collisionTimeoutLeft;
    }

    /**
     * Gets the current momentum frame of passenger
     * @return passenger's current momentum frame
     */
    public int getMomentumCurrentFrame() {
        return momentumCurrentFrame;
    }

    /**
     * Sets the current momentum frame of passenger
     * @param momentumCurrentFrame passenger's current momentum frame
     */
    public void setMomentumCurrentFrame(int momentumCurrentFrame) {
        this.momentumCurrentFrame = momentumCurrentFrame;
    }

    /**
     * Moves passenger and passenger's trip end flag by incrementing its y-coordinates based on MOVE_FRAME
     */
    public void moveDown(){
        setCoorY(getCoorY() + TAXI_MOVE_FRAME_Y);
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
     * Decrements passenger's current and original priority
     */
    public void decrementPriority(){
        if (currentPriority < 1 || originalPriority < 1){
            return;
        }
        currentPriority--;
        originalPriority--;
        earnings = calcExpEarnings(getYDist());
    }

    /**
     * Sets current priority to original priority
     */
    public void lockPriority(){
        currentPriority = originalPriority;
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
     * Ejects Passenger
     * @param coorX x-coordinate of Passenger
     * @param coorY y-coodinate of Passenger
     */
    public void eject(int coorX, int coorY){
        setIsEjected(true);
        setCoorX(coorX);
        setCoorY(coorY);
    }

    /**
     * Updates coordinates of Passenger with Driver by altering either x or y coordinate once
     * @param driver Driver object
     */
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

    /**
     * Handles momentum of Passenger during collision
     */
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

    /**
     * Decrements Passenger's collision timeout remaining frames
     */
    public void decrementCollisionTimeoutLeft(){
        if (collisionTimeoutLeft > 0){
            collisionTimeoutLeft--;
        }
    }

    /**
     * Checks the health of Passenger and if below zero, activates blood
     */
    public void checkHealth(){
        if (getHealth() <= 0){
            activateBlood();
        }
    }

    /**
     * Activates blood of Passenger
     */
    public void activateBlood(){
        BLOOD.setIsActive(true);
        BLOOD.setCoorX(getCoorX());
        BLOOD.setCoorY(getCoorY());
    }

    /**
     * Takes damage from a DamageDealer object
     * @param damageDealer DamageDealer object
     */
    @Override
    public void takeDamage(DamageDealer damageDealer) {
        if (getHealth() > 0 && getCollisionTimeoutLeft() == 0){
            setHealth(getHealth() - damageDealer.getDamagePoints());
            setCollisionTimeoutLeft(COLLISION_TIMEOUT);
            checkHealth();
        }
    }

    /**
     * Changes Passenger's priority when raining and recalculates expected earnings
     */
    public void changePriorityWhenRaining(){
        if (!hasUmbrella){
            currentPriority = 1;
            calcExpEarnings(yDist);
        }
    }

    /**
     * Reverts Passenger's priority to original priority when no longer raining and recalculates expected earnings
     */
    public void revertPriorityWhenSunny(){
        currentPriority = originalPriority;
        calcExpEarnings(yDist);
    }

    /**
     * Returns the state of passenger class
     * @return String of states in passenger class
     */
    public String toString(){
        return "Passenger\n" + "_____________" +
                "\nIMAGE: " + IMAGE +
                "\nx-coordinate: " + getCoorX() +
                "\ny-coordinate: " + getCoorY() +
                "\ncurrent priority: " + currentPriority +
                TRIP_END_FLAG +
                "\nhas umbrella: " + hasUmbrella +
                "\nis ejected: " + isEjected +
                "\nis picked up: " + isPickedUp +
                "\nis dropped off: " + isDroppedOff+
                "\nmomentum current frame: " + momentumCurrentFrame;
    }
}
