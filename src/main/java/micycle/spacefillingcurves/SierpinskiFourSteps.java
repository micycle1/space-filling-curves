package micycle.spacefillingcurves;

import org.locationtech.jts.geom.Coordinate;

public class SierpinskiFourSteps extends SpaceFillingCurve {

	public SierpinskiFourSteps(double width, double height) {
		super(width, height);
	}

	private void render(Coordinate a, Coordinate b, Coordinate c, int n, boolean sw) {
		if (n >= 0) {
			Coordinate pto1 = new Coordinate(0, 0);
			Coordinate pto2 = new Coordinate(0, 0);
			Coordinate pto3 = new Coordinate(0, 0);

			pto1.x = ((b.x + c.x) / 2);
			pto1.y = ((b.y + c.y) / 2);
			pto2.x = ((a.x + c.x) / 2);
			pto2.y = ((a.y + c.y) / 2);
			pto3.x = ((b.x + pto1.x) / 2);
			pto3.y = ((b.y + pto1.y) / 2);
			if (n == 0) {
				if (this.malla) {
					drawTriangle(a, b, c);
				}
				calcularMedianas(a, b, c);
			}
			if (!sw) {
				this.render(pto2, pto1, c, n - 1, false);
				this.render(pto2, pto1, a, n - 1, true);
				this.render(pto3, pto1, a, n - 1, false);
				this.render(pto3, b, a, n - 1, true);
			} else {
				this.render(pto3, b, a, n - 1, false);
				this.render(pto3, pto1, a, n - 1, true);
				this.render(pto2, pto1, a, n - 1, false);
				this.render(pto2, pto1, c, n - 1, true);
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
		this.perimetro = 0;
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
			this.pintar(true);
		} else {
			this.contadorDePoints = 0;
			a.x = (this.getCentroDeLaPantalla().x * p);
			a.y = (this.getCentroDeLaPantalla().y * p);
			b.x = (this.getCentroDeLaPantalla().x * p);
			b.y = ((this.getCentroDeLaPantalla().y - this.getCentroDeLaPantalla().x / Math.sqrt(3)) * p);
			c.x = (2 * this.getCentroDeLaPantalla().x * p);
			c.y = (this.getCentroDeLaPantalla().y * p);
			this.render(a, b, c, n, false);
			b.x = (this.getCentroDeLaPantalla().x * p);
			b.y = ((this.getCentroDeLaPantalla().y + this.getCentroDeLaPantalla().x / Math.sqrt(3)) * p);
			this.render(a, b, c, n, false);
			c.x = (0);
			this.render(a, b, c, n, false);
			b.x = (this.getCentroDeLaPantalla().x * p);
			b.y = ((this.getCentroDeLaPantalla().y - this.getCentroDeLaPantalla().x / Math.sqrt(3)) * p);
			this.render(a, b, c, n, false);
			pintar(false);
		}
	}
}
