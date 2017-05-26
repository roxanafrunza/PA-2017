
import java.util.ArrayList;

public class GameMap {

	private final Site[][] contents; // matrix of squares
	private final Location[][] locations; // matrix of location
											// each value has (x, y) coordinates
											// and a square
	public final int width, height; // map dimensions
	
	// GameMap constructor
	public GameMap(int width, int height, int[][] productions) {

		this.width = width;
		this.height = height;
		this.contents = new Site[width][height];
		this.locations = new Location[width][height];
		// each square on the map has a production cost
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				final Site site = new Site(productions[x][y]);
				contents[x][y] = site;
				// we create the location based on the coordinates and the
				// square
				locations[x][y] = new Location(x, y, site);
			}
		}
	}

	/**
	 * 
	 * @param loc
	 *            - the current location
	 * @return true or false
	 * 
	 *         This method checks if the current location is between the map
	 *         limits
	 */
	public boolean inBounds(Location loc) {
		return loc.x < width && loc.x >= 0 && loc.y < height && loc.y >= 0;
	}

	/**
	 * This method returns the distance between two locations given
	 * 
	 * @param loc1
	 *            - location 1
	 * @param loc2
	 *            - location 2
	 * @return the distance between two locations
	 */

	public double getDistance(Location loc1, Location loc2) {
		int dx = Math.abs(loc1.x - loc2.x);
		int dy = Math.abs(loc1.y - loc2.y);

		if (dx > width / 2.0)
			dx = width - dx;
		if (dy > height / 2.0)
			dy = height - dy;

		return dx + dy;
	}

	/**
	 * This method returns atan between two locations
	 * 
	 * @param loc1
	 *            - location 1
	 * @param loc2
	 *            - location 2
	 * @return - atan between two locations
	 */

	public double getAngle(Location loc1, Location loc2) {
		int dx = loc1.x - loc2.x;

		// Flip order because 0,0 is top left
		// and want atan2 to look as it would on the unit circle
		int dy = loc2.y - loc1.y;

		if (dx > width - dx)
			dx -= width;
		if (-dx > width + dx)
			dx += width;

		if (dy > height - dy)
			dy -= height;
		if (-dy > height + dy)
			dy += height;

		return Math.atan2(dy, dx);
	}

	/**
	 * @param location
	 *            - the current location
	 * @param direction
	 *            - the direction where I want to go
	 * @return - the new location where I want to go
	 */
	public Location getLocation(Location location, Direction direction) {
		switch (direction) {
		case STILL:
			return location; // the square stays in the same location
		case NORTH:
			// the square goes on the north direction;
			// if the square is on the first line on the map, it goes on the
			// last one
			return locations[location.getX()][(location.getY() == 0 ? height : location.getY()) - 1];
		case EAST:
			// the square goes on the east direction;
			// if the square is on the last column on the map, it goes on the
			// first one
			return locations[location.getX() == width - 1 ? 0 : location.getX() + 1][location.getY()];
		case SOUTH:
			// the square goes on the south direction;
			// if the square is on the last line on the map, it goes on the
			// first one
			return locations[location.getX()][location.getY() == height - 1 ? 0 : location.getY() + 1];
		case WEST:
			// the square goes on the west direction;
			// if the square is on the first column on the map, it goes on the
			// last one
			return locations[(location.getX() == 0 ? width : location.getX()) - 1][location.getY()];
		default:
			throw new IllegalArgumentException(String.format("Unknown direction %s encountered", direction));
		}
	}

	/**
	 * This method returns the new location from the direction where I want to go.
	 * 
	 * @param loc - current location
	 * @param dir - the direction where I want to go
	 * @return - the new location 
	 */
	public Site getSite(Location loc, Direction dir) {
		return getLocation(loc, dir).getSite();
	}

	/**This method return the square from a specific location
	 * 
	 * @param loc - current location 
	 * @return - the square from the current location
	 */
	public Site getSite(Location loc) {
		return loc.getSite();
	}
/**
 * This method returns the location from the specific coordinates
 * 
 * @param x - x coordinate
 * @param y - y coordinate
 * @return - the location from (x, y) coordinates
 */
	public Location getLocation(int x, int y) {
		return locations[x][y];
	}
/**
 * This method resets a location. It doesn't have an owner or any strength anymore
 * 
 */
	void reset() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				final Site site = contents[x][y];
				site.owner = 0;
				site.strength = 0;
				site.moves.clear();
			}
		}
	}
}
