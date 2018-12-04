package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * A new Class that attached to SWActor that has <code>SWInnerWorld</code>.
 * and to reduce workload of SWActor and also make classification more explicit.
 * @author wen
 *
 */
public abstract class SWActorWorldable extends SWActor {

	//Fields
	/**If or not this <code>SWActor</code> has innerworld*/
	private boolean hasInnerworld = false;
	
	/**The inner world of the <code>Sandcrawler</code>*/
	protected SWInnerWorld innerworld;

	

	
	
	public SWActorWorldable(Team team, boolean canUseForce, int hitpoints, MessageRenderer m, SWWorldBase world) {
		super(team, canUseForce, hitpoints, m, world);
	}
	

	// Accessors
	/**
	 * Give a boolean value to show whether the SWActor has a inner world or not
	 * @return True if the SWActor has SWInnerWorld. False if cannot.
	 */
	public boolean isHasInnerworld() {
		return hasInnerworld;
	}
	
	/**
	 * getter for returning the reference of <code>Sandcrawler</code> inner world
	 */
	public SWInnerWorld getInnerworld() {
		return innerworld;
	}
	
	
	//Mutators
	public void setHasInnerworld(boolean hasInnerworld) {
		this.hasInnerworld = hasInnerworld;
	}


}
