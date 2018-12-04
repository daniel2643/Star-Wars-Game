package starwars.actions;



import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActorWorldable;
import starwars.SWWorldBase;

/**
 * This is the class for implementing transfer action from one world to another world.
 * @author wen
 */
public abstract class Transfer extends SWAction {
	
	/**the current world*/
	SWWorldBase inworld;
	
	/**the world to transfer*/
	SWWorldBase outworld;
	
	/**the inner world's <code>SWActorWorldable</code> host*/
	SWActorWorldable host;

	/**
	 * constructor of <code>Transfer</code> action
	 * @param m: message renderer to display messages
	 * @param inworld: the current world
	 * @param outworld: the world to transfer
	 * @param host: the inner world's <code>SWActorWorldable</code> host
	 */
	public Transfer(MessageRenderer m, SWWorldBase inworld, SWWorldBase outworld, SWActorWorldable host) {
		super(m);
		this.inworld = inworld;
		this.outworld = outworld;
		this.host = host;
	}

	
	/**
	 *Returns the time taken to perform this <code>Exit</code> action.
	 *
	 *@return the duration of the <code>Exit</code> action. Currently hard coded to return 1
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	

	
	/**
	 * This is a wrapper for getDescription().
	 * 
	 * @author wen
	 * @return a String describing this <code>Exit</code>, suitable for display to the user
	 */
	@Override
	public String toString() {
		return getDescription();
	}



}

