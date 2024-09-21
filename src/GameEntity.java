import bagel.Image;
import java.util.Properties;

public abstract class GameEntity {

    public final Properties GAME_PROPS;
    public Image IMAGE;

    private int coorX;
    private int coorY;

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

    public abstract String toString();

}
