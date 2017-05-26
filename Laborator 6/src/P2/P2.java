package P2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Proiectarea Algoritmilor Lab 3: Parcurgerea Grafurilor. Sortare Topologica
 * Task 2 & 3: Planificarea studierii materiilor
 *
 * @author adinu
 * @email mandrei.dinu@gmail.com
 *
 */

public class P2 {

	/* Use to read, parse and write the output of the problem */
	private ReaderWriter rw;

	/* Holds the final order of subjects to be studied */
	private List<Integer> solution;

	/* The graph of dependencies between subjects */
	private GraphT graph;

	int[] colors; // 0 = alb, 1 = gri; 2 = negru
	int[] p;
	int[] sTime;
	int[] fTime;
	int time = 0;

	public P2(String fileName) {
		rw = new ReaderWriter(fileName);
		solution = new ArrayList<>();
		graph = rw.parseInput();
		colors = new int[graph.getTotalNumOfNodes()];
		p = new int[graph.getTotalNumOfNodes()];
		sTime = new int[graph.getTotalNumOfNodes()];
		fTime = new int[graph.getTotalNumOfNodes()];
	}

	/**
	 * Apply a topological sort algorithm to find the correct order of subjects
	 * to be studied
	 */
	void topologicalSort() {
		// TODO
		// use the GraphT graph data structre
		// perform a dfs algorithm on it
		// save the finalizing times for each node
		int n = graph.getTotalNumOfNodes();
		for (int i = 0; i < n; i++) {
			colors[i] = 0;
			p[i] = -1;
			sTime[i] = 0;
			fTime[i] = 0;
		}

		for (int i = 0; i < n; i++) {
			if (colors[i] == 0)
				DFS(graph, i);
		}

	}

	void DFS(GraphT graph, int node) {
		sTime[node] = time++;
		colors[node] = 1;
		for (Integer v : graph.getNeighboursOf(node)) {
			if (colors[v] == 0) {
				p[v] = node;
				DFS(graph, v);
			}
		}
		colors[node] = 2;
		fTime[node] = time++;

	}

	/**
	 * Apply a topological sort algorithm to find the correct order of subjects
	 * to be studied
	 */
	void topologicalSortByKahn() {
		// TODO BONUS
		// use the GraphT graph data structure
		// apply the kahn algorithm
		int n = graph.getTotalNumOfNodes();
		Stack<Integer> S = new Stack<Integer>();
		ArrayList<Integer> L = new ArrayList<Integer>();

		for(int i = 0; i < n; i++)
		{
			if(graph.getIndegreeOf(i) == 0)
				S.push(i);
		}
		while(!S.isEmpty()){
			int u = S.pop();
			L.add(u);
			List<Integer> neighbors = new ArrayList<Integer>(graph.getNeighboursOf(u));
			for(Integer v : neighbors){
				// remove u,v
				graph.removeEdge(u, v);
				if(graph.getIndegreeOf(v) == 0)
					S.push(v);
			}
		}
		
		System.out.println("====Algoritm KAHN====");
		rw.writeSolution(L);
	}

	void printSolution() {
		// TODO
		// use the corresponding method from ReaderWriter
		int n = graph.getTotalNumOfNodes();
		int[] initialOrder = new int[n];
		int aux;
		for (int i = 0; i < n; i++)
			initialOrder[i] = i;
		
		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++) {
				if (fTime[j] > fTime[i]) {
					aux = fTime[j];
					fTime[j] = fTime[i];
					fTime[i] = aux;

					aux = initialOrder[j];
					initialOrder[j] = initialOrder[i];
					initialOrder[i] = aux;
				}
			}
		
		List<Integer> solution = new ArrayList<Integer>();
		for(int i = 0; i < n; i++)
		{
			solution.add(initialOrder[i]);
		}
		
		rw.writeSolution(solution);

	}

	public static void main(String[] argv) {
		// TODO
		// create an object of type P2 and use it to solve our problem
		P2 solution = new P2("subjects.in");
		solution.topologicalSort();
		solution.printSolution();
		
		solution.topologicalSortByKahn();
	}
}
