import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This Java class contains attributes and methods related to GamePlayScreen
 * @author Alysha Thean Student ID: 1495768
 */
public class GamePlayScreen extends Screen{
    /**
     * The frame speed of game play screen
     */
    public static final int FRAME_SPEED = 5;
    /**
     * The x-coordinate incrementation for display of passenger's priority gap in game play screen
     */
    public static final int PRIORITY_GAP = 30;
    /**
     * The x-coordinate incrementation for display of passenger's earnings gap in game play screen
     */
    public static final int EARNINGS_GAP = 100;
    /**
     * The maximum y-coordinate of game play screen's background
     */
    public static final int FRAME_GREATER = 1152;
    /**
     * The number of lanes in game play screen
     */
    public static final int NUM_LANES = 3;
    /**
     * The number of random y-coordinates for creating cars
     */
    public static final int NUM_RANDOM_Y_COOR = 2;
    /**
     * The image of raining background in game play screen
     */
    public final Image BACKGROUND_RAIN;
    /**
     * The object file of game play screen
     */
    public final String OBJECT_FILE;
    /**
     * The weather file of game play screen
     */
    public final String WEATHER_FILE;
    /**
     * The target score of game play screen
     */
    public final double TARGET_SCORE;
    /**
     * The maximum number of frame in game play screen
     */
    public final int MAX_FRAMES;
    /**
     * The display information font size of game play screen
     */
    public final int INFO_FONT_SIZE;
    /**
     * The passenger display details font size
     */
    public final int PASSENGER_FONT_SIZE;
    /**
     * The passenger earnings' x-coordinate display
     */
    public final int EARNINGS_COOR_X;
    /**
     * The passenger earnings' y-coordinate display
     */
    public final int EARNINGS_COOR_Y;
    /**
     * The target score's x-coordinate display
     */
    public final int TARGET_COOR_X;
    /**
     * The target score's y-coordinate display
     */
    public final int TARGET_COOR_Y;
    /**
     * The maximum frames x-coordinate display
     */
    public final int MAX_FRAMES_COOR_X;
    /**
     * The maximum frames y-coordinate display
     */
    public final int MAX_FRAMES_COOR_Y;
    /**
     * The passenger's health x-coordinate display
     */
    public final int PASSENGER_HEALTH_COOR_X;
    /**
     * The passenger's health y-coordinate display
     */
    public final int PASSENGER_HEALTH_COOR_Y;
    /**
     * The driver's health x-coordinate display
     */
    public final int DRIVER_HEALTH_COOR_X;
    /**
     * The driver's health y-coordinate display
     */
    public final int DRIVER_HEALTH_COOR_Y;
    /**
     * The taxi's health x-coordinate display
     */
    public final int TAXI_HEALTH_COOR_X;
    /**
     * The taxi's health y-coordinate display
     */
    public final int TAXI_HEALTH_COOR_Y;
    /**
     * The x-coordinates of first lane centre
     */
    public final int LANE_CENTRE_1;
    /**
     * The x-coordinates of second lane centre
     */
    public final int LANE_CENTRE_2;
    /**
     * The x-coordinates of third lane centre
     */
    public final int LANE_CENTRE_3;
    /**
     * The game weather of game play screen
     */
    public final String[][] GAME_WEATHER;
    /**
     * The taxi's maximum y-coordinate when spawning
     */
    public final int TAXI_MAX_SPAWN_MAX_Y;
    /**
     * The taxi's minimum y-coordinate when spawning
     */
    public final int TAXI_MAX_SPAWN_MIN_Y;
    /**
     * The pay word of game play screen
     */
    public final String PAY_WORD;
    /**
     * The target word of game play screen
     */
    public final String TARGET_WORD;
    /**
     * The frames remaining word of game play screen
     */
    public final String FRAMES_REM_WORD;
    /**
     * The taxi health word of game play screen
     */
    public final String TAXI_HEALTH_WORD;
    /**
     * The driver health word of game play screen
     */
    public final String DRIVER_HEALTH_WORD;
    /**
     * The passenger health word of game play screen
     */
    public final String PASSENGER_HEALTH_WORD;

