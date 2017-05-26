import java.util.ArrayList;
import java.util.Random;

public class Generator {
	private static Random random = new Random();

	/**
	 * Generates N random points in k clusters in [0; 1] x [0; 1], hopefully stable enough.
	 *
	 * @param N
	 *            number of points to generate
	 * @param k
	 *            number of clusters
	 * @return a list of generated points
	 */
	public static ArrayList<Point> generate(int N, int k, double minRad, double maxRad) {
		ArrayList<Point> points = new ArrayList<Point>();
		Point[] clusters = generateRandomClusters(k);
		double[] r = generateRandomRadiuses(k, minRad, maxRad);

		for (int i = 0; i < N; i++) {
			int clusterIndex = random.nextInt(k);
			double theta = 2 * Math.PI * random.nextDouble();
			double radius = r[clusterIndex] * random.nextDouble();
			double x = clusters[clusterIndex].x + radius * Math.cos(theta);
			double y = clusters[clusterIndex].y + radius * Math.sin(theta);

			points.add(new Point(x, y, clusterIndex));
		}

		return points;
	}

	private static Point[] generateRandomClusters(int k) {
		Point[] clusters = new Point[k];
		for (int i = 0; i < k; i++) {
			clusters[i] = new Point(random.nextDouble(), random.nextDouble(), i);
		}
		return clusters;
	}

	private static double[] generateRandomRadiuses(int k, double minRad, double maxRad) {
		double[] r = new double[k];
		for (int i = 0; i < k; i++) {
			r[i] = minRad + random.nextDouble() * (maxRad - minRad);
		}
		return r;
	}
}
