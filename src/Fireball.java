import bagel.Image;
import bagel.Window;

import java.util.Properties;

public class Fireball extends GameEntity implements DamageDealer{

    public static final int DIVISIBILITY = 300;

    public final int TAXI_MOVE_FRAME_Y;

    public final int SHOOT_SPEED;
    public final double RADIUS;
    public final int DAMAGE_POINTS;

    private boolean isActive;

    public Fireball(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));

        this.IMAGE = new Image(gameProps.getProperty("gameObjects.fireball.image"));
        this.SHOOT_SPEED =Integer.parseInt(gameProps.getProperty("gameObjects.fireball.shootSpeedY"));
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.fireball.radius"));
        this.DAMAGE_POINTS = (int) (Double.parseDouble(gameProps.getProperty("gameObjects.fireball.damage")) * 100);

        this.isActive = true;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void move(){
        if (getCoorY() <= 0){
            setIsActive(false);
        }
        setCoorY(getCoorY() - SHOOT_SPEED);
    }

    public void moveRelativeToTaxi(){
        if (getCoorY() <= 0){
            setIsActive(false);
        }
        setCoorY(getCoorY() - (SHOOT_SPEED - TAXI_MOVE_FRAME_Y));
    }

    @Override
    public String toString() {
        return "FIREBALL\n____________\n" + "coor x: " + getCoorX() + "coor y: "  + getCoorY();
    }


    @Override
    public double getDamagePoints() {
        return DAMAGE_POINTS;
    }

}
