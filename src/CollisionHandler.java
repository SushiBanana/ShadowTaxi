/**
 * This Java class contains methods related to CollisionHandler
 * @author Alysha Thean Student ID: 1495768
 */
public class CollisionHandler {
    /**
     * Checks collision between Taxi and Car
     * @param taxi Taxi object
     * @param car Car object
     */
    public static void checkCollision(Taxi taxi, Car car){
        double sumOfRadius = taxi.RADIUS + car.RADIUS;

        if (taxi.calcDist(car.getCoorX(), car.getCoorY()) < sumOfRadius && car.getIsActive()) {

            // taxi can only take damage if it's not in collision timeout and not invincible
            if (taxi.getCollisionTimeoutLeft() == 0 && taxi.getInvinciblePower().getFrameLeft() == 0) {
                taxi.takeDamage(car);
            }

            if (car.getCollisionTimeoutLeft() == 0){
                car.takeDamage(taxi);
            }

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

    /**
     * Checks collision between a Car and another Car
     * @param car1 Car object
     * @param car2 Car object
     */
    public static void checkCollision(Car car1, Car car2) {
        double sumOfRadius = car1.RADIUS + car2.RADIUS;

        if (GamePlayScreen.calcDist(car1, car2) < sumOfRadius && car1.getIsActive() && car2.getIsActive()) {

            if (car1.getCollisionTimeoutLeft() == 0) {
                car1.takeDamage(car2);
            }

            if (car2.getCollisionTimeoutLeft() == 0){
                car2.takeDamage(car1);
            }

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

    /**
     * Checks collision between Car and Driver
     * @param car Car object
     * @param driver Driver object
     */
    public static void checkCollision(Car car, Driver driver) {
        double sumOfRadius = car.RADIUS + driver.RADIUS;
        // collision can only occur if driver is ejected from car
        if (GamePlayScreen.calcDist(car, driver) < sumOfRadius && car.getIsActive() && driver.getIsEjected()) {

            if (driver.getCollisionTimeoutLeft() == 0){
                driver.takeDamage(car);
            }

            // car is below driver
            if (car.getCoorY() > driver.getCoorY()) {
                car.setMomentumCurrentFrame(Car.MOMENTUM);
                driver.setMomentumCurrentFrame(-Driver.MOMENTUM);
            }

        }
    }

    /**
     * Checks collision between Car and Passenger
     * @param car Car object
     * @param passenger Passenger object
     */
    public static void checkCollision(Car car, Passenger passenger) {

        double sumOfRadius = car.RADIUS + passenger.RADIUS;
        if (GamePlayScreen.calcDist(car, passenger) < sumOfRadius && car.getIsActive()) {

            if (passenger.getCollisionTimeoutLeft() == 0){
                passenger.takeDamage(car);
            }

            // car is below passenger
            if (car.getCoorY() > passenger.getCoorY()) {
                car.setMomentumCurrentFrame(Car.MOMENTUM);
                passenger.setMomentumCurrentFrame(-Passenger.MOMENTUM);
            }
        }
    }

    /**
     * Checks collision of EnemyCar's fireballs with Passenger
     * @param enemyCar EnemyCar object
     * @param passenger Passenger object
     */
    public static void checkCollision(EnemyCar enemyCar, Passenger passenger){
        double sumOfRadius = enemyCar.RADIUS + passenger.RADIUS;

        for (Fireball f: enemyCar.getFireballs()){
            if (GamePlayScreen.calcDist(f, passenger) < sumOfRadius && f.getIsActive()) {

                if (passenger.getCollisionTimeoutLeft() == 0){
                    passenger.takeDamage(f);
                }

                // fireball is below passenger
                if (f.getCoorY() > passenger.getCoorY()) {
                    f.setIsActive(false);
                    passenger.setMomentumCurrentFrame(-Passenger.MOMENTUM);
                }
            }
        }
    }

    /**
     * Checks collision between EnemyCar's fireballs and Driver
     * @param enemyCar EnemyCar object
     * @param driver Driver object
     */
    public static void checkCollision(EnemyCar enemyCar, Driver driver){
        double sumOfRadius = enemyCar.RADIUS + driver.RADIUS;

        for (Fireball f: enemyCar.getFireballs()){
            if (GamePlayScreen.calcDist(f, driver) < sumOfRadius && f.getIsActive() && driver.getIsEjected()) {

                if (driver.getCollisionTimeoutLeft() == 0){
                    driver.takeDamage(f);
                }

                // fireball is below taxi
                if (f.getCoorY() > driver.getCoorY()) {
                    f.setIsActive(false);
                    driver.setMomentumCurrentFrame(-Passenger.MOMENTUM);
                }
            }
        }
    }

    /**
     * Checks collision between EnemyCar's fireballs and Taxi
     * @param enemyCar EnemyCar object
     * @param taxi Taxi object
     */
    public static void checkCollision(EnemyCar enemyCar, Taxi taxi){
        double sumOfRadius = enemyCar.RADIUS + taxi.RADIUS;

        for (Fireball f: enemyCar.getFireballs()){
            if (GamePlayScreen.calcDist(f, taxi) < sumOfRadius && f.getIsActive()) {

                if (taxi.getCollisionTimeoutLeft() == 0){
                    taxi.takeDamage(f);
                }

                // fireball is below taxi
                if (f.getCoorY() > taxi.getCoorY()) {
                    f.setIsActive(false);
                    taxi.setMomentumCurrentFrame(-Passenger.MOMENTUM);
                }
            }
        }
    }

    /**
     * Checks collision between EnemyCar's fireballs and Car
     * @param enemyCar EnemyCar object
     * @param car Car object
     */
    public static void checkCollision(EnemyCar enemyCar, Car car){
        double sumOfRadius = enemyCar.RADIUS + car.RADIUS;

        for (Fireball f: enemyCar.getFireballs()){
            if (GamePlayScreen.calcDist(f, car) < sumOfRadius && f.getIsActive() && car.getIsActive()) {

                if (car.getCollisionTimeoutLeft() == 0){
                    car.takeDamage(f);
                }

                // fireball is above taxi
                if (f.getCoorY() < car.getCoorY()) {
                    f.setIsActive(false);
                    car.setMomentumCurrentFrame(-Passenger.MOMENTUM);
                }
            }
        }
    }

}
