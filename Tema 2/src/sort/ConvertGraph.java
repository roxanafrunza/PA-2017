package sort;

import java.util.ArrayList;
import java.util.List;
import utils.GraphT;

public class ConvertGraph {

	/**
	 * Create a graph with 26 nodes, each node corresponding to a letter in
	 * English alphabet. 0 -> 'a', 1 -> 'b' etc.
	 * 
	 * @return graph
	 */
	public GraphT createGraph() {
		GraphT graph = new GraphT(26);

		return graph;
	}

	/**
	 * Add an edge in graph. Gets the corresponding index for the given letters
	 * 
	 * @param g
	 *            graph
	 * @param fromNode
	 *            source node
	 * @param toNode
	 *            destination node
	 */
	public void updateGraph(GraphT g, char fromNode, char toNode) {
		int from = fromNode - 'a';
		int to = toNode - 'a';
		g.addEdge(from, to);
	}

	/**
	 * Given a list of nodes, it creates the string corresponding with the
	 * letters order
	 * 
	 * @param sortedNodes
	 *            list of nodes
	 * @return string with sorted letters
	 */
	public String writeSolution(List<Integer> sortedNodes) {

		String solution = "";
		List<Character> outputAsStrings = new ArrayList<>();
		char value;

		for (Integer letterIdx : sortedNodes) {
			value = (char) (letterIdx + 'a');
			outputAsStrings.add(value);
		}

		for (Character letter : outputAsStrings) {
			solution += letter;
		}

		return solution;
	}

}
