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

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Graph {

	public enum GraphType {
		DIRECTED, UNDIRECTED
	};

	public GraphType _type;

	ArrayList<Node> nodes;
	ArrayList<ArrayList<Node>> edges;

	public int time;
	
	/**
	 * Partea I - Structuri auxiliare pentru determinarea componentelor tare conexe
	 */
	public Stack<Node> stack;
	public ArrayList<ArrayList<Node>> ctc;

	/**
	 * Partea II - Structuri auxiliare pentru muchii & noduri critice.
	 */
	public ArrayList<Node> articulationPoints;
	public ArrayList<Pair<Node, Node>> criticalEdges;

	public Graph(GraphType type) {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<ArrayList<Node>>();

		stack = new Stack<Node>();
		ctc = new ArrayList<ArrayList<Node>>();

		articulationPoints = new ArrayList<Node>();
		criticalEdges = new ArrayList<Pair<Node, Node>>();

		_type = type;
	}

	/**
	 * @return number of nodes in the graph
	 */
	public int getNodeCount() {
		return nodes.size();
	}

	/**
	 * Creates a link between the two specified argument nodes.
	 * 
	 * @param node1
	 * @param node2
	 */
	public void insertEdge(Node node1, Node node2) {
		edges.get(node1.getId()).add(node2);
	}

	/**
	 * Adds the argument node to the graph.
	 * 
	 * @param node
	 */
	public void insertNode(Node node) {
		nodes.add(node);
		edges.add(new ArrayList<Node>());
	}

	/**
	 * @return a list with all the nodes in the graph
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	/**
	 * @param node
	 * @return a list with all the neighbors of the given node
	 */
	public ArrayList<Node> getEdges(Node node) {
		return edges.get(node.getId());
	}

	/**
	 * This method removes all the temporary information related to the state
	 * of the graph during traversals. (for example it sets every node as being
	 * unvisited)
	 */
	public void reset() {
		/**
		 * Reset the state of each node
		 */
		for (Node n : nodes)
			n.reset();

		/**
		 * Reset the data structures required for computing the strongly
		 * connected components.
		 */
		stack.clear();
		ctc.clear();

		/**
		 * Reset the data structures required for computing the biconnected
		 * components.
		 */
		articulationPoints.clear();
		criticalEdges.clear();
		
		time = 0;
	}

	public void printCTC() {
		System.out.println("Strongly Connected Components:");
		for (ArrayList<Node> c_ctc : ctc) {
			System.out.println(c_ctc);
		}
		System.out.println("\n");
	}

	/**
	 * Method to parse a graph from a file. </br>
	 *
	 * Input Format: </br>
	 * N M </br>
	 * Node_i Node_j -- list of edges </br>
	 * ... </br>
	 * where </br>
	 * N = Number of Nodes </br>
	 * M = Number of Edges </br>
	 * </br>
	 * 
	 * @param scanner
	 *            object, initialized with the input file </br>
	 */

	public void readData(Scanner scanner) {

		if (scanner == null)
			return;

		int numNodes = scanner.nextInt();
		int numEdges = scanner.nextInt();

		for (int i = 0; i < numNodes; ++i) {
			Node new_node = new Node(i);
			insertNode(new_node);
		}

		for (int i = 0; i < numEdges; ++i) {
			int node1 = scanner.nextInt();
			int node2 = scanner.nextInt();
			insertEdge(nodes.get(node1), nodes.get(node2));
			if (_type == Graph.GraphType.UNDIRECTED) {
				insertEdge(nodes.get(node2), nodes.get(node1));
			}
		}

	}

	@Override
	public String toString() {
		StringBuilder ans = new StringBuilder();

		ans.append("Graph:\n");
		for (Node n : nodes) {
			ans.append(n.toString() + " : ");
			ans.append(edges.get(n.getId()));
			ans.append('\n');
		}
		ans.append('\n');
		return ans.toString();
	}
}
