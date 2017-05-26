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
import java.util.HashSet;
import java.util.Scanner;

import graph.Graph;
import graph.Node;

public class p1 {

	public static int index = 0;

	/**
	 * Algoritmul lui Tarjan pentru determinarea componentelor tare conexe </br>
	 * Complexitate: O(N + M) </br>
	 * unde </br>
	 * N - nr de noduri </br>
	 * M - nr de muchii </br>
	 * 
	 * Useful API: </br>
	 * 
	 * Lista cu vecinii nodului </br>
	 * graph.getEdges(Node) </br>
	 * 
	 * Stiva cu nodurile din componenta tare conexa curenta. </br>
	 * graph.stack </br>
	 * 
	 * Variabila booleana - true daca nodul se afla pe stiva </br>
	 * node.inStack </br>
	 * 
	 * Timpul de descoperire al unui nod. </br>
	 * node.discoveryTime </br>
	 * 
	 * In functie de timpul de descoperire, intoarce true daca </br>
	 * nodul a mai fost vizitat. </br>
	 * node.wasVisited() </br>
	 * 
	 * Cel mai mic timp de descoperire </br>
	 * al unui nod accesibil din nodul curent. </br>
	 * node.lowLink </br>
	 * 
	 * Indexul componentei conexe din care face parte nodul. </br>
	 * node.ctcIndex </br>
	 */

	static void dfsCTC(Graph g, Node node) {

		/** TODO: Initializeaza timpul de descoperire si lowlink */
		node.discoveryTime = index;
		node.lowLink = index;
		index++;
		/** TODO: Adauga nodul in stiva */
		g.stack.push(node);
		node.inStack = true;
		
		/** TODO: Parcurgere DF pentru fiecare vecin nevizitat */
		ArrayList<Node> neighbors = g.getEdges(node);
		for (Node v : neighbors) {
			if (v.wasVisited() == false) {
				dfsCTC(g, v);
				node.lowLink = Math.min(v.lowLink, node.lowLink);
			} else {
				if (v.inStack == true) {
					node.lowLink = Math.min(v.discoveryTime, node.lowLink);
				}
			}
		}
		//terminat de parcurs nod, il scoatem din stiva
		node.inStack = false;
		
		Node u;
		ArrayList<Node> aux = new ArrayList<Node>();
		/** TODO: Salveaza componenta tare conexa curenta */
		if (node.discoveryTime == node.lowLink) {
			do {
				u = g.stack.pop();
				node.ctcIndex = u.getId();
				aux.add(u);
			} while(u.getId() != node.getId());
		}
		if (!aux.isEmpty())
			g.ctc.add(aux);
	}

	/**
	 * Identifica componentele tare conexe din graful primit ca parametru.
	 */
	static void StronglyConnectedComponents(Graph g) {

		g.reset(); // reseteaza starea variabilelor auxiliare din graf

		/** TODO: Apeleaza dfs_ctc pentru fiecare nod nevizitat. */
		index = 0;
		g.stack.clear();
		ArrayList<Node> nodes = g.getNodes();
		for (Node u : nodes) {
			if (u.discoveryTime == Node.UNSET) {
				dfsCTC(g,u);
			}

		}
		System.out.println();
		g.printCTC(); // afiseaza componentele tare conexe
	}

	final static String PATH = "./res/test01";

	public static void main(String... args) throws FileNotFoundException {

		Scanner scanner = new Scanner(new File(PATH));

		int test_count = scanner.nextInt();
		for (int i = 1; i <= test_count; ++i) {
			System.out.println("TEST " + i + "\n");
			Graph g = new Graph(Graph.GraphType.DIRECTED);
			g.readData(scanner);
			System.out.print("Input " + g);
			StronglyConnectedComponents(g);
			// bonus(g);
			System.out.println("###########################");
		}

		scanner.close();
	}

