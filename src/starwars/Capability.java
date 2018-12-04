package starwars;
/**
 * Capabilities that various entities may have.  This is useful in <code>canDo()</code> methods of 
 * <code>SWActionInterface</code> implementations.
 *  
 * @author 	ram, Xutong
 * @see 	SWActionInterface
 * @see     starwars.entities.Fillable
 */

public enum Capability {
	CHOPPER,//CHOPPER capability allows an entity to Chop another entity which has the Chop Affordance e.g.== lightsaber
	WEAPON,//WEAPON capability allows an entity to Attack another entity which has the Attack Affordance e.g.== blaster
	FILLABLE,//FILLABLE capability allows an entity to be refilled by another entity that e.g.== canteen
	            // has the Dip affordance.  Any FILLABLE Entity MUST implement the Fillable interface 
	DRINKABLE,//DRINKABLE capability allows an entity to be consumed by another entity e.g.== water
	ABSORBABLE,//ABSORBABLE capability allows an entity to be absorbed by another entity  e.g. == the force
	THROWABLE, //THROWABLE capacity allows an entity to be thrown by another entity e.g. == grenade
}
