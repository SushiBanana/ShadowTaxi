import java.util.Properties;

/**
 * This Java abstract class contains attributes and methods related to Car
 * @author Alysha Thean Student ID: 1495768
 */
public abstract class Car extends GameEntity implements Damageable, DamageDealer{

    public final static int COLLISION_TIMEOUT = 200;
    public final static int CAR_COOR_Y_RAND_1 = -50;
    public final static int CAR_COOR_Y_RAND_2 = 768;
    public final static int MOMENTUM = 10;
    public final static int COOR_Y_MOVEMENT_STEP = 1;

    public final int MIN_SPEED_Y;
    public final int MAX_SPEED_Y;
    public final double RADIUS;
    public final double DAMAGE_POINTS;
    public final int NUM_TYPES;
    public final int TAXI_MOVE_FRAME_Y;
    public Fire FIRE;
    public Smoke SMOKE;

    private int moveFrame;
    private double health;
    private int momentumCurrentFrame;
    private int collisionTimeoutLeft;
    private boolean isActive;

    /**
     * Constructor for Car class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of Car
     * @param coorY y-coordinate of Car
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

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public int getMomentumCurrentFrame() {
        return momentumCurrentFrame;
    }

    public void setMomentumCurrentFrame(int momentumCurrentFrame) {
        this.momentumCurrentFrame = momentumCurrentFrame;
    }

    public int getCollisionTimeoutLeft() {
        return collisionTimeoutLeft;
    }

    public void setCollisionTimeoutLeft(int collisionTimeoutLeft) {
        this.collisionTimeoutLeft = collisionTimeoutLeft;
    }

    public int getMoveFrame() {
        return moveFrame;
    }

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
