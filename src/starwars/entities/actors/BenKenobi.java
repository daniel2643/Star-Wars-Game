package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;



import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLegend;
import starwars.SWWorld;
import starwars.SWWorldBase;
import starwars.Team;
import starwars.actions.Move;
import starwars.actions.Train;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.Patrol;

/**
 * Ben (aka Obe-Wan) Kenobi.  

 * 
 * At this stage, he's an extremely strong critter with a <code>Lightsaber</code>
 * who wanders around in a fixed pattern and neatly slices any Actor not on his
 * team with his lightsaber.
 * add affordance <code>Train<code> to training some people
 * 
 * Note that you can only create ONE Ben, like all SWLegends.
 * @author rober_000
 */
/*
 * Change log
 * 2018-05-07: In the Constructor, add parameter: (initialized forcepoints) for Ben.(wen)
 * 2018-05-11: In the Constructor, add Train affordance for Ben.(xutong)
 */
public class BenKenobi extends SWLegend {

	private static BenKenobi ben = null; // yes, it is OK to return the static instance!
	private Patrol path;
	
	/**
	 * 	Constructor for the <code>Ben</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Ben</code></li>
	 * 	<li>Initialize the world for this <code>Player</code></li>
	 *  <li>Initialize the <code>moves</code> for <code>Ben</code></li>
	 * 	<li>Initialize the force points for this <code>Ben</code></li>
	 * </ul>
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>Player</code> belongs to
	 * @param moves a Direction type array that the moving path that is available to move.
	 * @param init_forcepoints the initial force points for Ben
	 */
	private BenKenobi(MessageRenderer m, SWWorldBase world, Direction [] moves, int init_forcepoints) {
		super(Team.GOOD, true, 1000, m, world);
		path = new Patrol(moves);
		this.setShortDescription("Ben Kenobi");
		this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
		LightSaber bensweapon = new LightSaber(m);
		setItemCarried(bensweapon);
		this.addForcepoints(init_forcepoints);
		
		this.addAffordance(new Train(this,m));
	}
	
	/**
	 * Singleton to make sure to instantiate ONLY ONE Ben in the game.
	 */
	public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction [] moves, int init_forcepoints) {
		ben = new BenKenobi(m, world, moves, init_forcepoints);
		ben.activate();
		return ben;
	}
	
	/**
	 * As a SWLegend, this method shows Ben's all affordances in certain order
	 */
	@Override
	protected void legendAct() {

		if(isDead()) {
			return;
		}
		
		//Ben Attack Affordance
		AttackInformation attack;
		attack = AttackNeighbours.attackLocals(ben,  ben.world, true, true);
		
		if (attack != null) {
			say(getShortDescription() + " suddenly looks sprightly and attacks " +
		attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, ben, 1);
		}
		//Ben Move Affordance
		else {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}
	

}
