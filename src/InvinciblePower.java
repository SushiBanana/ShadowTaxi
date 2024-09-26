import bagel.Image;

import java.util.Properties;

public class InvinciblePower extends GameEntity{

    public final static int MOVE_FRAME = 5;

    public final double RADIUS;
    public final int MAX_FRAMES;

    private boolean isCollided;
    private int frameLeft;

    public InvinciblePower(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.invinciblePower.image"));
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.invinciblePower.radius"));
        this.MAX_FRAMES = Integer.parseInt(gameProps.getProperty("gameObjects.invinciblePower.maxFrames"));

        this.isCollided = false;
        this.frameLeft = 0;
    }

    public boolean getIsCollided() {
        return isCollided;
    }

    public void setIsCollided(boolean collided) {
        isCollided = collided;
    }

    public int getFrameLeft() {
        return frameLeft;
    }

    public void setFrameLeft(int frameLeft) {
        this.frameLeft = frameLeft;
    }

    public void moveDown(){
        setCoorY(getCoorY() + MOVE_FRAME);
    }

    public boolean hasCollided(Taxi taxi){
        double sumOfRadius = RADIUS + taxi.RADIUS;
        if (GamePlayScreen.calcDist(this, taxi) < sumOfRadius) {
            frameLeft = MAX_FRAMES;
            setIsCollided(true);
            return true;
        }
        return false;
    }

    public boolean hasCollided(Driver driver){
        double sumOfRadius = RADIUS + driver.RADIUS;
        if (GamePlayScreen.calcDist(this, driver) < sumOfRadius) {
            frameLeft = MAX_FRAMES;
            setIsCollided(true);
            return true;
        }
        return false;
    }

    public void decrementFrameLeft(){
        if (frameLeft > 0){
            frameLeft--;
        }
    }

    public String toString(){
        return "INVINCIBLE POWER\n____________\nradius: " + RADIUS + "\nmax frames: " + MAX_FRAMES + "\ncoor x: " +
                getCoorX() + "\ncoor y:" + getCoorY();
    }
}
