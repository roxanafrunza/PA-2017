/**
 * Proiectarea Algoritmilor, 2016
 * Lab 9: Arbori minimi de acoperire
 *
 * @author  adinu
 * @email   mandrei.dinu@gmail.com
 */

package lab9;

import graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

class CostComparator implements Comparator<Pair<Pair<Integer, Integer>, Integer>> {

	@Override
	public int compare(Pair<Pair<Integer, Integer>, Integer> arg0, Pair<Pair<Integer, Integer>, Integer> arg1) {
		if (arg0.second() < arg1.second())
			return -1;
		if (arg0.second() > arg1.second())
			return 1;
		return 0;
	}

}

class DistanceComparator implements Comparator<Pair<Integer, Integer>> {

	@Override
	public int compare(Pair<Integer, Integer> arg0, Pair<Integer, Integer> arg1) {
		if (arg0.second() < arg1.second())
			return -1;
		if (arg0.second() > arg1.second())
			return 1;
		return 0;
	}

}

public class Main {

	final static String PATH = "test01.in";
	final static int INF = 12345678;

	public static void main(String... args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(PATH));

		// create the graph and read the data
		Graph g = new Graph();
		g.readData(sc);
		// debugging: print the graph
		System.out.println(g);
		ArrayList<Pair<Integer, Integer>> kruskalSolution = Kruskal(g);

		System.out.println("KRUSKAL");
		for (Pair<Integer, Integer> edge : kruskalSolution)
			System.out.println(edge.first() + " " + edge.second());

		List<Pair<Integer, Integer>> primSolution = Prim(g, 0);
		System.out.println("PRIM");
		for (Pair<Integer, Integer> edge :primSolution)
			System.out.println(edge.first() + " " + edge.second());

		sc.close();
	}

	public static ArrayList<Pair<Integer, Integer>> Kruskal(Graph g) {
		ArrayList<Pair<Integer, Integer>> muchiiAMA = new ArrayList<Pair<Integer, Integer>>();
		// muchie - pair<integer,integer>
		// muchii - multime de pair<muchie,cost>
		ArrayList<Pair<Pair<Integer, Integer>, Integer>> muchii = new ArrayList<Pair<Pair<Integer, Integer>, Integer>>();

		// creare set
		ArrayList<Set<Integer>> sets = new ArrayList<Set<Integer>>(g.getNodeCount());

		// creare multime de muchii
		for (int i = 0; i < g.getNodeCount(); i++) {
			List<Pair<Integer, Integer>> adjNodes = g.getEdges(i);
			for (Pair<Integer, Integer> adj : adjNodes) {
				int cost = adj.second();
				Pair<Integer, Integer> m = new Pair<Integer, Integer>(i, adj.first());
				Pair<Pair<Integer, Integer>, Integer> muchie = new Pair<Pair<Integer, Integer>, Integer>(m, cost);
				muchii.add(muchie);
			}
			Set<Integer> set = new HashSet<Integer>();
			set.add(i);
			sets.add(set); // make set
		}

		Collections.sort(muchii, new CostComparator());

		for (Pair<Pair<Integer, Integer>, Integer> muchie : muchii) {
			Integer u = muchie.first().first();
			Integer v = muchie.first().second();
			int uSet = getSet(u, sets);
			int vSet = getSet(v, sets);

			if (uSet != vSet && uSet != -1 && vSet != -1) {
				muchiiAMA.add(muchie.first());
				sets.get(uSet).addAll(sets.get(vSet));
				sets.remove(vSet);
			}
		}
		return muchiiAMA;
	}

	public static int getSet(int node, ArrayList<Set<Integer>> sets) {
		for (int i = 0; i < sets.size(); i++) {
			Set<Integer> currentSet = sets.get(i);
			for (Integer j : currentSet)
				if (j == node)
					return i;
		}

		return -1;
	}

	public static List<Pair<Integer, Integer>> Prim(Graph g, int root) {
		List<Pair<Integer, Integer>> muchiiAMA = new ArrayList<Pair<Integer, Integer>>();
		// pair : first-> nodul; second -> distanta pana la radacina
		PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(g.getNodeCount(), new DistanceComparator());
		Integer[] d = new Integer[g.getNodeCount()];
		Integer[] p = new Integer[g.getNodeCount()];
		int[][] w = getCost(g);
		
		for (int i = 0; i < g.getNodeCount(); i++) {
			d[i] = INF; // initial distantele sunt infinit
			p[i] = null; // nu exista predecesor
		}
		d[root] = 0;
		
		// creare heap
		for (int i = 0; i < g.getNodeCount(); i++) {
			Pair<Integer, Integer> pair = new Pair<Integer, Integer>(i, d[i]);
			pq.add(pair);
		}

		while(!pq.isEmpty())
		{
			Pair<Integer, Integer> u = pq.poll();
			Pair<Integer, Integer> edge = new Pair<Integer,Integer>(u.first(), p[u.first()]);
			if(!contains(edge,muchiiAMA))
				muchiiAMA.add(edge);
			
			List<Pair<Integer,Integer>> adjList = g.getEdges(u.first());
			for(Pair<Integer,Integer> v : adjList)
			{
				if(w[u.first()][v.first()] < d[v.first()])
				{

					Pair<Integer, Integer> oldV = new Pair<Integer, Integer> (v.first(), d[v.first()]);
					pq.remove(oldV);
					
					d[v.first()] = w[u.first()][v.first()];
					p[v.first()] = u.first();
					
					Pair<Integer, Integer> newV = new Pair<Integer, Integer> (v.first(), d[v.first()]);
					pq.add(newV);
				}
			}
			
		}

		Pair<Integer, Integer> toBeRemoved = new Pair<Integer, Integer>(root, p[root]); 
		muchiiAMA.remove(toBeRemoved);
		
		return muchiiAMA;

	}

	public static int[][] getCost(Graph g) {
		int[][] w = new int[g.getNodeCount()][g.getNodeCount()];

		for (int u = 0; u < g.getNodeCount(); u++) {
			List<Pair<Integer, Integer>> adjList = g.getEdges(u);
			for (int v = 0; v < adjList.size(); v++) {
				Pair<Integer,Integer> node = adjList.get(v);
				w[u][node.first()] = node.second();
			}
		}

		return w;
	}
	
	public static boolean contains(Pair<Integer,Integer> edge,List<Pair<Integer, Integer>> edges)
	{
		Pair<Integer,Integer> aux = new Pair<Integer,Integer>(edge.second(), edge.first());
		if(edges.contains(edge))
			return true;
		if(edges.contains(aux))
			return true;
		return false;
		
	}

}
