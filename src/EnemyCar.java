import bagel.Image;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to EnemyCar
 * @author Alysha Thean Student ID: 1495768
 */
public class EnemyCar extends Car{

    public final static int DIVISIBILITY = 400;

    public final int TAXI_MOVE_FRAME_Y;
    public final int SHOOT_SPEED_Y;
    public final double DAMAGE_POINTS;

    private ArrayList<Fireball> fireballs;

    /**
     * Constructor for EnemyCar class
     * @param gameProps properties file for values of various attributes
     * @param coorX x-coordinate of EnemyCar
     * @param coorY y-coordinate of EnemyCar
     */
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

    /**
     * Takes damage from DamageDealer and assigns new move frame speed
     * @param damageDealer GameEntity object that can deal damage
     */
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

    /**
     * Adds a fireball to EnemyCar's fireballs arraylist
     */
    public void addFireball(){
        Fireball newFireball = new Fireball(GAME_PROPS, getCoorX(), getCoorY());
        fireballs.add(newFireball);
    }

    /**
     * Moves all fireballs
     */
    public void moveFireballs(){
        for (Fireball f: fireballs){
            f.move();
        }
    }

    /**
     * Moves all fireballs relative to Taxi's speed
     */
    public void moveFireballsRelativeToTaxi(){
        for (Fireball f: fireballs){
            f.moveRelativeToTaxi();
        }
    }

    /**
     * Returns the state of EnemyCar object
     * @return String of states of EnemyCar object
     */
    public String toString(){
        return  "EnemyCar\n_________________" +
                "\nx coor: " + getCoorX() +
                "\ny coor: " + getCoorY() +
                "\nMIN_SPEED_Y: " + MIN_SPEED_Y +
                "\nMAX_SPEED_Y: " + MAX_SPEED_Y +
                "\nRADIUS: " + RADIUS +
                "\nDAMAGE_POINTS: " + DAMAGE_POINTS +
                "\nmove frame: " + getMoveFrame() +
                "\nhealth: " + getHealth() +
                "\ncollision timeout left: " + getCollisionTimeoutLeft();
    }
}
