/**
 * Proiectarea Algoritmilor, 2016
 * Lab 8: Drumuri minime
 *
 * @author  adinu
 * @email   mandrei.dinu@gmail.com
 */

package graph;

/**
 * Describes the Node structure of a GraphT
 *
 * @param <T> the type of the information saved within the node
 */
public class NodeT<T> {

	// information saved within the node
	private T info;

	// each node has an index which helps identifying it
	private int id;

	private boolean visited = false;

	private NodeT parent = null;

	public NodeT(T info, int id) {
		this(id);
		this.info = info;
	}

	public NodeT(int id) {
		this.id = id;
	}

	public T info() {
		return info;
	}

	public int id() {
		return id;
	}

	public void setParent(NodeT parent) {
		this.parent = parent;
	}

	public NodeT parent() {
		return parent;
	}

	public void visit() {
		visited = true;
	}

	public boolean isVisited() {
		return visited;
	}

	public void reset() {
		visited = false;
	}

	@Override
	public String toString() {
		return String.valueOf(info);
	}
}
