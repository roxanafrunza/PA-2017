/**
 * Proiectarea Algoritmilor, 2016
 * Lab 8: Drumuri minime
 *
 * @author  adinu
 * @email   mandrei.dinu@gmail.com
 */

package minCost;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import graph.GraphT;
import graph.NodeT;
import graph.PairT;

public class OneSourceShortestPath {

	private int[] distances;

	private GraphT<String, Integer> g;

	public OneSourceShortestPath(GraphT<String, Integer> g) {
		this.g = g;
		distances = new int[g.size()];
	}

	private class NodeComparator implements Comparator<NodeT<String>> {
		/**
		 * Compares nodes using the current estimation of the distance from the
		 * source node.
		 */
		@Override
		public int compare(NodeT<String> arg0, NodeT<String> arg1) {
			int dist1 = distances[arg0.id()];
			int dist2 = distances[arg1.id()];

			return dist1 > dist2 ? 1 : -1;
		}
	}

	public int[] applyDijkstra(NodeT<String> source) {

		resetDistances();

		PriorityQueue<NodeT<String>> pq = new PriorityQueue<>(g.size(), new NodeComparator());

		// TODO
		Integer[][] cost = new Integer[g.size()][g.size()]; // cost
		boolean[] selected = new boolean[g.size()]; // a fost selectat
		boolean[][] isPath = new boolean[g.size()][g.size()];
		List<NodeT<String>> parent = new ArrayList<>(); // parent[i] = parintele
														// nodului i

		// initializare cost cu infinit
		for (int i = 0; i < g.size(); i++)
			for (int j = 0; j < g.size(); j++)
				cost[i][j] = Integer.MAX_VALUE;

		// initializare isPath si cost
		for (NodeT<String> node : g.nodes()) {
			List<PairT<NodeT<String>, Integer>> adjList = g.adjList(node);
			for (PairT<NodeT<String>, Integer> p : adjList) {
				isPath[node.id()][p.first().id()] = true;
				cost[node.id()][p.first().id()] = p.second();
			}

		}

		for (NodeT<String> node : g.nodes()) { // pentru multimea nodurilor
			if (node.id() == source.id()) {// distanta de la sursa la sursa = 0
				distances[node.id()] = 0;
				parent.add(node.id(), null);
			} else if (isPath[source.id()][node.id()]) // exista muchie
			{
				// initializam distanta pana la nodul respectiv
				distances[node.id()] = cost[source.id()][node.id()];
				pq.add(node);
				parent.add(node.id(), source);
			} else {

				distances[node.id()] = Integer.MAX_VALUE;
				parent.add(node.id(), null);
			}
		}

		selected[source.id()] = true;
		// relaxari succesive
		while (!pq.isEmpty()) {
			NodeT<String> u = pq.poll();
			selected[u.id()] = true;
			for (PairT<NodeT<String>, Integer> nod : g.adjList(u)) {
				int index = nod.first().id();
				/*
				 * daca drumul de la sursa la nod prin u este mai mic decat cel
				 * curent
				 */
				if (selected[index] == false && distances[index] > distances[u.id()] + cost[u.id()][index]) {
					// actualizeaza distanta si parinte
					distances[index] = distances[u.id()] + cost[u.id()][index];
					parent.add(index, u);
					/* actualizeaza pozitia nodului in coada prioritara */
					pq.add(g.getNode(index));
				}
			}
		}

		return distances;

	}

	public int[] applyBellmanFord(NodeT<String> source) {

		resetDistances();

		Integer[][] cost = new Integer[g.size()][g.size()]; // cost
		boolean[][] isPath = new boolean[g.size()][g.size()];
		List<NodeT<String>> parent = new ArrayList<>(); // parent[i] = parintele
														// nodului i

		// TODO BONUS
		// initializare cost cu infinit
		for (int i = 0; i < g.size(); i++)
			for (int j = 0; j < g.size(); j++)
				cost[i][j] = Integer.MAX_VALUE;

		// initializare isPath si cost
		for (NodeT<String> node : g.nodes()) {
			List<PairT<NodeT<String>, Integer>> adjList = g.adjList(node);
			for (PairT<NodeT<String>, Integer> p : adjList) {
				isPath[node.id()][p.first().id()] = true;
				cost[node.id()][p.first().id()] = p.second();
			}

		}
				
		for (NodeT<String> node : g.nodes()) { // pentru multimea nodurilor
			if (node.id() == source.id()) {// distanta de la sursa la sursa = 0
				distances[node.id()] = 0;
				parent.add(node.id(), null);
			} else if (isPath[source.id()][node.id()]) // exista muchie
			{
				// initializam distanta pana la nodul respectiv
				distances[node.id()] = cost[source.id()][node.id()];
				parent.add(node.id(), source);
			} else {

				distances[node.id()] = Integer.MAX_VALUE;
				parent.add(node.id(), null);
			}
		}
		for(int j = 0; j < g.size(); j++)
			System.out.print(distances[j] + " ");
		System.out.println();
		
		for (int i = 0; i < g.size() - 1; i++) {
			for(NodeT<String> u : g.nodes())
				for(NodeT<String> v : g.nodes()){
					if(distances[v.id()] > distances[u.id()] + cost[u.id()][v.id()])
					{
						System.out.print(distances[v.id()] + " ");
						System.out.print(cost[u.id()][v.id()] + " ");
						System.out.println();
						distances[v.id()] = distances[u.id()] + cost[u.id()][v.id()];
						parent.add(v.id(), u);
					}
				}
		}

		return distances;
	}

	public void printInfiniteLoop(NodeT<String> destination) {

		// TODO BONUS
	}

	private void resetDistances() {
		for (int i = 0; i < g.size(); i++) {
			distances[i] = Integer.MAX_VALUE;
		}
	}
}
