package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.actions.Dip;
import starwars.actions.Thrown;

/**
 * Class to represent a water reservoir.  <code>Reservoirs</code> are currently pretty passive.
 * They can be dipped into to fill fillable entities (such as <code>Canteens</code>.  They
 * are assumed to have infinite capacity.
 * 
 * @author 	ram
 * @see 	{@link starwars.entities.Canteen}
 * @see {@link starwars.entites.Fillable}
 * @see {@link starwars.actions.Fill} 
 */
public class Reservoir extends SWEntity {

	/**
	 * Constructor for the <code>Reservoir</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Reservoir</code></li>
	 * 	<li>Set the short description of this <code>Reservoir</code> to "a water reservoir</li>
	 * 	<li>Set the long description of this <code>Reservoir</code> to "a water reservoir..."</li>
	 * 	<li>Add a <code>Dip</code> affordance to this <code>Reservoir</code> so it can be taken</li>
	 *  <li>Add a <code>Thrown</code> affordance to this <code>Reservoir</code> so it can be thrown a grenade</li>
	 *	<li>Set the symbol of this <code>Reservoir</code> to "T"</li>
	 * </ul>
	 * set hit points to 40
	 * 
	 * @param 	m <code>MessageRenderer</code> to display messages.
	 * @see 	{@link starwars.actions.Dip} 
	 */
	public Reservoir(MessageRenderer m, int hp) {
		super(m);
		this.hitpoints = hp;
		
		this.setLongDescription("a water reservoir.");
		this.setShortDescription("a water reservoir, full of cool, clear, refreshing water");
		this.setSymbol("W");
		
		SWAffordance dip = new Dip(this, m);
		this.addAffordance(dip);
		this.addAffordance(new Thrown(this,m));
		
	}
	
	/**
	 * Method insists damage on this <code>Reservoir</code> by reducing amount of <code>damage</code> from <code>hitpoints</code>
	 * <p>
	 * This method will also change this <code>Reservoir</code>s <code>longDescription</code>
	 * and this <code>Reservoir</code>s <code>shortDescription</code> if the <code>hitpoints</code> after taking the damage is 
	 * less than 20 or less than zero
	 * <p>
	 * 
	 * @author 	xutong
	 * @param 	damage the amount of <code>hitpoints</code> to be reduced
	 * @see 	{@link starwars.actions.Dip}
	 */
	
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		//updating short and long description when hitpoints in different range
		if (this.hitpoints > 20) {
			this.setLongDescription("a water reservoir.");
			this.setShortDescription("a water reservoir, full of cool, clear, refreshing water");
			this.setSymbol("W");
		}
		else if (this.hitpoints > 0 && this.hitpoints <= 20) {
			this.setLongDescription("a damaged water reservoir");
			this.setShortDescription("a damaged water reservoir, leaking slowly");
			this.setSymbol("V");
		}
		else {
			this.setLongDescription("the wreckage of a water reservoir");
			this.setShortDescription("the wreckage of a water reservoir, surrounded by slightly damp soil");
			this.setSymbol("X");
		}
	}

	@Override 
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}

}
