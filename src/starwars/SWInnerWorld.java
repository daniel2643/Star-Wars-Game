package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import javafx.util.Pair;

/**
 * The <code>SWInnerWorld</code> implements all kinds of inner area in SWWorld.
 * @author wen
 */

public class SWInnerWorld extends SWWorldBase {
	
	/**Tuple for storing the Exit Door's coordinates*/
	private Pair<Integer, Integer> exitCoordinates;
	
	/**getter of Exit Door*/
	public Pair<Integer, Integer> getExitCoordinates() {
		return new Pair<Integer, Integer>(exitCoordinates.getKey(), exitCoordinates.getValue());
	}
	
	/**setter of Exit Door*/
	public void setExitCoordinates(Pair<Integer, Integer> exitCoordinates) {
		this.exitCoordinates = exitCoordinates;
	}
	
	/**
	 * Constractor of <code>SWInnerWorld</code>
	 * @param width: The width of the <code>SWInnerWorld</code>
	 * @param height: The height of the <code>SWInnerWorld</code>
	 */
	public SWInnerWorld(int width, int height) {
		
		
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		myGrid = new SWGrid(factory, width, height);
		exitCoordinates = new Pair<Integer, Integer>(width()-1, height()/2);

	}
	
	/**
	 * The method for initializing the <code>SWInnerWorld</code>
	 * @param iface: a MessageRenderer to be passed onto newly-created entities
	 */
	public void initializeInnerWorld(MessageRenderer iface) {
			
		SWLocation loc; 
		
		// Initialize InnerLocation
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = getGrid().getLocationByCoordinates(col, row);
				loc.setLongDescription(loc.getShortDescription() + " (" + col + ", " + row + ")");
				loc.setShortDescription(loc.getShortDescription() + " (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		// Initialize InnerDoor
		loc = this.getGrid().getLocationByCoordinates(exitCoordinates.getKey(), exitCoordinates.getValue());
		loc.setLongDescription("Exit");
		loc.setShortDescription("Exit");
		loc.setSymbol('E');	
		
//		Droid droid = new Droid(Team.EVIL, 1000, "H", iface, this);
//		this.getEntityManager().setLocation(droid, loc);

			
	}

	@Override
	public String toString() {
		return "SWInnerWorld";
	}


}
