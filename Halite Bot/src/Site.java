import java.util.ArrayList;

public class Site {
	//a square atributes - each square has an owner, strength and a production cost
	public final int production;
	public int owner, strength;
	//a list where all the moves a square does are saved
	public ArrayList<Move> moves;

	public Site(int production) {
		this.production = production;
		moves = new ArrayList<Move>(5);
	}

	/**
	 * This method calculates the total strength from all the square's moves
	 * 
	 * @return - the total strength from a square moves.
	 */
	public int movesTotalStrength() {
		int strengthSum = 0;
		//Iterating the moves array and add the strength from each square's move
		for (int i = 0; i < moves.size(); i++) {
			strengthSum += moves.get(i).loc.getSite().strength;
		}
		return strengthSum;
	}
	/**
	 * This method checks if the square can gain more strength than it already has.
	 * 
	 * @param proposedStrength - the strength which is supposed to be added 
	 * @return - boolean: true if the strength doesn't overpass the imposed limit (255), false otherwise
	 */
	public boolean isAllowed(int proposedStrength) {
		return (movesTotalStrength() + proposedStrength) < 255;
	}

	/**
	 * This method returns a value ( production/strength) which will be useful for 
	 * the way the square moves.
	 * @return - the raport production / strength 
	 */
	public float getSiteValue() {
		if ( strength == 0)
			return production;
		
		return (float) production / strength;
	}
	

}
