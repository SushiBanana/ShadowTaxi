import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.util.ArrayList;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Game Play Screen
 * @author Alysha Thean Student ID: 1495768
 */
public class GamePlayScreen extends Screen{

    public static final int FRAME_SPEED = 5;
    public static final int PRIORITY_GAP = 30;
    public static final int EARNING_GAP = 100;
    public static final int FRAME_GREATER = 1152;


    public final Image BACKGROUND_RAIN; // new
    public final String OBJECT_FILE;
    public final String WEATHER_FILE; // new
    public final double TARGET_SCORE;
    public final int MAX_FRAMES;
    public final int INFO_FONT_SIZE;
    public final int PASSENGER_FONT_SIZE;
    public final int EARNINGS_COOR_X;
    public final int EARNINGS_COOR_Y;
    public final int TARGET_COOR_X;
    public final int TARGET_COOR_Y;
    public final int MAX_FRAMES_COOR_X;
    public final int MAX_FRAMES_COOR_Y;

    public final String PAY_WORD;
    public final String TARGET_WORD;
    public final String FRAMES_REM_WORD;

    private int bottomCoorY;
    private int topCoorY;
    private double score;
    private int frameLeft;
    private String name;

    private Taxi taxi;
    private Coin[] coins;
    private Passenger[] passengers;

    private Coin collidedCoin;
    private boolean isCoinActive;

    private Trip currentTrip;
    private Trip lastTrip;

    // below are new attributes


    public static final int NUM_LANES = 3;
    public static final int NUM_RANDOM_Y_COOR = 2;



    public final int LANE_CENTRE_1;
    public final int LANE_CENTRE_2;
    public final int LANE_CENTRE_3;
    public final int PASSENGER_HEALTH_COOR_X;
    public final int PASSENGER_HEALTH_COOR_Y;
    public final int DRIVER_HEALTH_COOR_X;
    public final int DRIVER_HEALTH_COOR_Y;
    public final int TAXI_HEALTH_COOR_X;
    public final int TAXI_HEALTH_COOR_Y;
    public final String[][] GAME_WEATHER;
    public final String TAXI_HEALTH_WORD;
    public final String DRIVER_HEALTH_WORD;
    public final String PASSENGER_HEALTH_WORD;
    public final int TAXI_MAX_SPAWN_MAX_Y;
    public final int TAXI_MAX_SPAWN_MIN_Y;


    private ArrayList<Car> cars;
    private int randomNumber;
    private InvinciblePower[] invinciblePowers;
    private boolean isRaining;
    private Taxi damagedTaxi;
    private Driver driver;


    /**
     * Constructor for Game Play Screen class, it also initialises objects from OBJECT_FILE
     * @param gameProps properties file for values of various attributes
     * @param messageProps properties file for display text
     */
    public GamePlayScreen(Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps, gameProps.getProperty("backgroundImage.sunny"));

        this.BACKGROUND_RAIN = new Image (gameProps.getProperty("backgroundImage.raining"));
        this.OBJECT_FILE = gameProps.getProperty("gamePlay.objectsFile");
        this.WEATHER_FILE = gameProps.getProperty("gamePlay.weatherFile");

        this.TARGET_SCORE = Double.parseDouble(gameProps.getProperty("gamePlay.target"));
        this.MAX_FRAMES = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames"));
        this.INFO_FONT_SIZE = Integer.parseInt(gameProps.getProperty("gamePlay.info.fontSize"));
        this.PASSENGER_FONT_SIZE = Integer.parseInt(gameProps.getProperty("gameObjects.passenger.fontSize"));
        this.EARNINGS_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.earnings.x"));
        this.EARNINGS_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.earnings.y"));
        this.TARGET_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.target.x"));
        this.TARGET_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.target.y"));
        this.MAX_FRAMES_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames.x"));
        this.MAX_FRAMES_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames.y"));

        this.PAY_WORD = messageProps.getProperty("gamePlay.earnings");
        this.TARGET_WORD = messageProps.getProperty("gamePlay.target");
        this.FRAMES_REM_WORD = messageProps.getProperty("gamePlay.remFrames");

        this.bottomCoorY = (Window.getHeight() / 2);
        this.topCoorY = -(Window.getHeight() / 2);
        this.score = 0.0;
        this.frameLeft = MAX_FRAMES;

        this.isCoinActive = false;

        initialiseClasses(IOUtils.readCommaSeparatedFile(OBJECT_FILE));
        //

