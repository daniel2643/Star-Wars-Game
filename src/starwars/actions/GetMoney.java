package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Player;

public class GetMoney extends SWAffordance{
	/**
	 Constructor for the <code>GetMoney</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @author 	Xutong
	 * @param 	theTarget a <code>SWEntity</code> that is money
	 * @param   m the message renderer to display messages
	 */

	public GetMoney(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
		priority = 1;
	}
	/**
	 * Returns if or not this <code>GetMoney</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is human controlled, which means a is a player.
	 *  
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is a player, false otherwise
	 * @see		{@link starwars.SWActor#isHumanControlled()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		if (a.isHumanControlled()) {
			return true;
		}
		return false;
	}

	/**
	 * Perform the <code>GetMoney</code> action by adding 10 dollars in <code>SWActor<code>
	 * the target should be removed from affordance
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is human controlled.
	 * 
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> that is picking money
	 */
	@Override
	public void act(SWActor a) {
		Player actor = (Player) a;
		// TODO Auto-generated method stub
		if (target instanceof SWEntityInterface) {
			actor.addMoney(10);
			SWAction.getEntitymanager().remove(target);//remove the target from the entity manager since it's now held by the SWActor
			//remove the take affordance
			target.removeAffordance(this);
		}
		
	}
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author xutong
	 * @return String comprising "get 10 dollars "
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "get 10 dollars";
	}

}
