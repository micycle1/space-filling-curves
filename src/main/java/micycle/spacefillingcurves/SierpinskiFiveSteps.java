package micycle.spacefillingcurves;

import org.locationtech.jts.geom.Coordinate;

public class SierpinskiFiveSteps extends SpaceFillingCurve {

	public SierpinskiFiveSteps(double width, double height) {
		super(width, height);
	}

	private void render(Coordinate a, Coordinate b, Coordinate c, int n, boolean sw) {
		/*-
		 *          c
		 *         /|
		 *        / |
		 *       /  |
		 *      /   |
		 *     /    |
		 *    /     |
		 *   /      |
		 * b/_______|
		 *          a
		 * */
		if (n >= 0) {
			Coordinate pto1 = new Coordinate(0, 0);
			Coordinate pto2 = new Coordinate(0, 0);
			Coordinate pto3 = new Coordinate(0, 0);
			Coordinate pto4 = new Coordinate(0, 0);
			Coordinate ptoT = new Coordinate(0, 0);

			ptoT.x = ((b.x + c.x) / 2);
			ptoT.y = ((b.y + c.y) / 2);

			pto2.x = ((b.x + ptoT.x) / 2);
			pto2.y = ((b.y + ptoT.y) / 2);

			pto1.x = ((pto2.x + c.x) / 2);
			pto1.y = ((pto2.y + c.y) / 2);

			pto3.x = ((pto2.x + a.x) / 2);
			pto3.y = ((pto2.y + a.y) / 2);

			pto4.x = ((c.x + a.x) / 2);
			pto4.y = ((c.y + a.y) / 2);

			if (n == 0) {
				if (this.drawTriangles) {
					this.drawTriangle(a, b, c);
				}
				this.calculateMedian(a, b, c);
			}
			if (!sw) {
				this.render(pto1, pto4, c, n - 1, false);
				this.render(pto1, pto4, pto2, n - 1, true);
				this.render(pto3, pto2, pto4, n - 1, true);
				this.render(pto3, a, pto4, n - 1, false);
				this.render(pto2, b, a, n - 1, false);
			} else {
				this.render(pto2, b, a, n - 1, true);
				this.render(pto3, a, pto4, n - 1, true);
				this.render(pto3, pto2, pto4, n - 1, false);
				this.render(pto1, pto4, pto2, n - 1, false);
				this.render(pto1, pto4, c, n - 1, true);
			}
		}
	}

	@Override
	public void start() {
		/*-          
		 * 			b
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

			/*-
			 *          b
			 *         /|
			 *        / |
			 *       /  |
			 *      /   |
			 *     /    |
			 *    / IZQ |
			 *   /      |
			 * a/_______|
			 *          pm
			 * */
			this.render(pm, a, b, n, false); // Triangulo de la izq.

			/*- 
			 * b
			 * |\
			 * | \
			 * |  \
			 * |   \
			 * |    \
			 * | DER \
			 * |      \
			 * |_______\c
			 * pm
			 * */
			this.render(pm, c, b, n, false); // Triangulo de la der.

			paint(true);
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
			paint(false);
		}
	}
}
