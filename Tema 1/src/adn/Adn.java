package adn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import utils.MyScanner;

public class Adn {

	public static void main(String[] args) throws FileNotFoundException {
		int T;
		int n;
		int solution;
		String s1, s2, s3, s4, result;

		MyScanner reader = new MyScanner("adn.in");
		PrintWriter writer = new PrintWriter(new File("adn.out"));

		T = reader.nextInt();

		for (int i = 0; i < T; i++) {
			n = reader.nextInt();
			if (n == 1) {
				s1 = reader.next();
				result = reader.next();
				if (s1.equals(result)) {
					writer.println(1);
				} else {
					writer.println(0);
				}
			} else if (n == 2) {
				s1 = reader.next();
				s2 = reader.next();
				result = reader.next();

				if ((s1.length() + s2.length()) != result.length()) {
					writer.println(0);
				} else {
					solution = get2Solution(s1, s2, result);
					writer.println(solution);
				}
			} else if (n == 3) {
				s1 = reader.next();
				s2 = reader.next();
				s3 = reader.next();
				result = reader.next();

				if ((s1.length() + s2.length() + s3.length()) != result.length()) {
					writer.println(0);
				} else {
					solution = get3Solution(s1, s2, s3, result);
					writer.println(solution);
				}
			} else if (n == 4) {
				s1 = reader.next();
				s2 = reader.next();
				s3 = reader.next();
				s4 = reader.next();
				result = reader.next();

				if ((s1.length() + s2.length() + s3.length() + s4.length()) != result.length()) {
					writer.println(0);
				} else {
					solution = get4Solution(s1, s2, s3, s4, result);
					writer.println(solution);
				}
			}

		}

		writer.close();
	}

