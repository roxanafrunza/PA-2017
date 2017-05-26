/**
 * Proiectarea Algoritmilor, 2016
 * Lab 9: Arbori minimi de acoperire
 *
 * @author  adinu
 * @email   mandrei.dinu@gmail.com
 */

package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Graph {

	/**
	 * Total number of nodes that makes the graph
	 */
	private int numNodes;

	/**
	 * The graph uses list of adjacencies for each node.
	 * The first item of the pair represents the index of the adjacent,
	 * while the second item represents the cost of the edge
	 */
	private List<List<Pair<Integer, Integer>>> edges;

	public Graph() {
		edges = new ArrayList<>();
	}

	public void insertNode(int nodeIdx) {
		edges.add(new ArrayList<Pair<Integer, Integer>>());
	}

	/**
	 * Inserts a new edge into the graph starting at 'fromNodeIdx' and
	 * ending at 'toNodeIdx', having cost value of 'cost'
	 *
	 * @param fromNodeIdx
	 * @param toNodeIdx
	 * @param cost
	 */
	public void insertEdge(int fromNodeIdx, int toNodeIdx, int cost) {
		edges.get(fromNodeIdx).add(new Pair<>(toNodeIdx, cost));
	}

	public int getNodeCount() {
		return numNodes;
	}

	public List<Pair<Integer, Integer>> getEdges(int nodeIdx) {
		return edges.get(nodeIdx);
	}

	/**
	 * Function to read all the tests
	 *
	 * Input Format:
	 * N M
	 * Nodei Nodej Costij			   -- list of edges
	 * ...
	 * where
	 * N = Number of Nodes
	 * M = Number of Edges
	 * Costij = cost of edge from i to j, as well as from j to i
	 * @param scanner
	 */
	public void readData(Scanner scanner) {

		numNodes = scanner.nextInt();
		int numEdges = scanner.nextInt();

		for (int i = 0; i < numNodes; i++) {
			insertNode(i);
		}

		for (int edgeIdx = 1; edgeIdx <= numEdges; edgeIdx++) {
			int node1 = scanner.nextInt();
			int node2 = scanner.nextInt();
			int cost = scanner.nextInt();

			insertEdge(node1, node2, cost);
			insertEdge(node2, node1, cost);
		}
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder("Print Graph:\n");

		for(int n = 0; n < numNodes; n++) {
			sb.append("OutEdges for " + n + " -> ");

			for (Pair<Integer, Integer> edge : edges.get(n)) {
				sb.append(edge.first());
                sb.append("( " + edge.second() + " ) | ");
			}

			sb.append("\n");
		}
		sb.append("\n");

		return sb.toString();
	}
}
