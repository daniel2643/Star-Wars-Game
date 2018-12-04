package starwars.swinterfaces;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.GridController;
import edu.monash.fit2099.gridworld.GridRenderer;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWGrid;
import starwars.SWWorld;
import starwars.SWWorldBase;

/**
 * Concrete implementation of the <code>GridController</code>.
 * <p>
 * This controller calls the UI methods to render map, messages and obtain user input.
 * 
 * @author 	Asel
 * @see 	{@link edu.monash.fit2099.gridworld.GridController}
 *
 */
/*
 * Change log:
 * 2018-05-21: Added one more constructor for sandcrawler inner place: a interior world (wen)
 * 2018-05-24: Modified render() method that it can render both main world map and inner world map (wen)
 * 2018-05-25: Applied Liskov Substitution Principle for enlarging the innerWorld scope from only
 * 			   Sandcrawler to all SWWorldBase Entity. (wen)
 * 2018-05-26:  Code Smell: Duplicated code —> DRY Principle: merge constructors and render() methods groups into general one (wen)
 * 2018-05-26:  instead of using one static ui for one userInterface, 
 *              create a list in the Class to contain all userInterfaces(ui) (wen)
 */
public class SWGridController implements GridController {

	/**The user interfaces to be used by the controller. All user interfaces should be concrete 
	 * implementations of the <code>GridRenderer</code> interface
	 * 
	 * @see {@link edu.monash.fit2099.gridworld.GridRenderer}*/	
	private static List<GridRenderer> uis = new ArrayList<GridRenderer>();
	
	/**SWgrid of the world*/
	private SWGrid grid;
	
	/**
	 * Constructor of this <code>SWGridController</code>
	 * <p>
	 * The constructor will initialize the <code>grid</code> and the user interface to be used by the controller.
	 * <p>
	 * If a different User Interface (also know as a View) is to be used it must be changed in this constructor.
	 * 
	 * @param 	world the world to be considered by the controller
	 * @pre 	the world should not be null
	 */
	public SWGridController(SWWorldBase world) {
		this.grid = world.getGrid();
		
		//change the user interface to be used here in the constructor
		SWGridController.uis.add(new SWGridTextInterface(this.grid)); //use a Text Interface to interact
		//this.ui = new SWGridBasicGUI(this.grid); //Use a Basic GUI to interact
		//this.ui = new SWGridGUI(this.grid); //Use a GUI with better graphics to interact
	}
	

	@Override
	public void render() {
		//Call the UI to handle this
		for (int i = 0; i < uis.size(); i++) {
			uis.get(i).displayMap();
		}
	}
	

	@Override
	public void render(String message) {
		//call the UI to handle this too
		uis.get(0).displayMessage(message);
	}
	
	
	/**
	 * Will return a Action selected by the user.
	 * <p>
	 * This method will provide the user interface with a list of commands from which the user 
	 * needs to select one from and will return this selection.	
	 * 
	 * @param 	a the <code>SWActor</code> for whom an Action needs to be selected
	 * @return	the selected action for the <code>SWActor a</code>
	 */
	public static SWActionInterface getUserDecision(SWActor a) {
		
		//this list will store all the commands that SWActor a can perform
		ArrayList<ActionInterface> cmds = new ArrayList<ActionInterface>();

		//Get all the actions the SWActor a can perform
		for (SWActionInterface ac : SWWorld.getEntitymanager().getActionsFor(a)) {
			if (ac.canDo(a))
				cmds.add(ac);
		}
		
		//Get the UI to display the commands to the user and get a selection
		//TO DO: Ensure the cmd list is not empty to avoid an infinite wait
		assert (cmds.size()>0): "No commands for Star Wars Actor";
		
		ActionInterface selectedAction = uis.get(0).getSelection(cmds);
		
		//cast and return selection
		return (SWActionInterface)selectedAction;
	}
	
}
