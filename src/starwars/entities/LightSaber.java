package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Take;
import starwars.actions.Thrown;

/**
 * An extremely powerful, indestructible weapon that implements the Weapon capability
 * 
 * It does not take damage with use.
 * 
 * This class does not implement all of the abilities shown in the films; there is
 * no capacity for using it to play cover drives with Blaster bolts, or parry 
 * other lightsabers; there are also no restrictions on who wields it.
 * 
 *  @author Robert
 *  @see {@link starwars.actions.Attack}
 */
/*
 * Change log
 * 2018-05-07: Set Lightsaber as conditional weapon, which only allows specific groups to use. (wen)
 * 2018-05-13: Added method 'setAsNonWeapon' to disable LightSaber as a weapon when the SWActor's
 *             forcepoints decreased below minimal level (wen)
 * 2018-05-13: use a constant for maginal level forcepoints to wield the LightSaber as a WEAPON (wen)
 */

public class LightSaber extends SWEntity {
	
	public static final int MARGIN_LEVEL = 30;
	/**
	 * Constructor for the <code>LightSaber</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>LightSaber</code></li>
	 * 	<li>Set the short description of this <code>LightSaber</code>>
	 * 	<li>Set the long description of this <code>LightSaber</code> 
	 * 	<li>Add a <code>Take</code> affordance to this <code>LightSaber</code> so it can be taken</li> 
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.Take}
	 * @see {@link starwars.SWActor}
	 * @see {@link starwars.Capability}
	 */
	public LightSaber(MessageRenderer m) {
		super(m);
		
		this.shortDescription = "A Lightsaber";
		this.longDescription = "A lightsaber.  Bzzz-whoosh!";
		this.hitpoints = 100000; // start with a nice powerful, sharp axe
		
		this.addAffordance(new Take(this, m));//add the take affordance so that the LightSaber can be taken by SWActors
		this.addAffordance(new Thrown(this,m));
		
		
	}
	
	/**
	 * Set the LightSaber as a WEAPON that can be used by its owner.
	 */
	public void setAsWeapon() {
		this.capabilities.add(Capability.WEAPON);// it's a weapon.  
	}
	
	/**
	 * Remove the LightSaber's Capability as a WEAPON that cannot be used by its owner.
	 */
	public void setAsNonWeapon() {
		this.capabilities.remove(Capability.WEAPON);// it's not a weapon anymore.  
	}
	
	
	/**
	 * Lightsabers are indestructible, so doing damage to them has no effect
	 * @param damage - the amount of damage that would be inflicted on a non-mystical Entity
	 */
	@Override
	public void takeDamage(int damage) {
		
		return;
	}
	
	/**
	 * A symbol that is used to represent the LightSaber on a text based user interface
	 * 
	 * @return 	A String containing a single character.
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return "†";
	}
	
	

}
