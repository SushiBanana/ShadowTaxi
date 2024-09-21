import bagel.Image;
import java.util.Properties;

public class Blood extends Effect{

    public final int TTL;

    public Blood(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.blood.image"));
        this.TTL = Integer.parseInt(gameProps.getProperty("gameObjects.blood.ttl"));

    }

    public String toString(){
        return "Blood\n_________\n" + "coor x: " + getCoorX() + "\ncoor y: " + getCoorY() + "\nframes left: "+
                getFramesLeft() + "is active: " + getIsActive();
    }
}
