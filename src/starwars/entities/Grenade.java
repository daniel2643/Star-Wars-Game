package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Take;

public class Grenade extends SWEntity{
	/**
	 * Constructor for the <code>Grenade</code> class. 
	 * 
	 * Initialize the message renderer for the <code>Grenade</code>
	 * Set the short description of this <code>Grenade</code> to "a Grenade"
	 * Set the long description of this <code>Grenade</code> to "a Grenade that can damage everything"
	 * Set the hit points of this <code>Grenade</code> to 40
	 * Add a <code>Take</code> affordance to this <code>Grenade</code> so it can be taken
	 * Add a <code>THROWABLE Capability</code> to this <code>Grenade</code> so it can be used to <code>Thrown</code>
	 * 
	 * @author xutong
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.Take}
	 * @see {@link starwars.Capability}
	 */
	public Grenade(MessageRenderer m, int hp) {
		super(m);
		// TODO Auto-generated constructor stub
		this.shortDescription = "a Grenade";
		this.longDescription = "a Grenade that can damage anything";
		this.hitpoints = hp;
		capabilities.add(Capability.THROWABLE);
		this.addAffordance(new Take(this, m));		
		
	}
	
	/**
	 * A symbol that is used to represent the Grid on SWWorld
	 * @author xutong
	 * @return 	Single Character string "G"
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	public String getSymbol() {
		return "G"; 
	}
	/**
	 * Method insists damage on this <code>Grenade</code> by reducing amount of <code>damage</code> in <code>hitpoints</code>
	 * <p>
	 * This method will also change this <code>Grenade</code>s <code>longDescription</code>
	 * and this <code>Grenade</code>s <code>shortDescription</code> if the <code>hitpoints</code> after taking the damage is 
	 * below 0
	 * and remove <code>THROWABLE</code> capability so that Grenade cannot be thrown to other entities anymore 
	 * <p>
	 * 
	 * @author 	xutong
	 * @param 	damage the amount of <code>hitpoints</code> to be reduced
	 */
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		
		if (this.hitpoints<=0) {
			this.shortDescription = "an empty Grenade";
			this.longDescription  = "An empty grenade that makes a pitiful \"ping\" sound when fired";
			//the grenade no longer can be used since its hit points no more greater than 0.
			this.capabilities.remove(Capability.THROWABLE);
		}
	}
}
