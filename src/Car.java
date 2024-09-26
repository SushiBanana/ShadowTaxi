
import java.util.Properties;

public abstract class Car extends GameEntity implements Damageable, DamageDealer{

    public final static int COLLISION_TIMEOUT = 200;
    public final static int CAR_COOR_Y_RAND_1 = -50;
    public final static int CAR_COOR_Y_RAND_2 = 768;
    public final static int MOMENTUM = 10;

    public final int MIN_SPEED_Y;
    public final int MAX_SPEED_Y;
    public final double RADIUS;
    public final double DAMAGE_POINTS;
    public final int NUM_TYPES;
    public final int TAXI_MOVE_FRAME_Y;
    public Fire FIRE;
    public Smoke SMOKE;

    private int moveFrame;
    private double health;
    private int momentumCurrentFrame;
    private int collisionTimeoutLeft;
    private int invincibilityLeft;


    private boolean isActive;


    public Car (Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.MIN_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.minSpeedY"));
        this.MAX_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.maxSpeedY"));
        this.moveFrame = MiscUtils.getRandomInt(MIN_SPEED_Y, MAX_SPEED_Y + 1);
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.radius"));
        this.DAMAGE_POINTS = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.damage")) * 100;
        this.NUM_TYPES = Integer.parseInt(gameProps.getProperty("gameObjects.otherCar.types"));
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));

        this.health = Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.health")) * 100;
        this.isActive = true;
        this.FIRE = new Fire(gameProps, coorX, coorY);
        this.SMOKE = new Smoke(gameProps, coorX, coorY);

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

    public int getMomentumCurrentFrame() {
        return momentumCurrentFrame;
    }

    public void setMomentumCurrentFrame(int momentumCurrentFrame) {
        this.momentumCurrentFrame = momentumCurrentFrame;
    }

    public int getCollisionTimeoutLeft() {
        return collisionTimeoutLeft;
    }

    public void setCollisionTimeoutLeft(int collisionTimeoutLeft) {
        this.collisionTimeoutLeft = collisionTimeoutLeft;
    }

    public int getMoveFrame() {
        return moveFrame;
    }

    public void setMoveFrame(int moveFrame) {
        this.moveFrame = moveFrame;
    }

    public void move() {
        if (momentumCurrentFrame != 0){
            return;
        }
        setCoorY(getCoorY() - moveFrame);
    }

    public void moveRelativeToTaxi(){
        if (momentumCurrentFrame != 0){
            return;
        }
        setCoorY(getCoorY() - (moveFrame - TAXI_MOVE_FRAME_Y));
    }

    public void momentumForward(){
        return;
    }

    public void momentumBackward(){
        return;
    }

    public void activateSmoke(){
        SMOKE.setIsActive(true);
        SMOKE.setCoorX(getCoorX());
        SMOKE.setCoorY(getCoorY());
    }

    public void activateFire(){

        FIRE.setIsActive(true);
        FIRE.setCoorX(getCoorX());
        FIRE.setCoorY(getCoorY());
    }

    public double getDamagePoints(){
        return DAMAGE_POINTS;
    }

    public void checkHealth() {
        if (getHealth() <= 0){
            SMOKE.setIsActive(false);
            activateFire();
        }
    }

    public void deactivateAfterFire(){
        if (!FIRE.getIsActive()) {
            setIsActive(false);
        }
    }

    public void handleMomentum() {
        if (momentumCurrentFrame == 0){
            return;
        }
        if (momentumCurrentFrame > 0){
            setCoorY(getCoorY() + 1);
            momentumCurrentFrame--;
        } else {
            setCoorY(getCoorY() - 1);
            momentumCurrentFrame++;
        }
    }

    public void setNewRandomMoveFrame(){
        moveFrame = MiscUtils.getRandomInt(MIN_SPEED_Y, MAX_SPEED_Y + 1);
    }

    public void decrementCollisionTimeoutLeft(){
        if (collisionTimeoutLeft > 0){
            collisionTimeoutLeft--;
        }
    }



}
