import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Fireball
 * @author Alysha Thean Student ID: 1495768
 */
public class Fireball extends GameEntity implements DamageDealer{
    /**
     * The divisibility of fireball
     */
    public static final int DIVISIBILITY = 300;
    /**
     * The taxi's vertical speed
     */
    public final int TAXI_MOVE_FRAME_Y;
    /**
     * The vertical speed of fireball
     */
    public final int SHOOT_SPEED;
    /**
     * The radius of fireball
     */
    public final double RADIUS;
    /**
     * The damage points of fireball
     */
    public final int DAMAGE_POINTS;

    private boolean isActive;

    /**
     * Constructor for Fireball class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of Fireball
     * @param coorY y-coordinate of Fireball
     */
    public Fireball(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);

        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.fireball.image"));
        this.SHOOT_SPEED =Integer.parseInt(gameProps.getProperty("gameObjects.fireball.shootSpeedY"));
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.fireball.radius"));
        this.DAMAGE_POINTS = (int) (Double.parseDouble(gameProps.getProperty("gameObjects.fireball.damage")) * 100);

        this.isActive = true;
    }

    /**
     * Gets whether the fireball is active
     * @return true if active, false otherwise
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets whether the fireball is active
     * @param active true if active, false otherwise
     */
    public void setIsActive(boolean active) {
        isActive = active;
    }

    /**
     * Getter method for damage points because Fireball can deal damage
     * @return double of Fireball damage points
     */
    @Override
    public double getDamagePoints() {
        return DAMAGE_POINTS;
    }

    /**
     * Moves Fireball based on SHOOT_SPEED and is considered not active after it reaches the top of the screen
     */
    public void move(){
        if (getCoorY() <= 0){
            setIsActive(false);
        }
        setCoorY(getCoorY() - SHOOT_SPEED);
    }

    /**
     * Moves Fireball relative to Taxi's speed
     */
    public void moveRelativeToTaxi(){
        if (getCoorY() <= 0){
            setIsActive(false);
        }
        setCoorY(getCoorY() - (SHOOT_SPEED - TAXI_MOVE_FRAME_Y));
    }

    /**
     * Returns the state of Fireball object
     * @return String of states of Fireball
     */
    @Override
    public String toString() {
        return "FIREBALL\n____________" +
                "\ncoor x: " + getCoorX() +
                "\ncoor y: "  + getCoorY();
    }
}
