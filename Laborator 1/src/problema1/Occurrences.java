package problema1;

/**
 * Proiectarea Algoritmilor
 * Lab 1: Divide et Impera
 * Task 1.1: Se da un sir sortat. Gasiti numarul de elemente egale cu x din sir.
 *
 * Codul citeste un set de teste din fisierul de date, fiecare test constand din:
 * - un set de numere ce trebuiesc cautate intr-un vector
 * - un vector in care trebuiesc cautate numerele
 *
 * @author 	Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Scanner;

public class Occurrences {

	public static final int NO_TESTS = 2;
	public int[][] haystacks = new int[NO_TESTS][]; // arrays in which to look
													// for data (max 10)
	public int[][] needles = new int[NO_TESTS][]; // data to be looked for in
													// arrays

	public static void main(String[] args) {
		Occurrences o = new Occurrences();
		o.readData("date.in");
		o.test();
	}

	/**
	 * for each of the tests count how many times each of the needles appears in
	 * the haystack
	 */
	private void test() {
		/* for each of the tests */
		for (int test = 0; test < NO_TESTS; test++) {
			/* for each of the needles for that test */
			for (int needle : needles[test]) {
				int occurrences = countOccurrences(haystacks[test], needle, 0, haystacks[test].length - 1);
				System.out.print("In {");
				for (int e : haystacks[test])
					System.out.print(e + " ");
				System.out.println("}, " + needle + " apare de " + occurrences + " ori.");
			}
		}
	}

	private int countOccurrences(int[] v, int key, int lower, int upper) {
		// TODO
		// Calculati recursiv numarul de aparitii ale lui key in v
		// intre pozitiile lower si upper.
		// La calcularea pozitiei de mijloc folositi
		// int m = lower + (upper - lower) / 2;
		// pentru a evita overflow la adunarea pe numere intregi.

		// conditia de oprire
		if (lower > upper)
			return 	0;
		
		// etapa divide
		int middle = lower + (upper - lower) / 2;

		// etapa stapaneste
		if(v[middle] == key)
		{
			int count = 1;
			int i = middle + 1;
			int j = middle - 1;
			while(i <= upper && v[i] == key)
			{
				count++;
				i++;
			}
			while(j >= lower && v[j] == key)
			{
				count++;
				j--;
			}
			return count;
		}
		//cautam in prima jumatate
		if (v[middle] > key)
			return countOccurrences(v, key, lower, middle - 1);
		//cautam in a doua jumatate
		if (v[middle] < key)
			return countOccurrences(v, key, middle + 1, upper);
		return 0;
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
				haystacks[i] = new int[n];
				for (int j = 0; j < n; j++) {
					haystacks[i][j] = scanner.nextInt();
				}

				/* read the array in which to look for data */
				n = scanner.nextInt(); // array length
				needles[i] = new int[n];
				for (int j = 0; j < n; j++) {
					needles[i][j] = scanner.nextInt();
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
