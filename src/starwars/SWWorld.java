package starwars;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Take;
import starwars.entities.*;
import starwars.entities.actors.*;
import starwars.swinterfaces.SWGridController;

/**
 * Class representing a world in the Star Wars universe. 
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02:  Render method was removed from Middle Earth
 * 				Displaying the Grid is now handled by the TextInterface rather 
 * 				than by the Grid or MiddleWorld classes (asel)
 * 2018-05-07:  Adjust the order of instantiating every entities and locations.
 *              Add C-3PO and The Force instantiation (wen)
 * 2018-05-19:  Follow ReD Principle: Made the size of the SWGrid map as a constant to reduce dependency (wen)
 * 2018-05-19:  Code Smell: Long Method --> Refactoring: Extract Method (wen)
 * 				Extract codes from long method InitializeWorld, and make 3 new methods called by it.
 * 2018-05-20:  Code Smell: Feature Envy --> Refactoring: Extract Method & Inline Method (wen)
 * 				Extract codes of creating reservoirs from Initialize moisture farm and inline to Initialze Reservoirs (wen)
 * 2018-05-21:  Added trigger for setting up Sandcrawler inner area (wen)
 * 2018-05-26:  Created an intermediate SWWorldable Class to inherit. Moved some base methods to parent class (wen)
 */
public class SWWorld extends SWWorldBase {
	

	/**The grid map's size*/
	private final int GRID_MAPSIZE = 10;
	
	/**
	 * Constructor of <code>SWWorld</code>. This will initialize the <code>SWLocationMaker</code>
	 * and the grid.
	 */
	public SWWorld() {
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		myGrid = new SWGrid(factory, GRID_MAPSIZE);
		space = myGrid;
		
	}


	
	/**
	 * Set up the world, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 */
	public void initializeWorld(MessageRenderer iface) {
		
		
		// Initialize SWLocation
		initializeLocation();
		

		// Initialize SWActor 
		initializeSWActor(iface);
		
		
		//Initialize SWEntity 
		initializeSWEntity(iface);

	}
	
	/**
	 * Setting descriptions for locations on the grid.
	 * 
	 * @author 	wen
	 */
	private void initializeLocation() {
		
		SWLocation loc;
		
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		
		// BadLands
		for (int row = 5; row < 8; row++) {
			for (int col = 4; col < 7; col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Badlands (" + col + ", " + row + ")");
				loc.setShortDescription("Badlands (" + col + ", " + row + ")");
				loc.setSymbol('b');
			}
		}
		
		//Ben's Hut
		loc = myGrid.getLocationByCoordinates(5, 6);
		loc.setLongDescription("Ben's Hut");
		loc.setShortDescription("Ben's Hut");
		loc.setSymbol('H');
		/*
		 * Scatter some other entities and actors around
		 */
		
		
		// Beggar's Canyon 
		for (int col = 3; col < 8; col++) {
			loc = myGrid.getLocationByCoordinates(col, 8);
			loc.setShortDescription("Beggar's Canyon (" + col + ", " + 8 + ")");
			loc.setLongDescription("Beggar's Canyon  (" + col + ", " + 8 + ")");
			loc.setSymbol('C');
			loc.setEmptySymbol('='); // to represent sides of the canyon
		}
		
		// Moisture Farms
		for (int row = 0; row < GRID_MAPSIZE; row++) {
			for (int col = GRID_MAPSIZE-2; col < GRID_MAPSIZE; col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setShortDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setSymbol('F');
						
			}
		}

	}
	
	
	/**
	 * Setting descriptions for placing actors on the grid.
	 * 
	 * @author 	wen
	 * @param 	iface a MessageRenderer to be passed onto newly-created SWActors
	 */
	private void initializeSWActor(MessageRenderer iface) {
		
		SWLocation loc;

		// Ben Kenobi
		Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.SOUTH,
                CompassBearing.WEST, CompassBearing.WEST,
                CompassBearing.SOUTH,
                CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.NORTHWEST, CompassBearing.NORTHWEST};
		
		
		BenKenobi ben = BenKenobi.getBenKenobi(iface, this, patrolmoves, 500);
		ben.setSymbol("B");
		loc = myGrid.getLocationByCoordinates(4,  5);
		entityManager.setLocation(ben, loc);
		
		
		

		
		// A Tusken Raider
		TuskenRaider tim = new TuskenRaider(350, "Tim", iface, this);
		tim.setSymbol("∞");
		loc = myGrid.getLocationByCoordinates(4,3);
		entityManager.setLocation(tim, loc);
		

		
		// A Sandcrawler 
		Sandcrawler j = new Sandcrawler(Team.JAWAS, 666666, "J", iface, this, patrolmoves, 5, 3);
		j.setSymbol("•");
		loc = myGrid.getLocationByCoordinates(7, 7);
		entityManager.setLocation(j, loc);
			//set up inner world
			setInnerWorld(j);
		
