import java.sql.DatabaseMetaData;
import java.util.Properties;

public abstract class Car extends GameEntity implements Damageable, DamageDealer{

    public final static int COLLISION_TIMEOUT = 200;
    public final static int CAR_COOR_Y_RAND_1 = -50;
    public final static int CAR_COOR_Y_RAND_2 = 768;
    public final static int MOMENTUM = 10;

    public final int MIN_SPEED_Y;
    public final int MAX_SPEED_Y;
    public final int MOVE_FRAME;
    public final double RADIUS;
    public final int DAMAGE_POINTS;
    public final int NUM_TYPES;

    private int health;
    private Smoke smoke;
    private Fire fire;
    private int momentumCurrentFrame;

    private boolean isActive;


    public Car (Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.MIN_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.minSpeedY"));
        this.MAX_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.maxSpeedY"));
        this.MOVE_FRAME = MiscUtils.getRandomInt(MIN_SPEED_Y, MAX_SPEED_Y);
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.radius"));
        this.DAMAGE_POINTS = (int) (Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.damage")) * 100);
        this.NUM_TYPES = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.types"));

        this.health = (int) (Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.health")) * 100);
        this.isActive = true;

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void move() {
        setCoorY(getCoorY() + MOVE_FRAME);
    }

    public void momentumForward(){
        return;
    }

    public void momentumBackward(){
        return;
    }




}
