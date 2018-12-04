package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWEntity;
import starwars.actions.GetMoney;
import starwars.actions.Thrown;

public class Money extends SWEntity{
	/**
	 * Constructor for the <code>Money</code> class. 
	 * 
	 * Initialize the message renderer for the <code>Money</code>
	 * Set the short description of this <code>Money</code> to "10 dollars"
	 * Set the long description of this <code>Money</code> to "Money that can be used for buying things in shop"
	 * Set the hit points of this <code>Money</code> to 50
	 * Add a <code>GetMoney</code> affordance to this <code>Money</code> so it can be picked up 
	 * Add a <code>Thrown</code> affordance to this <code>Money</code> so it can be thrown a grenade
	 * @author xutong
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.GetMoney}
	 * @see {@link starwars.actions.Thrown}
	 */
	public Money(MessageRenderer m, int hp) {
		super(m);
		// TODO Auto-generated constructor stub
		this.shortDescription = " 10 dollars";
		this.longDescription = "Money that can be used for buying things in shop ";
		this.hitpoints = hp;
		
		this.addAffordance(new GetMoney(this, m));
		this.addAffordance(new Thrown(this,m));
		
	}

	/**
	 * A symbol that is used to represent the Grid on SWWorld
	 * @author xutong
	 * @return 	Single Character string "$"
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	public String getSymbol() {
		return "$"; 
	}
	
	/**
	 * Method insists damage on this <code>Money</code> by reducing amount of <code>damage</code> in <code>hitpoints</code>
	 * <p>
	 * This <code>Money</code>s <code>will be removed from entity manager since its hit pints below 0 
	 * <p>
	 * 
	 * @author 	xutong
	 * @param 	damage the amount of <code>hitpoints</code> to be reduced
	 */
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		//remove from entity manager if hit points less or equal to 0
		if (this.hitpoints<=0) {
			SWAction.getEntitymanager().remove(this);
		}
	}

}
