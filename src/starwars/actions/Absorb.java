package starwars.actions;



import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Force;
import starwars.entities.LightSaber;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> absorb a surge of power/object.
 * 
 * @author wen
 */


public class Absorb extends SWAffordance{
	/**
	 * Constructor for the <code>Absorb</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being Absorbed
	 * @param m the message renderer to display messages
	 */
	public Absorb(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}


	/**
	 * Returns if or not this <code>Absorb</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is not carrying any item already.
	 *  
	 * @author 	wen
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can absorb this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		if (a.getCanUseForce()) {
			return true;
		}
		else {
			return false;
		}
		//need to modify when ForceOwner created.
	}

	/**
	 * Perform the <code>Absorb</code> action by setting the item carried by the <code>SWActor</code> to the target (
	 * the <code>SWActor a</code>'s item carried would be the target of this <code>Absorb</code>).
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	wen
	 * @param 	a the <code>SWActor</code> that is absorbing the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	@Override
	public void act(SWActor a) {
		SWEntityInterface theTarget = this.getTarget();
		if (theTarget.hasCapability(Capability.ABSORBABLE) && canDo(a)) {
			if (theTarget instanceof Force) {
				Force force = (Force) theTarget;
				a.addForcepoints(force.getForcepoints());
				SWAction.getEntitymanager().remove(target);//remove the target from the entity manager since it's now absorbed by the SWActor
			}
			//TODO ELSE: Other type of entity can be absorbed.
		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author wen
	 * @return String comprising "absorb " and the short description of the target of this <code>Absorb</code>
	 */
	@Override
	public String getDescription() {
		return "absorb " + target.getShortDescription();
	}
}