        this.LANE_CENTRE_1 = Integer.parseInt(gameProps.getProperty("roadLaneCenter1"));
        this.LANE_CENTRE_2 = Integer.parseInt(gameProps.getProperty("roadLaneCenter2"));
        this.LANE_CENTRE_3 = Integer.parseInt(gameProps.getProperty("roadLaneCenter3"));
        this.PASSENGER_HEALTH_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.passengerHealth.x"));
        this.PASSENGER_HEALTH_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.passengerHealth.y"));
        this.DRIVER_HEALTH_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.driverHealth.x"));
        this.DRIVER_HEALTH_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.driverHealth.y"));
        this.TAXI_HEALTH_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.taxiHealth.x"));
        this.TAXI_HEALTH_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.taxiHealth.y"));
        this.TAXI_HEALTH_WORD = messageProps.getProperty("gamePlay.taxiHealth");
        this.DRIVER_HEALTH_WORD = messageProps.getProperty("gamePlay.driverHealth");
        this.PASSENGER_HEALTH_WORD = messageProps.getProperty("gamePlay.passengerHealth");
        this.TAXI_MAX_SPAWN_MAX_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.nextSpawnMaxY"));
        this.TAXI_MAX_SPAWN_MIN_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.nextSpawnMinY"));


        this.GAME_WEATHER = IOUtils.readCommaSeparatedFile(WEATHER_FILE);
        this.isRaining = false;
        this.driver = new Driver(gameProps, 0, 0);
        this.cars = new ArrayList<>();

    }

    /**
     * Getter method of game play screen's bottom screen y-coordinate
     * @return integer of game play screen's bottom screen y-coordinate
     */
    public int getBottomCoorY() {
        return bottomCoorY;
    }

    /**
     * Setter method of game play screen's bottom screen y-coordinate
     * @param bottomCoorY integer of game play screen's bottom screen y-coordinate
     */
    public void setBottomCoorY(int bottomCoorY) {
        this.bottomCoorY = bottomCoorY;
    }

    /**
     * Getter method of game play screen's top screen y-coordinate
     * @return integer of game play screen's top screen y-coordinate
     */
    public int getTopCoorY() {
        return topCoorY;
    }

    /**
     * Setter method of game play screen's top screen y-coordinate
     * @param topCoorY integer of game play screen's top screen y-coordinate
     */
    public void setTopCoorY(int topCoorY) {
        this.topCoorY = topCoorY;
    }

    /**
     * Getter method of game play screen's score
     * @return double of game play screen's score
     */
    public double getScore() {
        return score;
    }

    /**
     * Setter method of game play screen's score
     * @param score double of game play screen's score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Getter method of game play screen's frame left
     * @return integer  of game play screen's frame left
     */
    public int getFrameLeft() {
        return frameLeft;
    }

    /**
     * Setter method of game play screen's frame left
     * @param frameLeft integer of game play screen's frame left
     */
    public void setFrameLeft(int frameLeft) {
        this.frameLeft = frameLeft;
    }

    /**
     * Getter method of game play screen's name
     * @return String of game play screen's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method of game play screen's name
     * @param name String of game play screen's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method of whether any coin is active
     * @return true if there is a coin active, false otherwise
     */
    public boolean getIsCoinActive() {
        return isCoinActive;
    }


    /**
     * Setter method of whether any coin is active
     * @param coinActive true if there is a coin active, false otherwise
     */
    public void setIsCoinActive(boolean coinActive) {
        isCoinActive = coinActive;
    }

    /**
     * Renders the game play screen and updates attributes based on user's input
     * @param input keyboard input
     */
    public void loadScreen(Input input){

        setName(name);
        loadWeather();

        loadTaxis();
        loadTopLeft();
        loadTopRight();
        loadPassengers(passengers);
        loadTripEndFlag(passengers);
        pickUpPassenger(input, passengers);
        updatePassengerWithTaxi();
        dropOff(input);
        loadBottomLeft();

        loadCoins(coins);
        collideCoins(coins);
        handleCoinFrames();

        //
        loadInvinciblePowers(invinciblePowers);
        loadDriver();
        createCars();
        moveCars(cars, input);
        loadCars(cars);

        checkCollisions();
        decrementCollisionTimeout();

        handleAllMomentum();


        if (input.isDown(Keys.UP)) {

            moveScreenDown();
            movePassengersDown(passengers);
            moveCoinsDown(coins);
            incrementDistTravelled();
            moveInvinciblePowersDown(invinciblePowers);
            moveTaxiDown();

        }

        if (input.isDown(Keys.LEFT)) {
            taxi.moveLeft();
        }

        if (input.isDown(Keys.RIGHT)) {
            taxi.moveRight();
        }
    }

    /**
     * Initialises all classes related to game play screen
     * @param allGameEntities array of String array containing all game entities
     */
    public void initialiseClasses(String[][] allGameEntities){
        int totalNoCoins = findNoOfObjects(allGameEntities, "COIN");
        Coin[] tempCoins = new Coin[totalNoCoins];
        int currCoinIndex = 0;

        int totalNoPassengers = findNoOfObjects(allGameEntities, "PASSENGER");
        Passenger[] tempPassengers = new Passenger[totalNoPassengers];
        int currPassengerIndex = 0;

        int totalNoInvinciblePower = findNoOfObjects(allGameEntities, "INVINCIBLE_POWER");
        InvinciblePower[] tempInvinciblePowers = new InvinciblePower[totalNoInvinciblePower];
        int currInvinciblePowerIndex = 0;

        for (int i = 0; i < allGameEntities.length; i++) {

            switch (allGameEntities[i][0]) {
                case "TAXI":
                    taxi = new Taxi(GAME_PROPS, Integer.parseInt(allGameEntities[i][1]),
                            Integer.parseInt(allGameEntities[i][2]), false);
                    break;

                case "COIN":
                    Coin newCoin = new Coin(GAME_PROPS, Integer.parseInt(allGameEntities[i][1]),
                            Integer.parseInt(allGameEntities[i][2]));
                    tempCoins[currCoinIndex++] = newCoin;
                    break;

                case "PASSENGER":
                    boolean tempBool;
                    int tempInt = Integer.parseInt(allGameEntities[i][6]);

                    if (tempInt == 1){
                        tempBool = true;
                    } else {
                        tempBool = false;
                    }
                    Passenger newPassenger = new Passenger(GAME_PROPS, Integer.parseInt(allGameEntities[i][1]),
                            Integer.parseInt(allGameEntities[i][2]), Integer.parseInt(allGameEntities[i][3]),
                            Integer.parseInt(allGameEntities[i][4]), Integer.parseInt(allGameEntities[i][5]), tempBool);

                    tempPassengers[currPassengerIndex++] = newPassenger;
                    break;

                case "INVINCIBLE_POWER":
                    InvinciblePower newInvinciblePower = new InvinciblePower(GAME_PROPS,
                            Integer.parseInt(allGameEntities[i][1]), Integer.parseInt(allGameEntities[i][2]));
                    tempInvinciblePowers[currInvinciblePowerIndex++] = newInvinciblePower;
                    break;

                case "DRIVER":
                    driver = new Driver(GAME_PROPS, Integer.parseInt(allGameEntities[i][1]),
                            Integer.parseInt(allGameEntities[i][2]));
                    break;
            }
        }
        coins = tempCoins;
        passengers = tempPassengers;
        invinciblePowers = tempInvinciblePowers;
    }

    /**
     * Finds the number of objects from all game entities based on object's name
     * @param allGameEntities array of String array containing all game entities
     * @param objName String of objects name intended to query
     * @return integer of number of objects found
     */
    public int findNoOfObjects(String[][] allGameEntities, String objName) {
        int res = 0;

        for (int i = 0; i < allGameEntities.length; i++) {
            if (allGameEntities[i][0].equals(objName)){
                res++;
            }
        }
        return res;
    }

    /**
     * Loads the top left portion of game play screen which contains the total pay, target score, and frames remaining
     */
    public void loadTopLeft(){
        loadFont(FONT_FILE, PAY_WORD + score, INFO_FONT_SIZE, EARNINGS_COOR_X, EARNINGS_COOR_Y);

        loadFont(FONT_FILE, TARGET_WORD+ TARGET_SCORE, INFO_FONT_SIZE, TARGET_COOR_X, TARGET_COOR_Y);

        loadFont(FONT_FILE, FRAMES_REM_WORD + frameLeft, INFO_FONT_SIZE, MAX_FRAMES_COOR_X, MAX_FRAMES_COOR_Y);
    }

    /**
     * Loads all passengers if they are not picked up or are dropped off,it also moves passenger to trip end flag if
     * it's visible
     * @param passengers array of Passengers objects
     */
    public void loadPassengers(Passenger[] passengers){

        for (Passenger p: passengers){

            if(!p.getIsPickedUp() || p.isDroppedOff()){
                p.IMAGE.draw(p.getCoorX(), p.getCoorY());
                loadPassengerDetails(p);
            }

            movePassengerToFlag(p);
        }

    }

    /**
     * Loads the expected earnings and priority of passenger if they haven't been picked up
     * @param p Passenger object
     */
    public void loadPassengerDetails(Passenger p){
        if (p.getIsPickedUp()) {
            return;
        }

        String priority = Integer.toString(p.getPriority());
        String earnings = String.format("%.1f", p.getEarnings());

        loadFont(FONT_FILE, priority, PASSENGER_FONT_SIZE, p.getCoorX() - PRIORITY_GAP, p.getCoorY());
        loadFont(FONT_FILE, earnings, PASSENGER_FONT_SIZE, p.getCoorX() - EARNING_GAP, p.getCoorY());
    }

    /**
     * Picks up passenger if taxi is stationary, taxi and passenger are adjacent, taxi is empty, and they haven't
     * been picked up before. If all conditions are fulfilled, it increments passenger's coordinates until they are
     * the same with taxi's coordinates
     * @param input keyboard input
     * @param passengers array of Passenger objects
     */
    public void pickUpPassenger(Input input, Passenger[] passengers) {

        for (Passenger p: passengers){

            if (!input.isDown(Keys.UP) && !input.isDown(Keys.LEFT) && !input.isDown(Keys.RIGHT) &&
                    taxi.getCurrentPassenger() == null && p.isAdjacent(taxi.getCoorX(), taxi.getCoorY()) &&
                    !p.getIsPickedUp()){
                p.incrementCoorXY(taxi.getCoorX(), taxi.getCoorY());

                // A passenger is only considered picked up when they have the same coordinates as taxi
                if (p.sameCoor(taxi.getCoorX(), taxi.getCoorY())){
                    p.setIsPickedUp(true);
                    p.TRIP_END_FLAG.setIsVisible(true);
                    taxi.setCurrentPassenger(p);
                    currentTrip = new Trip(GAME_PROPS, MESSAGE_PROPS, taxi.getCurrentPassenger(), taxi);

                    /*
                    if taxi has collided with a coin before picking up a passenger and that coin still has
                    remaining frames, then the passenger's priority is decreased. Expected earnings are recalculated
                    to reflect this change
                     */
                    if (isCoinActive){
                        currentTrip.decreasePassengerPriority();
                        currentTrip.PASSENGER.calcExpEarnings(currentTrip.PASSENGER.getYDist());

                    }

                }
            }
        }
    }

    /**
     * Modifies y-coordinates of the background to create scrolling effect, speed is determined by constant FRAME_SPEED
     */
    public void moveScreenDown(){
        bottomCoorY += FRAME_SPEED;
        topCoorY += FRAME_SPEED;

        if (bottomCoorY >= FRAME_GREATER) {
            bottomCoorY = topCoorY - Window.getHeight();
        } else if (topCoorY >= FRAME_GREATER) {
            topCoorY = bottomCoorY - Window.getHeight();
        }

    }

    /**
     * Moves passenger down
     * @param passengers array of Passenger objects
     */
    public void movePassengersDown(Passenger[] passengers){
        for (Passenger p: passengers){
            p.moveDown();
        }
    }

    /**
     * Decrements the frames left of game play screen
     */
    public void decrementFrameLeft(){
        frameLeft--;
    }

    /**
     * Updates passenger's coordinates with taxi's coordinates only if taxi has a current passenger
     */
    public void updatePassengerWithTaxi(){
        if (taxi.getCurrentPassenger() == null){
            return;
        }
        taxi.getCurrentPassenger().setCoorX(taxi.getCoorX());
        taxi.getCurrentPassenger().setCoorY(taxi.getCoorY());
    }

    /**
     * Loads all passenger's trip end flag if visible
     * @param passengers array of Passenger objects
     */
    public void loadTripEndFlag(Passenger[] passengers){

        for (Passenger p: passengers) {
            if (p.TRIP_END_FLAG.getIsVisible()) {

                p.TRIP_END_FLAG.IMAGE.draw(p.TRIP_END_FLAG.getCoorX(), p.TRIP_END_FLAG.getCoorY());
            }
        }
    }

    /**
     * Loads all the coins if they have not been collided with taxi
     * @param coins array of Coin objects
     */
    public void loadCoins(Coin[] coins){
        for (Coin c: coins){

            if (!c.getIsCollided()) {
                c.IMAGE.draw(c.getCoorX(), c.getCoorY());
            }
        }
    }

    /**
     * Move coins down
     * @param coins array of Coin objects
     */
    public void moveCoinsDown(Coin[] coins){
        for (Coin c: coins){
            c.moveDown();
        }
    }

    /**
     * Collides coin with taxi
     * @param coins array of Coin objects
     */
    public void collideCoins(Coin[] coins){
        for (Coin c: coins){
            if (c.hasCollided(taxi)){
                collidedCoin = c;
                setIsCoinActive(true);
                /*
                Decreases current trip's passenger's priority only if there is a current trip, and updates passenger's
                earnings to reflect this change
                 */
                if (currentTrip != null){
                    currentTrip.decreasePassengerPriority();
                    currentTrip.PASSENGER.calcExpEarnings(currentTrip.PASSENGER.getYDist());
                }
            }
        }

    }

    /**
     * Handles coin's current frame if there is an active coin. It increments the current coin's frame until the
     * coin's MAX FRAME is reached
     */
    public void handleCoinFrames(){
        if (!isCoinActive) {
            return;
        }
        if (collidedCoin.getCurrentFrame() < collidedCoin.MAX_FRAMES) {
            collidedCoin.incrementFrames();
        } else {
            setIsCoinActive(false);
        }

    }

    /**
     * Loads the top right portion of game play screen which is the active coin's current frame
     */
    public void loadTopRight(){
        if (isCoinActive) {
            int coinCurrFrame = collidedCoin.getCurrentFrame();
            if (coinCurrFrame <= collidedCoin.MAX_FRAMES){
                loadFont(FONT_FILE, Integer.toString(coinCurrFrame), INFO_FONT_SIZE, collidedCoin.DISPLAY_FRAME_COOR_X,
                        collidedCoin.DISPLAY_FRAME_COOR_Y);
            }
        }

        String taxiHealth = String.format("%.1f", taxi.getHealth());
        loadFont(FONT_FILE, TAXI_HEALTH_WORD + taxiHealth, INFO_FONT_SIZE, TAXI_HEALTH_COOR_X,
                TAXI_HEALTH_COOR_Y);

        String driverHealth = String.format("%.1f", driver.getHealth());
        loadFont(FONT_FILE, DRIVER_HEALTH_WORD + driverHealth, INFO_FONT_SIZE, DRIVER_HEALTH_COOR_X,
                DRIVER_HEALTH_COOR_Y);

        String passengerHealth;
        if (currentTrip == null){
            passengerHealth = String.format("%.1f", findPassengerMinHealth(passengers));

        } else {
            passengerHealth = String.format("%.1f", currentTrip.PASSENGER.getHealth());
        }
        loadFont(FONT_FILE, PASSENGER_HEALTH_WORD + passengerHealth, INFO_FONT_SIZE, PASSENGER_HEALTH_COOR_X,
                PASSENGER_HEALTH_COOR_Y);
    }

    /**
     * Drops passenger off if taxi has a current passenger. It only drops the passenger if taxi is stationary,taxi is
     * beyond trip end flag's y-coordinate, and if taxi has stopped in the radius of trip end flag
     * @param input keyboard input
     */
    public void dropOff(Input input){
        if (taxi.getCurrentPassenger() == null){
            return;
        }

        Passenger passengerInTaxi = taxi.getCurrentPassenger();

        if (!input.isDown(Keys.UP) && !input.isDown(Keys.RIGHT) && !input.isDown(Keys.LEFT)) {

            if (taxi.getCoorY() < passengerInTaxi.TRIP_END_FLAG.getCoorY() ||
                    taxi.calcDist(passengerInTaxi.TRIP_END_FLAG.getCoorX(),
                            passengerInTaxi.TRIP_END_FLAG.getCoorY()) <= taxi.RADIUS){
                passengerInTaxi.setIsDroppedOff(true);
                calcEarnings(input);
                taxi.setCurrentPassenger(null);
                increaseScore(currentTrip.getEarnings());
                lastTrip = currentTrip;
                currentTrip = null;
            }
        }
    }

    /**
     * Moves passenger to trip end flag if its visible
     * @param p Passenger object to move
     */
    public void movePassengerToFlag(Passenger p){
        if (p.TRIP_END_FLAG.getIsVisible()){
            if (!p.sameCoor(p.TRIP_END_FLAG.getCoorX(), p.TRIP_END_FLAG.getCoorY())) {
                p.moveTowardsFlag();
            } else {
                p.TRIP_END_FLAG.setIsVisible(false);
            }

        }
    }

    /**
     * Loads the bottom left portion of screen, which is current or last trip's information
     */
    public void loadBottomLeft(){
        if (currentTrip == null && lastTrip == null) {
            return;
        }

        if (currentTrip != null) {
            String expFee = String.format("%.1f", currentTrip.PASSENGER.getEarnings());

            loadFont(FONT_FILE, currentTrip.CURRENT_TRIP_WORD, INFO_FONT_SIZE, currentTrip.TRIP_INFO_COOR_X,
                    currentTrip.TRIP_INFO_COOR_Y);
            loadFont(FONT_FILE, currentTrip.EXP_FEE_WORD + expFee, INFO_FONT_SIZE,
                    currentTrip.TRIP_INFO_COOR_X, currentTrip.TRIP_INFO_COOR_Y + Trip.INFO_GAP);
            loadFont(FONT_FILE, currentTrip.PRIORITY_WORD + currentTrip.PASSENGER.getPriority(),
                    INFO_FONT_SIZE, currentTrip.TRIP_INFO_COOR_X,
                    currentTrip.TRIP_INFO_COOR_Y + (Trip.INFO_GAP * 2));

        } else {
            String expFee = String.format("%.1f", lastTrip.PASSENGER.getEarnings());
            String penalty = String.format("%.2f", lastTrip.getPenalty());

            loadFont(FONT_FILE, lastTrip.LAST_TRIP_WORD, INFO_FONT_SIZE, lastTrip.TRIP_INFO_COOR_X,
                    lastTrip.TRIP_INFO_COOR_Y);
            loadFont(FONT_FILE, lastTrip.EXP_FEE_WORD + expFee, INFO_FONT_SIZE, lastTrip.TRIP_INFO_COOR_X,
                    lastTrip.TRIP_INFO_COOR_Y + Trip.INFO_GAP);
            loadFont(FONT_FILE, lastTrip.PRIORITY_WORD + lastTrip.PASSENGER.getPriority(), INFO_FONT_SIZE,
                    lastTrip.TRIP_INFO_COOR_X, lastTrip.TRIP_INFO_COOR_Y + (Trip.INFO_GAP * 2));
            loadFont(FONT_FILE, lastTrip.PENALTY_WORD + penalty, INFO_FONT_SIZE,
                    lastTrip.TRIP_INFO_COOR_X, lastTrip.TRIP_INFO_COOR_Y + (Trip.INFO_GAP * 3));
        }
    }

    /**
     * Calculates the earnings of current trip
     * @param input keyboard input
     */
    public void calcEarnings(Input input) {
        if (currentTrip == null){
            return;
        }
        findPenalty(input);
        currentTrip.setEarnings(currentTrip.calcTripEarnings());
    }

    /**
     * Finds if penalty applies to current trip. A penalty is only applied if taxi is stationary, taxi is beyond trip
     * end flag and if distance between taxi and trip end flag coordinates is greater than trip end flag's radius
     * @param input keyboard input
     */
    public void findPenalty(Input input){
        if (taxi.getCurrentPassenger() == null){
            return;
        }

        if (!input.isDown(Keys.DOWN) && !input.isDown(Keys.RIGHT) && !input.isDown(Keys.LEFT)) {
            if (taxi.getCoorY() < taxi.getCurrentPassenger().TRIP_END_FLAG.getCoorY() &&
                    taxi.calcTaxiFlagDist() > taxi.getCurrentPassenger().TRIP_END_FLAG.RADIUS) {
                double distTaxiFlagCoorY = taxi.calcTaxiFlagCoorY();
                currentTrip.setPenalty(currentTrip.TRIP_PENALTY_PER_Y * distTaxiFlagCoorY);


            }
        }
    }

    /**
     * Adds new score to game play screen's score
     * @param newScore double of new score
     */
    public void increaseScore(double newScore){
        score += newScore;
    }

    /**
     * Increments distance travelled of current trip
     */
    public void incrementDistTravelled(){
        if (currentTrip == null){
            return;
        }
        currentTrip.incrementDistTravelled();
    }

    /**
     * Loads the background of game's weather
     */
    public void loadWeather() {

        for (int i = 0; i < GAME_WEATHER.length; i++) {

            String weather = GAME_WEATHER[i][0];
            int startFrame = Integer.parseInt(GAME_WEATHER[i][1]);
            int endFrame = Integer.parseInt(GAME_WEATHER[i][2]);

            if (frameLeft >= startFrame && frameLeft <= endFrame){
                switch(weather) {
                    case ("SUNNY"):
                        IMAGE.draw(Window.getWidth()/2.0, bottomCoorY);
                        IMAGE.draw(Window.getWidth()/2.0, topCoorY);
                        isRaining = false;
                        break;

                    case("RAINING"):
                        BACKGROUND_RAIN.draw(Window.getWidth()/2.0, bottomCoorY);
                        BACKGROUND_RAIN.draw(Window.getWidth()/2.0, topCoorY);
                        isRaining = true;
                        break;
                }
                break;
            }


        }
    }

    /**
     * Creates OtherCars and EnemyCars based on whether they can spawn
     */
    public void createCars(){
        Car newCar;
        if(MiscUtils.canSpawn(OtherCar.DIVISIBILITY)){
            newCar = new OtherCar(GAME_PROPS, randomLaneNumber(), randomYCoor());
            cars.add(newCar);

        } else if (MiscUtils.canSpawn(EnemyCar.DIVISIBILITY)){
            newCar = new EnemyCar(GAME_PROPS, randomLaneNumber(), randomYCoor());
            cars.add(newCar);

        }

    }

    public int randomLaneNumber(){
        int sentinelLane = MiscUtils.getRandomInt(1, NUM_LANES + 1);
        int randNumLane;

        switch(sentinelLane){
            case 1:
                randNumLane = LANE_CENTRE_1;
                break;
            case 2:
                randNumLane = LANE_CENTRE_2;
                break;
            case 3:
                randNumLane = LANE_CENTRE_3;
                break;
            default:
                randNumLane = 0;
                break;
        }

        return randNumLane;

    }

    public int randomYCoor(){
        int sentinelYCoor = MiscUtils.getRandomInt(1, NUM_RANDOM_Y_COOR + 1);
        int randYCoor;

        switch(sentinelYCoor){
            case 1:
                randYCoor = Car.CAR_COOR_Y_RAND_1;
                break;
            case 2:
                randYCoor = Car.CAR_COOR_Y_RAND_2;
                break;
            default:
                randYCoor = 0;
                break;
        }
        return randYCoor;

    }


    /**
     * Checks collisions with other GameEntities
     */
    public void checkCollisions(){

        for (Car c: cars){
            checkCollision(taxi, c);
        }

        for (int i = 0; i < cars.size(); i++){
            for (int j = 0; j < cars.size(); j++) {
                if (i != j){
                    checkCollision(cars.get(i), cars.get(j));

                }
            }
        }
    }


    public void checkCollision(Taxi taxi, Car car){
        double sumOfRadius = taxi.RADIUS + car.RADIUS;

        if (taxi.calcDist(car.getCoorX(), car.getCoorY()) < sumOfRadius && car.getIsActive() &&
                taxi.getCollisionTimeoutLeft() == 0) {

            taxi.takeDamage(car);
            car.takeDamage(taxi);

            // taxi is above car
            if (taxi.getCoorY() < car.getCoorY()) {
                taxi.setMomentumCurrentFrame(-Taxi.MOMENTUM);
                car.setMomentumCurrentFrame(Car.MOMENTUM);
            } else {
                taxi.setMomentumCurrentFrame(Taxi.MOMENTUM);
                car.setMomentumCurrentFrame(-Car.MOMENTUM);
            }

        }
    }

    public void checkCollision(Car car1, Car car2) {
        double sumOfRadius = car1.RADIUS + car2.RADIUS;
        if (calcDist(car1, car2) < sumOfRadius && car1.getIsActive() && car2.getIsActive() && car1.getCollisionTimeoutLeft() == 0 && car2.getCollisionTimeoutLeft() == 0) {

            car1.takeDamage(car2);
            car2.takeDamage(car1);

            System.out.println(car1);
            System.out.println(car2);

            // car1 is above car2
            if (car1.getCoorY() < car2.getCoorY()) {
                car1.setMomentumCurrentFrame(-Car.MOMENTUM);
                car2.setMomentumCurrentFrame(Car.MOMENTUM);
            } else {
                car1.setMomentumCurrentFrame(Car.MOMENTUM);
                car2.setMomentumCurrentFrame(-Car.MOMENTUM);
            }

        }
    }

    public double calcDist(GameEntity gameEntity1, GameEntity gameEntity2) {
        return Math.sqrt(Math.pow(gameEntity1.getCoorX() - gameEntity2.getCoorX(), 2) + Math.pow(gameEntity1.getCoorY() - gameEntity2.getCoorY(), 2));
    }

    public void handleAllMomentum(){
        taxi.handleMomentum();
        for (Car c: cars){
            c.handleMomentum();
        }
    }

    public void decrementCollisionTimeout(){
        taxi.decrementCollisionTimeoutLeft();
        for (Car c: cars){
            c.decrementCollisionTimeoutLeft();
        }
    }


    /**
     * Loads Invincible Powers to GamePlayScreen
     * @param invinciblePowers array of Invincible Power
     */
    public void loadInvinciblePowers(InvinciblePower[] invinciblePowers){
        for (InvinciblePower i: invinciblePowers){

            if(!i.getIsCollided()){
                i.IMAGE.draw(i.getCoorX(), i.getCoorY());

            }

        }
    }

    /**
     * Moves Invincible Powers down
     * @param invinciblePowers array of Invincible Power
     */
    public void moveInvinciblePowersDown(InvinciblePower[] invinciblePowers){
        for (InvinciblePower i: invinciblePowers){
            i.moveDown();
        }
    }

    /**
     * Handles the collisions of different GameEntity with Invincible Powers
     */
    public void collideInvinciblePowers(){
        return;
    }

    /**
     * Assigns a new taxi if current taxi is permanently damaged
     */
    public void assignNewTaxi(){
        if (taxi.getIsDamaged()){
            damagedTaxi = new Taxi(GAME_PROPS, taxi.getCoorX(), taxi.getCoorY(), true);

            int newSpawnCoorY = MiscUtils.getRandomInt(TAXI_MAX_SPAWN_MIN_Y, TAXI_MAX_SPAWN_MAX_Y + 1);
            taxi = new Taxi(GAME_PROPS, randomLaneNumber(), newSpawnCoorY, false);
        }
    }

    public void loadTaxis(){
        if (taxi != null){
            taxi.IMAGE.draw(taxi.getCoorX(), taxi.getCoorY());
            if (taxi.FIRE.getIsActive()){
                taxi.FIRE.IMAGE.draw(taxi.FIRE.getCoorX(), taxi.FIRE.getCoorY());
                taxi.FIRE.incrementCurrentFrame();

            }
            if (taxi.SMOKE.getIsActive()){
                taxi.SMOKE.IMAGE.draw(taxi.SMOKE.getCoorX(), taxi.SMOKE.getCoorY());
                taxi.SMOKE.incrementCurrentFrame();
            }
        }

        if (damagedTaxi != null){
            damagedTaxi.IMAGE.draw(damagedTaxi.getCoorX(), damagedTaxi.getCoorY());
        }

        assignNewTaxi();
    }

    public double findPassengerMinHealth(Passenger[] passengers){
        double minHealth = Double.MAX_VALUE;
        for (Passenger p: passengers){
            if (p.getHealth() < minHealth){
                minHealth = p.getHealth();
            }
        }
        return minHealth;
    }

    public void loadDriver(){
        if (driver.getIsEjected()){
            driver.IMAGE.draw(driver.getCoorX(), driver.getCoorY());
        }
    }

    public void loadCars(ArrayList<Car> cars){
        for (Car c: cars){
            if (c.getIsActive()) {
                c.IMAGE.draw(c.getCoorX(), c.getCoorY());
            }
            if (c.FIRE.getIsActive()){
                c.FIRE.IMAGE.draw(c.FIRE.getCoorX(), c.FIRE.getCoorY());
                c.FIRE.incrementCurrentFrame();
                c.deactivateAfterFire();
            }
            if (c.SMOKE.getIsActive()){
                c.SMOKE.IMAGE.draw(c.SMOKE.getCoorX(), c.SMOKE.getCoorY());
                c.SMOKE.incrementCurrentFrame();
            }
        }
    }


    public void moveCars(ArrayList<Car> cars, Input input){
        if (input.isDown(Keys.UP)){
            for (Car c: cars){
                c.moveRelativeToTaxi();
            }
        } else {
            for (Car c: cars) {
                c.move();
            }
        }

    }



    public boolean noKeysPressed(Input input){
        boolean flag;
        if (!input.isDown(Keys.UP) && !input.isDown(Keys.RIGHT) && !input.isDown(Keys.LEFT)){
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public void moveTaxiDown(){
        if (damagedTaxi != null){
            damagedTaxi.moveDown();

        }
        if (!taxi.getHasDriver()) {
            taxi.moveDown();
        }
    }

    // once taxi is damaged, driver gets ejected, and movement input is given to driver




}
