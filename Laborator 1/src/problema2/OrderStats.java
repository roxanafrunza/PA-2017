package problema2;

/**
 * Proiectarea Algoritmilor
 * Lab 1: Divide et Impera
 * Task 2.1: Statistici de ordine: al k-lea element (+ quick sort)
 *
 * @author 	Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Scanner;

public class OrderStats {

	public static final int NO_TESTS = 2;
	public int[][] v = new int[NO_TESTS][]; // arrays in with input data

	public static void main(String[] args) {
		OrderStats os = new OrderStats();
		os.readData("date2.in");
		os.test();
	}

	/**
	 * for each of the tests compute order statistics and sort
	 */
	private void test() {
		/* for each of the tests */
		for (int test = 0; test < NO_TESTS; test++) {
			/* aflam al k-lea element, pentru toti k */
			for (int k = 0; k < v[test].length; k++) {
				int kth = kthMin(v[test], 0, v[test].length - 1, k);
				System.out.print("In {");
				for (int e : v[test])
					System.out.print(e + " ");
				System.out.println("} al " + (k + 1) + "-lea element ca ordine este " + kth + ".");
			}
			/* sortam vectorul folosind qsort definit mai jos */
			qsort(v[test], 0, v[test].length - 1);
			System.out.print("iar vectorul sortat este {");
			for (int e : v[test])
				System.out.print(e + " ");
			System.out.println("}");
		}
	}

	int kthMin(int[] v, int lower, int upper, int k) {
		/*
		 * TODO Completati codul pentru a afla al k-lea minim din vectorul v
		 * trebuie sa adaugati si o functie de partitionare (ca la quick sort)
		 */
		if (upper == lower && k == upper)
			return v[k];

		if (lower < upper) {
			int m = partition(v, lower, upper);
			if (k == m)
				return v[k];
			if (k < m)
				return kthMin(v, lower, m - 1, k);
			if (k > m)
				return kthMin(v, m + 1, upper, k);
		}
		return 0;
	}

	void qsort(int[] v, int lower, int upper) {
		/*
		 * TODO Completati codul pentru a realiza quicksort folositi aceeasi
		 * functie de partitionare scrisa pentru kthMin
		 */
		if (lower < upper) {
			int m = partition(v, lower, upper);
			qsort(v, lower, m - 1);
			qsort(v, m + 1, upper);
		}
	}

	int partition(int[] v, int lower, int upper) {
		int pivot = v[lower + (upper - lower) / 2];
		int tmp;
		while (lower < upper) {
			while (v[lower] < pivot)
				lower++;
			while (v[upper] > pivot)
				upper--;
			if (lower < upper) {
				tmp = v[lower];
				v[lower] = v[upper];
				v[upper] = tmp;
			}
		}
		return lower;
	}

	/**
	 * Function to read all the tests as pairs of arrays
	 * 
	 * @param filename
	 */
	private void readData(String filename) {
		Scanner scanner = null;

		/* you should use try-catch for proper error handling! */
		try {

			scanner = new Scanner(new File(filename));

			for (int i = 0; i < NO_TESTS; i++) {

				/* read the array in which to look for data */
				int n = scanner.nextInt(); // array length
				v[i] = new int[n];
				for (int j = 0; j < n; j++) {
					v[i][j] = scanner.nextInt();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/* trebuie sa inchidem buffered reader-ul */
			try {
				if (scanner != null)
					scanner.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
