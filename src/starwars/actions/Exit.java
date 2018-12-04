package starwars.actions;



import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWActorWorldable;
import starwars.SWLocation;
import starwars.SWWorldBase;

public class Exit extends Transfer {
	

	/**
	 * constructor of <code>Exit</code> action
	 * @param m: message renderer to display messages
	 * @param inworld: the current world
	 * @param outworld: the world to transfer
	 * @param host: the inner world's <code>SWActorWorldable</code> host
	 */
	public Exit(MessageRenderer m, SWWorldBase inworld, SWWorldBase outworld, SWActorWorldable host) {
		super(m, inworld, outworld, host);

	}
	
	/**
	 * Returns if or not this <code>Exit</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying any item already.
	 *  
	 * @author 	wen
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is already taking any item, false otherwise
	 */
	@Override
	public boolean canDo(SWActor a) {
		SWLocation loc = a.getWorld().getEntityManager().whereIs(a);
		if (loc.getSymbol() == 'E') {
			return true && !a.isDead();
		}
		else {
			return false;
		}
	}

	/**
	 * Perform the <code>Exit</code> action by exit out <code>SWInnerWorld</code> and appear on the <code>SWWorld</code>
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	wen
	 * @param 	a the <code>SWActor</code> that exit from <code>SWInnerWorld</code>
	 */
	@Override
	public void act(SWActor a) {
		if (canDo(a)) {
			outworld.getEntityManager().setLocation(a, outworld.getEntityManager().whereIs(host));
		}
	}
	

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author wen
	 * @return String comprising "Exit: out of the inner area " 
	 */
	@Override
	public String getDescription() {
		return "Exit: out of the inner area";

	}

}
