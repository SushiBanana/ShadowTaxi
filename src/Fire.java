import bagel.Image;

import java.util.Properties;

public class Fire extends Effect{

    public final int TTL;


    public Fire(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.fire.image"));
        this.TTL = Integer.parseInt(gameProps.getProperty("gameObjects.fire.ttl"));


    }

    public String toString(){
        return "FIRE\n___________\n" + "TTL: " + TTL + "coor x: " + getCoorX() + "coor y: " + getCoorY();
    }

}
