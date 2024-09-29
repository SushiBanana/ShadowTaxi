/**
 * This Java interface contains methods related to Damageable objects
 * @author Alysha Thean Student ID: 1495768
 */
public interface Damageable {

    /**
     * Takes damage from DamageDealer
     * @param damageDealer GameEntity object that can deal damage
     */
    void takeDamage(DamageDealer damageDealer);

}
