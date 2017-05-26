package sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import utils.GraphT;
import utils.MyScanner;

public class Permutari {
	public static void main(String[] args) throws FileNotFoundException {

		MyScanner reader = new MyScanner("permutari.in");
		PrintWriter writer = new PrintWriter(new File("permutari.out"));

		// create a graph with nodes for each letter
		ConvertGraph cv = new ConvertGraph();
		GraphT graph = cv.createGraph();

		boolean impossible = false;
		int minLen;
		int N = reader.nextInt();
		String firstWord = reader.next();
		String secondWord;

		for (int i = 1; i < N; i++) { // read every word
			secondWord = reader.next();
			minLen = Math.min(firstWord.length(), secondWord.length());
			// check if first word is longer than second
			if (minLen == secondWord.length()) {
				// if first word begins with the second word
				if (firstWord.substring(0, minLen).equals(secondWord) == true) {
					writer.println("Imposibil"); // can't sort
					impossible = true;
					break;
				}
			}
			// find the letter where the words are different
			for (int j = 0; j < minLen; j++) {
				if (firstWord.charAt(j) != secondWord.charAt(j)) {
					// add edge in graph
					cv.updateGraph(graph, firstWord.charAt(j), secondWord.charAt(j));
					break;
				}
			}
			firstWord = secondWord;
		}

		// do topological sort
		if (impossible == false) {
			List<Integer> res = topologicalSortByKahn(graph);
			if (res == null) { // cyclic graph
				writer.println("Imposibil");
			} else { // write solution
				writer.println(cv.writeSolution(res));
			}
		}
		writer.close();
	}

	static List<Integer> topologicalSortByKahn(GraphT graph) {

		int n = graph.getTotalNumOfNodes();
		Stack<Integer> stack = new Stack<Integer>();
		// list which will have the sorted nodes
		List<Integer> sorted = new ArrayList<Integer>();

		// push in stack the nodes with no in-edges
		for (int i = 0; i < n; i++) {
			if (graph.getIndegreeOf(i) == 0) {
				stack.push(i);
			}
		}
		while (!stack.isEmpty()) {
			int u = stack.pop(); // get a node from stack
			sorted.add(u); // add it in sorted list

			List<Integer> neighbors = new ArrayList<Integer>(graph.getNeighboursOf(u));
			for (Integer v : neighbors) { // for all neighbors
				graph.removeEdge(u, v); // remove u,v
				if (graph.getIndegreeOf(v) == 0) { // v has no in-edges
					stack.push(v); // add v to S
				}
			}
		}
		// cyclic graph
		if (graph.getTotalNumOfEdges() != 0) {
			return null;
		}
		return sorted;
	}
}
