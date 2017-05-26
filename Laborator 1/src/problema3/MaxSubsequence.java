package problema3;

public class MaxSubsequence {
	private static final int MIN_VALUE = -424242;
	private static int s;
	public static void main(String[] args) {
		int[] v1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
		// subsecventa (4, -1, 2, 1) 4 - 1 + 2 + 1 = 6
		int sum = maximSum(v1,0, v1.length - 1);
		System.out.println("suma maxima pentru v1:" + sum);
		
		int[] v2 = { -1, 2, 3, -4, -2, 2, 1, -3, -2, -3, -4, 9, -2, 1, 7, 8, -19, -7, 2, 4, 3};
		// subsecventa (9, -2, 1, 7, 8) 9 - 2 + 1 + 7 + 8 =  23
		sum = maximSum(v2,0,v2.length - 1);
		System.out.println("suma maxima pentru v2:" + sum);
		
	}

	static int maximSum(int[] v, int lower, int upper) {

		int middle = lower + (upper - lower) / 2;

		if (lower == upper)
			return v[lower];

		// suma maxima din prima jumatate
		int leftSum = maximSum(v, lower, middle);
		// suma maxima din a doua jumatate
		int rightSum = maximSum(v, middle + 1, upper);
		
		s = Integer.max(leftSum, rightSum);
		
		// calcul suma ambele jumatati
		int maximLeftSum = MIN_VALUE;
		int maximRightSum = MIN_VALUE;
		int left = 0;
		int right = 0;

		for (int i = middle - 1; i >= lower; i--) {
			left += v[i];
			if (left > maximLeftSum)
				maximLeftSum = left;
		}
		
		for (int i = middle; i <= upper; i++){
			right += v[i];
			if(right > maximRightSum)
				maximRightSum = right;
		}
		int crossSum = maximRightSum + maximLeftSum;
		
		return Integer.max(s, crossSum);
	}
}
