package homework;

import java.io.File;
import java.util.Scanner;

/**
 * Proiectarea algoritmilor Lab 2: Greedy Task 2: Planificarea temelor de catre
 * studenti
 * 
 * @author adinu
 * @email mandrei.dinu@gmail.com
 *
 */
public class HomeworkPlanner {

	public static final int NO_TESTS = 2;
	int n; // num of homeworks
	Homework[][] homeworks = new Homework[NO_TESTS][]; // lista de teme

	public static void main(String[] args) {
		HomeworkPlanner hp = new HomeworkPlanner();
		hp.readInput("date.in");
		hp.test();
	}

	private void test() {
		for (int t = 0; t < NO_TESTS; t++) {

			System.out.println("Pentru temele");
			printV(homeworks[t]);
			System.out.println();

			Homework[] solutie = chooseHomeworks(homeworks[t]);
			System.out.println("Avem solutia\n");
			printV(solutie);
			System.out.println();
		}
	}

	private void readInput(String filename) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File(filename));

			for (int t = 0; t < NO_TESTS; t++) {
				n = sc.nextInt();
				homeworks[t] = new Homework[n];
				int d, w;
				for (int i = 0; i < n; i++) {
					d = sc.nextInt();
					w = sc.nextInt();
					homeworks[t][i] = new Homework(d, w);
				}
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
	 * Make the best planification in time for a given set of tasks.
	 * 
	 * @param homeworks
	 * @return a list of selected tasks
	 */
	Homework[] chooseHomeworks(Homework[] tasks) {
		// TODO:
		// Adaugati true in isPlanned pe pozitia i, daca tema respectiva
		// a fost planificata.
		// Sortati lista de task-uri dupa deadline si alegeti intotdeauna
		// tema care se termina cel mai curand, in asa fel incat sa nu se
		// suprapuna
		// cu nicio alta tema aleasa in trecut.
		boolean[] isPlanned = new boolean[tasks.length];
		int numberOfHomework = 0;
		int temp;
		int[] initialOrder= new int[tasks.length];
		for(int i = 0; i < tasks.length; i++)
			initialOrder[i] = i;
		
		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++) {
				if (tasks[j].deadline < tasks[i].deadline) {
					Homework aux;
					aux = tasks[j];
					tasks[j] = tasks[i];
					tasks[i] = aux;
					
					temp = initialOrder[j];
					initialOrder[j] = initialOrder[i];
					initialOrder[i] = temp;
				} else if (tasks[j].deadline == tasks[i].deadline && tasks[j].points > tasks[i].points) {
					Homework aux;
					aux = tasks[j];
					tasks[j] = tasks[i];
					tasks[i] = aux;
					
					temp = initialOrder[j];
					initialOrder[j] = initialOrder[i];
					initialOrder[i] = temp;
				}
			}
		int currentWeek = 1;
		for(int i = 0; i < tasks.length; i++)
		{
			if(tasks[i].deadline >= currentWeek){
				isPlanned[initialOrder[i]] = true;
				numberOfHomework++;
				currentWeek++;
			}
		}

		Homework[] homeworkPlanner = new Homework[numberOfHomework];
		int j = 0;
		for(int i = 0; i < tasks.length; i++)
		{
			if(isPlanned[initialOrder[i]] == true){
				homeworkPlanner[j++] = tasks[i];
			}

		}
		return homeworkPlanner;
	}

	/**
	 * Prints on the screen an array of objects
	 * 
	 * @param v
	 */
	private void printV(Object[] v) {
		System.out.print("{ ");
		for (Object elem : v) {
			if (elem == null) {
				System.out.println("null");
			} else {
				System.out.print(elem + " ");
			}
		}
		System.out.print("}");
	}

	private class Homework {
		int deadline;
		int points;

		public Homework(int deadline, int points) {
			this.deadline = deadline;
			this.points = points;
		}

		@Override
		public String toString() {
			return "(d:" + deadline + ", p:" + points + ") ";
		}
	}
}
