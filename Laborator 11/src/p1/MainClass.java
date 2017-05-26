package p1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class StateComparator implements Comparator<State> {

	enum Algorithm {
		BestFirst, AStar
	}

	private Algorithm algorithm;
	private State solutionState;

	/**
	 * Create a new StateComparator which compares two different states given
	 * the wanted algorithm and solution state.
	 * 
	 * @param algorithm
	 *            BestFirst or AStar
	 * @param solutionState
	 *            desired solution state
	 */
	public StateComparator(Algorithm algorithm, State solutionState) {
		this.algorithm = algorithm;
		this.solutionState = solutionState;
	}

	private int computeF(State state) {
		int result = 0;
		/* f(n) = g(n) + h(n) */
		switch (algorithm) {
		case BestFirst:
			/* g(n) = 0 */
			result = state.approximateDistance(solutionState);
		case AStar:
			/* g(n) = numarul de mutari din pozitia initiala */
			result = state.getDistance() + state.approximateDistance(solutionState);
		}
		return result;
	}

	@Override
	public int compare(State arg0, State arg1) {
		return computeF(arg0) - computeF(arg1);
	}
}

public class MainClass {

	static State initialState;
	static State solutionState;

	public static void readData(String filename) {
		int x, y;
		int numRows, numCols;

		Scanner scanner;
		try {
			scanner = new Scanner(new File(filename));

			/* read map stats */
			numRows = scanner.nextInt();
			numCols = scanner.nextInt();
			State.init(numRows, numCols);

			x = scanner.nextInt();
			y = scanner.nextInt();
			initialState = new State(x, y);

			x = scanner.nextInt();
			y = scanner.nextInt();
			solutionState = new State(x, y);

			/* read the map */
			for (int i = 0; i < State.numRows; i++)
				for (int j = 0; j < State.numCols; j++)
					if (scanner.nextInt() == 1)
						State.matrix[i][j] = true;

			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/* Citire harta, stare initiala si finala */
		readData("Puzzle.in");

		Comparator<State> stateComparator = new StateComparator(StateComparator.Algorithm.AStar, solutionState);
		PriorityQueue<State> open = new PriorityQueue<State>(1, stateComparator);

		/* Initial doar nodul de start este in curs de explorare */
		open.add(initialState);

		/* Pentru nodurile care au fost deja expandate. */
		ArrayList<State> closed = new ArrayList<State>();

		/* TODO: A* */
		int steps = 0;
		initialState.setDistance(0);
		initialState.setParent(null);
		int cost_from_n;
		while (true) {
			steps++;
			if (open.isEmpty()) { // nu avem solutie
				System.out.println("Failure");
				break;
			}
			State n = open.peek(); // luam starea cu f(n) minim
			open.poll();
			if (n.equals(solutionState)) { // am ajuns la solutie
				n.printPath();
				break;
			} else { // expandeaza starea
				if (!closed.contains(n)) { // nodul devine negru
					closed.add(n);
				}
				ArrayList<State> neigh = n.expand();
				for (State s : neigh) { // expandeaza vecinii
					State nPrim = null;
					cost_from_n = n.getDistance() + n.approximateDistance(s);
					if (!closed.contains(s) && !open.contains(s)) { // nu a fost
																	// generata
																	// de alt
																	// nod
						nPrim = new State(s.x, s.y, s.getParent(), s.getDistance());
						open.add(nPrim);
					} else {
						if (closed.contains(s)) { // caut in closed
							for (State sPrim : closed)
								if (s.equals(sPrim)) {
									nPrim = s;
									break;
								}
						} else if (open.contains(s)) { // caut in open
							for (State sPrim : open)
								if (s.equals(sPrim)) {
									nPrim = sPrim;
									break;
								}
						}
						if (cost_from_n < nPrim.getDistance()) { // am gasit drum mai bun
							nPrim.setParent(n);
							nPrim.setDistance(cost_from_n);
							if (closed.contains(nPrim)) {
								open.add(nPrim);
								closed.remove(nPrim);
							}
						}
					}
				}

			}
		}
		System.out.println("Numarul de pasi pana la solutie: " + steps);
	}

}