		// Luke 
		Player luke = new Player(Team.GOOD, true, 100, iface, this, 0);
		luke.setShortDescription("Luke");
		loc = myGrid.getLocationByCoordinates(5,9);
		entityManager.setLocation(luke, loc);
		luke.resetMoveCommands(loc);
		
		// A Droid - C-3PO
		Droid C_3PO = new Droid(Team.GOOD, 200, "C-3PO", iface, this);
		C_3PO.setSymbol("C-3PO");
		loc  = myGrid.getLocationByCoordinates(7, 7);
		entityManager.setLocation(C_3PO, loc);
		C_3PO.setOwner(luke);

		

		

		
		
		

	}
	
	private void setInnerWorld(Sandcrawler s) {
		
		//Grid controller controls the data and commands between the UI and the model
		SWGridController sandcrawlerController = new SWGridController(s.getInnerworld());
		
		// set up the world
		s.getInnerworld().initializeInnerWorld(sandcrawlerController);
		
	}
	
	
	/**
	 * Setting descriptions for placing items on the grid.
	 * 
	 * @author 	wen, xutong
	 * @param 	iface a MessageRenderer to be passed onto newly-created SWEntities
	 */
	private void initializeSWEntity(MessageRenderer iface) {
		SWLocation loc;
		
		// a lightsaber
		LightSaber lightSaber = new LightSaber(iface);
		loc = myGrid.getLocationByCoordinates(5,5);
		entityManager.setLocation(lightSaber, loc);
		
		// A blaster 
		Blaster blaster = new Blaster(iface);
		loc = myGrid.getLocationByCoordinates(3, 4);
		entityManager.setLocation(blaster, loc);
		
		// a canteen
		loc = myGrid.getLocationByCoordinates(3,1);
		SWEntity canteen = new Canteen(iface, 10,0);
		canteen.setSymbol("©");
		canteen.setHitpoints(500);
		entityManager.setLocation(canteen, loc);
		canteen.addAffordance(new Take(canteen, iface));
		
		// reserviors
		for (int row = 0; row < GRID_MAPSIZE; row++) {
			for (int col = GRID_MAPSIZE-2; col < GRID_MAPSIZE; col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				// moisture farms have reservoirs
				entityManager.setLocation(new Reservoir(iface,40), loc);				
			}
		}

		// an oil can treasure
		loc = myGrid.getLocationByCoordinates(1,5);
		SWEntity oilcan = new SWEntity(iface);
		oilcan.setShortDescription("an oil can");
		oilcan.setLongDescription("an oil can, which would theoretically be useful for fixing robots");
		oilcan.setSymbol("o");
		oilcan.setHitpoints(100);
			// add a Take affordance to the oil can, so that an actor can take it
		entityManager.setLocation(oilcan, loc);
		oilcan.addAffordance(new Take(oilcan, iface));
		
		// The Force
		for (int i=0; i<(int)(Math.random()*100); i++) {
			Force force = new Force(iface, (int)(Math.random()*10));
			loc = myGrid.getLocationByCoordinates((int)(Math.random()*10), (int)(Math.random()*10));
			entityManager.setLocation(force, loc);
		}
		
		// a random number of grenades
		for (int i=0; i<(int)(Math.random()*3+2); i++) {
			Grenade g = new Grenade(iface,40);
			loc = myGrid.getLocationByCoordinates((int)(Math.random()*10), (int)(Math.random()*10));
			entityManager.setLocation(g, loc);
		}
		
		// a random number of money
		for (int i=0; i<(int)(Math.random()*5+3); i++) {
			Money d = new Money(iface,50);
			loc = myGrid.getLocationByCoordinates((int)(Math.random()*10), (int)(Math.random()*10));
			entityManager.setLocation(d, loc);
		}
		
		// The shop
		Shop s = new Shop(iface, 70);
		loc = myGrid.getLocationByCoordinates(2,8);
		entityManager.setLocation(s, loc);
		
		
	}
	

	@Override
	public String toString() {
		return "SWWorld";
	}


}
