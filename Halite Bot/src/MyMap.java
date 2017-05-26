import java.util.ArrayList;
import java.util.Collections;


public class MyMap {
	public ArrayList<Location> myPieces;
	private int myID;
	private GameMap map;

	public MyMap(int myID, GameMap map) {
		this.map = map;
		myPieces = new ArrayList<Location>(10);
		this.myID = myID;

	}

	public void addPiece(Location loc) {
		myPieces.add(loc);
		sortMap();
	}

	public void sortMap() {
		Collections.sort(myPieces, new StrengthComparator());
	}

	public ArrayList<Location> getMap() {

		myPieces = new ArrayList<Location>(10);
		for (int i = 0; i < map.width; i++) {
			for (int j = 0; j < map.height; j++) {
				if (map.getLocation(i, j).getSite().owner == myID) {
					myPieces.add(map.getLocation(i, j));
				}

			}
		}
		sortMap();
		return myPieces;
	}

	/**
	 * Given a location, we first check if the strength of it is 0. If it is, we
	 * stay still. If the strength is bigger than 0, we check if it is smaller
	 * than 5, if it is smaller than 5 * its production and if we are allowed to
	 * stay still. If all of these are true, we don't move. Otherwise, we create
	 * a list of the neighbors which we are enemies. If we don't have any
	 * enemies close by, we check if location is safe (all the locations nearby
	 * are mine). If true we move towards the closest boundary and we return the
	 * move. If we have neighbors which we do not own, we call the method
	 * getBestNeighbor. If this method returns STILL we move towards the best
	 * direction. Otherwise, we move towards the direction of the closest enemy
	 * 
	 * @param location
	 * @return
	 */
	public Move calculateMove(Location location) {
		Direction newDirection;

		ArrayList<Location> neighbors = location.getNeighbors(location, map, 2);
		// remove neighbors that are mine or neutral
		neighbors = location.getEnemies(location, map, myID);

		if (location.getSite().strength == 0) {
			Location.planMove(location, Direction.STILL);
			return new Move(location, Direction.STILL);
		}
		if ((location.getSite().strength < 5 || location.getSite().strength < 4 * location.getSite().production)
				&& location.getSite().isAllowed(location.getSite().production)) {
			Location.planMove(location, Direction.STILL);
			return new Move(location, Direction.STILL);
		}

		if(neighbors.size() == 0) {

			if (isSafe(location)) {
				newDirection = getClosestBoundary(location);
				Location.planMove(location, newDirection);
				return new Move(location, newDirection);
			}



			newDirection = getBestNeighbor(location);
			if (newDirection != Direction.STILL) {
				Location.planMove(location, newDirection);
				return new Move(location, newDirection);
			}
			newDirection = chooseBestDirection(location);
			Location.planMove(location, newDirection);
			return new Move(location, newDirection);
		}
		newDirection = getClosestEnemy(location);
		Location.planMove(location, newDirection);
		return new Move(location, newDirection);
	}

	/**
	 * Given a location, check if the most valuable direction can be attacked.
	 * If location's strength isn't enough and it's allowed to stay, don't move.
	 * 
	 * @param location
	 * @return
	 */
	public Direction chooseBestDirection(Location location) {

		Location loc = map.getLocation(location, getMostValuableDirection(location));

		if ((loc.getSite().strength > location.getSite().strength)
				&& (location.getSite().isAllowed(location.getSite().production)))
			return Direction.STILL;

		return getMostValuableDirection(location);
	}

