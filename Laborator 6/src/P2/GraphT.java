package P2;

import java.util.ArrayList;
import java.util.List;

/**
 * Proiectarea Algoritmilor Lab 3: Parcurgerea Grafurilor. Sortare Topologica
 * Task 2: Planificarea studierii materiilor
 *
 * @author adinu
 * @email mandrei.dinu@gmail.com
 *
 */

public class GraphT {

	/* holds the graph by using a list of adjacents for each node */
	private List<List<Integer>> adjList;

	/* holds the in degree of each node of the graph */
	private int[] indegree;

	public GraphT(int numNodes) {
		adjList = new ArrayList<List<Integer>>();

		for (int nodeIdx = 0; nodeIdx < numNodes; nodeIdx++) {
			adjList.add(nodeIdx, new ArrayList<Integer>());
		}

		indegree = new int[numNodes];
		for (int nodeIdx = 0; nodeIdx < numNodes; nodeIdx++) {
			indegree[nodeIdx] = 0;
		}
	}

	/**
	 * Add an edge in the graph
	 * 
	 * @param from
	 *            is the starting node
	 * @param to
	 *            is the ending node
	 */
	void addEdge(int from, int to) {
		adjList.get(from).add(to);
		indegree[to]++;
	}

	void removeEdge(int from, int to) {
		adjList.get(from).remove(new Integer(to));
		indegree[to]--;
	}

	/**
	 * Get the in degree of a node specified by its index in the graph
	 * 
	 * @param nodeIdx
	 * @return
	 */
	int getIndegreeOf(int nodeIdx) {
		return indegree[nodeIdx];
	}

	/**
	 * Get a list of all the neighbours of a node, given by its index in the
	 * graph
	 * 
	 * @param nodeIdx
	 * @return
	 */
	List<Integer> getNeighboursOf(int nodeIdx) {
		return adjList.get(nodeIdx);
	}

	/**
	 * Get the total number of nodes in the graph
	 * 
	 * @return
	 */
	int getTotalNumOfNodes() {
		return adjList.size();
	}

}
