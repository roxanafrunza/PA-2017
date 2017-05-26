import java.util.ArrayList;

public class Location implements Comparable<Location> {

	// Public for backward compability
	public final int x, y;
	private final Site site;
	private ArrayList<Location> neighbors;

	public Location(int x, int y, Site site) {
		this.x = x;
		this.y = y;
		this.site = site;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Site getSite() {
		return site;
	}

	@Override
	public int compareTo(Location obj) {
		return site.strength - obj.getSite().strength;
	}

	/**
	 * Given a location, the map and a distance, we extract all of our neighbors 
	 * not further than distance cells. So, if we have a distance of 2, 
	 * we will extract these neighbors:
	 * --------------------------------
	 * | v9   | v10 | v11 | v12| v13 |
	 * --------------------------------
	 * | v24  |  v1 | v2  | v3 | v14 | 
	 * --------------------------------
	 * | v23  |  v8 | cell| v4 | v15 |
	 * --------------------------------
	 * | v22  |  v7 | v6  | v5 | v16 |
	 * --------------------------------
	 * | v21  | v20 | v19 | v18| v17 |
	 * --------------------------------
	 * @param location
	 * @param map
	 * @param distance
	 * @return
	 */
	public ArrayList<Location> getNeighbors(Location location, GameMap map, int distance) {
		location.neighbors = new ArrayList<Location>(10);
		int x = 0;
		int y = 0;
		for (int dist = 1; dist <= distance; dist++) {
			for (Direction cardinal : Direction.CARDINALS) {
				for (int counter = -dist; counter <= dist; counter++) {
					switch (cardinal) {
					case NORTH:
						x = location.x + counter;
						y = location.y - dist;
						break;
					case SOUTH:
						x = location.x + counter;
						y = location.y + dist;
						break;
					case EAST:
						x = location.x + dist;
						y = location.y + counter;
						break;
					case WEST:
						x = location.x - dist;
						y = location.y + counter;
						break;
					default:
						x = location.x;
						y = location.y;
						break;
					}
					location.neighbors.add(checkBoundaries(x, y, map));
				}

			}
		}

		return location.neighbors;
	}

	/**
	 * Given the coordinates and the map, we check if our location 
	 * is in boundaries. If it isn't we subtract and add 
	 * accordingly, in order to get on the other side of the map.
	 * @param x the x coordinate of our location
	 * @param y the y coordinate of our location
	 * @param map the map
	 * @return the adjusted location
	 */
	public Location checkBoundaries(int x, int y, GameMap map) {
		if (x >= map.width)
			x = x - map.width;
		else if (x < 0)
			x = x + map.width;

		if (y >= map.height)
			y = y - map.height;
		else if (y < 0)
			y = y + map.height;

		return map.getLocation(x, y);
	}

	/**
	 * Given the location, the map and an ID, we delete from the list of 
	 * neighbors of the location those ones which the player with 
	 * the given ID already owns.
	 * @param location - the location
	 * @param map - the map
	 * @param myID - player's ID
	 * @return - the list of neighbors which the player with the given ID does not own
	 */
	public ArrayList<Location> deleteNeighbors(Location location, GameMap map, int myID) {
		int i = 0;
		while(i < location.neighbors.size()) {
			if (location.neighbors.get(i).site.owner == myID)
				location.neighbors.remove(i);
			else
				i++;
		}

		return location.neighbors;
	}
	
	/**
	 * Given two locations and the map, we calculate the direction of the second location 
	 * to the first one.
	 * @param loc1 - the location of a site
	 * @param loc2 - the location of another site
	 * @param map - the map
	 * @return - the direction of the second location to the first one
	 */
	public Direction getNeighborDirection(Location loc1, Location loc2, GameMap map) {
		if (loc1.x - loc2.x == 0)
			if (loc1.y - loc2.y == 1 || loc2.y - loc1.y == map.height - 1)
				return Direction.NORTH;
			else
				return Direction.SOUTH;
		if (loc1.y - loc2.y == 0)
			if (loc1.x - loc2.x == 1 || loc2.x - loc1.x == map.width - 1)
				return Direction.WEST;
			else
				return Direction.EAST;
		return Direction.STILL;
	}
	
	
	/**
	 * This method adds the move to the list of moves for the given location.
	 * @param location - the location of a site
	 * @param direction - the direction of the move
	 */
	public static void planMove(Location location, Direction direction)
	{
		Move plannedMoved = new Move(location,direction);
		location.getSite().moves.add(plannedMoved);
	}
	
	
	public ArrayList<Location> getEnemies(Location location, GameMap map, int myID) {
		int i = 0;
		while(i < location.neighbors.size()) {
			if (location.neighbors.get(i).site.owner == myID || location.neighbors.get(i).site.owner == 0)
				location.neighbors.remove(i);
			else
				i++;
		}

		return location.neighbors;
	}
}
