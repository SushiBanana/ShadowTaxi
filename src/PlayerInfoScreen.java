import bagel.*;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Player Information Screen
 * @author Alysha Thean Student ID: 1495768
 */
public class PlayerInfoScreen extends Screen{
    public final static int PLAYER_INFO_SPACING = 30;
    public final static double BLACK_CODE = 0.0;

    public final String ENTER_NAME_WORD;
    public final String START_AND_INSTRUCTION_WORD;
    public final int FONT_SIZE;
    public final int ENTER_NAME_COOR_Y;
    public final int INSTRUCTIONS_COOR_Y;
    public final int PLAYER_NAME_COOR_Y;

    private String name;

    /**
     * Constructor for Player Information Screen
     * @param gameProps properties file for values of various attributes
     * @param messageProps properties file for display text
     */
    public PlayerInfoScreen(Properties gameProps, Properties messageProps){
        super(gameProps, messageProps, gameProps.getProperty("backgroundImage.playerInfo"));
        this.ENTER_NAME_WORD = messageProps.getProperty("home.instruction");
        this.START_AND_INSTRUCTION_WORD = messageProps.getProperty("playerInfo.start");
        this.FONT_SIZE = Integer.parseInt(gameProps.getProperty("playerInfo.fontSize"));
        this.ENTER_NAME_COOR_Y = Integer.parseInt(gameProps.getProperty("playerInfo.playerName.y"));
        this.INSTRUCTIONS_COOR_Y = Integer.parseInt(gameProps.getProperty("playerInfo.start.y"));
        this.PLAYER_NAME_COOR_Y = Integer.parseInt(gameProps.getProperty("playerInfo.playerNameInput.y"));

        this.setIsActive(false);

        this.name = "";

    }

    /**
     * Renders Player Information Screen and gets input from player, while handling removal of name
     * @param input keyboard input
     */
    public void loadScreen(Input input){

        IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        loadFont(FONT_FILE, ENTER_NAME_WORD, FONT_SIZE, ENTER_NAME_COOR_Y);

        String[] temp = START_AND_INSTRUCTION_WORD.split("\n");
        for (int i = 0; i < temp.length; i++){
            loadFont(FONT_FILE, temp[i], FONT_SIZE, INSTRUCTIONS_COOR_Y - (i * PLAYER_INFO_SPACING));
        }

        name += getPlayerInput(input);
        displayName(name);
        handleNameRemoval(input);

    }

    /**
     * Get player's input
     * @param input keyboard input
     * @return String of character entered by user
     */
    public String getPlayerInput(Input input){

        String res = "";
        String tempInput = MiscUtils.getKeyPress(input);

        if (tempInput != null){
            res = tempInput;
        }

        return res;

    }

    /**
     * Getter method for name
     * @return String of player's name
     */
    public String getName(){
        return name;
    }

    /**
     * Setter method for name
     * @param name String of player's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Removes last character in name when "BACKSPACE" or "DELETE" is pressed
     * @param input keyboard input
     */
    public void handleNameRemoval(Input input){
        if (!name.isEmpty() && input.wasPressed(Keys.BACKSPACE) || input.wasPressed(Keys.DELETE)) {
            name = name.substring(0, name.length()-1);
        }
    }

    /**
     * Display player's name in black inside of White Box
     * @param name String of player's name
     */
    public void displayName(String name){

        if (name.isEmpty()){
            return;
        }

        Font playerNameInput = new Font(FONT_FILE, FONT_SIZE);
        double playerNameCoorX = (Window.getWidth() - playerNameInput.getWidth(name)) / 2.0;

        loadFont(FONT_FILE, name, FONT_SIZE, PLAYER_NAME_COOR_Y);

        DrawOptions drawOptions = new DrawOptions();
        drawOptions.setBlendColour(BLACK_CODE, BLACK_CODE, BLACK_CODE);

        playerNameInput.drawString(name, playerNameCoorX, PLAYER_NAME_COOR_Y, drawOptions);

    }

}