    private int bottomCoorY;
    private int topCoorY;
    private double score;
    private int frameLeft;
    private String name;
    private Taxi taxi;
    private Taxi damagedTaxi;
    private Passenger[] passengers;
    private Passenger ejectedPassenger;
    private ArrayList<Car> cars;
    private Driver driver;
    private Coin[] coins;
    private InvinciblePower[] invinciblePowers;
    private Coin collidedCoin;
    private boolean isCoinActive;
    private Trip currentTrip;
    private Trip lastTrip;
    private boolean isRaining;
    private boolean isEjectedPassengerInTaxi;

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
        this.LANE_CENTRE_1 = Integer.parseInt(gameProps.getProperty("roadLaneCenter1"));
        this.LANE_CENTRE_2 = Integer.parseInt(gameProps.getProperty("roadLaneCenter2"));
        this.LANE_CENTRE_3 = Integer.parseInt(gameProps.getProperty("roadLaneCenter3"));
        this.PASSENGER_HEALTH_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.passengerHealth.x"));
        this.PASSENGER_HEALTH_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.passengerHealth.y"));
        this.DRIVER_HEALTH_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.driverHealth.x"));
        this.DRIVER_HEALTH_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.driverHealth.y"));
        this.TAXI_HEALTH_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.taxiHealth.x"));
        this.TAXI_HEALTH_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.taxiHealth.y"));
        this.GAME_WEATHER = IOUtils.readCommaSeparatedFile(WEATHER_FILE);
        this.TAXI_HEALTH_WORD = messageProps.getProperty("gamePlay.taxiHealth");
        this.DRIVER_HEALTH_WORD = messageProps.getProperty("gamePlay.driverHealth");
        this.PASSENGER_HEALTH_WORD = messageProps.getProperty("gamePlay.passengerHealth");
        this.PAY_WORD = messageProps.getProperty("gamePlay.earnings");
        this.TARGET_WORD = messageProps.getProperty("gamePlay.target");
        this.FRAMES_REM_WORD = messageProps.getProperty("gamePlay.remFrames");
        this.TAXI_MAX_SPAWN_MAX_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.nextSpawnMaxY"));
        this.TAXI_MAX_SPAWN_MIN_Y = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.nextSpawnMinY"));

        this.bottomCoorY = (Window.getHeight() / 2);
        this.topCoorY = -(Window.getHeight() / 2);
        this.score = 0.0;
        this.frameLeft = MAX_FRAMES;
        this.isCoinActive = false;
        this.isRaining = false;
        this.driver = new Driver(gameProps, 0, 0);
        this.cars = new ArrayList<>();
        this.isEjectedPassengerInTaxi = true;

