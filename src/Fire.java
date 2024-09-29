import bagel.Image;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Fire
 * @author Alysha Thean Student ID: 1495768
 */
public class Fire extends Effect{

    public final int TTL;

    /**
     * Constructor for Fire class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of Fire
     * @param coorY y-coordinate of Fire
     */
    public Fire(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.fire.image"));
        this.TTL = Integer.parseInt(gameProps.getProperty("gameObjects.fire.ttl"));

    }

    /**
     * Increments current frame of Fire
     */
    public void incrementCurrentFrame(){
        if (getCurrentFrame() == TTL){
            setIsActive(false);
            return;
        }
        setCurrentFrame(getCurrentFrame() + 1);
    }

    /**
     * Returns the state of Fire object
     * @return String of states of Fire
     */
    public String toString(){
        return "FIRE\n___________" +
                "\nTTL: " + TTL +
                "\ncoor x: " + getCoorX() +
                "\ncoor y: " + getCoorY()+
                "\ncurrent frame: " + getCurrentFrame();
    }
}
