package rucsac;

import java.util.Scanner;

public class Knapsack {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		//Scanner scanner = new Scanner("knapsack.in");
		double T = scanner.nextDouble();
		int n = scanner.nextInt();

		double[] weight = new double[n];
		double[] value = new double[n];

		for (int i = 0; i < n; i++) {
			weight[i] = scanner.nextDouble();
			value[i] = scanner.nextDouble();
		}

		double finalValue = getKnapsack(T, n, weight, value);
		System.out.println("Valoare finala: " + finalValue);
		scanner.close();
	}

	public static void sortElements(int n, double[] efficiency, double[] weight, double[] value) {
		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++) {
				if (efficiency[j] > efficiency[i]) {
					double aux;
					// swap efficiency
					aux = efficiency[j];
					efficiency[j] = efficiency[i];
					efficiency[i] = aux;

					// swap weight
					aux = weight[j];
					weight[j] = weight[i];
					weight[i] = aux;

					// swap value
					aux = value[j];
					value[j] = value[i];
					value[i] = aux;
				}
			}
	}

	public static double[] getEfficiency(int n, double[] weight, double[] value) {
		double[] efficiency = new double[n];
		for (int i = 0; i < n; i++)
			efficiency[i] = value[i] / weight[i];

		return efficiency;
	}

	public static double getKnapsack(double T, int n, double[] weight, double[] value) {
		double[] efficiency = getEfficiency(n, weight, value);
		int elementCounter = 0;
		double totalValue = 0;
		sortElements(n, efficiency, weight, value);

		while (T > 0 && elementCounter < n) {
			if (weight[elementCounter] <= T) {
				totalValue += value[elementCounter];
				T -= weight[elementCounter];
				System.out.println("Element with w = " + weight[elementCounter] + " and v = " + value[elementCounter]
						+ " was added!");

			} else {
				totalValue += T * value[elementCounter] / weight[elementCounter];
				System.out.println("Knapsack is almost full. Element with w = " + T + " and v = "
						+ T * value[elementCounter] / weight[elementCounter] + " was added!");
				T = 0;
			}
			elementCounter++;
		}

		return totalValue;
	}

}
