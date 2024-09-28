import bagel.Image;

import java.util.Properties;

public abstract class Effect extends GameEntity{

    public final int TAXI_MOVE_FRAME_Y;

    private int currentFrame;
    private boolean isActive;

    public Effect(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.TAXI_MOVE_FRAME_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));


        this.currentFrame = 0;
        this.isActive = false;

    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void moveDown(){
        setCoorY(getCoorY() - TAXI_MOVE_FRAME_Y);
    }

    public void activate(int coorX, int coorY){
        currentFrame = 0;
        isActive = true;
        setCoorX(coorX);
        setCoorY(coorY);
    }
}
