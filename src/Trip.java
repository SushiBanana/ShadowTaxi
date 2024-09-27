import java.util.Properties;

/**
 * This Java class contains attributes and methods related to Trip
 * @author Alysha Thean Student ID: 1495768
 */
public class Trip {

    public final static int INFO_GAP = 30;

    public final String CURRENT_TRIP_WORD;
    public final String LAST_TRIP_WORD;
    public final String EXP_FEE_WORD;
    public final String PRIORITY_WORD;
    public final String PENALTY_WORD;

    public final Properties GAME_PROPS;
    public final Properties MESSAGE_PROPS;
    public final double RATE_PER_Y;
    public final int RATE_PRIORITY_1;
    public final int RATE_PRIORITY_2;
    public final int RATE_PRIORITY_3;
    public final int TRIP_INFO_COOR_X;
    public final int TRIP_INFO_COOR_Y;
    public final double TRIP_PENALTY_PER_Y;

    public final Passenger PASSENGER;
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
     * Getter method of trip's penalty
     * @return double of trip's penalty
     */
    public double getPenalty() {
        return penalty;
    }

    /**
     * Setter method of trip's penalty
     * @param penalty double of trip's penalty
     */
    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    /**
     * Getter method of trip's earnings
     * @return double of trip's earnings
     */
    public double getEarnings() {
        return earnings;
    }

    /**
     * Setter methood of trip's earnings
     * @param earnings double of trip's earnings
     */
    public void setEarnings(double earnings) {
        if (earnings < 0){
            return;
        }
        this.earnings = earnings;
    }

    /**
     * Getter method of trip's y-coordinate travelled
     * @return integer of trip's y-coordinate travelled
     */
    public int getYDistanceTravelled() {
        return yDistanceTravelled;
    }

    /**
     * Setter method of trip's y-coordinate travelled
     * @param yDistanceTravelled integer of trip's y-coordinate travelled
     */
    public void setYDistanceTravelled(int yDistanceTravelled) {
        this.yDistanceTravelled = yDistanceTravelled;
    }

    /**
     * Getter method of trip's distance fee
     * @return double of trip's distance fee
     */
    public double getDistanceFee() {
        return distanceFee;
    }

    /**
     * Setter method of trip's distance fee
     * @param distanceFee double of trip's distance fee
     */
    public void setDistanceFee(double distanceFee) {
        this.distanceFee = distanceFee;
    }

    /**
     * Getter method of trip's priority fee
     * @return double of trip's priority fee
     */
    public double getPriorityFee() {
        return priorityFee;
    }

    /**
     * Setter method of trip's priority fee
     * @param priorityFee double of trip's priority fee
     */
    public void setPriorityFee(double priorityFee) {
        this.priorityFee = priorityFee;
    }

    /**
     * Getter method for whether priority of current passenger in trip has decreased
     * @return true if current passenger's priority has been decreased before, false otherwise
     */
    public boolean getHasPriorityDecreased() {
        return hasPriorityDecreased;
    }

    /**
     * Setter method for whether priority of current passenger in trip has decreased
     * @param hasPriorityDecreased true if current passenger's priority has been decreased before, false otherwise
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
     * Returns the state of trip
     * @return String of states of trip
     */
    public String toString(){
        return "TRIP\n ______________\n" + PASSENGER + TAXI + "y-distance travelled: " + yDistanceTravelled +
                "\ndistance fee: " + distanceFee + "\npriority fee: " + priorityFee + "\nhas priority decreased: " +
                hasPriorityDecreased + "\npenalty: " + penalty + "\nearnings: " + earnings;
    }
}
