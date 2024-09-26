import bagel.Image;

import java.util.Properties;

public class Driver extends GameEntity implements Damageable{

    public final static int EJECTION_COOR_X_MINUS = 50;
    public final static int MOMENTUM = 10;
    public final static int MOMENTUM_COOR_Y_MINUS = 2;
    public final static int COLLISION_TIMEOUT = 200;
    public final static int COOR_Y_MOVEMENT_STEP = 2;


    public final int MOVE_FRAME_Y;
    public final int WALK_SPEED_X;
    public final int WALK_SPEED_Y;
    public final int RADIUS;
    public final int TAXI_GET_IN_RADIUS;

    private double health;
    private int collisionTimeoutLeft;
    private boolean isEjected;
    public Blood BLOOD;
    private int momentumCurrentFrame;
    private InvinciblePower invinciblePower;

    public Driver(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);

        this.IMAGE = new Image(gameProps.getProperty("gameObjects.driver.image"));
        this.WALK_SPEED_X = Integer.parseInt(gameProps.getProperty("gameObjects.driver.walkSpeedX"));
        this.WALK_SPEED_Y = Integer.parseInt(gameProps.getProperty("gameObjects.driver.walkSpeedY"));
        this.RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.driver.radius"));
        this.TAXI_GET_IN_RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.driver.taxiGetInRadius"));
        this.MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));


        this.health = Double.parseDouble(gameProps.getProperty("gameObjects.driver.health")) * 100;
        this.isEjected = false;
        this.invinciblePower = new InvinciblePower(gameProps, coorX, coorY);
        this.BLOOD = new Blood(gameProps, coorX, coorY);

    }

    /**
     * Getter method for driver's health
     * @return double of driver's health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Setter method for driver's health
     * @param health double of driver's health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Getter method for whether driver is ejected
     * @return true if driver has been ejected, false otherwise
     */
    public boolean getIsEjected() {
        return isEjected;
    }

    /**
     * Setter method for whether driver is ejected
     * @param ejected true if driver has been ejected, false otherwise
     */
    public void setIsEjected(boolean ejected) {
        isEjected = ejected;
    }

    public InvinciblePower getInvinciblePower() {
        return invinciblePower;
    }

    public void setInvinciblePower(InvinciblePower invinciblePower) {
        this.invinciblePower = invinciblePower;
    }

    public int getCollisionTimeoutLeft() {
        return collisionTimeoutLeft;
    }

    public void setCollisionTimeoutLeft(int collisionTimeoutLeft) {
        this.collisionTimeoutLeft = collisionTimeoutLeft;
    }

    public int getMomentumCurrentFrame() {
        return momentumCurrentFrame;
    }

    public void setMomentumCurrentFrame(int momentumCurrentFrame) {
        this.momentumCurrentFrame = momentumCurrentFrame;
    }

    public void eject(int coorX, int coorY){
        setIsEjected(true);
        setCoorX(coorX);
        setCoorY(coorY);
    }

    public void handleMomentum(){
        if (momentumCurrentFrame == 0){
            return;
        }
        if (momentumCurrentFrame > 0){
            setCoorY(getCoorY() + 1);
            momentumCurrentFrame = momentumCurrentFrame - COOR_Y_MOVEMENT_STEP;
        } else {
            setCoorY(getCoorY() - 1);
            momentumCurrentFrame = momentumCurrentFrame + COOR_Y_MOVEMENT_STEP;
        }
    }

    /**
     * If health is equal or less than 0, blood is set active
     */
    public void checkHealth(){
        if (getHealth() <= 0){
            activateBlood();
        }
    }


    public void activateBlood(){
        BLOOD.setIsActive(true);
        BLOOD.setCoorX(getCoorX());
        BLOOD.setCoorY(getCoorY());
    }


    @Override
    public void takeDamage(DamageDealer damageDealer) {
        if (getHealth() > 0 && getCollisionTimeoutLeft() == 0){

            setHealth(getHealth() - damageDealer.getDamagePoints());
            setCollisionTimeoutLeft(COLLISION_TIMEOUT);
            checkHealth();

        }
    }



    public void decrementCollisionTimeoutLeft(){
        if (collisionTimeoutLeft > 0){
            collisionTimeoutLeft--;
        }
    }

    public void moveLeft() {
        setCoorX(getCoorX() - WALK_SPEED_X);
    }

    public void moveRight() {
        setCoorX(getCoorX() + WALK_SPEED_X);
    }

    public void moveUp() {
        setCoorY(getCoorY() - WALK_SPEED_Y);
    }

    public void moveDown() {
        setCoorY(getCoorY() + WALK_SPEED_Y);
    }

    public void moveWithTaxi() {
        setCoorY(getCoorY() - MOVE_FRAME_Y);
    }




    public String toString(){
        return "DRIVER\n________\nis ejected:" + isEjected + "\ncoor x: " + getCoorX() + "\ncoor y: " + getCoorY();
    }
}
