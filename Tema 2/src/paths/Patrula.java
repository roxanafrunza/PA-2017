package paths;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import utils.GraphT;
import utils.MyScanner;

public class Patrula {
	public static final int INF = 424242;
	public static int numberOfPaths;
	public static int pathLength;
	public static int[] distance;
	static ArrayList<List<Integer>> paths;
	static boolean[][] isPath;

	public static void main(String[] args) throws FileNotFoundException {
		MyScanner reader = new MyScanner("patrula.in");

		int u, v; // used to read an edge from file

		int N = reader.nextInt(); // number of nodes
		int M = reader.nextInt(); // number of edges
		GraphT graph = new GraphT(N); // create graph
		isPath = new boolean[N][N]; // used to save

		// the number of edges for each node for every path
		ArrayList<Long> pathCount = new ArrayList<>(Collections.nCopies(N, new Long(0)));
		// the number of edges for each node for all paths
		ArrayList<Long> pathTotal = new ArrayList<>(Collections.nCopies(N, new Long(0)));
		int idx1, idx2;
		long val1, val2;

		// read every edge
		for (int i = 0; i < M; i++) {
			u = reader.nextInt() - 1;
			v = reader.nextInt() - 1;

			// add edge in graph
			graph.addEdge(u, v);
			graph.addEdge(v, u);
			isPath[u][v] = true;
			isPath[v][u] = true;
		}

		// create distances array using bfs
		countMinPath(graph, N - 1, 0);

		// get list of paths that use different nodes
		paths = new ArrayList<>(numberOfPaths);
		getPaths(graph, 0, new LinkedList<Integer>());

		long product, sum = 0;

		for (List<Integer> path : paths) {
			// get number of possible paths for same nodes
			product = 1;
			for (int j = 1; j < path.size(); j++) {
				idx1 = path.get(j);
				idx2 = path.get(j - 1);
				// get the number of multiple edges between two nodes
				product *= multipleEdges(idx1, graph.getNeighboursOf(idx2));
				// increased for node the number of edges used
				val1 = pathCount.get(idx1) + 1;
				val2 = pathCount.get(idx2) + 1;
				pathCount.set(idx1, val1);
				pathCount.set(idx2, val2);

			}
			// get total number of edges
			for (int i = 0; i < N; i++) {
				val1 = pathTotal.get(i) + product * pathCount.get(i);
				pathTotal.set(i, val1);
				pathCount.set(i, (long) 0);
			}
			sum += product;
		}

		// get best node and calculate average
		double average = Collections.max(pathTotal) / (double) sum;

		PrintWriter writer = new PrintWriter(new File("patrula.out"));
		writer.println(sum);
		writer.format("%.3f", average);
		writer.close();
	}

	/**
	 * Using BFS, calculates an array of distances from source to every node in
	 * graph
	 * 
	 * @param graph
	 *            graph
	 * @param source
	 *            source node
	 * @param destination
	 *            destination node
	 */
	public static void countMinPath(GraphT graph, int source, int destination) {

		int n = graph.getTotalNumOfNodes();

		distance = new int[n];
		int[] color = new int[n]; // 0 - white, 1 - grey, 2 - black

		// initializing distance and colors
		for (int i = 0; i < n; i++) {
			distance[i] = INF;
			color[i] = 0;
		}

		Queue<Integer> Q = new LinkedList<Integer>();
		distance[source] = 0; // distance to source is zero
		color[source] = 1; // started visiting node
		Q.add(source); // add source in queue

		while (!Q.isEmpty()) { // while there are nodes
			int u = Q.poll();
			List<Integer> adj = graph.getNeighboursOf(u);
			for (Integer v : adj) { // for every neighbors
				if (color[v] == 0) { // unvisited node
					distance[v] = distance[u] + 1; // update distance
					color[v] = 1; // update color
					Q.add(v); // add to queue
				}
			}
			color[u] = 2; // node was visited completely

		}
		pathLength = distance[destination];
	}

	/**
	 * Get the number of edges between two nodes
	 * 
	 * @param destination
	 *            one of edge's node
	 * @param adjList
	 *            the adjacent nodes for the other edge's node
	 * @return the number of occurrences of an edge
	 */
	public static int multipleEdges(int destination, List<Integer> adjList) {

		int occurrences = 0;
		for (int i = 0; i < adjList.size(); i++) {
			if (adjList.get(i) == destination) {
				occurrences++;
			}
		}

		return occurrences;
	}

	/**
	 * Build a list of paths from source to destination
	 * 
	 * @param graph
	 *            graph
	 * @param source
	 *            source node
	 * @param path
	 *            current path
	 */
	public static void getPaths(GraphT graph, int source, LinkedList<Integer> path) {
		// add current node to path
		path.addFirst(source);
		if (distance[source] == 0) { // reached destination
			paths.add(new LinkedList<Integer>(path)); // found a path
		}

		// for every node in graph
		for (int i = 0; i < graph.getTotalNumOfNodes(); i++) {
			// see if there is a path
			int dist = distance[source] - distance[i];
			if (dist == 1 && isPath[source][i]) {
				getPaths(graph, i, path); // find path from new node
			}

		}
		// remove current node from path
		path.removeFirst();

	}

}
