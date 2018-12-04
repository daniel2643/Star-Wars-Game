package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.Fillable;

/**
 * An Affordance for dipping things into large bodies of liquid.
 * 
 * The affordance is offered by the large bodies of liquid and can only be
 * applied in partnership with an <code>Entity</code> that is  <code>Fillable</code>
 * and implements the <code>Fill</code> interface.
 * 
 * @author Robert Merkel
 * @see {@link starwars.actions.Fill}
 * @see {@link starwars.entities.Fillable}
 */
public class Dip extends SWAffordance implements SWActionInterface {

	public Dip(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isMoveCommand() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns if or not this <code>Dip</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying any item with Fillable capacity and target's hitpoints greater than zero.
	 *  
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is already taking any item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		if (item!= null && this.getTarget().getHitpoints()>0) {
			return item.hasCapability(Capability.FILLABLE);
		}
		return false;
	}
	
	/**
	 * Perform the <code>Dip</code> action and damage target 10 points of hit points 
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> carries item with FILLABLE capacity.
	 * 
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> that dips water
	 */

	@Override
	public void act(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		assert(item instanceof Fillable);

		for(Affordance aff: item.getAffordances()) {
			if (aff instanceof Fill) {
				aff.execute(a);
			}
		}
		
		//find the target and decrease the hit points of that target(reservoir) 
		SWEntityInterface target = this.getTarget();
		target.takeDamage(10);
		
		
		a.say(item.getShortDescription() + "has been refilled to capacity");
	}
	
	@Override
	public String getDescription() {
		return "dip carried item in" + target.getShortDescription();
	}
}
