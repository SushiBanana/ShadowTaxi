import bagel.Image;

import java.util.Properties;

public class EnemyCar extends Car{

    public final static int DIVISIBILITY = 400;

    private Fireball fireball;


    public EnemyCar(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.enemyCar.image"));

    }

    @Override
    public void dealDamage(Damageable damageable) {
        return;
    }

    @Override
    public void takeDamage(DamageDealer damageDealer) {
        return;

    }

    public String toString(){
        return  "EnemyCar\n" +"_________________" + "x coor: " + getCoorX() + "\ny coor: " + getCoorY() +
                "\nMIN_SPEED_Y: " +
                MIN_SPEED_Y + "\nMAX_SPEED_Y: " + MAX_SPEED_Y + "\nRADIUS: " + RADIUS + "\nDAMAGE_POINTS: " +
                DAMAGE_POINTS + "\nmove frame: " + MOVE_FRAME + "\nhealth: " + getHealth();
    }
}
