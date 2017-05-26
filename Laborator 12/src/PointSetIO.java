import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PointSetIO {
	public static ArrayList<Point> readFromFile(String filename) throws Exception {
		ArrayList<Point> pointset = new ArrayList<Point>();
		Scanner scan = new Scanner(new File(filename));
		try {
			Point point = new Point(scan.nextDouble(), scan.nextDouble(), scan.nextInt());
			pointset.add(point);
		} catch (Exception e) {
			throw e;
		} finally {
			scan.close();
		}
		return pointset;
	}

	public static void saveToFile(ArrayList<Point> pointset, String filename) throws Exception {
		BufferedWriter br = new BufferedWriter(new FileWriter(filename));
		try {
			for (Point point : pointset) {
				br.write(String.format("%lf %lf %d\n", point.x, point.y, point.clusterIndex));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			br.close();
		}
	}
}
