package problema2;

/**
 * Proiectarea Algoritmilor
 * Lab 1: Divide et Impera
 * Task 2.3: Se dau n-1 numere naturale distincte intre 0 si n -1. Se cere numarul lipsa.
 *
 * @author 	Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Scanner;

public class FindMissing {

	public static final int NO_TESTS = 2;
	public int[][] v = new int[NO_TESTS][]; // arrays in with input data

	public static void main(String[] args) {
		FindMissing fm = new FindMissing();
		fm.readData("date2.in");
		fm.test();
	}

	/**
	 * for each of the tests count the inversions
	 */
	private void test() {
		/* for each of the tests */
		for (int test = 0; test < NO_TESTS; test++) {
			int missing = findMissing(v[test]);
			System.out.print("In {");
			for (int e : v[test])
				System.out.print(e + " ");
			System.out.println("} elementul lipsa este " + missing + ".");
		}
	}

	private int findMissing(int[] vector) {
		int[] vec = vector.clone();
		/*
		 * TODO Cautati binar elementul lipsa din vector va trebui sa adaugati
		 * si o functie de partitionare, ca la quick sort
		 */
		int lower = 0;
		int upper = vec.length - 1;
		while (lower < upper) {
			int pivot = partition(vec, lower, upper);
			if (pivot != vec[pivot]) {
				upper = pivot - 1;
			}
			else if(pivot == vec[pivot])
				lower = pivot + 1;

		}
		return lower;
	}

	int partition(int[] v, int lower, int upper) {
		int pivot = v[lower + (upper - lower) / 2];
		int tmp;
		
		//punem pivotul pe ultima pozitie
		tmp = v[pivot];
		v[pivot] = v[upper];
		v[upper] = tmp;
		
		while (lower < upper) {
			//mergem cu lower pana ajungem la valoare mai mare ca pivot
			while (v[lower] < pivot)
				lower++;
			// mergem cu upper pana ajungem la valoare mai mica ca pivot
			while (v[upper] > pivot)
				upper--;
			//interschimb v[lower] cu v[upper]
			tmp = v[lower];
			v[lower] = v[upper];
			v[upper] = tmp;

		}
		//punem pivotul la loc pe pozitia lower
		tmp = v[pivot];
		v[pivot] = v[lower];
		v[lower] = tmp;
		
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
