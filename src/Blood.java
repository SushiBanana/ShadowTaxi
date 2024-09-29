import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Blood
 * @author Alysha Thean Student ID: 1495768
 */
public class Blood extends Effect{

    public final int TTL;

    /**
     * Constructor for Blood class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of Blood
     * @param coorY y-coordinate of Blood
     */
    public Blood(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.blood.image"));
        this.TTL = Integer.parseInt(gameProps.getProperty("gameObjects.blood.ttl"));
    }

    /**
     * Increments current frame of Blood until it reaches its Time To Live
     */
    public void incrementCurrentFrame(){
        if (getCurrentFrame() == TTL){
            setIsActive(false);
            return;
        }

        setCurrentFrame(getCurrentFrame() + 1);
    }

    /**
     * Return the state of Blood object
     * @return String of states of Blood object
     */
    public String toString(){
        return "Blood\n_________" +
                "\ncoor x: " + getCoorX() +
                "\ncoor y: " + getCoorY() +
                "\nframes left: " + getCurrentFrame() +
                "\nis active: " + getIsActive();
    }
}
