package starwars.actions;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;

public class Thrown extends SWAffordance{
	/**
	 Constructor for the <code>Thrown</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @author 	Xutong
	 * @param 	theTarget a <code>SWEntity</code> that is been thrown by an grenade
	 * @param   m the message renderer to display messages
	 */
	public Thrown(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
		priority = 1;
	}

	/**
	 * Returns if or not this <code>Thrown</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying any item with Throwable capability.
	 *  
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is already taking any item with Throwable, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 * @see     {@link starwars.SWEntityInterface#hasCapability(Capability)}
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		SWEntityInterface item = a.getItemCarried();
		if (item!= null) {
			return item.hasCapability(Capability.THROWABLE);
		}
		return false;
	}

	/**
	 * Perform the <code>Thrown</code> action by damage entity except the actors in the same team
	 * when the <code>Thrown</code> action performs, damage will affect up to two steps range of entities 
	 * setting the item carried to null at the end since the grenade has already been used.
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is holding an entity with Throwable capability.
	 * 
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> that is having the Throwable entity
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWAction#getEntitymanager()}
	 * @see 	{@link edu.monash.fit2099.Grid#CompassBearing}
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		SWEntityInterface target = this.getTarget();

		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		//the amount of energy required to use grenade
		int energyForAttackWithWeapon = 1;
//		see if the target is an actor
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
					
		//don't throw SWActors in the same team
		if (targetIsActor&&(a.getTeam() == targetActor.getTeam())) { 
			a.say("\t" + a.getShortDescription() + " says: Silly me! We're on the same team, " + target.getShortDescription() + ". No harm done");
		}
		else { 
				
			a.say(a.getShortDescription() + " throws grenade!");
			SWLocation loc = SWAction.getEntitymanager().whereIs(a);
			
//			an array list recording all damaged entities, if the entity has already damaged, it wont be damage again.
			ArrayList<SWEntityInterface> affectedEntities = new ArrayList<SWEntityInterface>();
			List<SWEntityInterface> allEntities = SWAction.getEntitymanager().contents(loc);
			
//			take damage at the current location
			for (SWEntityInterface entity : allEntities) {
				if (entity != a) {
					entity.takeDamage(20);
					affectedEntities.add(entity);
				}	
			}
//		find one step of location, and take damage if there is an entity
			for (Grid.CompassBearing neighbor : Grid.CompassBearing.values()) {
//				see if actor a has neighboring location
				if(SWWorld.getEntitymanager().seesExit(a, neighbor)) {
//					get that location
					SWLocation neighborLocation = (SWLocation) loc.getNeighbour(neighbor);
//					get all entities in that location
					allEntities = SWAction.getEntitymanager().contents(neighborLocation);
//					if there are some entities in that location, take damage
					if (allEntities != null) {
						for (SWEntityInterface entity : allEntities) {
							entity.takeDamage(10);
							affectedEntities.add(entity);
						}
					}
				}
			}
//			find two step of location, and take damage
			for (Grid.CompassBearing neighbor : Grid.CompassBearing.values()) {
				if(SWWorld.getEntitymanager().seesExit(a, neighbor)) {
					SWLocation neighborLoc = (SWLocation) loc.getNeighbour(neighbor);
//					check if the neighbor of a's neighbor has other neighbors
					for (Grid.CompassBearing neighbor2 :Grid.CompassBearing.values()) {
						if(neighborLoc.hasExit(neighbor2)) {
							SWLocation neighborLoc2 = (SWLocation)neighborLoc.getNeighbour(neighbor2);
							allEntities = SWAction.getEntitymanager().contents(neighborLoc2);
							if (allEntities != null) {
								for(SWEntityInterface entity : allEntities) {
//									see if the entity in affectedEntity array, if not, then damage it
									if (affectedEntities.contains(entity) == false) {
										entity.takeDamage(5);
										affectedEntities.add(entity);
									}
								}
							}
						}
					}
				}
			}
			
			a.takeDamage(energyForAttackWithWeapon);
			a.setItemCarried(null);
			}
		//After the attack
		
		if (a.isDead()) {//the actor who attacked is dead after the attack
						
			a.setLongDescription(a.getLongDescription() + ", that died of exhaustion while attacking someone");
			
			//remove the attack affordance of the dead actor so it can no longer be attacked
			a.removeAffordance(this);
			
			
		}
		if (this.getTarget().getHitpoints() <= 0) {  // can't use isDead(), as we don't know that the target is an actor
			target.setLongDescription(target.getLongDescription() + ", that was killed in a fight");
						
			//remove the attack affordance of the dead actor so it can no longer be attacked
			if (targetIsActor == true) {
				targetActor.removeAffordance(this);
			}
			

			
		}
		
	
	}
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author xutong
	 * @return String comprising "thrown a grenade to " and the short description of the target to be <code>Thrown</code>  by a grenade
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "thrown a grenage to " + target.getShortDescription();
	}

}
