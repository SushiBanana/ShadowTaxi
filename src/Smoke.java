import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Smoke
 * @author Alysha Thean Student ID: 1495768
 */
public class Smoke extends Effect {
    /**
     * The time to live of smoke
     */
    public final int TTL;

    /**
     * Constructor for Smoke class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of smoke
     * @param coorY y-coordinate of smoke
     */
    public Smoke(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.smoke.image"));
        this.TTL = Integer.parseInt(gameProps.getProperty("gameObjects.smoke.ttl"));
    }

    /**
     * Increments current frame of smoke
     */
    public void incrementCurrentFrame(){
        if (getCurrentFrame() == TTL){
            setIsActive(false);
            return;
        }
        setCurrentFrame(getCurrentFrame() + 1);
    }

    /**
     * Returns the state of Smoke object
     * @return String of states of smoke
     */
    public String toString(){
        return "Smoke\n____________" +
                "\ncoorX: " + getCoorX() +
                "\ncoorY: " + getCoorY() +
                "\nframes left: " + getCurrentFrame() +
                "\nis active: " + getIsActive();
    }
}
