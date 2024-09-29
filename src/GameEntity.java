import bagel.Image;
import java.util.Properties;

/**
 * This Java abstract class contains attributes and methods related to GameEntity
 * @author Alysha Thean Student ID: 1495768
 */
public abstract class GameEntity {

    public final Properties GAME_PROPS;
    public Image IMAGE;

    private int coorX;
    private int coorY;

    /**
     * Constructor for GameEntity class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of GameEntity
     * @param coorY y-coordinate of GameEntity
     */
    public GameEntity(Properties gameProps, int coorX, int coorY){
        this.GAME_PROPS = gameProps;
        this.coorX = coorX;
        this.coorY = coorY;
    }

    public int getCoorX() {
        return coorX;
    }

    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    public int getCoorY() {
        return coorY;
    }

    public void setCoorY(int coorY) {
        this.coorY = coorY;
    }

    /**
     * Returns the state of GameEntity object
     * @return String of states of GameEntity
     */
    public abstract String toString();

}
