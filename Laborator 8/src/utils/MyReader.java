package utils;
/**
 * Proiectarea Algoritmilor, 2016
 * Lab 8: Drumuri minime
 *
 * @author  adinu
 * @email   mandrei.dinu@gmail.com
 */

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

import graph.GraphT;
import graph.NodeT;

public class MyReader {

	/**
	 * Read and parse the data from a file given by its name into a
	 * corresponding directed graph
	 *
	 * @param filename
	 * @return
	 */
	public static GraphT<String, Integer> readData(String filename) {

		Scanner sc = null;

		try {

			sc = new Scanner(new File(filename));

			GraphT<String, Integer> g = new GraphT<>();
			int numNodes = sc.nextInt();
			for (int idx = 0; idx < numNodes; idx++) {
				String cityName = sc.next();
				NodeT<String> city = new NodeT<>(cityName, idx);
				g.insertNode(city);
			}

			int edgesNum = sc.nextInt();
			String sPattern = "[a-zA-Z0-9-]+";
			Pattern pattern = Pattern.compile(sPattern);
			for (int i = 0; i < edgesNum; i++) {
				String info1 = sc.next(pattern);
				String info2 = sc.next(pattern);
				int cost = Integer.valueOf(sc.next(pattern));
				g.insertEdge(g.getNode(info1), g.getNode(info2), cost);
			}

			return g;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (sc != null) {
					sc.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return null;
	}

}
