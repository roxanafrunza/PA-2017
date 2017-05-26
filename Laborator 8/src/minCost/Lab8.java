/**
 * Proiectarea Algoritmilor, 2016
 * Lab 8: Drumuri minime
 *
 * @author  Andrei Dinu
 * @email   mandrei.dinu@gmail.com
 */

package minCost;

import java.util.List;

import graph.GraphT;
import graph.NodeT;
import utils.MyReader;

public class Lab8 {

	/**
     * Problem configuration
     */
    final public static String startLabel = "Bucuresti";

    /* graph with positive edges */
    final public static String dataSet1 = "date1.in";
    /* graph with negative edges, Dijkstra should fail */
    final public static String dataSet2 = "date2.in";
    /* graph with negative cycle */
    final public static String dataSet3 = "date3.in";

    public enum Task{
        ROY_FLOYD,
        DIJKSTRA,
        BELLMAN_FORD
    }

    /* TODO: change dataSet and currentTask depending on the problem you are
     * currently running */
    public static String dataSet = dataSet1;
    public static Task currentTask = Task.DIJKSTRA;

    public static void main(String[] argv) {

    	GraphT<String, Integer> g = MyReader.readData(dataSet);
    	// debugging
    	//System.out.println(g);
    	NodeT<String> source = g.getNode(startLabel);


    	switch (currentTask) {

	    	case ROY_FLOYD:
	    	{
	    		FloydWarshall solver = new FloydWarshall(g);
	    		solver.computeTransitiveClosure();
	    		String s1 = "Budapesta", d1 = "Atena";
	    		String s2 = "Sofia", d2 = "Viena";
	    		testTranzClojure(solver, g, s1, d1);
	    		testTranzClojure(solver, g, s2, d2);
	    		break;
	    	}

	    	case DIJKSTRA:
	    	{
	    		OneSourceShortestPath solver = new OneSourceShortestPath(g);
	    		int[] distances = solver.applyDijkstra(source);
	    		printMinDistances(g, distances);
	    		break;
	    	}

	    	case BELLMAN_FORD:
	    	{
	    		OneSourceShortestPath solver = new OneSourceShortestPath(g);
	    		int[] distances = solver.applyBellmanFord(source);
	    		// take care of the values from distances[] in case of neg cycle
	    		printMinDistances(g, distances);
	    		break;
	    	}

	    	default: break;
    	}
    }

    public static void testTranzClojure(FloydWarshall solver,
    									GraphT<String, Integer> g,
    									String source,
    									String destination) {

    	boolean res = solver.isPathBetween(g.getNode(source),
    									   g.getNode(destination));

    	if (res) {
    		System.out.println("Exista drum intre " + g.getNode(source) +
    				" si intre " + g.getNode(destination));
    		solver.printMinPath(g.getNode(source), g.getNode(destination));
    	} else {
    		System.out.println("NU exista drum intre " + g.getNode(source) +
    				" si intre " + g.getNode(destination));
    	}
    	
    	
    }

    public static void printMinDistances(GraphT<String, Integer> g,
    									 int[] distances) {

    	System.out.println("Min distances:");
        List<NodeT<String>> nodes = g.nodes();

        for (int i = 0; i < g.size(); i++){
            System.out.println(nodes.get(i).info() + " , " + distances[i]);
        }
    }
}
