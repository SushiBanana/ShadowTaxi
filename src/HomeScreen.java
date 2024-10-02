import bagel.Input;
import bagel.Window;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Home Screen which extends Screen
 * @author Alysha Thean Student ID: 1495768
 */
public class HomeScreen extends Screen {
    /**
     * The title word of home screen
     */
    public final String HOME_TITLE_WORD;
    /**
     * The instruction word of home screen
     */
    public final String HOME_INSTRUCTION_WORD;
    /**
     * The title's font size of home screen
     */
    public final int HOME_TITLE_FONT_SIZE;
    /**
     * The y-coordinate of title in home screen
     */
    public final double TITLE_COOR_Y;
    /**
     * The instruction's font size of home screen
     */
    public final int HOME_INSTRUCTION_FONT_SIZE;
    /**
     * The y-coordinate of instruction in home screen
     */
    public final int INSTRUCTION_COOR_Y;

    /**
     * Constructor for Home Screen
     * @param gameProps properties file for values of various attributes
     * @param messageProps properties file for display text
     */
    public HomeScreen(Properties gameProps, Properties messageProps){
        super(gameProps, messageProps, gameProps.getProperty("backgroundImage.home"));
        this.HOME_TITLE_WORD = messageProps.getProperty("home.title");
        this.HOME_INSTRUCTION_WORD = messageProps.getProperty("home.instruction");
        this.HOME_TITLE_FONT_SIZE = Integer.parseInt(GAME_PROPS.getProperty("home.title.fontSize"));
        this.TITLE_COOR_Y = Double.parseDouble(GAME_PROPS.getProperty("home.title.y"));
        this.HOME_INSTRUCTION_FONT_SIZE = Integer.parseInt(GAME_PROPS.getProperty("home.instruction.fontSize"));
        this.INSTRUCTION_COOR_Y = Integer.parseInt(GAME_PROPS.getProperty("home.instruction.y"));
        setIsActive(true);

    }

    /**
     * Renders Home Screen
     * @param input keyboard input
     */
    @Override
    public void loadScreen(Input input) {
        IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        loadFont(FONT_FILE, HOME_TITLE_WORD, HOME_TITLE_FONT_SIZE, TITLE_COOR_Y);
        loadFont(FONT_FILE, HOME_INSTRUCTION_WORD, HOME_INSTRUCTION_FONT_SIZE, INSTRUCTION_COOR_Y);

    }
}
