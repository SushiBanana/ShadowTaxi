import bagel.Image;
import bagel.Window;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Taxi
 * @author Alysha Thean Student ID: 1495768
 */
public class Taxi extends GameEntity implements Damageable, DamageDealer{

    public final static int COLLISION_TIMEOUT = 200;
    public final static int MOMENTUM = 10;
    public final static int COOR_Y_MOVEMENT_STEP = 1;

    public final double RADIUS;
    public final int MOVE_FRAME_X;
    public final Image UNDAMAGED_IMAGE;
    public final Image DAMAGED_IMAGE;
    public final double DAMAGE_POINTS;
    public final int SPAWN_MIN_Y;
    public final int SPAWN_MAX_Y;
    public final int MOVE_FRAME_Y;
    public Smoke SMOKE;
    public Fire FIRE;

    private Passenger currentPassenger;
    private double health;
    private int collisionTimeoutLeft;
    private int momentumCurrentFrame;
    private boolean isDamaged;
    private boolean hasDriver;
    private InvinciblePower invinciblePower;

    /**
     * Constructor for Taxi class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of Taxi
     * @param coorY y-coordinate of Taxi
     */
    public Taxi(Properties gameProps, int coorX, int coorY, boolean isDamaged){
        super(gameProps, coorX, coorY);

        this.UNDAMAGED_IMAGE = new Image(gameProps.getProperty("gameObjects.taxi.image"));
        this.DAMAGED_IMAGE = new Image(gameProps.getProperty("gameObjects.taxi.damagedImage"));
        this.IMAGE = generateImage(isDamaged);
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.taxi.radius"));
        this.MOVE_FRAME_X = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedX"));
        this.DAMAGE_POINTS = Double.parseDouble(gameProps.getProperty("gameObjects.taxi.health")) * 100;
        this.SPAWN_MIN_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.nextSpawnMinY"));
        this.SPAWN_MAX_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.nextSpawnMaxY"));
        this.MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        this.FIRE = new Fire(gameProps, coorX, coorY);
        this.SMOKE = new Smoke(gameProps, coorX, coorY);

        this.currentPassenger = null;
        this.health = Double.parseDouble(gameProps.getProperty("gameObjects.taxi.damage")) * 100;
        this.isDamaged = isDamaged;
        this.collisionTimeoutLeft = 0;
        this.hasDriver = true;
        this.invinciblePower = new InvinciblePower(gameProps, coorX, coorY);
    }

    public Passenger getCurrentPassenger() {
        return currentPassenger;
    }

    public void setCurrentPassenger(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean getIsDamaged(){
        return isDamaged;
    }

    public void setIsDamaged(boolean isDamaged){
        this.isDamaged = isDamaged;
    }

    public boolean getHasDriver(){
        return hasDriver;
    }

    public void setHasDriver(boolean hasDriver){
        this.hasDriver = hasDriver;
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

    public InvinciblePower getInvinciblePower() {
        return invinciblePower;
    }

    public void setInvinciblePower(InvinciblePower invinciblePower) {
        this.invinciblePower = invinciblePower;
    }

    /**
     * Returns the damage points of Taxi
     * @return double of Taxi's damage points
     */
    @Override
    public double getDamagePoints() {
        return DAMAGE_POINTS;
    }

    /**
     * Decrements the x-coordinate of taxi when moving left based on MOVE_FRAME
     */
    public void moveLeft(){
        setCoorX(getCoorX() - MOVE_FRAME_X);
    }

    /**
     * Increments the x-coordinate of taxi when moving right based on MOVE_FRAME
     */
    public void moveRight(){
        setCoorX(getCoorX() + MOVE_FRAME_X);
    }

    /**
     * Calculates the distance of taxi's coordinates with another set of coordinates
     * @param targetCoorX x-coordinates of target
     * @param targetCoorY y-coordinates of target
     * @return double of distance between taxi's and target's coordinates
     */
    public double calcDist(double targetCoorX, double targetCoorY){
        return Math.sqrt(Math.pow(getCoorX() - targetCoorX, 2) + Math.pow(getCoorY() - targetCoorY, 2));
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
        return currentPassenger.TRIP_END_FLAG.getCoorY() - getCoorY();
    }

    /**
     * Generates Image of Taxi based on whether it is damaged
     * @param isDamaged boolean if taxi is damaged false otherwise
     * @return Image object of Taxi
     */
    public Image generateImage(boolean isDamaged){
        if (isDamaged){
            return DAMAGED_IMAGE;
        } else {
            return UNDAMAGED_IMAGE;
        }
    }

    /**
     * Checks if taxi is permanently damaged
     */
    public void checkIfPermanentlyDamaged(){
        if (health <= 0){
            isDamaged = true;
            SMOKE.setIsActive(false);
            FIRE.activate(getCoorX(), getCoorY());
            hasDriver = false;
        }
    }

    /**
     * Taxi takes damage from a DamageDealer object if its health is above 0 and collision timeout left is 0
     * @param damageDealer GameEntity object that can deal damage
     */
    @Override
    public void takeDamage(DamageDealer damageDealer) {
        if (health > 0 && collisionTimeoutLeft == 0){
            SMOKE.activate(getCoorX(), getCoorY());
            health = health - damageDealer.getDamagePoints();
            collisionTimeoutLeft = COLLISION_TIMEOUT;
            checkIfPermanentlyDamaged();
        }

    }

    /**
     * Decrements collision timeout frames remaining of Taxi
     */
    public void decrementCollisionTimeoutLeft(){
        if (collisionTimeoutLeft > 0){
            collisionTimeoutLeft--;
        }
    }

    /**
     * Moves Taxi and its Fire and Smoke down based on its move speed
     */
    public void moveDown(){
        FIRE.moveDown();
        SMOKE.moveDown();

        setCoorY(getCoorY() + MOVE_FRAME_Y);
    }

    /**
     * Moves Taxi's Effects only down based on its move speed
     */
    public void moveEffectsDown(){
        FIRE.moveDown();
        SMOKE.moveDown();
    }

    /**
     * Handles momentum of Taxi
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
     * Checks if Taxi is out of frame
     * @return true if it is out of frame, false otherwise
     */
    public boolean isOutOfFrame() {
        boolean flag;
        if (getCoorY() >= Window.getHeight()) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Returns the state of taxi object
     * @return String of states in taxi object
     */
    public String toString(){
        return "Taxi\n" + "_____________" +
                "\nIMAGE: " + IMAGE +
                "\nRADIUS: " + RADIUS +
                "\nMOVE_FRAME_X: " + MOVE_FRAME_X +
                "\nx" + "-coordinate: " + getCoorX() +
                "\ny-coordinate: " + getCoorY() +
                "\nDAMAGE_POINTS: " + DAMAGE_POINTS;
    }

}
