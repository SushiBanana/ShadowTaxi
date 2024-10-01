import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Fireball
 * @author Alysha Thean Student ID: 1495768
 */
public class Fireball extends GameEntity implements DamageDealer{

    public static final int DIVISIBILITY = 300;

    public final int TAXI_MOVE_FRAME_Y;
    public final int SHOOT_SPEED;
    public final double RADIUS;
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

    public boolean getIsActive() {
        return isActive;
    }

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
