package micycle.spacefillingcurves;

import org.locationtech.jts.geom.Coordinate;

public class SierpinskiThreeSteps extends SpaceFillingCurve {

	public SierpinskiThreeSteps(double width, double height) {
		super(width, height);
	}

	private void render(Coordinate a, Coordinate b, Coordinate c, int n, boolean sw) {
		if (n >= 0) {
			Coordinate pto1 = new Coordinate(0, 0);
			Coordinate pto2 = new Coordinate(0, 0);
			Coordinate ctemp = new Coordinate(0, 0);
			double temp;

			if ((b.x - c.x) == 0) {
				temp = Math.pow(10, 40);
			} else {
				temp = (b.y - c.y) / (b.x - c.x);
			}

			if (temp == 0) {
				temp = Math.pow(10, 40);
			} else {
				temp = (-1) / temp;
			}

			pto1.x = ((b.x + c.x) / 2);
			pto1.y = ((b.y + c.y) / 2);
			ctemp.x = (1);
			ctemp.y = (temp * (ctemp.x - pto1.x) + pto1.y);
			this.calcularInterseccion(a, c, ctemp, pto1, pto2);
			if (n == 0) {
				if (this.drawTriangles) {
					this.drawTriangle(a, b, c);
				}
				this.calcularMedianas(a, b, c);
			}
			if (!sw) {
				this.render(pto1, pto2, c, n - 1, false);
				this.render(pto1, pto2, b, n - 1, true);
				this.render(a, pto2, b, n - 1, false);
			} else {
				this.render(a, pto2, b, n - 1, true);
				this.render(pto1, pto2, b, n - 1, false);
				this.render(pto1, pto2, c, n - 1, true);
			}
		}
	}

	@Override
	public void start() {
		/*-
		 *          b
		 *         /|\
		 *        / | \
		 *       /  |  \
		 *      /   |   \
		 *     /    |    \
		 *    /     |     \
		 *   /      |      \
		 * a/_______|_______\c
		 *          pm
		 * */
		Coordinate a = new Coordinate(0, 0);
		Coordinate b = new Coordinate(0, 0);
		Coordinate c = new Coordinate(0, 0);
		double p = 0.99d;
		Coordinate pm = new Coordinate(0, 0);
		int n;

		this.getCentroDeLaPantalla().x = (width / 2d);
		this.getCentroDeLaPantalla().y = (height / 2d);
		n = this.N;
		if (this.conf) {
			if (!this.points.isEmpty()) {
				a = this.points.get(0);
				b = this.points.get(1);
				c = this.points.get(2);
			} else {
				a.x = (this.getCentroDeLaPantalla().x - (Math.sqrt(3) / 3) * 2 * this.getCentroDeLaPantalla().y * p);
				a.y = (this.getCentroDeLaPantalla().y * (p + 1));

				b.x = (this.getCentroDeLaPantalla().x);
				b.y = (this.getCentroDeLaPantalla().y * (1 - p));

				c.x = (this.getCentroDeLaPantalla().x + (Math.sqrt(3) / 3) * 2 * this.getCentroDeLaPantalla().y * p);
				c.y = (a.y);
			}

			pm.x = ((a.x + c.x) / 2);
			pm.y = ((a.y + c.y) / 2);
			this.contadorDePoints = 0;
			this.render(pm, a, b, n, false);
			this.render(pm, c, b, n, false);
			pintar(true);
		} else {
			this.contadorDePoints = 0;
			a.x = (this.getCentroDeLaPantalla().x);
			a.y = (this.getCentroDeLaPantalla().y);
			b.x = (this.getCentroDeLaPantalla().x);
			b.y = (this.getCentroDeLaPantalla().y - this.getCentroDeLaPantalla().x / Math.sqrt(3));
			c.x = (2 * this.getCentroDeLaPantalla().x);
			c.y = (this.getCentroDeLaPantalla().y);
			this.render(a, b, c, n, false);
			b.x = (this.getCentroDeLaPantalla().x);
			b.y = (this.getCentroDeLaPantalla().y + this.getCentroDeLaPantalla().x / Math.sqrt(3));
			this.render(a, b, c, n, false);
			c.x = (0);
			this.render(a, b, c, n, false);
			b.x = (this.getCentroDeLaPantalla().x);
			b.y = (this.getCentroDeLaPantalla().y - this.getCentroDeLaPantalla().x / Math.sqrt(3));
			this.render(a, b, c, n, false);
			pintar(false);
		}
	}
}