	static int get2Solution(String s1, String s2, String result) {
		int[][] solution = new int[s1.length() + 1][s2.length() + 1];

		for (int i = 0; i <= s1.length(); i++) {
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0 && j == 0) {
					// both string are empty
					solution[i][j] = 1;
				} else if (j == 0) {
					// s2 is empty
					// (i - 1)th char from s1 matches with (i - 1)th char from
					// result
					if (solution[i - 1][j] == 1 && result.charAt(i - 1) == s1.charAt(i - 1)) {
						solution[i][j] = 1;
					}
				} else if (i == 0) {
					// s1 is empty
					// (j - 1)th char from s2 matches with (j - 1)th char from
					// result
					if (solution[i][j - 1] == 1 && result.charAt(j - 1) == s2.charAt(j - 1)) {
						solution[i][j] = 1;
					}
				} else {
					// (j-1)th char from s2 or (i-1)th char from s2
					// matches with (i+j-1)th char from result
					if ((solution[i][j - 1] == 1 && (result.charAt(i + j - 1) == s2.charAt(j - 1)))
							|| (solution[i - 1][j] == 1
									&& (result.charAt(i + j - 1) == s1.charAt(i - 1)))) {
						solution[i][j] = 1;
					}

				}
			}
		}

		return solution[s1.length()][s2.length()];
	}

	static int get3Solution(String s1, String s2, String s3, String result) {
		int[][][] solution = new int[s1.length() + 1][s2.length() + 1][s3.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			for (int j = 0; j <= s2.length(); j++) {
				for (int k = 0; k <= s3.length(); k++) {
					// all strings are empty
					if (i == 0 && j == 0 && k == 0) {
						solution[i][j][k] = 1;
					} else if (i == 0 && j == 0) {
						// s1 and s2 are empty
						// s3[k-1] matches with result[k-1]
						if (solution[i][j][k - 1] == 1
								&& result.charAt(k - 1) == s3.charAt(k - 1)) {
							solution[i][j][k] = 1;
						}
					} else if (i == 0 && k == 0) {
						// s1 and s3 are empty
						// s2[j-1] matches with result[j-1]
						if (solution[i][j - 1][k] == 1
								&& result.charAt(j - 1) == s2.charAt(j - 1)) {
							solution[i][j][k] = 1;
						}
					} else if (j == 0 && k == 0) {
						// s1 and s3 are empty
						// s2[j-1] matches with result[j-1]
						if (solution[i - 1][j][k] == 1
								&& result.charAt(i - 1) == s1.charAt(i - 1)) {
							solution[i][j][k] = 1;
						}
					} else if (i == 0) {
						// s1 is empty
						// s2[j-1] or s3[k-1] matches with result[j+k-1]
						if ((solution[i][j - 1][k] == 1
								&& result.charAt(i + j + k - 1) == s2.charAt(j - 1))
								|| solution[i][j][k - 1] == 1
										&& result.charAt(i + j + k - 1) == s3.charAt(k - 1)) {
							solution[i][j][k] = 1;
						}
					} else if (j == 0) {
						// s2 is empty
						// s1[i-1] or s3[k-1] matches with result[i+k-1]
						if ((solution[i - 1][j][k] == 1
								&& result.charAt(i + j + k - 1) == s1.charAt(i - 1))
								|| (solution[i][j][k - 1] == 1
										&& result.charAt(i + j + k - 1) == s3.charAt(k - 1))) {
							solution[i][j][k] = 1;
						}
					} else if (k == 0) {
						// s3 is empty
						// s1[i-1] or s2[j-1] matches with result[i+j-1]
						if ((solution[i - 1][j][k] == 1
								&& result.charAt(i + j + k - 1) == s1.charAt(i - 1))
								|| (solution[i][j - 1][k] == 1
										&& result.charAt(i + j + k - 1) == s2.charAt(j - 1))) {
							solution[i][j][k] = 1;
						}

					} else {
						// s1[i-1] or s2[j-1] or s3[k-1] matches with
						// result[i+j+k-1]
						if ((solution[i - 1][j][k] == 1
								&& result.charAt(i + j + k - 1) == s1.charAt(i - 1))
								|| (solution[i][j - 1][k] == 1
										&& result.charAt(i + j + k - 1) == s2.charAt(j - 1))
								|| solution[i][j][k - 1] == 1
										&& result.charAt(i + j + k - 1) == s3.charAt(k - 1)) {
							solution[i][j][k] = 1;
						}
					}
				}
			}
		}

		return solution[s1.length()][s2.length()][s3.length()];
	}

	static int get4Solution(String s1, String s2, String s3, String s4, String r) {
		int n = s1.length() + 1;
		int m = s2.length() + 1;
		int p = s3.length() + 1;
		int q = s4.length() + 1;
		int[][][][] sol = new int[n][m][p][q];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int k = 0; k < p; k++) {
					for (int l = 0; l < q; l++) {
						// all strings are empty
						if (i == 0 && j == 0 && k == 0 && l == 0) {
							sol[i][j][k][l] = 1;
						} else if (i == 0 && j == 0 && k == 0) {
							// s1, s2 and s3 are empty
							// s4[l-1] matches r[l-1]
							if (sol[i][j][k][l - 1] == 1 && r.charAt(l - 1) == s4.charAt(l - 1)) {
								sol[i][j][k][l] = 1;
							}
						} else if (i == 0 && j == 0 && l == 0) {
							// s1, s2 and s4 are empty
							// s3[k-1] matches r[k-1]
							if (sol[i][j][k - 1][l] == 1 && r.charAt(k - 1) == s3.charAt(k - 1)) {
								sol[i][j][k][l] = 1;
							}
						} else if (i == 0 && k == 0 && l == 0) {
							// s1, s3 and s4 are empty
							// s2[j-1] matches with r[j-1]
							if (sol[i][j - 1][k][l] == 1 && r.charAt(j - 1) == s2.charAt(j - 1)) {
								sol[i][j][k][l] = 1;
							}
						} else if (j == 0 && k == 0 && l == 0) {
							// s2, s3 and s4 are empty
							// s1[i-1] matches with r[i-1]
							if (sol[i - 1][j][k][l] == 1 && r.charAt(i - 1) == s1.charAt(i - 1)) {
								sol[i][j][k][l] = 1;
							}
						} else if (i == 0 && j == 0) {
							// s1 and s2 are empty
							// s3[k-1] or s4[l-1] matches with result[i + j + k
							// + l-1]
							if ((sol[i][j][k - 1][l] == 1
									&& r.charAt(i + j + k + l - 1) == s3.charAt(k - 1))
									|| (sol[i][j][k][l - 1] == 1
											&& r.charAt(i + j + k + l - 1) == s4.charAt(l - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else if (i == 0 && k == 0) {
							// s1 and s3 are empty
							// s2[j-1] or s4[l-1] matches with result[i + j + k
							// + l-1]
							if ((sol[i][j - 1][k][l] == 1
									&& r.charAt(i + j + k + l - 1) == s2.charAt(j - 1))
									|| (sol[i][j][k][l - 1] == 1
											&& r.charAt(i + j + k + l - 1) == s4.charAt(l - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else if (i == 0 && l == 0) {
							// s1 and s4 are empty
							// s2[j-1] or s3[k-1] matches with result[i + j + k
							// + l-1]
							if ((sol[i][j - 1][k][l] == 1
									&& r.charAt(i + j + k + l - 1) == s2.charAt(j - 1))
									|| (sol[i][j][k - 1][l] == 1
											&& r.charAt(i + j + k + l - 1) == s3.charAt(k - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else if (j == 0 && k == 0) {
							// s2 and s3 are empty
							// s1[i-1] or s4[l-1] matches with result[i + j + k
							// + l-1]
							if ((sol[i - 1][j][k][l] == 1
									&& r.charAt(i + j + k + l - 1) == s1.charAt(i - 1))
									|| (sol[i][j][k][l - 1] == 1
											&& r.charAt(i + j + k + l - 1) == s4.charAt(l - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else if (j == 0 && l == 0) {
							// s2 and s4 are empty
							// s1[i-1] or s3[k-1] matches with result[i + j + k
							// + l-1]
							if ((sol[i - 1][j][k][l] == 1
									&& r.charAt(i + j + k + l - 1) == s1.charAt(i - 1))
									|| (sol[i][j][k - 1][l] == 1
											&& r.charAt(i + j + k + l - 1) == s3.charAt(k - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else if (k == 0 && l == 0) {
							// s3 and s4 are empty
							// s1[i-1] or s2[j-1] matches with result[i + j + k
							// + l-1]
							if ((sol[i - 1][j][k][l] == 1
									&& r.charAt(i + j + k + l - 1) == s1.charAt(i - 1))
									|| (sol[i][j - 1][k][l] == 1
											&& r.charAt(i + j + k + l - 1) == s2.charAt(j - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else if (i == 0) {
							// s1 is empty
							// s2[j-1] or s3[k-1] or s4[l-1] matches with
							// result[i + j + k + l-1]
							if ((sol[i][j][k][l - 1] == 1
									&& r.charAt(i + j + k + l - 1) == s4.charAt(l - 1))
									|| (sol[i][j][k - 1][l] == 1
											&& r.charAt(i + j + k + l - 1) == s3.charAt(k - 1))
									|| (sol[i][j - 1][k][l] == 1
											&& r.charAt(i + j + k + l - 1) == s2.charAt(j - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else if (j == 0) {
							// s2 is empty
							// s1[i-1] or s3[k-1] or s4[l-1] matches with
							// result[i + j + k + l-1]
							if ((sol[i][j][k][l - 1] == 1
									&& r.charAt(i + j + k + l - 1) == s4.charAt(l - 1))
									|| (sol[i][j][k - 1][l] == 1
											&& r.charAt(i + j + k + l - 1) == s3.charAt(k - 1))
									|| (sol[i - 1][j][k][l] == 1
											&& r.charAt(i + j + k + l - 1) == s1.charAt(i - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else if (k == 0) {
							// s3 is empty
							// s2[j-1] or s3[k-1] or s4[l-1] matches with
							// result[i + j + k + l-1]
							if ((sol[i][j][k][l - 1] == 1
									&& r.charAt(i + j + k + l - 1) == s4.charAt(l - 1))
									|| (sol[i][j - 1][k][l] == 1
											&& r.charAt(i + j + k + l - 1) == s2.charAt(j - 1))
									|| (sol[i - 1][j][k][l] == 1
											&& r.charAt(i + j + k + l - 1) == s1.charAt(i - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else if (l == 0) {
							// s4 is empty
							// s2[j-1] or s3[k-1] or s1[i-1] matches with
							// result[i + j + k + l-1]
							if ((sol[i][j][k - 1][l] == 1
									&& r.charAt(i + j + k + l - 1) == s3.charAt(k - 1))
									|| (sol[i][j - 1][k][l] == 1
											&& r.charAt(i + j + k + l - 1) == s2.charAt(j - 1))
									|| (sol[i - 1][j][k][l] == 1
											&& r.charAt(i + j + k + l - 1) == s1.charAt(i - 1))) {
								sol[i][j][k][l] = 1;
							}
						} else {
							// s1[i-1] or s2[j-1] or s3[k-1] or s4[l-1] matches
							// with result[i + j + k + l-1]
							if ((sol[i][j][k][l - 1] == 1
									&& r.charAt(i + j + k + l - 1) == s4.charAt(l - 1))
									|| (sol[i][j][k - 1][l] == 1
											&& r.charAt(i + j + k + l - 1) == s3.charAt(k - 1))
									|| (sol[i][j - 1][k][l] == 1
											&& r.charAt(i + j + k + l - 1) == s2.charAt(j - 1))
									|| (sol[i - 1][j][k][l] == 1
											&& r.charAt(i + j + k + l - 1) == s1.charAt(i - 1))) {
								sol[i][j][k][l] = 1;
							}
						}
					}
				}
			}
		}

		return sol[n - 1][m - 1][p - 1][q - 1];
	}
}
