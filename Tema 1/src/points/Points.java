package points;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import utils.MyScanner;

class Point {
	int coordinate;

	Point(int coordinate) {
		this.coordinate = coordinate;
	}

	Interval intervalsIntersected(ArrayList<Interval> intervals) {

		// sorting intervals descending by their upper bound
		Collections.sort(intervals);
		// find the first interval that intersects point
		for (Interval i : intervals) {
			if (coordinate > i.upperLimit) {
				break;
			}
			if (coordinate >= i.lowerLimit && coordinate <= i.upperLimit) {
				return i; // return the interval
			}
		}
		return null; // no interval found
	}

	// check if given interval contains point
	boolean isCovered(Interval i) {
		if (coordinate >= i.lowerLimit && coordinate <= i.upperLimit) {
			return true;
		}
		return false;
	}
}

class Interval implements Comparable<Interval> {
	int lowerLimit;
	int upperLimit;

	Interval(int lower, int upper) {
		if (lower > upper) {
			int aux = lower;
			lower = upper;
			upper = aux;
		}
		lowerLimit = lower;
		upperLimit = upper;
	}

	@Override
	public int compareTo(Interval o) {

		return o.upperLimit - this.upperLimit;
	}
}

public class Points {

	public static void main(String[] args) throws IOException {

		PrintWriter writer = new PrintWriter(new File("points.out"));
		MyScanner reader = new MyScanner("points.in");

		// read first line
		int N = reader.nextInt(); // get N
		int M = reader.nextInt(); // get M

		ArrayList<Point> points = new ArrayList<Point>(N);
		ArrayList<Interval> intervals = new ArrayList<Interval>(M);

		// read point coordinate and add it to points array
		for (int i = 0; i < N; i++) {
			points.add(new Point(reader.nextInt()));
		}

		// read interval coordinates, create it and add it to array
		for (int i = 0; i < M; i++) {
			Interval in = new Interval(reader.nextInt(), reader.nextInt());
			intervals.add(in);
		}

		int solution = getIntervals(points, intervals);
		writer.println(solution);
		writer.close();
	}

	public static int getIntervals(ArrayList<Point> points, ArrayList<Interval> intervals) {

		int coveredPoints = 0;
		int uncoveredPoints = points.size();
		int begin;
		int count = 0;
		int numberOfIntervals = 0;
		Point p;
		Point aux;

		// while there are points uncovered
		while (uncoveredPoints > 0) {
			count = 0;
			// get the first uncovered point
			p = points.get(coveredPoints);
			// find the interval that contains it
			Interval solution = p.intervalsIntersected(intervals);
			// increase the number of intervals
			numberOfIntervals++;
			// check the following points from the last covered point
			begin = coveredPoints;
			for (int i = begin; i < points.size(); i++) {
				aux = points.get(i);
				if (aux.coordinate > solution.upperLimit) {
					break;
				}
				// see if point is covered
				if (aux.isCovered(solution)) {
					count++;
					coveredPoints++;
				}
			}
			// update the number of uncovered points
			uncoveredPoints -= count;
		}

		return numberOfIntervals;
	}
}
