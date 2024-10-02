import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Driver
 * @author Alysha Thean Student ID: 1495768
 */
public class Driver extends GameEntity implements Damageable{
    /**
     * The driver's ejection x-coordinate offset
     */
    public final static int EJECTION_COOR_X_MINUS = 50;
    /**
     * The momentum frames of driver
     */
    public final static int MOMENTUM = 10;
    /**
     * The collision timeout of driver
     */
    public final static int COLLISION_TIMEOUT = 200;
    /**
     * The movement step of driver's y-coordinate
     */
    public final static int COOR_Y_MOVEMENT_STEP = 2;
    /**
     * The horizontal walking speed of driver
     */
    public final int WALK_SPEED_X;
    /**
     * The vertical walking speed of driver
     */
    public final int WALK_SPEED_Y;
    /**
     * The radius of driver
     */
    public final int RADIUS;
    /**
     * The driver's taxi get in radius
     */
    public final int TAXI_GET_IN_RADIUS;
    /**
     * The blood of driver
     */
    public final Blood BLOOD;

    private double health;
    private int collisionTimeoutLeft;
    private boolean isEjected;
    private int momentumCurrentFrame;
    private InvinciblePower invinciblePower;

    /**
     * Constructor for Driver class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of Driver
     * @param coorY y-coordinate of Driver
     */
    public Driver(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);

        this.IMAGE = new Image(gameProps.getProperty("gameObjects.driver.image"));
        this.WALK_SPEED_X = Integer.parseInt(gameProps.getProperty("gameObjects.driver.walkSpeedX"));
        this.WALK_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.driver.walkSpeedY"));
        this.RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.driver.radius"));
        this.TAXI_GET_IN_RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.driver.taxiGetInRadius"));
        this.BLOOD = new Blood(gameProps, coorX, coorY);

        this.health = Double.parseDouble(gameProps.getProperty("gameObjects.driver.health")) * 100;
        this.isEjected = false;
        this.invinciblePower = new InvinciblePower(gameProps, coorX, coorY);
    }

    /**
     * Gets the health of driver
     * @return the driver's health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the health of driver
     * @param health the driver's health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Gets whether the driver is ejected
     * @return true if ejected, false otherwise
     */
    public boolean getIsEjected() {
        return isEjected;
    }

    /**
     * Sets whether the driver is ejected
     * @param ejected true if ejected, false otherwise
     */
    public void setIsEjected(boolean ejected) {
        isEjected = ejected;
    }

    /**
     * Gets the invincible power of driver
     * @return the driver's invincible power
     */
    public InvinciblePower getInvinciblePower() {
        return invinciblePower;
    }

    /**
     * Sets the invincible power of driver
     * @param invinciblePower the driver's invincible power
     */
    public void setInvinciblePower(InvinciblePower invinciblePower) {
        this.invinciblePower = invinciblePower;
    }

    /**
     * Gets the collision timeout remaining of driver
     * @return the driver's collision timeout remaining
     */
    public int getCollisionTimeoutLeft() {
        return collisionTimeoutLeft;
    }

    /**
     * Sets the collision timeout remaining of driver
     * @param collisionTimeoutLeft the driver's collision timeout remaining
     */
    public void setCollisionTimeoutLeft(int collisionTimeoutLeft) {
        this.collisionTimeoutLeft = collisionTimeoutLeft;
    }

    /**
     * Gets the momentum current frame of driver
     * @return the driver's current momentum frame
     */
    public int getMomentumCurrentFrame() {
        return momentumCurrentFrame;
    }

    /**
     * Sets the momentum current frame of driver
     * @param momentumCurrentFrame the driver's current momentum frame
     */
    public void setMomentumCurrentFrame(int momentumCurrentFrame) {
        this.momentumCurrentFrame = momentumCurrentFrame;
    }

    /**
     * Ejects Driver from Taxi
     * @param coorX x-coordinate of Driver's ejected position
     * @param coorY y-coordinate of Driver's ejected position
     */
    public void eject(int coorX, int coorY){
        setIsEjected(true);
        setCoorX(coorX);
        setCoorY(coorY);
    }

    /**
     * Handles momentum of Driver after collision
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
     * Checks health of Driver and activates blood if health if below 0
     */
    public void checkHealth(){
        if (getHealth() <= 0){
            activateBlood();
        }
    }

    /**
     * Activates Blood of Driver
     */
    public void activateBlood(){
        BLOOD.setIsActive(true);
        BLOOD.setCoorX(getCoorX());
        BLOOD.setCoorY(getCoorY());
    }

    /**
     * Takes damage from DamageDealer
     * @param damageDealer GameEntity object that can deal damage
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
     * Decrements collision timeout frames remaining
     */
    public void decrementCollisionTimeoutLeft(){
        if (collisionTimeoutLeft > 0){
            collisionTimeoutLeft--;
        }
    }

    /**
     * Moves Driver left based on x-coordinate walking speed
     */
    public void moveLeft() {
        setCoorX(getCoorX() - WALK_SPEED_X);
    }

    /**
     * Moves Driver right based on x-coordinate walking speed
     */
    public void moveRight() {
        setCoorX(getCoorX() + WALK_SPEED_X);
    }

    /**
     * Moves Driver up based on y-coordinate walking speed
     */
    public void moveUp() {
        setCoorY(getCoorY() - WALK_SPEED_Y);
    }

    /**
     * Moves Driver and their Blood down based on y-coordinate walking speed
     */
    public void moveDown() {
        BLOOD.moveDown();
        setCoorY(getCoorY() + WALK_SPEED_Y);
    }

    /**
     * Updates Driver's coordinates with Taxi when inside
     */
    public void moveWithTaxi(Taxi taxi) {
        setCoorX(getCoorX() - taxi.MOVE_FRAME_X);
        setCoorY(getCoorY() - taxi.MOVE_FRAME_Y);
    }

    /**
     * Returns the state of Driver object
     * @return String of states of Driver object
     */
    public String toString(){
        return "DRIVER\n________" +
                "\nis ejected:" + isEjected +
                "\ncoor x: " + getCoorX() +
                "\ncoor y: " + getCoorY();
    }
}