	/**
	 * TODO: Calculeaza numarul maxim de jucatori daca s-ar adauga o </br>
	 * legatura artificiala in graf. </br>
	 * Complexitate solutie: O( N + M ) </br>
	 * N - numarul de noduri </br>
	 * M - numarul de muchii </br>
	 */

	static void bonus(Graph g) {

		/**
		 * HINT: In urma compactarii grafului original rezulta un graf orientat,
		 * fara cicluri, pe care il vom numi in mod sugestiv, Cluster (mai multe
		 * noduri grupate in acelasi loc)
		 */

		Graph cluster = compressGraph(g);

		/*
		 * HINT: 1) Se sorteaza topologic graful aciclic al componentelor tare
		 * conexe obtinute si sa retine solutia 2) Se tine si se updateaza la
		 * fiecare pas intr-un vector auxiliar lungimea maxima a unui lant
		 * format pana in acel punct din nodurile clusterului, daca am adauga o
		 * legatura artificiala.
		 * 
		 * Exemplu (dupa efectuarea sortarii topologice): C1 -> C2 -> C3 C4 ->
		 * C5 |____________| |______|
		 * 
		 * C1..5 sunt noduri ale clusterului (clanuri) Avem doua posibilitati:
		 * fie lantul C1, C2, C3, caz in care vom alege sa unim un nod din C3 cu
		 * un nod din C1 (nu conteaza care atata timp cat exista conectivitate),
		 * fie lantul format din C4, C5, caz in care vom uni un nod din
		 * componenta C5 cu unul din componenta C4.
		 * 
		 * Astfel, de exemplu, pe pozitia 3 din vector vom avea un numar de
		 * noduri egal cu suma tuturor nodurilor din C1, C2, respectiv C3, iar
		 * pe pozitia 5, vom avea numarul nodurilor insumate din clanurile C4 si
		 * C5. La final vom lua maximul dintre aceste valori, reprezentand
		 * numarul maxim de jucatori obtinut, daca s-ar adauga o legatura
		 * articifiala.
		 */

		// TODO afiseaza numarul de noduri maxim obtinut in urma adaugarii
		// legaturii artificiale

	}

	/**
	 * Construim un nou graf, unind elementele care alcatuiau o componenta tare
	 * conexa intr-un singur nod.
	 */

	static Graph compressGraph(Graph inputGraph) {

		int countCTC = inputGraph.ctc.size();
		if (countCTC == 0) {
			StronglyConnectedComponents(inputGraph);
			countCTC = inputGraph.ctc.size();
		}

		/**
		 * Alocam un nod nou pentru fiecare componenta tare conexa din graful
		 * original
		 **/
		Graph cluster = new Graph(Graph.GraphType.DIRECTED);
		for (int i = 0; i < countCTC; ++i) {
			cluster.insertNode(new Node(i));
		}

		ArrayList<Node> all_clusters = cluster.getNodes();

		for (int i = 0, sz = inputGraph.ctc.size(); i < sz; ++i) {
			// nodurile care alcatuiesc componenta conexa curenta
			ArrayList<Node> inner_nodes = inputGraph.ctc.get(i);

			// folosim un Set pentru a ne asigura ca nu avem muchii duplicate
			HashSet<Integer> connections = new HashSet<Integer>();

			// Pentru fiecare nod..
			for (Node n : inner_nodes) {
				ArrayList<Node> edges = inputGraph.getEdges(n);

				// Pentru fiecare muchie..
				for (Node v : edges) {
					int idx_ctc = v.ctcIndex;
					if (idx_ctc != i && !connections.contains(idx_ctc)) {
						cluster.insertEdge(all_clusters.get(i), all_clusters.get(idx_ctc));
						connections.add(idx_ctc);
					}
				}
			}
			// System.out.println(inner_nodes);
			all_clusters.get(i).countNodes = inner_nodes.size();
		}

		System.out.println("Graful rezultat in urma compactarii: ");
		System.out.println(cluster);

		return cluster;
	}

}
