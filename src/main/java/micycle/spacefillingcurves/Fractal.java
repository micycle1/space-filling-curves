package micycle.spacefillingcurves;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;

public abstract class Fractal {

	private Coordinate centroDeLaPantalla = new Coordinate(0, 0);
	private String description;
	protected double width, height;
	private List<double[]> lineSegments;

	protected Fractal(double width, double height) {
		this.centroDeLaPantalla.x = (width / 2d);
		this.centroDeLaPantalla.y = (height / 2d);
		this.width = width;
		this.height = height;
		lineSegments = new ArrayList<>();
	}

	public String getDescription() {
		return description;
	}

	public List<double[]> getLineSegments() {
		return lineSegments;
	}

	protected void addLineSegment(Coordinate a, Coordinate b) {
		lineSegments.add(new double[] { a.x, a.y, b.x, b.y });
	}

	protected Coordinate getCentroDeLaPantalla() {
		return this.centroDeLaPantalla;
	}
}