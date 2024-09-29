import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Driver
 * @author Alysha Thean Student ID: 1495768
 */
public class Driver extends GameEntity implements Damageable{

    public final static int EJECTION_COOR_X_MINUS = 50;
    public final static int MOMENTUM = 10;
    public final static int COLLISION_TIMEOUT = 200;
    public final static int COOR_Y_MOVEMENT_STEP = 2;

    public final int WALK_SPEED_X;
    public final int WALK_SPEED_Y;
    public final int RADIUS;
    public final int TAXI_GET_IN_RADIUS;

    private double health;
    private int collisionTimeoutLeft;
    private boolean isEjected;
    public final Blood BLOOD;
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

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean getIsEjected() {
        return isEjected;
    }

    public void setIsEjected(boolean ejected) {
        isEjected = ejected;
    }

    public InvinciblePower getInvinciblePower() {
        return invinciblePower;
    }

    public void setInvinciblePower(InvinciblePower invinciblePower) {
        this.invinciblePower = invinciblePower;
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
