import bagel.Image;

import java.util.ArrayList;
import java.util.Properties;

public class EnemyCar extends Car{

    public final static int DIVISIBILITY = 400;

    public final int TAXI_MOVE_FRAME_Y;
    public final int SHOOT_SPEED_Y;
    public final double DAMAGE_POINTS;


    private ArrayList<Fireball> fireballs;


    public EnemyCar(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.enemyCar.image"));
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        this.SHOOT_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.fireball.shootSpeedY"));
        this.fireballs = new ArrayList<>();
        this.DAMAGE_POINTS = Double.parseDouble(gameProps.getProperty("gameObjects.fireball.damage"));

    }

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    public void setFireballs(ArrayList<Fireball> fireballs) {
        this.fireballs = fireballs;
    }

    @Override
    public void takeDamage(DamageDealer damageDealer) {
        if (getHealth() > 0 && getCollisionTimeoutLeft() == 0){
            setNewRandomMoveFrame();
            SMOKE.activate(getCoorX(), getCoorY());
            setHealth(getHealth() - damageDealer.getDamagePoints());
            setCollisionTimeoutLeft(COLLISION_TIMEOUT);
            checkHealth();
        }
    }

    public void addFireball(){
        Fireball newFireball = new Fireball(GAME_PROPS, getCoorX(), getCoorY());
        fireballs.add(newFireball);
    }

    public void moveFireballs(){
        for (Fireball f: fireballs){
            f.move();
        }
    }

    public void moveFireballsRelativeToTaxi(){
        for (Fireball f: fireballs){
            f.moveRelativeToTaxi();
        }
    }


    public String toString(){
        return  "EnemyCar\n" +"_________________\n" + "x coor: " + getCoorX() + "\ny coor: " + getCoorY() +
                "\nMIN_SPEED_Y: " +
                MIN_SPEED_Y + "\nMAX_SPEED_Y: " + MAX_SPEED_Y + "\nRADIUS: " + RADIUS + "\nDAMAGE_POINTS: " +
                DAMAGE_POINTS + "\nmove frame: " + getMoveFrame() + "\nhealth: " + getHealth() + "\ncollision timeout" +
                " left: " + getCollisionTimeoutLeft();
    }
}
