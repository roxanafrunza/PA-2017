/**
 * Proiectarea Algoritmilor, 2016
 * Lab 8: Drumuri minime
 *
 * @author  adinu
 * @email   mandrei.dinu@gmail.com
 */

package minCost;

import java.util.ArrayList;
import java.util.List;

import graph.GraphT;
import graph.NodeT;
import graph.PairT;

public class FloydWarshall {

	private boolean[][] isPath;

	private Integer[][] cost;

	private Integer[][] next;

	private List<Integer> minPath;

	private GraphT<String, Integer> g;

	public FloydWarshall(GraphT<String, Integer> g) {
		this.g = g;
		isPath = new boolean[g.size()][g.size()];
		cost = new Integer[g.size()][g.size()];
		next = new Integer[g.size()][g.size()];
		minPath = new ArrayList<>();
	}

	public void computeTransitiveClosure() {

		List<NodeT<String>> nodes = g.nodes();
		int nodeCount = g.size();
		prepareTransitiveClosure();

		// init isPath for existing direct edges
		for (NodeT<String> node : nodes) {
			List<PairT<NodeT<String>, Integer>> adjList = g.adjList(node);
			for (PairT<NodeT<String>, Integer> p : adjList) {
				isPath[node.id()][p.first().id()] = true;
			}
		}
		
		for(NodeT<String> node : nodes) {
			isPath[node.id()][node.id()] = true;
		}

		// TODO
		// Hint: apply a similar version of Floyd Warshall algorithm,
		// you don't need to use either cost[][], or next[][] matrices
		
		for (int k = 0; k < nodeCount; k++) {
			for (NodeT<String> i : nodes) {
				for (NodeT<String> node2 : nodes) { 
					isPath[i.id()][node2.id()] = (isPath[i.id()][node2.id()]
							|| (isPath[i.id()][k] && isPath[k][node2.id()]));
				}
			}
		}
	}

	public boolean isPathBetween(NodeT<String> source, NodeT<String> destination) {
		// TODO
		// Hint: just check within the isPath[][] matrix
		List<NodeT<String>> nodes = g.nodes();
		
		 return isPath[source.id()][destination.id()];

	}

	public void computeAllMinPaths() {

		List<NodeT<String>> nodes = g.nodes();
		int nodeCount = g.size();
		prepareAllMinPathsAlgo();

		// TODO
		// Hint: apply Floyd-Warshall algorithm
		// use cost[][] matrix to find the min cost path between all pairs
		// of nodes
		// use next[][] matrix to print the entire path
		// next[i][j] = next node in the path from i to j
		// initialize next[u][v] with v, for existing direct edges
		for (NodeT<String> u : nodes) {
			List<PairT<NodeT<String>, Integer>> adjList = g.adjList(u);
			for (PairT<NodeT<String>, Integer> v : adjList) {
				next[u.id()][v.first().id()] = v.second();
			}
		}
		
		// initializare matrice d
		int[][] d = new int[nodeCount][nodeCount];
		for(int i = 0; i < nodeCount; i++)
			for(int j = 0; j < nodeCount; j++)
				d[i][j] = cost[i][j];
		
		for (int k = 0; k < nodeCount; k++)
			for (int i = 0; i < nodeCount; i++)
				for (int j = 0; j < nodeCount; j++)
				{									
					if (d[i][j] < d[i][k] + d[k][j]) {
						//if (isPath[i][j])
							next[i][j] = j;
					} else /*if (isPath[i][k] && isPath[k][j])*/ {
						d[i][j] = d[i][k] + d[k][j];
						next[i][j] = k;
					}
				}
	}

	public void printMinPath(NodeT<String> source, NodeT<String> destination) {

		// TODO
		// after using Floyd-Warshall algo, use next[][] matrix to
		// build the entire minimum path between them
		// use minPath to save it
		computeAllMinPaths();
		
		int src = source.id();
		int dest = next[source.id()][destination.id()];
		
		for(int i = 0; i < g.size(); i++){
			for(int j = 0; j < g.size(); j++)
			{
				System.out.print(next[i][j] + " ");
			}
			System.out.println();
		}
		minPath.add(source.id());

		if (dest == destination.id()) {
			minPath.add(destination.id());
		}
		while (dest != destination.id()) {
			minPath.add(dest);
			src = dest;
			dest = next[src][destination.id()];
		}
		
		
		System.out.print("Drumul minim: ");
		for (Integer nodes : minPath)
			System.out.print(g.getNode(nodes) + " ");
		System.out.println();
	}

	private void prepareTransitiveClosure() {
		for (int i = 0; i < g.size(); i++) {
			for (int j = 0; j < g.size(); j++) {
				isPath[i][j] = false;
			}
		}
	}

	private void prepareAllMinPathsAlgo() {
		for (int i = 0; i < g.size(); i++) {
			for (int j = 0; j < g.size(); j++) {
				cost[i][j] = Integer.MAX_VALUE;
				next[i][j] = -1;
			}
		}
	}
}
