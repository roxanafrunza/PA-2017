/**
 * Proiectarea Algoritmilor, 2016
 * Lab 7: Aplicatii DFS
 *
 * @author 	Radu Iacob
 * @email	radu.iacob23@gmail.com
 * @author adinu
 * @email  mandrei.dinu@gmail.com
 */

package lab7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import graph.Graph;
import graph.Node;
import graph.Pair;

public class p2 {

	static void DfsArticulationPoints(Graph g, Node node, int idx, int father) {
		// TODO
		node.discoveryTime = idx;
		node.lowLink = idx;
		idx++;
		ArrayList<Node> child = new ArrayList<Node>();
		ArrayList<Node> edges = g.getEdges(node);

		for (Node v : edges) {
			if (v.wasVisited() == false) {
				child.add(v);
				DfsArticulationPoints(g, v, idx, father + 1);
				node.lowLink = Math.min(node.lowLink, v.lowLink);
			} else {
				node.lowLink = Math.min(node.lowLink, v.discoveryTime);
			}
		}

		if (father == -1) {
			if (child.size() >= 2)
				g.articulationPoints.add(node);
		} else {
			for (Node v : child) {
				if (v.lowLink >= node.discoveryTime) {
					g.articulationPoints.add(node);
				}
			}
		}

	}

	/**
	 * Computes and prints at standard output the Articulation Points of the
	 * </br>
	 * given graph. </br>
	 * 
	 * Useful API: </br>
	 * 
	 * List with the articulation points of the graph. </br>
	 * graph.articulationPoints </br>
	 * 
	 * @param g
	 */
	static void ArticulationPoints(Graph g) {
		g.reset();

		// TODO
		// HINT: use dfsArticulationPoints after you implement it
		ArrayList<Node> nodes = g.getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).wasVisited() == false)
				DfsArticulationPoints(g, nodes.get(i), 0, -1);
		}
		// TODO: uncomment this after you're done

		System.out.println("\nPuncte. de articulatie: \n" + g.articulationPoints);

	}

	static void DfsCriticalEdges(Graph g, Node node, int idx, int father) {
		// TODO
		node.discoveryTime = idx;
		node.lowLink = idx;
		idx++;
		ArrayList<Node> edges = g.getEdges(node);

		for (Node v : edges) {
			if (father != v.getId()) {
				if (v.wasVisited() == false) {
					DfsCriticalEdges(g, v, idx, father + 1);
					node.lowLink = Math.min(node.lowLink, v.lowLink);
					if (v.lowLink > node.lowLink) // v.lowLink > node.discoveryTime
						g.criticalEdges.add(new Pair<Node, Node>(node, v));
				} else {
					node.lowLink = Math.min(node.lowLink, v.discoveryTime);
				}
			}
		}
	}

	/**
	 * Computes and prints at standard output the Critical Edges of the given
	 * graph. </br>
	 * 
	 * Useful API: Lista with the critical edges. </br>
	 * graph.criticalEdges </br>
	 *
	 * @param g
	 */
	static void CriticalEdges(Graph g) {
		g.reset();

		// TODO
		// Hint: use dfsCriticalEdges after you implement it
		ArrayList<Node> nodes = g.getNodes();
		for (Node v : nodes) {
			if (v.wasVisited() == false)
				DfsCriticalEdges(g, v, 0, -1);
		}
		// TODO: uncomment this after you're done
		System.out.println("\nMuchii critice: \n" + g.criticalEdges);
	}

	final static String PATH = "./res/test02";

	public static void main(String... args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(PATH));

		int test_count = scanner.nextInt();
		for (int i = 1; i <= test_count; ++i) {
			System.out.println("TEST " + i + "\n");

			Graph g = new Graph(Graph.GraphType.UNDIRECTED);
			g.readData(scanner);

			System.out.println(g);
			ArticulationPoints(g);
			CriticalEdges(g);
		}

		scanner.close();
	}
}
