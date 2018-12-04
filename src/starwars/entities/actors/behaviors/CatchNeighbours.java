package starwars.entities.actors.behaviors;

import java.util.List;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWActorWorldable;
import starwars.SWEntityInterface;
import starwars.SWInnerWorld;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Enter;
import starwars.actions.Exit;
import starwars.entities.actors.Droid;
import starwars.entities.actors.Player;

/**
 * This Class implements a <code>SWActorWorldable</code> catching <code>Droid</code> 
 * and <code>SWActor</code> who can use force
 * @author wen
 *
 */
public class CatchNeighbours {
	
	/**
	 * This is the public static method that implementing catching SWActors
	 * @param actor: a <code>SWActorWorldable</code> that implementing catching SWActors
	 */
	public static void catchLocals(SWActorWorldable actor, MessageRenderer m) {
		// search all entity in the sandcrawler’s location.
		SWInnerWorld innerworld = actor.getInnerworld();
		EntityManager<SWEntityInterface, SWLocation> em = actor.getWorld().getEntityManager();
		EntityManager<SWEntityInterface, SWLocation> inem = innerworld.getEntityManager();
		SWLocation sandLocation = em.whereIs(actor);
		List<SWEntityInterface> entities = em.contents(sandLocation);
		
		if (entities != null) {
			// check if there is a droid in the place
			for (SWEntityInterface e : entities) {
				if (e instanceof Droid) {
					// display the droid in Sandcrawler innerWorld's door and remove the droid in the SWWorld		
					inem.setLocation((Droid)e, innerworld.getGrid().getLocationByCoordinates(innerworld.getExitCoordinates().getKey(), innerworld.getExitCoordinates().getValue()));
					
	
				}
				else if (e instanceof SWActor && ((SWActor) e).getCanUseForce() && !(e instanceof Player)) {
					if ((int)(Math.random()*100) < 70) {
						inem.setLocation((SWActor)e, innerworld.getGrid().getLocationByCoordinates(innerworld.getExitCoordinates().getKey(), innerworld.getExitCoordinates().getValue()));
					}
				}
				else if (e instanceof Player && ((Player) e).getCanUseForce()) {
					
					((Player) e).removeActionByEnter();

					Enter myEnter = new Enter(m, actor.getWorld(), innerworld, actor);
					((Player) e).addAction(myEnter);
	
					((Player) e).removeActionByExit();
					SWActor.getScheduler().schedule(myEnter, ((Player) e), 1);
				}
	
				
			}
		}
	}
	
	/**
	 * This is the public static method that implementing releasing SWActors
	 * @param actor: a <code>SWActorWorldable</code> that implementing releasing SWActors
	 */
	public static void releaseInnerActor(SWActorWorldable actor, MessageRenderer m) {
		// search all entity in the sandcrawler’s location.
		SWInnerWorld innerworld = actor.getInnerworld();
		SWWorld world = (SWWorld)actor.getWorld();
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		SWLocation sandLocation = em.whereIs(actor);
		List<SWEntityInterface> entities = em.contents(innerworld.getGrid().getLocationByCoordinates(innerworld.getExitCoordinates().getKey(), innerworld.getExitCoordinates().getValue()));
		
		// check if there is a droid in the place
		if (entities != null) {
			for (SWEntityInterface e : entities) {
				if (e instanceof SWActor && ((SWActor) e).getCanUseForce() && !(e instanceof Player)) {
					if ((int)(Math.random()*100) < 20) {
						em.setLocation((SWActor)e, sandLocation);
					}
				}
				else if (e instanceof Player && ((Player) e).getCanUseForce()) {
					
					((Player) e).removeActionByExit();
					Exit myExit = new Exit(m, innerworld, world, actor);
					((Player) e).addAction(myExit);
					
					((Player) e).removeActionByEnter();
					SWActor.getScheduler().schedule(myExit, ((Player) e), 1);


				}
				
			}
		}


	}
}
