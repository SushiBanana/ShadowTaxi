import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Trip
 * @author Alysha Thean Student ID: 1495768
 */
public class Trip {

    /**
     * The y-coordinate information display gap of trip
     */
    public final static int INFO_GAP = 30;
    /**
     * The current trip word of trip
     */
    public final String CURRENT_TRIP_WORD;
    /**
     * The last trip word of trip
     */
    public final String LAST_TRIP_WORD;
    /**
     * The expected fee word of trip
     */
    public final String EXP_FEE_WORD;
    /**
     * The priority word of trip
     */
    public final String PRIORITY_WORD;
    /**
     * The penalty word of trip
     */
    public final String PENALTY_WORD;
    /**
     * The game properties of trip
     */
    public final Properties GAME_PROPS;
    /**
     * The message properties of trip
     */
    public final Properties MESSAGE_PROPS;
    /**
     * The rate per y-coordinate travelled of trip
     */
    public final double RATE_PER_Y;
    /**
     * The rate per y-coordinate travelled of passenger of priority 1
     */
    public final int RATE_PRIORITY_1;
    /**
     * The rate per y-coordinate travelled of passenger of priority 2
     */
    public final int RATE_PRIORITY_2;
    /**
     * The rate per y-coordinate travelled of passenger of priority 3
     */
    public final int RATE_PRIORITY_3;
    /**
     * The x-coordinate of trip information display in trip
     */
    public final int TRIP_INFO_COOR_X;
    /**
     * The y-coordinate of trip information display in trip
     */
    public final int TRIP_INFO_COOR_Y;
    /**
     * The penalty rate per y-coordinate travelled in trip
     */
    public final double TRIP_PENALTY_PER_Y;
    /**
     * The passenger of trip
     */
    public final Passenger PASSENGER;
    /**
     * The taxi of trip
     */
    public final Taxi TAXI;

    private int yDistanceTravelled;
    private double distanceFee;
    private double priorityFee;
    private boolean hasPriorityDecreased;
    private double penalty;
    private double earnings;

    /**
     * Construcfor for trip class
     * @param gameProps properties file for values of various attributes
     * @param messageProps properties file for display text
     * @param passenger Passenger object
     * @param taxi Taxi object
     */
    public Trip(Properties gameProps, Properties messageProps, Passenger passenger, Taxi taxi){
        this.CURRENT_TRIP_WORD = messageProps.getProperty("gamePlay.onGoingTrip.title");
        this.LAST_TRIP_WORD = messageProps.getProperty("gamePlay.completedTrip.title");
        this.EXP_FEE_WORD = messageProps.getProperty("gamePlay.trip.expectedEarning");
        this.PRIORITY_WORD = messageProps.getProperty("gamePlay.trip.priority");
        this.PENALTY_WORD = messageProps.getProperty("gamePlay.trip.penalty");
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
        this.RATE_PER_Y = Double.parseDouble(gameProps.getProperty("trip.rate.perY"));
        this.RATE_PRIORITY_1 = Integer.parseInt(gameProps.getProperty("trip.rate.priority1"));
        this.RATE_PRIORITY_2 = Integer.parseInt(gameProps.getProperty("trip.rate.priority2"));
        this.RATE_PRIORITY_3 = Integer.parseInt(gameProps.getProperty("trip.rate.priority3"));
        this.TRIP_INFO_COOR_X = Integer.parseInt(gameProps.getProperty("gamePlay.tripInfo.x"));
        this.TRIP_INFO_COOR_Y = Integer.parseInt(gameProps.getProperty("gamePlay.tripInfo.y"));
        this.TRIP_PENALTY_PER_Y = Double.parseDouble(gameProps.getProperty("trip.penalty.perY"));
        this.PASSENGER = passenger;
        this.TAXI = taxi;

        this.yDistanceTravelled = 0;
        this.distanceFee = 0.0;
        this.priorityFee = 0.0;
        this.hasPriorityDecreased = false;
        this.penalty = 0.00;
        this.earnings = 0.0;

    }

    /**
     * Gets the penalty of trip
     * @return trip's penalty
     */
    public double getPenalty() {
        return penalty;
    }

