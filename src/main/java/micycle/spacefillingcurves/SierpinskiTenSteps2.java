package micycle.spacefillingcurves;

import org.locationtech.jts.geom.Coordinate;

public class SierpinskiTenSteps2 extends SpaceFillingCurve {

	public SierpinskiTenSteps2(int width, int height) {
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
			Coordinate pto5 = new Coordinate(0, 0);
			Coordinate pto6 = new Coordinate(0, 0);
			Coordinate pto7 = new Coordinate(0, 0);
			Coordinate pto8 = new Coordinate(0, 0);

			pto1.x = ((a.x + c.x) / 2);
			pto1.y = ((a.y + c.y) / 2);

			pto3.x = ((b.x + c.x) / 2);
			pto3.y = ((b.y + c.y) / 2);

			pto2.x = ((pto3.x + pto1.x) / 2);
			pto2.y = ((pto3.y + pto1.y) / 2);

			pto5.x = ((b.x + a.x) / 2);
			pto5.y = ((b.y + a.y) / 2);

			pto6.x = ((pto5.x + a.x) / 2);
			pto6.y = ((pto5.y + a.y) / 2);

			pto4.x = ((pto5.x + pto3.x) / 2);
			pto4.y = ((pto5.y + pto3.y) / 2);

			pto7.x = ((a.x + pto1.x) / 2);
			pto7.y = ((a.y + pto1.y) / 2);

			pto8.x = ((pto3.x + a.x) / 2);
			pto8.y = ((pto3.y + a.y) / 2);

			if (n == 0) {
				if (this.malla) {
					this.drawTriangle(a, b, c);
				}
				this.calcularMedianas(a, b, c);
			}
			if (!sw) {
				this.render(pto1, pto3, c, n - 1, false);
				this.render(pto2, pto3, pto8, n - 1, true);
				this.render(pto2, pto8, pto1, n - 1, true);
				this.render(pto7, pto8, pto1, n - 1, false);

				this.render(pto7, pto8, a, n - 1, true);
				this.render(pto6, a, pto8, n - 1, true);

				this.render(pto6, pto5, pto8, n - 1, false);
				this.render(pto4, pto5, pto8, n - 1, true);
				this.render(pto4, pto8, pto3, n - 1, true);
				this.render(pto5, b, pto3, n - 1, false);
			} else {
				this.render(pto5, b, pto3, n - 1, true);
				this.render(pto4, pto8, pto3, n - 1, false);
				this.render(pto4, pto5, pto8, n - 1, false);
				this.render(pto6, pto5, pto8, n - 1, true);

				this.render(pto6, a, pto8, n - 1, false);
				this.render(pto7, pto8, a, n - 1, false);

				this.render(pto7, pto8, pto1, n - 1, true);
				this.render(pto2, pto8, pto1, n - 1, false);
				this.render(pto2, pto3, pto8, n - 1, false);
				this.render(pto1, pto3, c, n - 1, true);
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
				a.x = (0);
				a.y = (this.getCentroDeLaPantalla().y * (p + 1));

				b.x = (this.getCentroDeLaPantalla().x);
				b.y = (a.y - this.getCentroDeLaPantalla().x);

				c.x = (this.getCentroDeLaPantalla().x * 2);
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
