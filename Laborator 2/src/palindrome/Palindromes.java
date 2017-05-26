package palindrome;

import java.io.File;
import java.util.Scanner;

/**
 * Proiectarea algoritmilor Lab 2: Greedy Task 3: Generare de palindroame de
 * suma minima
 *
 * @author adinu
 * @email mandrei.dinu@gmail.com
 *
 */
class Palindrome {
	StringBuffer fstHalf = new StringBuffer();
	StringBuffer sndHalf = new StringBuffer();
	String middleChar;
	int digitCnt;

	Palindrome() {
	};

	Palindrome(int digitCnt) {
		this.digitCnt = digitCnt;
	}

	public void addDigit(int digit) {
		fstHalf.append(String.valueOf(digit));
	}

	public void mirroring() {
		// TODO:
		// obtinerea jumatatii 2 a palindromului
		// prin mirroring-ul primei jumatati
		for (int i = fstHalf.length() - 1; i >= 0; i--) {
			sndHalf.append(fstHalf.charAt(i));
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(fstHalf.toString());
		if (middleChar != null) {
			sb.append(middleChar);
		}
		sb.append(sndHalf.toString());
		return sb.toString();
	}
}

public class Palindromes {
	public static final int NO_TESTS = 2;

	/* the length of one of the numbers */
	private int L;
	/* total occurences for each digit in both numbers */
	private int[] occurences;
	/* an array containing the two palindromes to be generated */
	Palindrome[] num;

	private int totalDigitOccurences;

	public Palindromes() {
		occurences = new int[10];
		/* before any reads, each digit does not occur in any number */
		for (int digit = 0; digit <= 9; digit++) {
			occurences[digit] = 0;
		}
		num = new Palindrome[2];
	}

	private void readInput(String filename) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File(filename));

			L = sc.nextInt();
			for (int digit = 0; digit <= 9; digit++) {
				occurences[digit] = sc.nextInt();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (sc != null) {
					sc.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Generates the two palindromes with respect to the condition that their
	 * sum should be minimum, and their start digit cannot be 0
	 */
	private void generatePalindromes() {
		// TODO
		// 1) creati obiectele de tip Palindrome
		// 2) daca exista un numar mai mare, respectiv unul mai mic (din
		// punct de vedere a nr de cifre,
		// se vor genera mai intai digitDiff digits in ordine crescatoare
		// pentru numarul cel mare.
		// 3) cand se ajunge la un ordin aproximativ egal de marime intre
		// numere,
		// se genereaza concomitent digits pentru ambele palindroame, in
		// ordinea crescatoare a cifrelor.
		//

		totalDigitOccurences = getNumberOfDigits();
		int minPalindrome = Math.min(L, totalDigitOccurences - L);
		int maxPalindrome = Math.max(L, totalDigitOccurences - L);

		// in num[0] pastram palindromul cu numarul de cifre minim
		num[0] = new Palindrome(minPalindrome);
		// in num[1] pastram palindromul cu numarul de cifre maxim
		num[1] = new Palindrome(maxPalindrome);

		int digitDiff = maxPalindrome - minPalindrome;
		int minPalindromedigits = 0;
		int maxPalindromeDigits = 0;
		// generam palindromul mai mare pana ajungem la ordin aprox egal
		// punem prima cifra nenula in palindromul mai mare
		for (int i = 1; i < occurences.length; i++) {
			if (occurences[i] >= 2) {
				num[1].addDigit(i);
				occurences[i] -= 2;
				digitDiff -= 2;
				maxPalindrome -= 2;
				break;
			}
		}
		// punem urmatoarele cifre pana ajungem la o diferenta aprox egala
		for (int i = 0; i < occurences.length; i++) {
			if (digitDiff == 0 || digitDiff == 1)
				break;
			if (occurences[i] >= 2) {
				num[1].addDigit(i);
				occurences[i] -= 2;
				digitDiff -= 2;
				maxPalindromeDigits++;

			}
		}

		// memorez prima cifra nenula pentru palindromul mic
		/*for (int i = 1; i < occurences.length; i++) {
			if (occurences[i] >= 2) {
				num[0].addDigit(i);
				occurences[i] -= 2;
				digitDiff -= 2;
				minPalindromedigits++;
				break;
			}
		}*/

		// am ajuns ca cele doua numere sa aiba aceeasi dimensiune
		// punem in paralel
		for (int i = 0; i < occurences.length; i++) {
			if (occurences[i] >= 2) {
				if (maxPalindromeDigits < (maxPalindrome / 2)) {
					num[1].addDigit(i);
					occurences[i] -= 2;
					maxPalindromeDigits++;
				} else if (minPalindromedigits < (minPalindrome) / 2) {
					num[0].addDigit(i);
					occurences[i] -= 2;
					minPalindromedigits++;
				}
			}
		}
		// palindromul mai mare are un numar impar de cifre
		if (maxPalindrome % 2 != 0)
			for (int i = 0; i < occurences.length; i++)
				if (occurences[i] == 1) {
					num[1].middleChar = Integer.toString(i);
					occurences[i]--;
					break;
				}
		// palindromul are un numar impar de cifre
		if (minPalindrome % 2 != 0)
			for (int i = 0; i < occurences.length; i++)
				if (occurences[i] == 1) {
					num[1].middleChar = Integer.toString(i);
					occurences[i]--;
					break;
				}

		// cream in paralel numarul
		num[0].mirroring();
		num[1].mirroring();

	}

	private int getNumberOfDigits() {
		int totalDigits = 0;
		for (int i = 0; i < occurences.length; i++)
			totalDigits += occurences[i];
		return totalDigits;
	}

	private void test(int t) {
		System.out.println("Pentru testul " + t + '\n');
		generatePalindromes();
		System.out.println("nr 1 - " + num[0] + ", nr 2 - " + num[1]);
		System.out.println();
	}

	public static void main(String[] args) {
		String[] inputFiles = { "date1.in", "date2.in" };
		Palindromes[] pal = new Palindromes[NO_TESTS];
		for (int t = 0; t < NO_TESTS; t++) {
			pal[t] = new Palindromes();
			pal[t].readInput(inputFiles[t]);
			pal[t].test(t);
		}
	}
}
