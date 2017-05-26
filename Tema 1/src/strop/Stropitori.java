package strop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import utils.MyScanner;

public class Stropitori {

	public static final int INF = 99999;

	public static void main(String[] args) throws FileNotFoundException {
		int n, S;
		String name;

		MyScanner reader = new MyScanner("stropitori.in");
		name = reader.nextLine(); // stadium name
		n = reader.nextInt(); // number of sprinklers
		S = reader.nextInt(); // stadium's length

		int[] coordinates = new int[n];
		int[] powers = new int[n];
		long[] leftLimits = new long[n];
		long[] rightLimits = new long[n];
		boolean[] leftValid = new boolean[n];
		boolean[] rightValid = new boolean[n];
		long currentLimit = -INF;
		int numbers = 0;
		// reading sprinklers' coordinates
		for (int i = 0; i < n; i++) {
			coordinates[i] = reader.nextInt();
		}

		// reading sprinklers' powers
		// calculating intervals left and right limits;
		for (int i = 0; i < n; i++) {
			powers[i] = reader.nextInt();
			leftLimits[i] = coordinates[i] - powers[i];
			rightLimits[i] = coordinates[i] + powers[i];
			leftValid[i] = false;
			rightValid[i] = false;
		}

		// check if left and right limits are valid
		for (int i = 0; i < n; i++) {
			// doesn't go outside the stadium
			if (leftLimits[i] >= 0) {
				if (i == 0) {
					// first sprinkler can't intersect a neighbor on the left
					leftValid[i] = true;
				} else {
					// doesn't intersect the previous sprinkler
					if (leftLimits[i] > coordinates[i - 1]) {
						leftValid[i] = true;
					}
				}
			}

			if (rightLimits[i] <= S) {
				if (i == n - 1) {
					// last sprinkler can't intersect a neighbor on the right
					rightValid[i] = true;
				} else if (rightLimits[i] < coordinates[i + 1]) {
					// doesn't intersect the next sprinkler
					rightValid[i] = true;
				}

			}
		}

		for (int i = 0; i < n; i++) {
			if (!rightValid[i] && !leftValid[i]) {
				// sprinkler is blocked
				currentLimit = coordinates[i];
				continue;
			} else if (leftValid[i] == true && leftLimits[i] > currentLimit) {
				// sprinkler can sprinkle to the left
				currentLimit = coordinates[i];
				numbers++;
			} else if (rightValid[i] == true && currentLimit < rightLimits[i]) {
				// sprinkler can sprinkle to the right
				currentLimit = rightLimits[i];
				numbers++;
			} else { // sprinkler is blocked
				currentLimit = coordinates[i];
			}
		}

		PrintWriter writer = new PrintWriter(new File("stropitori.out"));
		writer.println(numbers);
		writer.close();
	}

}
