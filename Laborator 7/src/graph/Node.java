/**
 * Proiectarea Algoritmilor, 2016
 * Lab 7: Aplicatii DFS
 *
 * @author 	Radu Iacob
 * @email	radu.iacob23@gmail.com
 * @author adinu
 * @email  mandrei.dinu@gmail.com
 */

package graph;

public class Node {

	String _name;
	int _id;

	public Node(String name, int id) {
		_name = name;
		_id = id;
		reset();
	}

	public Node(int id) {
		_name = "";
		_id = id;
		reset();
	}

	/**
	 * Variable holds the moment in time a node was discovered during DFS. </br>
	 * (the idx from the pseudo-code). </br>
	 * For an unvisited node, the value defaults to UNSET. </br>
	 */
	public int discoveryTime;

	/**
	 * Default discovery time for an unvisited node.
	 */
	public final static int UNSET = -1;
	
	/**
	 * The smallest discovery time for a node that is accessible from the
	 * current node. </br>
	 */
	public int lowLink;

	/**
	 * Variable holds true if the node is currently stored on the
	 * stack during Tarjan's algorithm. </br>
	 */
	public boolean inStack;
	
	/**
	 * Bonus: The index of the strongly connected component
	 */
	public int ctcIndex;
	
	/**
	 * Bonus: The number of nodes in a 'cluster'.
	 */
	public int countNodes;
	
	/**
	 * @return true if the node was visited, false otherwise.
	 */
	public boolean wasVisited() {
		return !(discoveryTime == UNSET);
	}

	/**
	 * Resets the state of the node.
	 */
	public void reset() {
		discoveryTime = lowLink = ctcIndex = UNSET;
		inStack = false;
	}

	public String getName() {
		return _name;
	}

	public int getId() {
		return _id;
	}

	@Override
	public String toString() {

		StringBuilder ans = new StringBuilder();
		ans.append("Node : ");

		if (_name.length() != 0) {
			ans.append(_name);
			ans.append(" , ");
		}

		ans.append(_id);
		return ans.toString();
	}

}
