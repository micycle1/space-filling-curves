package org.fractals;

import org.locationtech.jts.geom.Coordinate;

public abstract class Fractal {
	
	private Coordinate centroDeLaPantalla = new Coordinate(0, 0);
	private String description;
	protected int width, height;

	public Fractal(int width, int height) {
		this.centroDeLaPantalla.x = (width / 2d);
		this.centroDeLaPantalla.y = (height / 2d);
		this.description = "<sin nombre>";
		this.width = width;
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

//	protected void dibujarLinea(Coordinate a, Coordinate b, Color color, float stroke) {
//		Line2D line = new Line2D.Double(a.getPoint2Dobject(), b.getPoint2Dobject());
//
//		this.g2d.setPaint(color);
//		this.g2d.setStroke(new BasicStroke(stroke));
//		this.g2d.draw(line);
//	}

	protected Coordinate getCentroDeLaPantalla() {
		return this.centroDeLaPantalla;
	}
}