	/**
	 * Given a location, compare the four neighbors' strength with current
	 * location's strength. If location is weaker than all the neighbors, stay
	 * on the same location. Otherwise, find the neighbor that isn't owned by
	 * player which has the strength smaller than our location and go in its
	 * direction. If there are more neighbors which match the condition, choose
	 * that with bigger strength
	 * 
	 * @param location
	 * @return
	 */
	public Direction getBestNeighbor(Location location) {
		// get the four neighbors
		Location northNeighbor = map.getLocation(location, Direction.NORTH);
		Location southNeighbor = map.getLocation(location, Direction.SOUTH);
		Location eastNeighbor = map.getLocation(location, Direction.EAST);
		Location westNeighbor = map.getLocation(location, Direction.WEST);

		ArrayList<Location> neighbors = new ArrayList<Location>(4);
		neighbors.add(northNeighbor);
		neighbors.add(southNeighbor);
		neighbors.add(eastNeighbor);
		neighbors.add(westNeighbor);
		
		// sort them increasingly by strength
		StrengthComparator comparator = new StrengthComparator();
		Collections.sort(neighbors, comparator);
		Location weakestNeighbor;

		// search for the strongest neighbors that isn't owned by player and
		// whose strength is smaller than location's
		for (int i = 0; i < neighbors.size() - 1; i++) {
			weakestNeighbor = neighbors.get(i);
			if(location.getSite().strength - weakestNeighbor.getSite().strength < 0)
				break;
			if (comparator.compare(location, weakestNeighbor) > 0 && weakestNeighbor.getSite().owner != myID) {
				// go in the neighbor's direction that matches the condition
				return location.getNeighborDirection(location, neighbors.get(i), map);
			}
		}
		
		return Direction.STILL;
	}

	/**
	 * Given a location, returns the direction to go to so that we reach a
	 * locations that isn't owned by player in the least number of moves
	 * 
	 * @param location
	 *            location on map
	 * @return direction to go to
	 */
	public Direction getClosestBoundary(Location location) {
		int maxDistance = Math.max(map.width, map.height);
		Direction initialDirection = Direction.SOUTH; // chose a random
														// direction
		Direction[] allowedDirections = Direction.values();
		int siteCounter = 0;
		Site nextSite;
		Location nextLocation;
		// for every direction (north, south, east, west)
		for (Direction dir : allowedDirections) {
			siteCounter = 0;
			nextSite = map.getSite(location, dir);
			nextLocation = map.getLocation(location, dir);
			// go in that direction until we reach a location that is owned by
			// player or the current direction is bigger than maxDistance
			while (nextSite.owner == myID && siteCounter < maxDistance) {
				siteCounter++;
				nextSite = map.getSite(nextLocation, dir);
				nextLocation = map.getLocation(nextLocation, dir);
			}
			// if we found a shorter way
			if (siteCounter < maxDistance) {
				maxDistance = siteCounter; // update maxDistance
				initialDirection = dir; // update direction
			}
		}
		return initialDirection;
	}

