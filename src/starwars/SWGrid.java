package starwars;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.LocationMaker;

/**
 * Grid of <code>SWLocation</code>s.
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-01-20: 	Bug fix where the location of width 8 used to display a location of width 7
 * 2017-02-02: 	Removed the render method and the location width attributes. The rendering of the map
 * 				and displaying it is now the job of the UI. The dependency with EntityManager package was hence removed
 * 				and this resulted in a simpler SWGrid class (asel) 
 * 2018-05-19:  Make the size of the SWGrid World as an input from user (wen)
 * 2018-05-19:  Create one more constructor for different height and width. (wen) 
 */
public class SWGrid extends Grid<SWLocation> {

	/**
	 * The constructor of the <code>SWGrid</code>. 
	 * Will create a size by size grid with size*size <code>SWLocation</code>s
	 * 
	 * @param factory the maker of the <code>SWLocation</code>s
	 * @param size: the SWWorld map size
	 */
	public SWGrid(LocationMaker<SWLocation> factory, int size) {
		super(size, size, factory);
	}
	
	
	/**
	 * Another constructor of the <code>SWGrid</code>. 
	 * Will create a height by width grid with height*width <code>SWLocation</code>s
	 * 
	 * @param factory the maker of the <code>SWLocation</code>s
	 * @param height: the height of the SWWorld map 
	 * @param width: the width of the SWWorld map
	 */
	public SWGrid(LocationMaker<SWLocation> factory, int width, int height) {
		super(width, height, factory);
	}

	

}
