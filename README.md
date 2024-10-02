Assumptions made
1. After collision, the taxi can only move if both driver and ejected passenger (if any) are within the 
   "taxiGetInRadius".
2. While game should immediately end if driver or any passenger's health is below zero, an additional 20 frames is 
   rendered to allow blood to display.
3. When it rains, passenger's priority without umbrella is changed to 1 only if they have not been picked up. For 
   example, if a passenger without an umbrella is picked up when it is sunny, and then it starts to rain, their 
   priority would remain unchanged because they are already in the car and have a roof over their heads (not 
   affected by rain).
4. During collision timeout, damageable entities like other car, enemy car and taxi, cannot take damage, however the 
   entity causing the collision with the entity in collision timeout can take damage as long as the entity causing 
   the collision is also not in collision timeout. For example, if an enemy car (not in collision timeout) causes a 
   collision with an other car (in collision timeout), the other car is not damaged but the enemy car gets damaged.
5. Cars immediately start moving after collision (unlike cars in the demo video that stop for a while before moving 
   again).
6. Although the fireball could collide with cars after it reaches the top of the screen, the fireball is not 
   rendered and is considered no longer active (according to the specification)