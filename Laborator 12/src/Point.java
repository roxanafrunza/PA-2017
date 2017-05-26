/**
 * Reprezinta un punct in plan.
 */
public class Point {
	/**
	 * Cluster necunoscut.
	 */
	final static int UNKNOWN_CLUSTER = -1;

	public double x;
	public double y;

	public int clusterIndex;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.clusterIndex = UNKNOWN_CLUSTER;
	}

	public Point(double x, double y, int clusterIndex) {
		this.x = x;
		this.y = y;
		this.clusterIndex = clusterIndex;
	}

	public Point clone() {
		return new Point(x, y, clusterIndex);
	}

	/**
	 * Calculeaza distanta pana la un alt punct din spatiu dat.
	 * @param other punctul pana la care se va calcula distanta
	 * @return distanta calculata
	 */
	public double distanceTo(Point other) {
		double dx = x - other.x;
		double dy = y - other.y;

		return Math.sqrt(dx * dx + dy * dy);
	}
}
