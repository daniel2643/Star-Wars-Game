package starwars.entities.actors;




import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActorWorldable;
import starwars.SWInnerWorld;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.CatchNeighbours;
import starwars.entities.actors.behaviors.Patrol;

/**
 * A vehicle called 'sandcrawler' that can carry droids and SWActor who can use 'The Force'.
 * @author wen
 *
 */
/*
 * Change log:
 * 2018-05-25: implements Worldable interface (wen)
 * 2018-05-26: cancel Worldable interface implementation. Instead, using a field represent the inner world (wen)
 * 2018-05-27: cut two size constant variables to make pass from user (wen)
 * 2018-05-27: create an intermediate SWActorWorlsable Class to inherit and 
 * 			   move innerworld attributes to parent class (wen)
 * 
 */
public class Sandcrawler extends SWActorWorldable {
	
	// Fields
	
	/**The name of the sandcrawler*/
	private String name;
	
	/**The patrol path of the sandcrawler*/
	private Patrol path;
	
	/**The number of times that act() method executed*/
	private int actCounter;
	



	
	
	// Constructor
	public Sandcrawler(Team team, int hitpoints, String name, MessageRenderer m, SWWorld world, Direction [] moves, int width, int height) {
		super(team, false, hitpoints, m, world);
		
		this.name = name;
		path = new Patrol(moves);
		actCounter = 0;
		
		innerworld = new SWInnerWorld(width, height);
		this.setHasInnerworld(true);
		
	}
	
	// Accessors
	public String getName() {
		return this.name;
	}


	
	// Mutators
	
	/**
	 * Sandcrawlers are indestructible, so doing damage to them has no effect
	 * @param damage - the amount of damage that would be inflicted on a non-mystical Entity
	 */
	@Override
	public void takeDamage(int damage) {
		return;
	}
	
	
	
	public void setSWActor() {
		
	}
	
	// toString Methods
	@Override
	public String getShortDescription() {
		return name + " the Sandcrawler";
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = (SWLocation)this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	
	/**
	 * This method implement Sandcrawler's affordances in certain order. 
	 * (Patrol) 
	 * @author wen 
	 */
	@Override
	public void act() {
		
		actCounter++;
		if (isDead()) {
			return;
		}
		say(describeLocation());
		
		// SandCrawler Patrol
		if (actCounter%2 == 0) {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
		
		// Sandcrawler Catching Droid and SWActor
		CatchNeighbours.catchLocals(this, messageRenderer);
		
		// Sandcrawler Releasing SWActor
		CatchNeighbours.releaseInnerActor(this, messageRenderer);
		

		
		
	}

}
	