    /**
     * Sets the penalty of trip
     * @param penalty trip's penalty
     */
    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    /**
     * Gets the earnings of trip
     * @return trip's earnings
     */
    public double getEarnings() {
        return earnings;
    }

    /**
     * Sets the earnings of trip
     * @param earnings trip's earnings
     */
    public void setEarnings(double earnings) {
        if (earnings < 0){
            return;
        }
        this.earnings = earnings;
    }

    /**
     * Gets the vertical distance travelled of trip
     * @return trip's vertical distance travelled
     */
    public int getYDistanceTravelled() {
        return yDistanceTravelled;
    }

    /**
     * Sets the vertical distance travelled of trip
     * @param yDistanceTravelled trip's vertical distance travelled
     */
    public void setYDistanceTravelled(int yDistanceTravelled) {
        this.yDistanceTravelled = yDistanceTravelled;
    }

    /**
     * Gets the distance fee of trip
     * @return trip's distance fee
     */
    public double getDistanceFee() {
        return distanceFee;
    }

    /**
     * Sets the distance fee of trip
     * @param distanceFee trip's distance fee
     */
    public void setDistanceFee(double distanceFee) {
        this.distanceFee = distanceFee;
    }

    /**
     * Gets the priority fee of trip
     * @return trip's priority fee
     */
    public double getPriorityFee() {
        return priorityFee;
    }

    /**
     * Sets the priority fee of trip
     * @param priorityFee trip's priority fee
     */
    public void setPriorityFee(double priorityFee) {
        this.priorityFee = priorityFee;
    }

    /**
     * Gets whether the trip's priority has decreased
     * @return true if trip's priority has already decreased, false otherwise
     */
    public boolean getHasPriorityDecreased() {
        return hasPriorityDecreased;
    }

    /**
     * Sets whether the trip's priority has decreased
     * @param hasPriorityDecreased true if trip's priority has already decreased, false otherwise
     */
    public void setHasPriorityDecreased(boolean hasPriorityDecreased) {
        this.hasPriorityDecreased = hasPriorityDecreased;
    }

    /**
     * Increments distance travelled based on Game Play Screen's FRAME_SPEED
     */
    public void incrementDistTravelled(){
        yDistanceTravelled += GamePlayScreen.FRAME_SPEED;
    }

    /**
     * Calculates trip's distance fee based on vertical distance travelled and rate per y-coordinate
     * @return double of distance fee
     */
    public double calcDistanceFee(){
        this.distanceFee = yDistanceTravelled * RATE_PER_Y;
        return distanceFee;
    }

    /**
     * Calculates trip's priority fee
     * @return double of trip's priority fee
     */
    public double calcPriorityFee(){
        int rate;

        switch (PASSENGER.getCurrentPriority()){
            case 1:
                rate = RATE_PRIORITY_1;
                break;


            case 2:
                rate = RATE_PRIORITY_2;
                break;


            case 3:
                rate = RATE_PRIORITY_3;
                break;


            default:
                rate = 0;
        }

        this.priorityFee = PASSENGER.getCurrentPriority() * rate;
        return priorityFee;
    }

    /**
     * Calculates the trip's earnings by summing distance fee and priority fee while subtracting any penalty
     * @return double of trip's earnings
     */
    public double calcTripEarnings(){
        this.earnings = calcDistanceFee() + calcPriorityFee() - penalty;
        return this.earnings;
    }

    /**
     * Decreases the trip passenger's current and original priority only if it has never been decreased before
     */
    public void decreasePassengerPriority(){
        if (hasPriorityDecreased || PASSENGER.getCurrentPriority() <= 1){
            return;
        }

        PASSENGER.decrementPriority();
        hasPriorityDecreased = true;
    }

    /**
     * Returns the state of Trip object
     * @return String of states of Trip
     */
    public String toString(){
        return "TRIP\n ______________\n" + PASSENGER + TAXI +
                "y-distance travelled: " + yDistanceTravelled +
                "\ndistance fee: " + distanceFee +
                "\npriority fee: " + priorityFee +
                "\nhas priority decreased: " + hasPriorityDecreased +
                "\npenalty: " + penalty +
                "\nearnings: " + earnings;
    }
}
