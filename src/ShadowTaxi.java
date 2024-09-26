import bagel.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Properties;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2024
 * Please enter your name below
 * @author Alysha Thean Student ID: 1495768
 */
public class ShadowTaxi extends AbstractGame {
    public final Properties GAME_PROPS;
    public final Properties MESSAGE_PROPS;
    public final String SCORE_FILE;

    private HomeScreen homeScreen;
    private PlayerInfoScreen playerInfoScreen;
    private GamePlayScreen gamePlayScreen;
    private GameEndScreen gameEndScreen;
    private boolean hasWrittenToFile;


    /**
     * Constructor for ShadowTaxi
     * @param gameProps properties file for values of various attributes
     * @param messageProps properties file for display text
     */
    public ShadowTaxi(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                messageProps.getProperty("home.title"));


        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
        SCORE_FILE = gameProps.getProperty("gameEnd.scoresFile");

        this.homeScreen = new HomeScreen(gameProps, messageProps);
        this.playerInfoScreen = new PlayerInfoScreen(gameProps, messageProps);
        this.gamePlayScreen = new GamePlayScreen(gameProps, messageProps);
        this.gameEndScreen = new GameEndScreen(gameProps, messageProps);
        this.hasWrittenToFile = false;


    }

    /**
     * Render the relevant screens and game objects based on the keyboard input given by the user and the status of
     * the game play.
     * @param input The current mouse/keyboard input.
     */
    @Override
    protected void update(Input input) {

        gamePlayScreen.decrementFrameLeft();


        if (homeScreen.getIsActive()) {
            loadHomeScreen(input);
        }

        if (playerInfoScreen.getIsActive()) {
            loadPlayerInfoScreen(input);
        }

        if (gamePlayScreen.getIsActive()) {
            loadGamePlayScreen(input);

        }

        if (gameEndScreen.getIsActive()) {
            loadGameEndScreen(input);
        }

        if(checkIfWon() || isMaxFrameReached() || checkTaxiOutOfFrame()){
            loadGameEndScreen(input);
        }

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }


    }

    /**
     * Main method for executing ShadowTaxi class
     * @param args input arguments from terminal
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
        ShadowTaxi game = new ShadowTaxi(game_props, message_props);
        game.run();

    }

    /**
     * Renders the home screen's display, if "ENTER" is pressed, Player Information Screen is set active
     */
    public void loadHomeScreen(Input input){

        homeScreen.loadScreen(input);

        if (input.wasPressed(Keys.ENTER)) {
            homeScreen.setIsActive(false);
            playerInfoScreen.setIsActive(true);
        }
    }

    /**
     * Renders Player Information Screen, if "ENTER" is pressed, Game Play Screen is set active
     * @param input keyboard input
     */
    public void loadPlayerInfoScreen(Input input){
        playerInfoScreen.loadScreen(input);

        if (input.wasPressed(Keys.ENTER) && !playerInfoScreen.getName().isEmpty()) {

            playerInfoScreen.setIsActive(false);
            gamePlayScreen.setIsActive(true);
        }
    }


    /**
     * Renders Game Play Screen
     * @param input keyboard input
     */
    public void loadGamePlayScreen(Input input){

        gamePlayScreen.loadScreen(input);
    }


    /**
     * Render Game End Screen and writes score to given file, resets game if "SPACE" is pressed
     * @param input keyboard input
     */
    public void loadGameEndScreen(Input input){
        writeToScoreFile();
        gameEndScreen.loadScreen(input);
        gameEndScreen.displayGameStatus(checkIfWon());

        if (input.wasPressed(Keys.SPACE)){
            resetGame();
        }

    }

    /**
     * Checks if game has reached maximum frame count
     * @return true if maximum frame count is reached, false otherwise
     */
    public boolean isMaxFrameReached(){
        boolean flag = false;
        if (gamePlayScreen.getFrameLeft() <= 0){
            flag = true;
            gamePlayScreen.setIsActive(false);
            gameEndScreen.setIsActive(true);

        }
        return flag;
    }

    /**
     * Checks if score has reached target score
     * @return true if reached, false otherwise
     */
    public boolean checkIfWon(){
        boolean flag = false;
        if (gamePlayScreen.getScore() >= gamePlayScreen.TARGET_SCORE){
            flag = true;
        }
        return flag;
    }

    /**
     * Resets game by assigning new objects to each respective screen
     */
    public void resetGame(){
        this.homeScreen = new HomeScreen(GAME_PROPS, MESSAGE_PROPS);
        this.playerInfoScreen = new PlayerInfoScreen(GAME_PROPS, MESSAGE_PROPS);
        this.gamePlayScreen = new GamePlayScreen(GAME_PROPS, MESSAGE_PROPS);
        this.gameEndScreen = new GameEndScreen(GAME_PROPS, MESSAGE_PROPS);
        hasWrittenToFile = false;

    }

    /**
     * Write name and score as comma-separated-values to given score file
     */
    public void writeToScoreFile(){
        if (hasWrittenToFile){
            return;
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            bw.write(playerInfoScreen.getName() + "," + gamePlayScreen.getScore());
            bw.write(System.lineSeparator());
            hasWrittenToFile = true;

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkTaxiOutOfFrame() {
        boolean flag;
        if (gamePlayScreen.getTaxi().isOutOfFrame() && gamePlayScreen.getDriver().getIsEjected()){
            gamePlayScreen.setIsActive(false);
            gameEndScreen.setIsActive(true);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

}
