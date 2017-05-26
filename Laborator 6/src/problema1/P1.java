package problema1;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * Proiectarea Algoritmilor Lab 3: Parcurgerea Grafurilor. Sortare Topologica
 * Task 1: Help me escape!
 *
 * @author adinu
 * @email mandrei.dinu@gmail.com
 *
 */

enum CellType {
	FREE, OBSTACLE, EXIT
}

class Cell {
	int x;
	int y;
	CellType type;
}

class Room {

	/* Dimension of the matrix corresponding to the map of the room */
	int dim;

	/* The map of the room */
	CellType[][] map;

	/*
	 * Starting point from where you should calculate the minimum distance to an
	 * exit point on the map
	 */
	Cell startPoint = new Cell();

	/**
	 * Read and parse the data from the input file
	 * 
	 * @param filename
	 */
	void loadRoomFromFile(String filename) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(filename));

			dim = sc.nextInt();
			map = new CellType[dim][];
			for (int row = 0; row < dim; row++) {
				map[row] = new CellType[dim];
			}

			for (int row = 0; row < dim; row++) {
				for (int col = 0; col < dim; col++) {
					map[row][col] = CellType.OBSTACLE;
					int val = sc.nextInt();
					if (val == 0) {
						map[row][col] = CellType.FREE;
					} else if (val == 2) {
						map[row][col] = CellType.EXIT;
					}
				}
			}
			startPoint.x = sc.nextInt() - 1;
			startPoint.y = sc.nextInt() - 1;

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
}

public class P1 {

	Room room;

	/* Holds the optimal path to an exit on the map */
	List<Cell> solution;

	/*
	 * Auxiliary matrix to hold the min distances between the starting point and
	 * the current point on the map
	 */
	int[][] distances;
	Cell finalCell = new Cell();
	/* Queue used in the BFS algorithm */
	Queue<Cell> q;
	Queue<Cell> path;

	P1(String loadFile) {
		room = new Room();
		room.loadRoomFromFile(loadFile);

		solution = new ArrayList<>();
		distances = new int[room.dim][];
		for (int row = 0; row < room.dim; row++) {
			distances[row] = new int[room.dim];
		}
		for (int row = 0; row < room.dim; row++) {
			for (int col = 0; col < room.dim; col++) {
				distances[row][col] = Integer.MAX_VALUE;
			}
		}
		q = new LinkedList<>();
	}

	/**
	 * Find the minimum path in terms of cell walked between a starting point
	 * and an exit on the map of the Room
	 */
	void findMinPathValue() {
		// TODO
		// apply the breadth first search algorithm
		// use the Room to handle the map of the room and the starting point

		// order: up, right, down, left
		int[] x = { 0, 1, 0, -1 };
		int[] y = { -1, 0, 1, 0 };

		Cell startPosition = room.startPoint; // getting starting point
		distances[startPosition.x][startPosition.y] = 0; // distance from
															// starting point is
															// 0;
		q.add(startPosition); // adding starting point to queue

		Cell currentPosition = new Cell();

		while (!q.isEmpty()) {
			currentPosition = q.poll();
			// check the four neighbors for current position
			for (int i = 0; i < 4; i++) {
				int neighborX = currentPosition.x + x[i];
				int neighborY = currentPosition.y + y[i];

				// check if coordinates are valid and position not already
				// checked
				if (neighborX >= 0 && neighborX < room.dim && neighborY >= 0 && neighborY < room.dim
						&& distances[neighborX][neighborY] == Integer.MAX_VALUE) {
					// position not obstacle
					if (room.map[neighborX][neighborY] != CellType.OBSTACLE) {
						distances[neighborX][neighborY] = 1 + distances[currentPosition.x][currentPosition.y];
						Cell c = new Cell();
						c.x = neighborX;
						c.y = neighborY;
						// add to queue cells that aren't an obstacle
						q.add(c);
					}
					if(room.map[neighborX][neighborY] == CellType.OBSTACLE)
						distances[neighborX][neighborY] = -1;
				}
			}
		}
		for (int i = 0; i < room.dim; i++) {
			for (int j = 0; j < room.dim; j++) { 
				if (distances[i][j] != Integer.MAX_VALUE && distances[i][j] != -1)
					System.out.print(distances[i][j] + " ");
				else if(distances[i][j] == -1)
					System.out.print("o ");
				else
					System.out.print("x ");
			}
			System.out.println();
		}
	}

	void rebuildMinPath() {
		// TODO
		// using the distances matrix, rebuild the optimal path between the
		// starting point and the found exit
		// save the path in solution
		// CORNER CASE 1: what if there is no path to any of the exits?

		Cell currentPosition = finalCell;
		path = new LinkedList<Cell>();

		// order: up, right, down, left
		int[] x = { 0, 1, 0, -1 };
		int[] y = { -1, 0, 1, 0 };

		while (distances[currentPosition.x][currentPosition.y] != 0) {
			// check the four neighbors to see in each direction to go
			for (int i = 0; i < 4; i++) {
				int neighborX = currentPosition.x + x[i];
				int neighborY = currentPosition.y + y[i];

				// check if coordinates are valid and position was checked
				if (neighborX >= 0 && neighborX < room.dim && neighborY >= 0 && neighborY < room.dim
						&& distances[neighborX][neighborY] != Integer.MAX_VALUE)
					// position not obstacle
					if (room.map[neighborX][neighborY] != CellType.OBSTACLE) {
					// see if one of the neighbours was visited
					if (distances[neighborX][neighborY] == (distances[currentPosition.x][currentPosition.y] - 1)) {
					System.out.println("Am gasit urmatoarea celula: " + neighborX + " " + neighborY);
					// update current position
					currentPosition.x = neighborX;
					currentPosition.y = neighborY;

					path.add(currentPosition);
					}
					}
			}

			if (path.isEmpty())
				break;

		}

	}

	void showMinPath() {
		findMinPathValue();
		rebuildMinPath();

		// TODO
		// print the path, one cell on each line (stdout)
	}

	public static void main(String[] argv) {
		// three tests to pass
		String[] testFiles = { "room1.in", "room2.in", "room3.in" };

		P1[] p = new P1[testFiles.length];
		for (int test = 0; test < testFiles.length; test++) {
			p[test] = new P1(testFiles[test]);
			p[test].showMinPath();
			System.out.println();
		}
	}
}
