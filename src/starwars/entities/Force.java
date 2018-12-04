package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Absorb;
import starwars.actions.Thrown;

public class Force extends SWEntity{
	
	private int forcepoints;  //FP: can take by certain actors

	/**
	 * Constructor for the <code>Force</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Force</code></li>
	 * 	<li>Set the short description of this <code>Force</code> to "The Force"</li>
	 * 	<li>Set the long description of this <code>Force</code> to "A surge of purified Force"</li>
	 * 	<li>Set the hit points of this <code>Force</code> from input</li>
	 * 	<li>Add a <code>Absorb</code> affordance to this <code>Force</code> so it can be absorbed</li> 
	 *	<li>Add a <code>ABSORBABLE Capability</code> to this <code>Force</code> so it can be used to be<code>Absorb</code></li>
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.Take}
	 * @see {@link starwars.Capability}
	 */
	public Force(MessageRenderer m, int init_forcepoints) {
		super(m);
		
		this.shortDescription = "The Force";
		this.longDescription = "A surge of purified Force";
		this.forcepoints = init_forcepoints; // start with a surge of randomly fully purified force
		
		this.addAffordance(new Absorb(this, m));//add the Absorb affordance so that can be digested 
		this.addAffordance(new Thrown(this, m));

													//the force has capabilities 
		this.capabilities.add(Capability.ABSORBABLE);   // and ABSORBABLE so that it can be used to be absorbed
		
	}
	
	
	/**
	 * A symbol that is used to represent the Force on a text based user interface
	 * 
	 * @return 	Single Character string "∑"
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	public String getSymbol() {
		return "∑"; 
	}
	
	public int getForcepoints() {
		return forcepoints;
	}
	
	/**
	 * Add new force points for The Force
	 * @param new_forcepoints added force points
	 */
	public void addForcepoints(int new_forcepoints) {
		assert (new_forcepoints > 0): "Force Points cannot be negative!";
		this.forcepoints+=new_forcepoints;
	}
	
	/**
	 * Reduce new force points for The Force
	 * @param consumed_forcepoints exhausted force points reduced from the Droid
	 */
	public void reduceForcepoints(int consumed_forcepoints) {
		assert (consumed_forcepoints > 0): "Force Points cannot be negative!";
		this.forcepoints-=consumed_forcepoints;
	}
}
