import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to OtherCar
 * @author Alysha Thean Student ID: 1495768
 */
public class OtherCar extends Car{

    public final static int DIVISIBILITY = 200;

    /**
     * Constructor for OtherCar class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of OtherCar
     * @param coorY y-coordinate of OtherCar
     */
    public OtherCar(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = generateImage();
    }

    /**
     * Generates Image of OtherCar based on NUM_TYPES available
     * @return Image object of OtherCar randomly chosen
     */
    public Image generateImage(){
        int randomNumber = MiscUtils.getRandomInt(1, NUM_TYPES + 1);
        String fileName = String.format(GAME_PROPS.getProperty("gameObjects.otherCar.image"), randomNumber);
        Image image = new Image(fileName);
        return image;
    }

    /**
     * Takes damage from DamageDealer and assigns new move frame speed
     * @param damageDealer GameEntity object that can deal damage
     */
    @Override
    public void takeDamage(DamageDealer damageDealer) {
        if (getHealth() > 0 && getCollisionTimeoutLeft() == 0){
            setNewRandomMoveFrame();
            SMOKE.activate(getCoorX(), getCoorY());
            setHealth(getHealth() - damageDealer.getDamagePoints());
            setCollisionTimeoutLeft(COLLISION_TIMEOUT);
            checkHealth();

        }
    }

    /**
     * Returns the state of OtherCar object
     * @return String of states of OtherCar
     */
    public String toString(){
        return  "OtherCar\n" +"_________________" +
                "\nx coor: " + getCoorX() +
                "\ny coor: " + getCoorY() +
                "\nMIN_SPEED_Y: " + MIN_SPEED_Y +
                "\nMAX_SPEED_Y: " + MAX_SPEED_Y +
                "\nRADIUS: " + RADIUS +
                "\nDAMAGE_POINTS: " + DAMAGE_POINTS +
                "\nmove frame: " + getMoveFrame() +
                "\nhealth: " + getHealth() +
                "\ncollision timeout left: " + getCollisionTimeoutLeft();
    }
}
