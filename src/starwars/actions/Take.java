package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.LightSaber;
/**
 * <code>SWAction</code> that lets a <code>SWActor</code> pick up an object.
 * 
 * @author ram
 */
/*
 * Changelog
 * 2017/01/26	- candDo method changed. An actor can only take if it's not holding any items already.
 * 				- act method modified. Take affordance removed from the item picked up, since an item picked up
 * 				  cannot be taken. This is just a safe guard.
 * 				- canDo method changed to return true only if the actor is not carrying an item (asel)
 * 2018/05/07	- change conditional setItemCarried way for act method (wen)
 * 2018/05/11	- add Leave afforance after act Take affordance (xutong)
 
 */
public class Take extends SWAffordance {

	/**
	 * Constructor for the <code>Take</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being taken
	 * @param m the message renderer to display messages
	 */
	public Take(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		
	}


	/**
	 * Returns if or not this <code>Take</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is not carrying any item already.
	 *  
	 * @author 	ram
	 * @author 	Asel (26/01/2017)
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can take this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried()==null;
	}

	/**
	 * Perform the <code>Take</code> action by setting the item carried by the <code>SWActor</code> to the target (
	 * the <code>SWActor a</code>'s item carried would be the target of this <code>Take</code>).
	 * the action <code>Leave</code> would be added back to affordance
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	ram 
	 * @author 	Asel (26/01/2017)
	 * @author  Xutong (11/5/2018)
	 * @param 	a the <code>SWActor</code> that is taking the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof SWEntityInterface) {
			SWEntityInterface theItem = (SWEntityInterface) target;
			if (target instanceof LightSaber && a.getCanUseForce() && a.getForcepoints() >= LightSaber.MARGIN_LEVEL) {
				LightSaber lightsaber = (LightSaber) theItem;
				lightsaber.setAsWeapon();
				a.setItemCarried(lightsaber);
			} else {
				a.setItemCarried(theItem);
			}
			SWAction.getEntitymanager().remove(target);//remove the target from the entity manager since it's now held by the SWActor
			
			//remove the take affordance
			target.removeAffordance(this);
			
			//add the leave affordance
			target.addAffordance(new Leave((SWEntityInterface) target, this.messageRenderer));
			
			

		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author ram
	 * @return String comprising "take " and the short description of the target of this <code>Take</code>
	 */
	@Override
	public String getDescription() {
		return "take " + target.getShortDescription();
	}

}
