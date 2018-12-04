package starwars.entities.actors;

import java.util.ArrayList;



import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLegend;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.SWWorldBase;
import starwars.Team;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;

public class Droid extends SWActor{


	private String name;
	private SWActor owner;
	private boolean isOwned; 
	private Direction previousHeading;
	
	// Constructors
	/**
	 * Create a Droid.  Droids will randomly choose a direction and wander
	 * around the playfield until it meets edges. and attack anything they can (if they can attack
	 * something, they will).  They 
	 * are members of team GOOD or EVIL, so their attempts to attack
	 * other same team Droids won't be effective but attack different team Droids will be effective.
	 * 
	 * @param team
	 * 			  the team that the Droid belongs to. 
	 * 			  Generally, if a Droid has a owner, 
	 * 			  the team of the Droid is the same as its owner.
	 * @param hitpoints
	 *            the number of hit points of this Droid. If this
	 *            decreases to below zero, the Droid become immobile.
	 * @param name
	 *            this droid's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Droid</code> belongs to
	 * 
	 */
	public Droid(Team team, int hitpoints, String name, MessageRenderer m, SWWorldBase world) {
		super(team, false, hitpoints, m, world);
		this.name = name;
		this.owner = null;
		this.isOwned = false;
		this.previousHeading = null;
	}
	
	/**
	 * Second Constructor if the initialization had allocated the owner.
	 */
	public Droid(Team team, int hitpoints, String name, MessageRenderer m, SWWorldBase world, SWActor owner) {
		super(team, false, hitpoints, m, world);
		this.name = name;
		this.owner = owner;
		this.isOwned = true;
		
	}


	// Accessors
	public String getName() {
		return this.name;
	}
	
	/**
	 * This method detect whether the Droid has a owner.
	 * @return True if the Driod has the owner
	 */
	public boolean isOwned() {
		return this.isOwned;
	}
	
	//Mutators
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setOwner(SWActor a) {
		if (a instanceof SWActor) {
			this.owner = a;
		}
		else if (a instanceof SWLegend){
			this.owner = (SWLegend)a;
		}
		this.isOwned = true;
	}
	
	/**
	 * This method will make Droid independent, which means it will not has the owner.
	 */
	public void kickOwner() {
		if (isOwned()) {
			this.owner = null;
			this.isOwned = false;
		}
		else {
			return;
		}
	}
	
	
	// toString Methods
	@Override
	public String getShortDescription() {
		return name + " the Droid";
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	
	/**
	 * This method implement Droid's affordances in certain order. 
	 * (health lost --> Attack --> Move) 
	 * @author wen 
	 */
	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());
		
		
		SWLocation droidLocation = this.world.getEntityManager().whereIs(this);
		SWLocation ownerLocation = this.world.getEntityManager().whereIs(owner);
		// Lose health if in Badlands
		if (droidLocation.getSymbol() == 'b') {
			this.takeDamage(this.getHitpoints()/100);
		}
		
		// Attack Action
		AttackInformation attack = AttackNeighbours.attackLocals(this, this.world, true, true);
		if (attack != null) {
			say(getShortDescription() + " has attacked" + attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, this, 1);
		}
		// Move Action
		else if (isOwned) {

			if (droidLocation != ownerLocation) {
				
				Direction heading = null;
				// owner is in a neighbouring location
				for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
					if (droidLocation.getNeighbour(d) == ownerLocation) {
						heading = d;
						break;
					}
				}
				
				// owner is in outsource (out of neibouring location)
				if (heading == null) {
					if ((this.previousHeading == null) || (this.previousHeading != null && !SWWorld.getEntitymanager().seesExit(this, previousHeading)) ) {
						ArrayList<Direction> possibledirections = new ArrayList<Direction>();

						// build a list of available directions
						for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
							if (SWWorld.getEntitymanager().seesExit(this, d)) {
								possibledirections.add(d);
							}
						}

						heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
						previousHeading = heading; //+++++

					}
					else if (this.previousHeading != null && SWWorld.getEntitymanager().seesExit(this, previousHeading)) {
						heading = previousHeading;
					}
				}


				
				// Display and Move in the grid
				say(getShortDescription() + "is heading " + heading + " next.");
				Move myMove = new Move(heading, messageRenderer, world);
	
				scheduler.schedule(myMove, this, 1);
			}


		}
	}
}

