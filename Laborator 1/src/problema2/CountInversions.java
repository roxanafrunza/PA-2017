package problema2;

/**
 * Proiectarea Algoritmilor
 * Lab 1: Divide et Impera
 * Task 2.2: Se da un sir S de lungime n. Sa se detemine cate inversiuni sunt in sirul dat.
 *
 * @author 	Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class CountInversions {

	public static final int NO_TESTS = 2;
	public int[][] v = new int[NO_TESTS][]; // arrays in with input data

	public static void main(String[] args) {
		CountInversions ci = new CountInversions();
		ci.readData("date2.in");
		ci.test();
	}

	/**
	 * for each of the tests count the inversions
	 */
	private void test() {
		/* for each of the tests */
		for (int test = 0; test < NO_TESTS; test++) {
			int[] aux = new int[v[test].length];
			System.arraycopy(v[test], 0, aux, 0, v[test].length);
			int inversions = countInversions(aux);
			System.out.print("In {");
			for (int e : v[test])
				System.out.print(e + " ");
			System.out.println("} sunt " + inversions + " inversiuni.");
		}
	}

	int countInversions(int[] vec) {
		// TODO Intoarceti numarul de inversiuni din vectorul v
		// un vector cu mai putin de 2 elemente nu are nicio inversiune
		if (vec.length < 2)
			return 0;

		int middle = (vec.length + 1) / 2;

		// prima jumatate a vectorului
		int[] left = new int[middle];
		System.arraycopy(vec, 0, left, 0, middle);

		// a doua jumatate a vectorului
		int[] right = new int[vec.length - middle];
		System.arraycopy(vec, middle, right, 0, vec.length - middle);

		return countInversions(left) + countInversions(right) + merge(vec, left, right);
	}

	int merge(int[] vec, int[] left, int[] right) {
		int i = 0;
		int j = 0;
		int number = 0;

		while (i < left.length || j < right.length) {

			// am ajuns la finalul vectorului left
			if (i == left.length) {
				vec[i + j] = right[j++];
				j++;
			}
			// am ajuns la finalul vectorului right
			else if (j == right.length) {
				vec[i + j] = left[i];
				i++;
			}
			// elementele nu sunt inversate
			else if (left[i] <= right[j]) {
				vec[i + j] = left[i];
				i++;
			}
			// Perechea curenta este inversiune
			else if (left[i] > right[j]) {
				vec[i + j] = right[j];
				j++;

				number += left.length - i;
			}
		}
		return number;
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
