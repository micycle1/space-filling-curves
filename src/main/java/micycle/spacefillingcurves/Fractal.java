package micycle.spacefillingcurves;

import org.locationtech.jts.geom.Coordinate;

import processing.core.PApplet;

public abstract class Fractal {
	
	private Coordinate centroDeLaPantalla = new Coordinate(0, 0);
	private String description;
	protected int width, height;
	private PApplet p;

	protected Fractal(int width, int height) {
		this.centroDeLaPantalla.x = (width / 2d);
		this.centroDeLaPantalla.y = (height / 2d);
		this.width = width;
		this.height = height;
	}
	
	public void setPApplet(PApplet p) {
		this.p = p;
		width = p.width;
		height = p.height;
	}

	public String getDescription() {
		return description;
	}

	protected void drawLine(Coordinate a, Coordinate b) {
		p.line((float) a.x, (float) a.y, (float) b.x, (float) b.y);
	}

	protected Coordinate getCentroDeLaPantalla() {
		return this.centroDeLaPantalla;
	}
}