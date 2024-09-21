import bagel.Image;

import java.util.Properties;

public class OtherCar extends Car{

    public final static int DIVISIBILITY = 200;


    public OtherCar(Properties gameProps, int coorX, int coorY){
        super(gameProps, coorX, coorY);
        this.IMAGE = generateImage();

    }

    public Image generateImage(){
        int randomNumber = MiscUtils.getRandomInt(1, NUM_TYPES);
        String fileName = String.format(GAME_PROPS.getProperty("gameObjects.otherCar.image"), randomNumber);
        Image image = new Image(fileName);
        return image;
    }

    @Override
    public void dealDamage(Damageable damageable) {
        return;
    }

    @Override
    public void takeDamage(DamageDealer damageDealer) {
        return;
    }

    public String toString(){
        return  "OtherCar\n" +"_________________" + "x coor: " + getCoorX() + "\ny coor: " + getCoorY() +
                "\nMIN_SPEED_Y: " +
                MIN_SPEED_Y + "\nMAX_SPEED_Y: " + MAX_SPEED_Y + "\nRADIUS: " + RADIUS + "\nDAMAGE_POINTS: " +
                DAMAGE_POINTS + "\nmove frame: " + MOVE_FRAME + "\nhealth: " + getHealth();
    }
}
