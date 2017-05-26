import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static void main(String[] args) throws Exception {
		/*
		 * Parametri generator de clustere: N - numarul de puncte generate. k -
		 * numarul de clustere generate. minRadius - raza minima a unui cluster
		 * generat. maxRadius - raza maxima a unui cluster generat.
		 *
		 * Parametri Kmeans: k - numarul de clustere ce trebuie identificate.
		 * maxIterations - numarul maxim de iteratii ce se vor executa.
		 *
		 * Sunteti incurajati sa modificati aceste valori pentru a observa
		 * comportarea algoritmului in cat mai multe cazuri.
		 */
		final int N = 1000;
		final int k = 5;
		final double minRadius = 0.0;
		final double maxRadius = 0.2;
		final int maxIterations = 100;

		ArrayList<Point> points = Generator.generate(N, k, minRadius, maxRadius);
		kmeans(points, k, maxIterations);
	}

	/**
	 * Atribuie fiecarui punct din setul primit indexul clusterului caruia
	 * apartine.
	 *
	 * @param points
	 *            setul de puncte ce trebuie clusterizat
	 * @param k
	 *            numarul de clustere
	 * @param iterations
	 *            numarul de iteratii dupa care algoritmul se opreste
	 */
	public static void kmeans(ArrayList<Point> points, int k, int iterations) {
		/* Initializam punctele cu UNKNOWN_CLUSTER. */
		for (Point point : points) {
			point.clusterIndex = Point.UNKNOWN_CLUSTER;
		}

		/* Construim un obiect Painter pentru a reprezenta vizual procesul. */
		Painter picasso = new Painter(points);

		/* Initializam centroizii. */
		ArrayList<Point> centroids = randomCentroids(points, k);

		/*
		 * TODO Algoritmul Kmeans: 1. Atribuiti fiecarui punct indexul celui mai
		 * apropiat centroid.
		 *  2. Recalculati pozitiile centroizilor. Noua
		 * pozitie a unui centroid este centrul de masa al punctelor ce au fost
		 * marcate cu indexul lui la pasul anterior. Pasii 1 si 2 se vor repeta
		 * de un numar finit de ori (variablia iterations).
		 */

		for (int i = 0; i < iterations; i++) {
			ArrayList<ArrayList<Point>> centroidPoints = new ArrayList<>(k);

			for (int j = 0; j < k; j++) {
				centroidPoints.add(j, new ArrayList<Point>());

			}
			int idx;
			/* PASUL 1 - atribuiti fiecarui punct cel mai apropiat centroid */
			for (Point p : points) {
				idx = p.clusterIndex;
				double d = Double.MAX_VALUE;

				for (Point c : centroids) {
					if (p.distanceTo(c) < d) {
						d = p.distanceTo(c);
						idx = c.clusterIndex;
					}
				}
				p.clusterIndex = idx;
				centroidPoints.get(idx).add(p);
			}
			/* PASUL 2 - recalculati pozitiile centroizilor */
			for (int j = 0; j < k; j++) {
				double newX = 0;
				double newY = 0;
				ArrayList<Point> aux = centroidPoints.get(j);
				for (Point p : aux) {
					newX += p.x;
					newY += p.y;
				}
				centroids.get(j).x = newX / aux.size();
				centroids.get(j).y = newY / aux.size();
			}
			picasso.setPoints(points);
			picasso.setCentroids(centroids);

			/* Sleep 1s to give us a chance to take a look at the image. */
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initializeaza aleator cei k centroizi in patratul [0, 1] x [0, 1].
	 *
	 * @param points
	 *            setul de date ce trebuie clusterizat (nu este folosit in
	 *            aceasta functie)
	 * @param k
	 *            numarul de clustere
	 * @return
	 */
	static ArrayList<Point> randomCentroids(ArrayList<Point> points, int k) {
		ArrayList<Point> centroids = new ArrayList<Point>();

		/*
		 * TODO Construiti aleator cei k centroizi. Este indicat sa folositi
		 * campul clusterIndex pentru a retine indexul fiecarui cluster.
		 */

		for (int i = 0; i < k; i++) {
			double x = new Random().nextDouble();
			double y = new Random().nextDouble();
			centroids.add(new Point(x, y, i));
		}

		return centroids;
	}

	/**
	 * Initializeaza cei k centroizi dupa metoda specifica Kmeans++.
	 *
	 * @param points
	 *            setul de date ce trebuie clusterizat
	 * @param k
	 *            numarul de clustere
	 * @return
	 */
	static ArrayList<Point> kmeansppCentroids(ArrayList<Point> points, int k) {
		/*
		 * TODO Implementati algoritmul de initializare a centroizilor specific
		 * Kmeans++.
		 * http://en.wikipedia.org/wiki/K-means%2B%2B#Initialization_algorithm
		 */

		return randomCentroids(points, k);
	}
}
