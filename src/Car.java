
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
    public final double DAMAGE_POINTS;
    public final int NUM_TYPES;
    public final int TAXI_MOVE_FRAME_Y;

    private double health;
    private Smoke smoke;
    private Fire fire;
    private int momentumCurrentFrame;

    private boolean isActive;


    public Car (Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.MIN_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.minSpeedY"));
        this.MAX_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.maxSpeedY"));
        this.MOVE_FRAME = MiscUtils.getRandomInt(MIN_SPEED_Y, MAX_SPEED_Y + 1);
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.radius"));
        this.DAMAGE_POINTS = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.damage")) * 100;
        this.NUM_TYPES = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.types"));
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));

        this.health = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.health")) * 100;
        this.isActive = true;

    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void move() {
        setCoorY(getCoorY() - MOVE_FRAME);
    }

    public void moveRelativeToTaxi(){
        setCoorY(getCoorY() - (MOVE_FRAME - TAXI_MOVE_FRAME_Y));
    }

    public void momentumForward(){
        return;
    }

    public void momentumBackward(){
        return;
    }

    public double getDamagePoints(){
        return DAMAGE_POINTS;
    }

    public void checkHealth() {
        if (getHealth() <= 0){
            setIsActive(false);
        }
    }




}