	public Direction getClosestEnemy(Location location) {
		int maxDistance = Math.max(map.width, map.height);
		Direction initialDirection = Direction.SOUTH; // chose a random
														// direction
		Direction[] allowedDirections = Direction.values();
		int siteCounter = 0;
		Site nextSite;
		Location nextLocation;
		// for every direction (north, south, east, west)
		for (Direction dir : allowedDirections) {
			siteCounter = 0;
			nextSite = map.getSite(location, dir);
			nextLocation = map.getLocation(location, dir);
			// go in that direction until we find an enemy
			while ( (nextSite.owner == myID || nextSite.owner == 0) && siteCounter < maxDistance) {
				siteCounter++;
				nextSite = map.getSite(nextLocation, dir);
				nextLocation = map.getLocation(nextLocation, dir);
			}
			// if we found a shorter way
			if (siteCounter < maxDistance) {
				maxDistance = siteCounter; // update maxDistance
				initialDirection = dir; // update direction
			}
		}
		return initialDirection;
	}
	/**
	 * This method iterates the array with all the neighbors a square can have
	 * (allowedNeighbors) and if it is found a direction different from "STILL"
	 * and a square can gain more strength than it already has, we can add the
	 * direction in allowedDirection array, where the square will go next.
	 * 
	 * @param location
	 *            - current location
	 * @return - the allowed directions where a square can move
	 */
	public ArrayList<Direction> allowedDirections(Location location) {
		int proposedStrength = location.getSite().production;
		ArrayList<Direction> allowedDirection = new ArrayList<Direction>();
		ArrayList<Location> allowedNeighbors = new ArrayList<Location>(4);
		allowedNeighbors = location.getNeighbors(location, map, 1);
		// Iterating the allowedNeighbors array
		for (int i = 0; i < allowedNeighbors.size(); i++) {
			// for each neighbor, we check if the square can move in its
			// direction
			Location currentNeighbor = allowedNeighbors.get(i);
			if (currentNeighbor.getNeighborDirection(location, currentNeighbor, map) != Direction.STILL)
				if (currentNeighbor.getSite().isAllowed(proposedStrength)) {
					allowedDirection.add(currentNeighbor.getNeighborDirection(location, currentNeighbor, map));
				}

		}
		return allowedDirection;
	}


/**
	 * For each direction, we calculate a sum which is based on the strenghs of
	 * the 3 neighbors that aren't mine in that direction. Basically, a square
	 * is surrounded by 3 neighbors on each direction. We return the direction
	 * where the sum is maximum.
	 * 
	 * @param location
	 *            - current location
	 * @return - the best direction where a square can go to
	 */
	public Direction getMostValuableDirection(Location location) {
		int northSum = 0, southSum = 0, westSum = 0, eastSum = 0;
		// the neighbors are saved in an array and we know the order for each
		// neighbor
		ArrayList<Location> neighbors = location.getNeighbors(location, map, 1);
		// so we know the north neighbors are on the 0, 1, 2 positions
		if(neighbors.get(0).getSite().owner != myID) {
			northSum += neighbors.get(0).getSite().strength;
		}
		if(neighbors.get(1).getSite().owner != myID) {
			northSum += neighbors.get(1).getSite().strength;
		}
		if(neighbors.get(2).getSite().owner != myID) {
			northSum += neighbors.get(2).getSite().strength;
		}

		// the east neighbors are on the 3, 4, 5 positions
		if(neighbors.get(3).getSite().owner != myID) {
			eastSum += neighbors.get(3).getSite().strength;
		}
		if(neighbors.get(4).getSite().owner != myID) {
			eastSum += neighbors.get(4).getSite().strength;
		}
		if(neighbors.get(5).getSite().owner != myID) {
			eastSum += neighbors.get(5).getSite().strength;
		}

		// the south neighbors are on the 6, 7, 8 positions
		if(neighbors.get(6).getSite().owner != myID) {
			southSum += neighbors.get(6).getSite().strength;
		}
		if(neighbors.get(7).getSite().owner != myID) {
			southSum += neighbors.get(7).getSite().strength;
		}
		if(neighbors.get(8).getSite().owner != myID) {
			southSum += neighbors.get(8).getSite().strength;
		}

		// the west neighbors are on the 9, 10, 11 positions
		if(neighbors.get(9).getSite().owner != myID) {
			westSum += neighbors.get(9).getSite().strength;
		}
		if(neighbors.get(10).getSite().owner != myID) {
			westSum += neighbors.get(10).getSite().strength;
		}
		if(neighbors.get(11).getSite().owner != myID) {
			westSum += neighbors.get(11).getSite().strength;
		}
		// get maximum sum
		float max = Math.max(Math.max(northSum, southSum), Math.max(eastSum, westSum));
		// return the best direction
		if (max == northSum)
			return Direction.NORTH;
		else if (max == southSum)
			return Direction.SOUTH;
		else if (max == eastSum)
			return Direction.EAST;
		else
			return Direction.WEST;
	}

	/**
	 * Check if the neighbors in the allowed directions are mine. If true,
	 * location is considered safe
	 * 
	 * @param location
	 *            given location
	 * @return location is safe or not
	 */
	public boolean isSafe(Location location) {

		Location northNeighbor = map.getLocation(location, Direction.NORTH);
		Location southNeighbor = map.getLocation(location, Direction.SOUTH);
		Location eastNeighbor = map.getLocation(location, Direction.EAST);
		Location westNeighbor = map.getLocation(location, Direction.WEST);

		ArrayList<Location> neighbors = new ArrayList<Location>(4);
		neighbors.add(northNeighbor);
		neighbors.add(southNeighbor);
		neighbors.add(eastNeighbor);
		neighbors.add(westNeighbor);

		if (northNeighbor.getSite().owner == myID && southNeighbor.getSite().owner == myID &&
				eastNeighbor.getSite().owner == myID && westNeighbor.getSite().owner == myID){
			
			return true;
		}
			return false;

	}
}
