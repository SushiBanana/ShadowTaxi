import bagel.Image;

import java.util.Properties;

public class InvinciblePower extends GameEntity{

    public final static int MOVE_FRAME = 5;

    public final double RADIUS;
    public final int MAX_FRAMES;

    private boolean isCollided;
    private int currentFrame;

    public InvinciblePower(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = new Image(gameProps.getProperty("gameObjects.invinciblePower.image"));
        this.RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.invinciblePower.radius"));
        this.MAX_FRAMES = Integer.parseInt(gameProps.getProperty("gameObjects.invinciblePower.maxFrames"));

        this.isCollided = false;
        this.currentFrame = 0;
    }

    public boolean getIsCollided() {
        return isCollided;
    }

    public void setIsCollided(boolean collided) {
        isCollided = collided;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void moveDown(){
        setCoorY(getCoorY() + MOVE_FRAME);
    }

    public boolean hasCollided(Taxi taxi){
        return false;
    }

    public boolean hasCollided(Driver driver){
        return false;
    }

    public String toString(){
        return "INVINCIBLE POWER\n____________\nradius: " + RADIUS + "\nmax frames: " + MAX_FRAMES + "\ncoor x: " +
                getCoorX() + "\ncoor y:" + getCoorY();
    }
}
