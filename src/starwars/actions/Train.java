package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class Train extends SWAffordance{
	
	/**
	 * Constructor for the <code>Train</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * @author 	Xutong
	 * @param theTarget a <code>SWEntity</code> that is providing training 
	 * @param m the message renderer to display messages
	 */
	public Train(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
		priority = 1;
	}
	
	/**
	 * Returns if or not this <code>Train</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> has force ability.
	 *  
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> can use force, false otherwise
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		if (a.getCanUseForce()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Perform the <code>Train</code> action increasing the <code>SWActor</code>'s force
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> that is trained
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		a.addForcepoints((int)(Math.random()*10+20));
		
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author xutong
	 * @return String comprising "get training by " and the short description of the target of this <code>Train</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "get training by " + target.getShortDescription();
	}

}
