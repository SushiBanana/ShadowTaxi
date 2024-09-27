import bagel.Image;

import java.util.Properties;

public class Fireball extends GameEntity implements DamageDealer{

    public static final int DIVISIBILITY = 300;

    public final int SHOOT_SPEED;
    public final double RADIUS;
    public final int DAMAGE_POINTS;

    private boolean isActive;

    public Fireball(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.fireball.image"));
        this.SHOOT_SPEED =Integer.parseInt(gameProps.getProperty("gameObjects.fireball.shootSpeedY"));
        this.RADIUS = Integer.parseInt(gameProps.getProperty("gameObjects.fireball.radius"));
        this.DAMAGE_POINTS = (int) (Double.parseDouble(gameProps.getProperty("gameObjects.fireball.damage")) * 100);

        this.isActive = false;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void moveDown(){
        return;
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
