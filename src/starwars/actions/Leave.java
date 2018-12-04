package starwars.actions;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

/*
 * Change log
 * 2018-05-08: Create the Leave affordance
 * 2018-05-11: Since the Take and Leave are mutually exclusive(i.e. one available without the other),
 *             Remove Take affordance after act Leave affordance.
 *             (And in Take Class, remove Leave afforance after act Take affordance.) (xutong)
 */


public class Leave extends SWAffordance{

	/**
	 Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @author 	Xutong
	 * @param 	theTarget a <code>SWEntity</code> that is taken
	 * @param   m the message renderer to display messages
	 */

	public Leave(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
		priority = 1;
	}

	/**
	 * Returns if or not this <code>Leave</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying any item already.
	 *  
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is already taking any item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return a.getItemCarried()!=null;
	}

	/**
	 * Perform the <code>Leave</code> action by setting the item carried to null by the <code>SWActor</code> to the target
	 * the action <code>Take</code> would be added back to affordance
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> that is leaving the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		if (target instanceof SWEntityInterface) {			
			a.setItemCarried(null);
            //set the symbol of holdTarget to the player's location
			SWAction.getEntitymanager().setLocation((SWEntityInterface)target, SWAction.getEntitymanager().whereIs(a));

			//remove the leave affordance
			target.removeAffordance(this);
			
			//add the take affordance
			target.addAffordance(new Take((SWEntityInterface) target, this.messageRenderer));

		}
		
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author xutong
	 * @return String comprising "leave " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "leave "+ target.getShortDescription();
	}

}