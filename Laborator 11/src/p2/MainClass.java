package p2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class StateComparator implements Comparator<State> {

	enum Algorithm {
		AStar
	}

	private Algorithm algorithm;

	/**
	 * Create a new StateComparator which compares two different states given
	 * the wanted algorithm.
	 * 
	 * @param algorithm
	 *            BestFirst or AStar
	 * @param solutionState
	 *            desired solution state
	 */
	public StateComparator(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	private int computeF(State state) {
		int result = 0;
		switch (algorithm) {
		case AStar:
			/* f(n) = g(n) + h(n) */
			/* g(n) = numarul de mutari din pozitia initiala */
			result = state.getDistance() + state.approximateDistance();
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

	public static void setData() {

		initialState = new State(3, 3, 0, 0, State.E, null, 0);
		solutionState = new State(0, 0, 3, 3, State.W, null, 0);
	}

	public static void main(String[] args) {
		/* setare stare initiala si finala */
		setData();

		Comparator<State> stateComparator = new StateComparator(StateComparator.Algorithm.AStar);
		PriorityQueue<State> open = new PriorityQueue<State>(1, stateComparator);

		/* Initial doar nodul de start este in curs de explorare */
		open.add(initialState);

		/* Pentru nodurile care au fost deja expandate. */
		ArrayList<State> closed = new ArrayList<State>();

		/* Numar pasi pana la solutie */
		int steps = 0;

		/* TODO: A* */
		int cost_from_n;
		while (true) {
			if (open.isEmpty()) { // nu avem solutie
				System.out.println("Failure");
				break;
			}
			State n = open.peek(); // luam starea cu f(n) minim
			open.poll();
			if (n.equals(solutionState)) { // am ajuns la solutie
				n.printPath();
				break;
			} else {
				if (!closed.contains(n)) { // nodul devine negru
					closed.add(n);
				}
				ArrayList<State> neigh = n.expand();
				for (State s : neigh) { // expandeaza vecinii
					State nPrim = null;
					cost_from_n = n.getDistance() + s.approximateDistance();
					if (!closed.contains(s) && !open.contains(s)) { // nu a fost
																	// expandat
																	// de alt
																	// nod
						nPrim = new State(s.misEast, s.canEast, s.misWest, s.canWest, s.boatPosition, s.getParent(),
								s.getDistance());
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
						if (cost_from_n < nPrim.getDistance()) { // am gasit un drum
																// mai bun
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
		System.out.println("Numarul de pasi pana la solutie: " + State.steps);
	}

}
