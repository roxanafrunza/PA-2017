package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Proiectarea Algoritmilor Lab 3: Parcurgerea Grafurilor. Sortare Topologica
 *
 * @author adinu
 * @email mandrei.dinu@gmail.com
 *
 */

public class GraphT {

	/* holds the graph by using a list of adjacent nodes for each node */
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
	public void addEdge(int from, int to) {
		adjList.get(from).add(to);
		indegree[to]++;
	}


	/**
	 * Remove an edge from graph
	 * 
	 * @param from
	 *            starting node
	 * @param to
	 *            ending node
	 */
	public void removeEdge(int from, int to) {
		adjList.get(from).remove(new Integer(to));
		indegree[to]--;
	}

	/**
	 * Get the in degree of a node specified by its index in the graph
	 * 
	 * @param nodeIdx
	 *            node's index
	 * @return indegree of node
	 */
	public int getIndegreeOf(int nodeIdx) {
		return indegree[nodeIdx];
	}

	/**
	 * Get a list of all the neighbours of a node, given by its index in the
	 * graph
	 * 
	 * @param nodeIdx
	 *            node's index
	 * @return adjacent list
	 */
	public List<Integer> getNeighboursOf(int nodeIdx) {
		return adjList.get(nodeIdx);
	}

	/**
	 * Get the total number of nodes in the graph
	 * 
	 * @return total number of nodes
	 */
	public int getTotalNumOfNodes() {
		return adjList.size();
	}

	/**
	 * Get the total number of edges in the graph
	 * 
	 * @return total number of edges
	 */
	public int getTotalNumOfEdges() {
		int s = 0;

		for (List<Integer> l : adjList) {
			s += l.size();
		}

		return s;
	}

}
