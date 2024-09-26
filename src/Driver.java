import bagel.Image;

import java.util.Properties;

public class Driver extends GameEntity implements Damageable{

    public final static int EJECTION_COOR_X_MINUS = 50;
    public final static int MOMENTUM = 10;
    public final static int MOMENTUM_COOR_Y_MINUS = 2;
    public final static int COLLISION_TIMEOUT = 200;


    public final int MOVE_FRAME_Y;
    public final int WALK_SPEED_X;
    public final int WALK_SPEED_Y;
    public final int RADIUS;
    public final int TAXI_GET_IN_RADIUS;

    private double health;
    private int collisionTimeoutLeft;
    private boolean isEjected;
    private Blood blood;
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

    public void eject(int coorX, int coorY){
        setIsEjected(true);
        setCoorX(coorX);
        setCoorY(coorY);
    }

    /**
     * If health is equal or less than 0, blood is set active
     */
    public void checkHealth(){
        return;
    }

    /**
     * Checks if driver is in taxi or if driver has overshoot window's height, which is considered a game loss
     */
    public void checkInTaxiOrOvershoot(){
        return;
    }


    @Override
    public void takeDamage(DamageDealer damageDealer) {
        return;
    }

    public void momentumForward(){
        return;
    }

    public void momentumBackward(){
        return;
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
