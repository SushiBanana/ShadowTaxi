import bagel.Input;
import bagel.Window;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Game End Screen which extends Screen
 * @author Alysha Thean Student ID: 1495768
 */
public class GameEndScreen extends Screen{
    public static final int NO_OF_TOP_SCORE = 5;
    public static final int GAME_END_INCREMENT_COOR = 40;

    public final String SCORE_FILE;
    public final String TOP_5_SCORES_WORD;
    public final String WIN_INSTRUCTION_WORD;
    public final String LOSE_INSTRUCTION_WORD;
    public final int STATUS_FONT_SIZE;
    public final int SCORES_FONT_SIZE;
    public final int TOP_5_SCORES_COOR_Y;
    public final int STATUS_COOR_Y;

    /**
     * Constructor for Game End Screen class
     * @param gameProps properties file for values of various attributes
     * @param messageProps properties file for display text
     */
    public GameEndScreen(Properties gameProps, Properties messageProps){
        super(gameProps, messageProps, gameProps.getProperty("backgroundImage.gameEnd"));
        this.SCORE_FILE = gameProps.getProperty("gameEnd.scoresFile");
        this.TOP_5_SCORES_WORD = messageProps.getProperty("gameEnd.highestScores");
        this.WIN_INSTRUCTION_WORD = messageProps.getProperty("gameEnd.won");
        this.LOSE_INSTRUCTION_WORD = messageProps.getProperty("gameEnd.lose");
        this.STATUS_FONT_SIZE = Integer.parseInt(gameProps.getProperty("gameEnd.status.fontSize"));
        this.SCORES_FONT_SIZE = Integer.parseInt(gameProps.getProperty("gameEnd.scores.fontSize"));
        this.TOP_5_SCORES_COOR_Y = Integer.parseInt(gameProps.getProperty("gameEnd.scores.y"));
        this.STATUS_COOR_Y = Integer.parseInt(gameProps.getProperty("gameEnd.status.y"));
    }

    /**
     * Renders Game End Screen and reads from SCORE_FILE
     * @param input keyboard input
     */
    public void loadScreen(Input input){
        IMAGE.draw(Window.getWidth()/ 2.0, Window.getHeight() /2.0);
        loadFont(FONT_FILE, TOP_5_SCORES_WORD, SCORES_FONT_SIZE, TOP_5_SCORES_COOR_Y);
        displayTop(IOUtils.readCommaSeparatedFile(SCORE_FILE), NO_OF_TOP_SCORE);

    }

    /**
     * Displays the topNo of player's name and score
     * @param allPlayerScores array of String array of player's name and score
     * @param topNo integer of top number of players score
     */
    public void displayTop(String[][] allPlayerScores, int topNo) {

        sortPlayerScores(allPlayerScores);

        for (int i = 0; i < topNo && i < allPlayerScores.length; i++){
            String displayNameScore = allPlayerScores[i][0] + " - " + allPlayerScores[i][1];
            double displayNameCoorY = TOP_5_SCORES_COOR_Y + GAME_END_INCREMENT_COOR * (i + 1);
            loadFont(FONT_FILE, displayNameScore, SCORES_FONT_SIZE, displayNameCoorY);
        }

    }

    /**
     * Uses insertion sort to sort player's score in descending order
     * @param allPlayerScores array of String array of player's name and score
     */
    public void sortPlayerScores(String[][] allPlayerScores){
        for (int i = 1; i < allPlayerScores.length; i++){
            for (int j = i - 1; j >= 0 && Double.parseDouble(allPlayerScores[j][1]) <
                    Double.parseDouble(allPlayerScores[j+1][1]); j--){
                swapPlayerScore(allPlayerScores, j, j+1);
            }
        }
    }

    /**
     * Swaps the position of two players score and name in all player's scores array
     * @param allPlayersScores array of String array of player's name and score
     * @param p1 index of player 1
     * @param p2 index of player 2
     */
    public void swapPlayerScore(String[][] allPlayersScores, int p1, int p2){
        String[] temp = allPlayersScores[p1];
        allPlayersScores[p1] = allPlayersScores[p2];
        allPlayersScores[p2] = temp;

    }

    /**
     * Displays the bottom of Game End Screen status
     * @param hasWon true if won, false otherwise
     */
    public void displayGameStatus(boolean hasWon){
        if (hasWon) {
            String[] temp = WIN_INSTRUCTION_WORD.split("\n");

            for (int i = 0; i < temp.length; i++){
                loadFont(FONT_FILE, temp[i], STATUS_FONT_SIZE, STATUS_COOR_Y + (i * GAME_END_INCREMENT_COOR));
            }
        } else {
            String[] temp = LOSE_INSTRUCTION_WORD.split("\n");

            for (int i = 0; i < temp.length; i++){
                loadFont(FONT_FILE, temp[i], STATUS_FONT_SIZE, STATUS_COOR_Y - (i * GAME_END_INCREMENT_COOR));
            }

        }
    }

}
