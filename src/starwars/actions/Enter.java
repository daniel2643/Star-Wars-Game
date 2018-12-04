package starwars.actions;

import java.util.List;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

import starwars.SWActor;
import starwars.SWActorWorldable;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorldBase;

/**
 * This is the class for Player entering action implementation.
 * @author wen
 */
public class Enter extends Transfer {


	/**
	 * constructor of <code>Enter</code> action
	 * @param m: message renderer to display messages
	 * @param inworld: the current world
	 * @param outworld: the world to transfer
	 * @param host: the inner world's <code>SWActorWorldable</code> host
	 */
	public Enter(MessageRenderer m, SWWorldBase inworld, SWWorldBase outworld, SWActorWorldable host) {
		super(m, inworld, outworld, host);

	}

	/**
	 * Returns if or not this <code>Enter</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying any item already.
	 *  
	 * @author 	wen
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is already taking any item, false otherwise
	 */
	@Override
	public boolean canDo(SWActor a) {
		if (a.isDead()) {
			return false;
		}
		EntityManager<SWEntityInterface, SWLocation> em = a.getWorld().getEntityManager();
		SWLocation loc = em.whereIs(a);
		List<SWEntityInterface> entities = em.contents(loc);
		for (SWEntityInterface e : entities) {
			if (e.getSymbol() == "•") {
				return true;
			}
		}

		
		return false;
		
	}

	
	/**
	 * Perform the <code>Enter</code> action by enter into <code>SWInnerWorld</code> and disappear from the <code>SWWorld</code>
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	wen
	 * @param 	a the <code>SWActor</code> that enter into <code>SWInnerWorld</code>
	 */
	@Override
	public void act(SWActor a) {
		if (canDo(a)) {
			outworld.getEntityManager().setLocation(a, outworld.getGrid().getLocationByCoordinates(4, 2));
		}
		
	}

	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author wen
	 * @return String comprising "Enter: into the inner area" 
	 */
	@Override
	public String getDescription() {
		return "Enter: into the inner area";

	}



}