        initialiseClasses(IOUtils.readCommaSeparatedFile(OBJECT_FILE));
    }

    /**
     * Gets the bottom screen's y-coordinate of game play screen
     * @return game play screen's bottom screen's y-coordinate
     */
    public int getBottomCoorY() {
        return bottomCoorY;
    }

    /**
     * Sets the bottom screen's y-coordinate of game play screen
     * @param bottomCoorY game play screen's bottom screen's y-coordinate
     */
    public void setBottomCoorY(int bottomCoorY) {
        this.bottomCoorY = bottomCoorY;
    }

    /**
     * Gets the top screen's y-coordinate of game play screen
     * @return game play screen's top screen's y-coordinate
     */
    public int getTopCoorY() {
        return topCoorY;
    }

    /**
     * Sets the top screen's y-coordinate of game play screen
     * @param topCoorY game play screen's top screen's y-coordinate
     */
    public void setTopCoorY(int topCoorY) {
        this.topCoorY = topCoorY;
    }

    /**
     * Gets the score of game play screen
     * @return game play screen's score
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the score of game play screen
     * @param score game play screen's score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Gets the frames left of game play screen
     * @return game play screen's frame left
     */
    public int getFrameLeft() {
        return frameLeft;
    }

    /**
     * Sets the frames left of game play screen
     * @param frameLeft game play screen's frame left
     */
    public void setFrameLeft(int frameLeft) {
        this.frameLeft = frameLeft;
    }

    /**
     * Gets the name of game play screen
     * @return game play screen's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of game play screen
     * @param name game play screen's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets whether a coin is active in game play screen
     * @return true if active, false otherwise
     */
    public boolean getIsCoinActive() {
        return isCoinActive;
    }

    /**
     * Sets whether a coin is active in game play screen
     * @param coinActive true if active, false otherwise
     */
    public void setIsCoinActive(boolean coinActive) {
        isCoinActive = coinActive;
    }

    /**
     * Gets the taxi of game play screen
     * @return game play screen's taxi
     */
    public Taxi getTaxi() {
        return taxi;
    }

    /**
     * Sets the taxi of game play screen
     * @param taxi game play screen's taxi
     */
    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }

    /**
     * Gets the driver of game play screen
     * @return game play screen's driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Sets the driver of game play screen
     * @param driver game play screen's driver
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * Gets whether is raining in game play screen
     * @return true if raining, false otherwise
     */
    public boolean getIsRaining(){
        return isRaining;
    }

    /**
     * Sets whether is raining in game play screen
     * @param isRaining true if raining, false otherwise
     */
    public void setIsRaining(boolean isRaining){
        this.isRaining = isRaining;
    }

    /**
     * Gets the damaged taxi of game play screen
     * @return game play screen's damaged taxi
     */
    public Taxi getDamagedTaxi(){
        return damagedTaxi;
    }

    /**
     * Sets the damaged taxi of game play screen
     * @param damagedTaxi game play screen's damaged taxi
     */
    public void setDamagedTaxi(Taxi damagedTaxi){
        this.damagedTaxi = damagedTaxi;
    }

    /**
     * Gets whether there is an ejected passenger in taxi of game play screen
     * @return true if there is an ejected passenger in taxi, false otherwise
     */
    public boolean getIsEjectedPassengerInTaxi(){
        return isEjectedPassengerInTaxi;
    }

    /**
     * Sets whether there is an ejected passenger in taxi of game play screen
     * @param isEjectedPassengerInTaxi true if there is an ejected passenger in taxi, false otherwise
     */
    public void setIsEjectedPassengerInTaxi(boolean isEjectedPassengerInTaxi){
        this.isEjectedPassengerInTaxi = isEjectedPassengerInTaxi;
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
        loadCoins(coins);
        loadInvinciblePowers(invinciblePowers);
        loadDriver();
        loadCars(cars);
        loadBottomLeft();
        loadFireballs(cars);

        pickUpPassenger(input, passengers);
        updatePassengerWithTaxi();
        dropOff(input);

        collideCoins(coins);
        collideInvinciblePowers(invinciblePowers);
        handleCoinFrames();

        createCars();
        createFireballs(cars);

        moveCars(cars, input);
        moveFireballs(cars, input);

        checkCollisions();
        decrementCollisionTimeout();
        decrementInvincibility();

        handleAllMomentum();
        handleDriverPassengerMovement(input);
        handleInput(input);
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
        String tempScore = String.format("%.2f", score);
        loadFont(FONT_FILE, PAY_WORD + tempScore, INFO_FONT_SIZE, EARNINGS_COOR_X, EARNINGS_COOR_Y);

        String tempTargetScore = String.format("%.2f", TARGET_SCORE);
        loadFont(FONT_FILE, TARGET_WORD + tempTargetScore, INFO_FONT_SIZE, TARGET_COOR_X, TARGET_COOR_Y);

        loadFont(FONT_FILE, FRAMES_REM_WORD + frameLeft, INFO_FONT_SIZE, MAX_FRAMES_COOR_X, MAX_FRAMES_COOR_Y);
    }

    /**
     * Loads all passengers if they are not picked up or are dropped off, it also moves passenger to trip end flag if
     * it's visible and modifies priority based on weather conditions
     * @param passengers array of Passengers objects
     */
    public void loadPassengers(Passenger[] passengers){

        for (Passenger p: passengers){

            if (isRaining && p != taxi.getCurrentPassenger()){
                p.changePriorityWhenRaining();
            } else {
                p.revertPriorityWhenSunny();
            }

            if(!p.getIsPickedUp() || p.isDroppedOff() || p.getIsEjected()){

                p.IMAGE.draw(p.getCoorX(), p.getCoorY());
                loadPassengerDetails(p);
            }

            if (p.BLOOD.getIsActive()){
                p.BLOOD.IMAGE.draw(p.BLOOD.getCoorX(), p.BLOOD.getCoorY());
                p.BLOOD.incrementCurrentFrame();
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

        String priority = Integer.toString(p.getCurrentPriority());
        String earnings = String.format("%.1f", p.getEarnings());

        loadFont(FONT_FILE, priority, PASSENGER_FONT_SIZE, p.getCoorX() - PRIORITY_GAP, p.getCoorY());
        loadFont(FONT_FILE, earnings, PASSENGER_FONT_SIZE, p.getCoorX() - EARNINGS_GAP, p.getCoorY());
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
                    !p.getIsPickedUp() && taxi.getHasDriver()){
                p.incrementCoorXY(taxi.getCoorX(), taxi.getCoorY());

                // A passenger is only considered picked up when they have the same coordinates as taxi
                if (p.sameCoor(taxi.getCoorX(), taxi.getCoorY())){
                    p.setIsPickedUp(true);
                    p.TRIP_END_FLAG.setIsVisible(true);
                    p.lockPriority();
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
     * Collides coin with taxi and driver
     * @param coins array of Coin objects
     */
    public void collideCoins(Coin[] coins){
        for (Coin c: coins){
            if (c.hasCollided(taxi)){
                collidedCoin = c;
                setIsCoinActive(true);

                handlePriorityDecrement();
            }

            if (driver.getIsEjected() && c.hasCollided(driver)) {
                collidedCoin = c;
                setIsCoinActive(true);

                handlePriorityDecrement();
            }
        }

    }

    /**
     * Decreases current trip's passenger's priority only if there is a current trip, and updates passenger' earnings
     * to reflect this change
     */
    public void handlePriorityDecrement(){
        if (currentTrip != null){
            currentTrip.decreasePassengerPriority();
            currentTrip.PASSENGER.calcExpEarnings(currentTrip.PASSENGER.getYDist());

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
            passengerHealth = String.format("%.1f", findPassengerMinHealth());

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
        if (taxi.getCurrentPassenger() == null || currentTrip == null){
            return;
        }

        Passenger passengerInTaxi = taxi.getCurrentPassenger();

        if (!input.isDown(Keys.UP) && !input.isDown(Keys.RIGHT) && !input.isDown(Keys.LEFT)) {

            if (taxi.getCoorY() < passengerInTaxi.TRIP_END_FLAG.getCoorY() ||
                    taxi.calcDist(passengerInTaxi.TRIP_END_FLAG.getCoorX(),
                            passengerInTaxi.TRIP_END_FLAG.getCoorY()) <= taxi.RADIUS){
                isEjectedPassengerInTaxi = true;
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

        if (p.TRIP_END_FLAG.getIsVisible() && !p.getIsEjected() && p.isDroppedOff()){
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
            loadFont(FONT_FILE, currentTrip.PRIORITY_WORD + currentTrip.PASSENGER.getCurrentPriority(),
                    INFO_FONT_SIZE, currentTrip.TRIP_INFO_COOR_X,
                    currentTrip.TRIP_INFO_COOR_Y + (Trip.INFO_GAP * 2));

        } else {
            String expFee = String.format("%.1f", lastTrip.PASSENGER.getEarnings());
            String penalty = String.format("%.2f", lastTrip.getPenalty());

            loadFont(FONT_FILE, lastTrip.LAST_TRIP_WORD, INFO_FONT_SIZE, lastTrip.TRIP_INFO_COOR_X,
                    lastTrip.TRIP_INFO_COOR_Y);
            loadFont(FONT_FILE, lastTrip.EXP_FEE_WORD + expFee, INFO_FONT_SIZE, lastTrip.TRIP_INFO_COOR_X,
                    lastTrip.TRIP_INFO_COOR_Y + Trip.INFO_GAP);
            loadFont(FONT_FILE, lastTrip.PRIORITY_WORD + lastTrip.PASSENGER.getCurrentPriority(), INFO_FONT_SIZE,
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
     * Creates OtherCars and EnemyCars if randomly generated number is divisible by 200 and 400 respectively
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

    /**
     * Generates a random x-coordinate of given lane values
     * @return integer of random x-coordinate of lane value
     */
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

    /**
     * Generates a random y-coordinate between given specific values for Car
     * @return integer of y-coordinate
     */
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

        // checks collision between Car and Taxi, Car and Driver
        for (Car c: cars){
            CollisionHandler.checkCollision(taxi, c);
            updateDriverWithTaxi();
            CollisionHandler.checkCollision(c, driver);

            // checks collision between EnemyCar's fireballs with Taxi and Driver
            if (c instanceof EnemyCar){
                EnemyCar enemyCar = (EnemyCar) c;
                CollisionHandler.checkCollision(enemyCar, driver);
                CollisionHandler.checkCollision(enemyCar, taxi);
                updateDriverWithTaxi();

            }

            // checks collision between Car and every other Passenger
            for (Passenger p: passengers){
                CollisionHandler.checkCollision(c, p);

                // checks collision between EnemyCar's fireballs and everyPassenger
                if (c instanceof EnemyCar){
                    EnemyCar enemyCar = (EnemyCar) c;
                    CollisionHandler.checkCollision(enemyCar, p);
                }
            }
        }

        // checks collision between car and car
        for (int i = 0; i < cars.size(); i++){
            for (int j = 0; j < cars.size(); j++) {
                if (i != j){
                    CollisionHandler.checkCollision(cars.get(i), cars.get(j));

                    // checks collision between EnemyCar's fireballs and all other Car
                    if (cars.get(i) instanceof EnemyCar){
                        EnemyCar enemyCar = (EnemyCar) cars.get(i);
                        CollisionHandler.checkCollision(enemyCar, cars.get(j));
                    }
                }
            }
        }
    }

    /**
     * Calculates distance between two GameEntities
     * @param gameEntity1 GameEntity object
     * @param gameEntity2 GameEntity object
     * @return double of distance between both GameEntity objects
     */
    public static double calcDist(GameEntity gameEntity1, GameEntity gameEntity2) {
        return Math.sqrt(Math.pow(gameEntity1.getCoorX() - gameEntity2.getCoorX(), 2) +
                Math.pow(gameEntity1.getCoorY() - gameEntity2.getCoorY(), 2));
    }

    /**
     * Handles momentum of all GameEntity
     */
    public void handleAllMomentum(){
        taxi.handleMomentum();
        for (Car c: cars){
            c.handleMomentum();
        }
        if (driver.getIsEjected()){
            driver.handleMomentum();
        }
        for (Passenger p: passengers){
            p.handleMomentum();
        }
    }

    /**
     * Decrements collision timeout of all GameEntity
     */
    public void decrementCollisionTimeout(){
        taxi.decrementCollisionTimeoutLeft();
        for (Car c: cars){
            c.decrementCollisionTimeoutLeft();
        }
        driver.decrementCollisionTimeoutLeft();
        for (Passenger p: passengers){
            p.decrementCollisionTimeoutLeft();
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
     * @param invinciblePowers array of Invincible Power
     */
    public void collideInvinciblePowers(InvinciblePower[] invinciblePowers){
        for (InvinciblePower i: invinciblePowers){
            if (i.hasCollided(taxi)) {
                taxi.setInvinciblePower(i);
            }

            if (driver.getIsEjected() && i.hasCollided(driver)) {
                driver.setInvinciblePower(i);
            }
        }
    }

    /**
     * Assigns a new taxi if current taxi is permanently damaged
     */
    public void assignNewTaxi(){
        if (taxi.getIsDamaged() && !taxi.FIRE.getIsActive()){
            isEjectedPassengerInTaxi = true;
            damagedTaxi = new Taxi(GAME_PROPS, taxi.getCoorX(), taxi.getCoorY(), true);

            if (!driver.getIsEjected()) {
                driver.eject(taxi.getCoorX() - Driver.EJECTION_COOR_X_MINUS, taxi.getCoorY());
            }

            if (taxi.getCurrentPassenger() != null){
                taxi.getCurrentPassenger().eject(taxi.getCoorX() - Passenger.EJECTION_COOR_X_MINUS,
                        taxi.getCoorY());
                ejectedPassenger = taxi.getCurrentPassenger();
            }

            int newSpawnCoorY = MiscUtils.getRandomInt(TAXI_MAX_SPAWN_MIN_Y, TAXI_MAX_SPAWN_MAX_Y + 1);
            taxi = new Taxi(GAME_PROPS, randomLaneNumber(), newSpawnCoorY, false);
            taxi.setHasDriver(false);
        }
    }

    /**
     * Loads both damaged and undamaged taxis, along with their Effects (if any)
     */
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

    /**
     * Finds the minimum health among all Passengers
     * @return double of minimum health of all Passengers
     */
    public double findPassengerMinHealth(){
        double minHealth = Double.MAX_VALUE;
        for (Passenger p: passengers){
            if (p.getHealth() < minHealth){
                minHealth = p.getHealth();
            }
        }
        return minHealth;
    }

    /**
     * Loads driver along with their Effects (if any)
     */
    public void loadDriver(){
        if (driver.getIsEjected()){
            driver.IMAGE.draw(driver.getCoorX(), driver.getCoorY());

            if (driver.BLOOD.getIsActive()) {
                driver.BLOOD.IMAGE.draw(driver.BLOOD.getCoorX(), driver.BLOOD.getCoorY());
                driver.BLOOD.incrementCurrentFrame();
            }
        }
    }

    /**
     * Loads cars along with their effects (if any)
     * @param cars ArrayList of Car
     */
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

    /**
     * Moves car based on whether "UP" button was pressed
     * @param cars ArrayList of Car
     * @param input keyboard input
     */
    public void moveCars(ArrayList<Car> cars, Input input){
        for (Car c: cars){
            if (input.isDown(Keys.UP) && !driver.getIsEjected() && isEjectedPassengerInTaxi){
                c.moveRelativeToTaxi();
            } else {
                c.move();
            }
        }
    }

    /**
     * Moves both damaged and undamaged Taxi down along with effects
     */
    public void moveTaxiDown(){
        if (damagedTaxi != null){
            damagedTaxi.moveDown();
        }
        if (!taxi.getHasDriver()) {
            taxi.moveDown();
        } else {
            taxi.moveEffectsDown();
        }
    }

    /**
     * Handles movement of driver and passenger when they are ejected
     * @param input keyboard input
     */
    public void handleDriverPassengerMovement(Input input){
        if (isEjectedPassengerInTaxi) {
            if (!driver.getIsEjected()) {
                return;
            }

            if (input.isDown(Keys.UP)) {
                moveScreenDown();
                movePassengersDown(passengers);
                moveCoinsDown(coins);
                moveInvinciblePowersDown(invinciblePowers);
                moveTaxiDown();
                driver.moveUp();
            }

            if (input.isDown(Keys.DOWN)){
                driver.moveDown();
            }

            if (input.isDown(Keys.LEFT)) {
                driver.moveLeft();
            }

            if (input.isDown(Keys.RIGHT)) {
                driver.moveRight();
            }
        }

        if (ejectedPassenger != null){
            ejectedPassenger.followDriver(driver);
        }

        checkDriverPassengerInTaxi();
    }

    /**
     * Checks if driver and passenger are in the taxi. If driver is inside taxi, but not passenger, taxi would not be
     * able to move until passenger is within TAXI_GET_IN_RADIUS
     */
    public void checkDriverPassengerInTaxi() {
        if (calcDist(driver, taxi) <= driver.TAXI_GET_IN_RADIUS){
            driver.setIsEjected(false);
            taxi.setHasDriver(true);
            taxi.setInvinciblePower(driver.getInvinciblePower());
            if (ejectedPassenger != null){
                isEjectedPassengerInTaxi = false;
            }

            if (ejectedPassenger != null && calcDist(taxi, ejectedPassenger) <= ejectedPassenger.TAXI_GET_IN_RADIUS){
                taxi.setCurrentPassenger(ejectedPassenger);
                ejectedPassenger.setIsEjected(false);
                ejectedPassenger = null;
                isEjectedPassengerInTaxi = true;
            }
        }
    }

    /**
     * Handles input when both driver and passenger are in the taxi
     * @param input keyboard input
     */
    public void handleInput(Input input) {
        if (driver.getIsEjected() || !isEjectedPassengerInTaxi){
            return;
        }

        if (input.isDown(Keys.UP)) {
            moveScreenDown();
            movePassengersDown(passengers);
            moveCoinsDown(coins);
            incrementDistTravelled();
            moveInvinciblePowersDown(invinciblePowers);
            moveTaxiDown();
            driver.moveWithTaxi(taxi);
        }

        if (input.isDown(Keys.LEFT)) {
            taxi.moveLeft();
            driver.moveWithTaxi(taxi);
        }

        if (input.isDown(Keys.RIGHT)) {
            taxi.moveRight();
            driver.moveWithTaxi(taxi);
        }
    }

    /**
     * Decrements invincibility of taxi and driver if they have previously collected an InvinciblePower
     */
    public void decrementInvincibility(){
        taxi.getInvinciblePower().decrementFrameLeft();

        if (driver.getIsEjected()) {
            driver.getInvinciblePower().decrementFrameLeft();
        }
    }

    /**
     * Creates fireball for each EnemyCar if each randomly generated number is divisible by 300
     * @param cars ArrayList of Car
     */
    public void createFireballs(ArrayList<Car> cars){
        for (Car c: cars){
            if (c instanceof EnemyCar && c.getIsActive() && MiscUtils.canSpawn(Fireball.DIVISIBILITY)){
                EnemyCar enemyCar = (EnemyCar) c;
                enemyCar.addFireball();
            }
        }
    }

    /**
     * Moves fireballs based on whether "UP" button was pressed
     * @param cars ArrayList of Car
     * @param input keyboard input
     */
    public void moveFireballs(ArrayList<Car> cars, Input input){
        for (Car c: cars){
            if (c instanceof EnemyCar){
                EnemyCar enemyCar = (EnemyCar) c;
                if (input.isDown(Keys.UP) && !driver.getIsEjected() && isEjectedPassengerInTaxi){
                    enemyCar.moveFireballsRelativeToTaxi();
                } else {
                    enemyCar.moveFireballs();
                }
            }
        }
    }

    /**
     * Loads fireballs of EnemyCar
     * @param cars ArrayList of Car
     */
    public void loadFireballs(ArrayList<Car> cars){
        for (Car c: cars){
            if (c instanceof EnemyCar){
                EnemyCar enemyCar = (EnemyCar) c;
                for (Fireball f: enemyCar.getFireballs()){
                    if (f.getIsActive()){
                        f.IMAGE.draw(f.getCoorX(), f.getCoorY());
                    }
                }
            }
        }
    }

    /**
     * Updates Driver coordinates with Taxi after a collision
     */
    public void updateDriverWithTaxi(){
        if (taxi.getHasDriver()) {
            driver.setCoorX(taxi.getCoorX());
            driver.setCoorY(taxi.getCoorY());
        }
    }
}
