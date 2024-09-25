import bagel.Image;

import java.util.Properties;

public class Smoke extends Effect {

    public final int TTL;


    // smoke is rendered when an entity takes damage, not the entity that inflicts the damage
    public Smoke(Properties gameProps, int coorX, int coorY){

        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.smoke.image"));
        this.TTL = Integer.parseInt(gameProps.getProperty("gameObjects.smoke.ttl"));


    }

    public void incrementCurrentFrame(){
        if (getCurrentFrame() == TTL){
            setIsActive(false);
            return;
        }

        setCurrentFrame(getCurrentFrame() + 1);
    }


    public String toString(){
        return "Smoke\n____________" + "\ncoorX: " + getCoorX() + "\ncoorY: " + getCoorY() + "\nframes left: " +
                getCurrentFrame() + "\nis active: " + getIsActive();
    }
}
