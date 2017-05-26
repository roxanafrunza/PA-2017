package p3;

import java.lang.reflect.Array;
import java.util.Scanner;

public class Sum {
	public static int NO_SET = 424242;

	public static void main(String[] args) {
		int[] currency = { 1, 2, 5, 10 };
		Scanner scanner = new Scanner(System.in);
		int sum = scanner.nextInt();
		Sum obj = new Sum();
		int min = obj.selectMinimCurrency(currency, sum);
		System.out.println("Numarul minim de monede: " + min);
	}

	public int selectMinimCurrency(int[] currency, int sum) {
		int[] solution = new int[sum + 1];
		solution = initializeArray(solution);
		int currentSum;
		solution[0] = 0;

		for (int i = 0; i <= sum; i++) {
			for (int j = 0; j < i; j++) {
				for (int k = 0; k < currency.length; k++) {
					if (k <= i) {
						currentSum = j + currency[k];
						if (currentSum == i) {
							solution[i] = Math.min(solution[i], solution[j] + 1);
						}
					}
				}
			}

		}
		for (int i = 0; i < solution.length; i++) {
			System.out.println("solution[" + i + "]" + solution[i]);
		}
		return solution[sum];
	}

	public int[] initializeArray(int[] arr) {
		for (int i = 0; i < arr.length; i++)
			arr[i] = NO_SET;

		return arr;
	}
}
