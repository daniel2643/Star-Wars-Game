package starwars.entities.actors;


import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorldBase;
import starwars.Team;
import starwars.entities.Force;
import starwars.swinterfaces.SWGridController;

/**
 * A very minimal <code>SWActor</code> that the user can control.  Its <code>act()</code> method
 * prompts the user to select a command.
 * 
 * @author ram
 */
/*
 * Change log
 * 2017/02/22	Schedule actions in the act method instead of tick. 
 * 				A controller used to get user input rather than the UI directly (Asel)
 */
public class Player extends SWActor {
	
	/**
	 * Constructor for the <code>Player</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Player</code></li>
	 * 	<li>Initialize the world for this <code>Player</code></li>
	 *  <li>Initialize the <code>Team</code> for this <code>Player</code></li>
	 * 	<li>Initialize the hit points for this <code>Player</code></li>
	 * 	<li>Set this <code>Player</code> as a human controlled <code>SWActor</code></li>
	 * </ul>
	 * 
	 * @param team the <code>Team</code> to which the this <code>Player</code> belongs to
	 * @param hitpoints the hit points of this <code>Player</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>Player</code> belongs to
	 * 
	 */
	/*
	 * Change log
	 * 2018-05-07: Added 'force' group-attributes for Player in the Constructor (wen)
	 * 2018-05-08: Added contents description of certain location in describeScene() method (wen)
	 */
	int money;
	public Player(Team team, boolean canUseForce, int hitpoints, MessageRenderer m, SWWorldBase world, int init_forcepoints) {
		super(team, canUseForce, hitpoints, m, world);
		humanControlled = true; // this feels like a hack. Surely this should be dynamic
		money = 0;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void addMoney(int dollar) {
		money += dollar;
	}
	
	public void decMoney(int dollar) {
		money -= dollar;
	}
	
	/**
	 * This method will describe this <code>Player</code>'s scene and prompt for user input through the controller 
	 * to schedule the command.
	 * <p>
	 * This method will only be called if this <code>Player</code> is alive and is not waiting.
	 * 
	 * @see {@link #describeScene()}
	 * @see {@link starwars.swinterfaces.SWGridController}
	 */
	@Override
	public void act() {	
		describeScene();
		scheduler.schedule(SWGridController.getUserDecision(this), this, 1);
		
	}
	/**
	 * This method will describe, 
	 * <ul>
	 * 	<li>the this <code>Player</code>'s location</li>
	 * 	<li>items carried (if this <code>Player</code> is carrying any)</li>
	 * 	<li>the contents of this <code>Player</code> location (what this <code>Player</code> can see) other than itself</li>
	 * <ul>
	 * <p>
	 * The output from this method would be through the <code>MessageRenderer</code>.
	 * 
	 *  @see {@link edu.monash.fit2099.simulator.userInterface.MessageRenderer}
	 */
	public void describeScene() {
		//get the location of the player and describe it
		SWLocation location = this.world.getEntityManager().whereIs(this);
		say(this.getShortDescription() + " [" + this.getHitpoints() + "] ∑[" + this.getForcepoints() + "] is at " + location.getShortDescription());
		
		//get the items carried for the player
		SWEntityInterface itemCarried = this.getItemCarried();
		if (itemCarried != null) {
			//and describe the item carried if the player is actually carrying an item
			say(this.getShortDescription() 
					+ " is holding " + itemCarried.getShortDescription() + " [" + itemCarried.getHitpoints() + "]");
		}
		
		//get the contents of the location
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		
		// get the money the player has
		System.out.println("Luke has money: "+money);
		
		//and describe the contents
		if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report
			say(this.getShortDescription() + " can see:");
			for (SWEntityInterface entity : contents) {
				if (entity != this) { // don't include self in scene description
					if (entity instanceof Force) {
						say("\t " + entity.getSymbol() + " - " + entity.getLongDescription() + " ∑[" + ((Force) entity).getForcepoints() + "]");
					}
					else if (entity instanceof SWActor && ((SWActor) entity).getCanUseForce()) {
						say("\t " + entity.getSymbol() + " - " + entity.getLongDescription() + " [" + entity.getHitpoints() + "]" + " ∑[" + ((SWActor) entity).getForcepoints() + "]");
					}
					else {
						say("\t " + entity.getSymbol() + " - " + entity.getLongDescription() + " [" + entity.getHitpoints() + "]");
					}
				}
			}
		}
	}
}
