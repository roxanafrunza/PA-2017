/**
 * Proiectarea Algoritmilor, 2016
 * Lab 8: Drumuri minime
 *
 * @author  adinu
 * @email   mandrei.dinu@gmail.com
 */

package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @param <T> the type of the information saved within the nodes, i.e. String,
 * Double, etc.
 * @param <C> the type corresponding to the cost value between two vertices,
 * i.e. Integer, Double, etc.
 */
public class GraphT<T, C> {

	private List<NodeT<T>> nodes = new ArrayList<>();

	private List<List<PairT<NodeT<T>, C>>> adjList =
			new ArrayList<>();

	private Map<T, NodeT<T>> nodeDict = new HashMap<>();


	public void insertNode(NodeT<T> node) {
		nodes.add(node);
		adjList.add(new ArrayList<PairT<NodeT<T>, C>>());
		registerNode(node);
	}

	public void insertEdge(NodeT<T> node1, NodeT<T> node2, C cost) {
		adjList.get(node1.id()).add(new PairT<NodeT<T>, C>(node2, cost));
	}

	public List<PairT<NodeT<T>, C>> adjList(NodeT<T> node) {
		return adjList.get(node.id());
	}

	public List<NodeT<T>> nodes() {
		return nodes;
	}

	public int size() {
		return nodes.size();
	}

	public NodeT<T> getNode(T info) {
        return nodeDict.get(info);
    }

	public NodeT<T> getNode(int idx) {
		return nodes.get(idx);
	}

    private void registerNode(NodeT<T> node) {
        nodeDict.put(node.info(), node);
    }

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder("Print Graph:\n");

        for (NodeT<T> node : nodes) {

            sb.append("OutEdges for " + node.toString() + " -> ");
            for (PairT<NodeT<T>, C> edge : adjList(node)) {
                 sb.append(edge.first().info());
                 sb.append("( " + edge.second() + " ) | ");
            }

            sb.append("\n");
        }

        sb.append("\n");
        return sb.toString();
	}

}
