package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.actions.Buy;
import starwars.actions.Thrown;

public class Shop extends SWEntity{
	/**
	 * Constructor for the <code>Shop</code> class. 
	 * 
	 * Initialize the message renderer for the <code>Shop</code>
	 * Set the short description of this <code>Shop</code> to "a shop"
	 * Set the long description of this <code>Shop</code> to "a Shop that sells magical things"
	 * Set the hit points of this <code>Shop</code> to 70
	 * Add a <code>Buy</code> affordance to this <code>Shop</code> so actor can by things here
	 * Add a <code>Thrown</code> affordance to this <code>Shop</code> so it can be thrown a grenade
	 * 
	 * @author xutong
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.GetMoney}
	 * @see {@link starwars.actions.Thrown}
	 */
	public Shop(MessageRenderer m, int hp) {
		super(m);
		// TODO Auto-generated constructor stub
		this.shortDescription = "a Shop";
		this.longDescription = "a Shop that sells magical things";
		this.hitpoints = hp;
		
		this.addAffordance(new Buy(this,m));
		this.addAffordance(new Thrown(this,m));
		
	}
	
	/**
	 * A symbol that is used to represent the Grid on SWWorld
	 * @author xutong
	 * @return 	Single Character string "S"
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	public String getSymbol() {
		return "S"; 
	}

	/**
	 * Method insists damage on this <code>Shop</code> by reducing amount of <code>damage</code> in <code>hitpoints</code>
	 * <p>
	 * This method will also change this <code>Shop</code>s <code>longDescription</code>
	 * and this <code>Shop</code>s <code>shortDescription</code> if the <code>hitpoints</code> after taking the damage is 
	 * below 0
	 * <p>
	 * 
	 * @author 	xutong
	 * @param 	damage the amount of <code>hitpoints</code> to be reduced
	 */
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		
		if (this.hitpoints<=0) {
			this.shortDescription = "an empty Shop";
			this.longDescription  = "The shop was closed in a war";
		}
	}

}
