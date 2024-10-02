import java.util.Properties;

/**
 * This Java abstract class contains attributes and methods related to Car
 * @author Alysha Thean Student ID: 1495768
 */
public abstract class Car extends GameEntity implements Damageable, DamageDealer{
    /**
     * The collision timeout of car
     */
    public final static int COLLISION_TIMEOUT = 200;
    /**
     * The first random y-coordinate of car
     */
    public final static int CAR_COOR_Y_RAND_1 = -50;
    /**
     * The second random y-coordinate of car
     */
    public final static int CAR_COOR_Y_RAND_2 = 768;
    /**
     * The momentum frames of car
     */
    public final static int MOMENTUM = 10;
    /**
     * The movement step of car's y-coordinate
     */
    public final static int COOR_Y_MOVEMENT_STEP = 1;
    /**
     * The minimum vertical speed of car
     */
    public final int MIN_SPEED_Y;
    /**
     * The maximum vertical speed of car
     */
    public final int MAX_SPEED_Y;
    /**
     * The radius of car
     */
    public final double RADIUS;
    /**
     * The damage points of car
     */
    public final double DAMAGE_POINTS;
    /**
     * The number of car types
     */
    public final int NUM_TYPES;
    /**
     * The taxi's vertical speed
     */
    public final int TAXI_MOVE_FRAME_Y;
    /**
     * The fire of car
     */
    public Fire FIRE;
    /**
     * The smoke of car
     */
    public Smoke SMOKE;

    private int moveFrame;
    private double health;
    private int momentumCurrentFrame;
    private int collisionTimeoutLeft;
    private boolean isActive;

    /**
     * Constructor for Car class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of car
     * @param coorY y-coordinate of car
     */
    public Car (Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.MIN_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.minSpeedY"));
        this.MAX_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.maxSpeedY"));
        this.moveFrame = MiscUtils.getRandomInt(MIN_SPEED_Y, MAX_SPEED_Y + 1);
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.radius"));
        this.DAMAGE_POINTS = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.damage")) * 100;
        this.NUM_TYPES = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.types"));
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        this.FIRE = new Fire(gameProps, coorX, coorY);
        this.SMOKE = new Smoke(gameProps, coorX, coorY);

        this.health = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.health")) * 100;
        this.isActive = true;

    }

    /**
     * Gets the health of car
     * @return the car's health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the health of car
     * @param health the car's health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Gets whether the car is active
     * @return true if active, false otherwise
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets whether the car is active
     * @param active true if active, false otherwise
     */
    public void setIsActive(boolean active) {
        isActive = active;
    }

    /**
     * Gets the car's current momentum frame
     * @return the car's current momentum frame
     */
    public int getMomentumCurrentFrame() {
        return momentumCurrentFrame;
    }

    /**
     * Gets the car's current momentum frame
     * @param momentumCurrentFrame the car's current momentum frame
     */
    public void setMomentumCurrentFrame(int momentumCurrentFrame) {
        this.momentumCurrentFrame = momentumCurrentFrame;
    }

    /**
     * Gets the car's collision timeout remaining
     * @return the car's collision timeout remaining
     */
    public int getCollisionTimeoutLeft() {
        return collisionTimeoutLeft;
    }

    /**
     * Sets the car's collision timeout remaining
     * @param collisionTimeoutLeft the car's collision timeout remaining
     */
    public void setCollisionTimeoutLeft(int collisionTimeoutLeft) {
        this.collisionTimeoutLeft = collisionTimeoutLeft;
    }

    /**
     * Gets the car's move frame
     * @return the car's move frame
     */
    public int getMoveFrame() {
        return moveFrame;
    }

    /**
     * Sets the car's move frame
     * @param moveFrame the car's move frame
     */
    public void setMoveFrame(int moveFrame) {
        this.moveFrame = moveFrame;
    }

    /**
     * Returns Car's damage points
     * @return double of Car damage points
     */
    @Override
    public double getDamagePoints(){
        return DAMAGE_POINTS;
    }

    /**
     * Sets Car's new randomly generated move frame
     */
    public void setNewRandomMoveFrame(){
        moveFrame = MiscUtils.getRandomInt(MIN_SPEED_Y, MAX_SPEED_Y + 1);
    }

    /**
     * Moves Car based on its moving frame speed
     */
    public void move() {
        if (momentumCurrentFrame != 0){
            return;
        }
        setCoorY(getCoorY() - moveFrame);
    }

    /**
     * Moves Car based on its moving frame speed relative to Taxi's speed
     */
    public void moveRelativeToTaxi(){
        if (momentumCurrentFrame != 0){
            return;
        }
        setCoorY(getCoorY() - (moveFrame - TAXI_MOVE_FRAME_Y));
    }

    /**
     * Checks the health of Car and activates relevant Effect
     */
    public void checkHealth() {
        if (getHealth() <= 0){
            SMOKE.setIsActive(false);
            FIRE.activate(getCoorX(), getCoorY());
        }
    }

    /**
     * Deactivates Car after all Fire frames are done rendering
     */
    public void deactivateAfterFire(){
        if (!FIRE.getIsActive()) {
            setIsActive(false);
        }
    }

    /**
     * Handles Car's momentum after collision and
     */
    public void handleMomentum() {
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
     * Decrements remaining collision timeout frames
     */
    public void decrementCollisionTimeoutLeft(){
        if (collisionTimeoutLeft > 0){
            collisionTimeoutLeft--;
        }
    }

}
