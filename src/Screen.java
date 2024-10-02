import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Window;
import java.util.Properties;

/**
 * This Java abstract class contains attributes and methods related to abstract class Screen
 * @author Alysha Thean Student ID: 1495768
 */
public abstract class Screen {
    /**
     * The game properties of screen
     */
    public final Properties GAME_PROPS;
    /**
     * The message properties of screen
     */
    public final Properties MESSAGE_PROPS;
    /**
     * The image of screen
     */
    public final Image IMAGE;
    /**
     * The font file of screen
     */
    public final String FONT_FILE;

    private boolean isActive;

    /**
     * Constructor for Screen class
     * @param gameProps properties file for values of various attributes
     * @param messageProps properties file for display text
     * @param image String of Screen image
     */
    public Screen(Properties gameProps, Properties messageProps, String image){
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
        this.IMAGE = new Image(image);
        this.FONT_FILE = gameProps.getProperty("font");

        this.isActive = false;
    }

    /**
     * Gets whether the screen is active
     * @return true if active, false otherwise
     */
    public boolean getIsActive(){
        return isActive;
    }

    /**
     * Sets whether the screen is active
     * @param flag true if active, false otherwise
     */
    public void setIsActive(boolean flag){
        this.isActive = flag;
    }

    /**
     * Abstract method to be implemented by all Screen subclasses
     * @param input keyboard input
     */
    public abstract void loadScreen(Input input);

    /**
     * Load font to screen based on y-coordinates given of bottom left corner of content. The x-coordinate of content
     * is loaded to middle of screen
     * @param fontFile String of font file
     * @param textContent String of text content
     * @param fontSize integer of font size
     * @param yCoor integer of y-coordinate
     */
    public void loadFont(String fontFile, String textContent, int fontSize, double yCoor){
        Font font = new Font(fontFile, fontSize);
        double fontCoorX = (Window.getWidth() - font.getWidth(textContent)) / 2.0;
        font.drawString(textContent, fontCoorX, yCoor);
    }

    /**
     * Load font to screen based on x-coordinates and y-coordinates given of bottom left corner of content
     * @param fontFile String of font file
     * @param textContent String of text content
     * @param fontSize integer of font size
     * @param xCoor double of x-coordinates of bottom left corner of content
     * @param yCoor double of y-coordinates of bottom left corner of content
     */
    public void loadFont(String fontFile, String textContent, int fontSize, double xCoor, double yCoor){
        Font font = new Font(fontFile, fontSize);
        font.drawString(textContent, xCoor, yCoor);
    }


}
