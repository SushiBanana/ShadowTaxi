import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Taxi
 * @author Alysha Thean Student ID: 1495768
 */
public class Taxi extends GameEntity implements Damageable, DamageDealer{

    public final double RADIUS;
    public final int MOVE_FRAME;
    public final Image UNDAMAGED_IMAGE;
    public final Image DAMAGED_IMAGE;

    private Passenger currentPassenger;

    //
    public final static int COLLISION_TIMEOUT = 200;
    public final static int MOMENTUM = 10;

    public final int DAMAGE_POINTS;
    public final int SPAWN_MIN_Y;
    public final int SPAWN_MAX_Y;

    private int health;
    private int collisionTimeoutLeft;
    private Smoke smoke;
    private Fire fire;
    private int momentumCurrentFrame;
    private boolean isDamaged;
    private Driver driver;


    /**
     * Constructor for Taxi class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of taxi
     * @param coorY y-coordinate of taxi
     */
    public Taxi(Properties gameProps, int coorX, int coorY, boolean isDamaged){
        super(gameProps, coorX, coorY);


        this.UNDAMAGED_IMAGE = new Image(gameProps.getProperty("gameObjects.taxi.image"));
        this.DAMAGED_IMAGE = new Image(gameProps.getProperty("gameObjects.taxi.damagedImage"));
        this.IMAGE = generateImage(isDamaged);


        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.taxi.radius"));
        this.MOVE_FRAME = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedX"));

        this.currentPassenger = null;

        //
        this.DAMAGE_POINTS = (int) (Double.parseDouble(gameProps.getProperty("gameObjects.taxi.health")) * 100);
        this.SPAWN_MIN_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.nextSpawnMinY"));
        this.SPAWN_MAX_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.nextSpawnMaxY"));


        this.health = (int) (Double.parseDouble(gameProps.getProperty("gameObjects.taxi.damage")) * 100);
        this.isDamaged = isDamaged;

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
        setCoorX(getCoorX() - MOVE_FRAME);
    }

    /**
     * Increments the x-coordinate of taxi when moving right based on MOVE_FRAME
     */
    public void moveRight(){
        setCoorX(getCoorX() + MOVE_FRAME);
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
     * Returns the state of taxi class
     * @return String of states in taxi class
     */
    public String toString(){
        return "Taxi\n" + "_____________\nIMAGE: " + IMAGE + "\nRADIUS: " + RADIUS + "\nMOVE_FRAME: " + MOVE_FRAME +
                "\nx" + "-coordinate: " + getCoorX() + "\ny-coordinate: " + getCoorY() + "\n";
    }

    public Image generateImage(boolean isDamaged){
        if (isDamaged){
            return DAMAGED_IMAGE;
        } else {
            return UNDAMAGED_IMAGE;
        }

    }

    /**
     * Checks if taxi is permanently damaged
     * @return true if yes, false otherwise
     */
    public boolean checkIfPermanentlyDamaged(){
        if (health <= 0){
            isDamaged = true;
            return true;
        }
        return false;
    }

    public void momentumForward(){
        return;
    }

    public void momentumBackward(){
        return;
    }


    /**
     * Taxi takes damage from other Game Entity
     *
     * @param damageDealer GameEntity that inflicted damage on Taxi
     */
    @Override
    public void takeDamage(DamageDealer damageDealer) {
        return;
    }

    /**
     * Taxi can only deal damage if its coordinates and other entities' coordinates is less than sum of radius of both
     * @param damageable GameEntity object that damage gets done on
     */
    @Override
    public void dealDamage(Damageable damageable) {
        return;
    }

    public void ejectDriverPassenger(){

    }


}
