package starwars.actions;
import java.util.Scanner;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Player;

public class Buy extends SWAffordance{
	/**
	 Constructor for the <code>Buy</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @author 	Xutong
	 * @param 	theTarget a <code>SWEntity</code> that is taken
	 * @param   m the message renderer to display messages
	 */
	public Buy(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
		priority = 1;
	}
	/**
	 * Returns if or not this <code>Buy</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is human controlled, which means a is a player.
	 *  
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is a player, false otherwise
	 * @see		{@link starwars.SWActor#isHumanControlled()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		if (a.isHumanControlled()) {
			return true;
		}
		return false;
	}
	/**
	 * Perform the <code>Buy</code> action that <code>SWActor<code> can choose things to buy if he has enough money
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is human controlled.
	 * 
	 * @author 	Xutong
	 * @param 	a the <code>SWActor</code> that wants to buy things
	 */
	@Override
	public void act(SWActor a) {
		// the shop only opens when its hit points greater than 0
		if (target instanceof SWEntityInterface) {
			if (((SWEntityInterface) target).getHitpoints()<=0) {
				System.out.println("No more goods since the shop is broken in the war");
			}
			else {
				Player actor = (Player) a;
				// TODO Auto-generated method stub
				System.out.println("Welcome to Starwars Shop:");
				boolean exit = false;
				while (exit == false) {
					//several things a player can buy
					System.out.println("What would you like to buy?");
					System.out.println("1. 10 points hit points: $10");
					System.out.println("2. 10 points force: $10");
					System.out.println("3. donate 10 dollars for charity");
					System.out.println("0. exit");
					// Reading from System.in
					Scanner s = new Scanner(System.in);  
					System.out.println("Enter your choice: ");
					int n = s.nextInt();
					
					if(actor.getMoney() < 10 && n != 0) {
						System.out.println("Bad luck, you do not have enough money.");
					}
					else if (n == 1) {
						a.setHitPoints(10);
						actor.decMoney(10);
						System.out.println("Your current hit points: " + a.getHitpoints());
						System.out.println("Money: "+ actor.getMoney());
					}
					else if(n == 2) {
						a.addForcepoints(10);
						actor.decMoney(10);
						System.out.println("Your current force: " + a.getForcepoints());
						System.out.println("Money: "+ actor.getMoney());
					}
					else if(n == 3) {
						actor.decMoney(10);
						System.out.println("Money: "+ actor.getMoney());
					}
					else if(n == 0) {
						exit = true;
					}
					
				}
				
			}
		}
			
	}
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author xutong
	 * @return String comprising "Enter a shop "
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Enter a shop";
	}

}
