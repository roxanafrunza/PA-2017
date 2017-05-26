import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Painter extends JFrame {
	private MyCanvas canvas = null;

	public Painter(ArrayList<Point> initialPoints) {
		canvas = new MyCanvas(initialPoints);
		this.add(canvas);
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		canvas.initStrategy();
	}

	public void setPoints(ArrayList<Point> points) {
		canvas.setPoints(points);
	}

	public void setCentroids(ArrayList<Point> points) {
		canvas.setCentroids(points);
	}
}

@SuppressWarnings("serial")
class MyCanvas extends Canvas {
	private BufferStrategy myStrategy = null;
	private ArrayList<Point> points = null;
	private ArrayList<Point> centroids = null;

	private Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.CYAN, Color.YELLOW };

	public MyCanvas(ArrayList<Point> points) {
		this.points = new ArrayList<Point>();
		this.centroids = new ArrayList<Point>();
		setPoints(points);
	}

	public void initStrategy() {
		this.createBufferStrategy(2);
		this.myStrategy = this.getBufferStrategy();
	}

	public void setPoints(ArrayList<Point> points) {
		deepCopy(points, this.points);
		repaint();
	}

	public void setCentroids(ArrayList<Point> points) {
		deepCopy(points, this.centroids);
		repaint();
	}

	private void deepCopy(ArrayList<Point> from, ArrayList<Point> to) {
		to.clear();
		for (Point point : from) {
			to.add(point.clone());
		}
	}

	void drawCircle(Graphics g, int x, int y, int r) {
		g.fillOval(x - r, y - r, 2 * r, 2 * r);
	}

	void clear(Graphics g, Color c) {
		g.setColor(c);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	void paintPoints(Graphics g) {
		if (points == null)
			return;
		for (Point point : points) {
			if (point.clusterIndex >= colors.length) {
				System.err.println("Color out of range: I only draw using " + colors.length + " distinct colors.");
				g.setColor(Color.GRAY);
			} else if (point.clusterIndex < 0) {
				g.setColor(Color.GRAY);
			} else {
				g.setColor(colors[point.clusterIndex]);
			}

			drawCircle(g, (int) (point.x * getWidth()), (int) (point.y * getHeight()), 1);
		}
	}

	void paintCentroids(Graphics g) {
		if (centroids == null)
			return;
		for (Point point : centroids) {
			g.setColor(Color.BLACK);
			drawCircle(g, (int) (point.x * getWidth()), (int) (point.y * getHeight()), 10);
			if (point.clusterIndex >= colors.length) {
				System.err.println("Color out of range: I only draw using " + colors.length + " distinct colors.");
				g.setColor(Color.GRAY);
			} else if (point.clusterIndex < 0) {
				g.setColor(Color.GRAY);
			} else {
				g.setColor(colors[point.clusterIndex]);
			}

			drawCircle(g, (int) (point.x * getWidth()), (int) (point.y * getHeight()), 6);
		}
	}

	@Override
	public void paint(Graphics g) {
		Graphics bg = myStrategy.getDrawGraphics();
		clear(bg, Color.WHITE);
		paintPoints(bg);
		paintCentroids(bg);
		bg.dispose();
		myStrategy.show();
		Toolkit.getDefaultToolkit().sync();
	}
}
