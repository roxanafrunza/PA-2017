import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public enum Direction {
    STILL, NORTH, EAST, SOUTH, WEST;
//array with the possible directions: north, east, south, west or still
    public static final Direction[] DIRECTIONS = new Direction[]{STILL, NORTH, EAST, SOUTH, WEST};
//array with the four cardinals ( north, east, south, west )
    public static final Direction[] CARDINALS = new Direction[]{NORTH, EAST, SOUTH, WEST};
/**
 * This method calculates a random direction and returns it.
 * 
 * @return - random direction
 */
    public static Direction randomDirection() {
        Direction[] values = values();
        return values[new Random().nextInt(values.length)];
    }
    /**
     * This method returns a random direction from a Direction arraylist
     * 
     * @param dir - a given direction
     * @return - a random direction except the given one
     */
    public static Direction randDirection(Direction dir){
    	ArrayList<Direction> values = new ArrayList<Direction>(4);
    	values.add(Direction.EAST);
    	values.add(Direction.NORTH);
    	values.add(Direction.SOUTH);
    	values.add(Direction.WEST);
    	values.remove(dir);
    	Collections.shuffle(values);
    	return values.get(0);
    	
    	
    }
}
