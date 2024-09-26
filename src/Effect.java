import bagel.Image;

import java.util.Properties;

public abstract class Effect extends GameEntity{

    public final static int MOVE_FRAME = 5;

    private int currentFrame;
    private boolean isActive;

    public Effect(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);

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
        setCoorY(getCoorY() - MOVE_FRAME);
    }

    public void activate(int coorX, int coorY){
        isActive = true;
        setCoorX(coorX);
        setCoorY(coorY);

    }
}
