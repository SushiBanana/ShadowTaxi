import bagel.Image;
import java.util.Properties;

/**
 * This Java abstract class contains attributes and methods related to GameEntity
 * @author Alysha Thean Student ID: 1495768
 */
public abstract class GameEntity {
    /**
     * The game properties of game entity
     */
    public final Properties GAME_PROPS;
    /**
     * The image of game entity
     */
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

    /**
     * Gets the x-coordinate of game entity
     * @return the game entity's x-coordinate
     */
    public int getCoorX() {
        return coorX;
    }

    /**
     * Sets the x-coordinate of game entity
     * @param coorX the game entity's x-coordinate
     */
    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    /**
     * Gets the y-coordinate of game entity
     * @return the game entity's y-coordinate
     */
    public int getCoorY() {
        return coorY;
    }

    /**
     * Sets the y-coordinate of game entity
     * @param coorY the game entity's y-coordinate
     */
    public void setCoorY(int coorY) {
        this.coorY = coorY;
    }

    /**
     * Returns the state of GameEntity object
     * @return String of states of GameEntity
     */
    public abstract String toString();

}
