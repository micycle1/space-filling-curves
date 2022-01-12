package micycle.spacefillingcurves;

import org.locationtech.jts.geom.Coordinate;

public class SierpinskiTenSteps extends SpaceFillingCurve {

	public SierpinskiTenSteps(int width, int height) {
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
			Coordinate pto9 = new Coordinate(0, 0);

			pto2.x = ((b.x + c.x) / 2);
			pto2.y = ((b.y + c.y) / 2);

			pto1.x = ((pto2.x + c.x) / 2);
			pto1.y = ((pto2.y + c.y) / 2);

			pto3.x = ((b.x + pto2.x) / 2);
			pto3.y = ((b.y + pto2.y) / 2);

			pto5.x = ((b.x + a.x) / 2);
			pto5.y = ((b.y + a.y) / 2);

			pto4.x = ((b.x + pto5.x) / 2);
			pto4.y = ((b.y + pto5.y) / 2);

			pto6.x = ((c.x + a.x) / 2);
			pto6.y = ((c.y + a.y) / 2);

			pto7.x = ((pto6.x + c.x) / 2);
			pto7.y = ((pto6.y + c.y) / 2);

			pto8.x = ((pto2.x + pto6.x) / 2);
			pto8.y = ((pto2.y + pto6.y) / 2);

			pto9.x = ((pto2.x + pto5.x) / 2);
			pto9.y = ((pto2.y + pto5.y) / 2);

			if (n == 0) {
				if (this.malla) {
					// try{Thread.sleep(500);}catch(Exception e){}
					this.drawTriangle(a, b, c);
				}
				this.calcularMedianas(a, b, c);
			}
			if (!sw) {
				this.render(pto7, pto1, c, n - 1, false);
				this.render(pto7, pto1, pto6, n - 1, true);
				this.render(pto8, pto6, pto1, n - 1, true);
				this.render(pto8, pto2, pto1, n - 1, false);
				this.render(pto6, pto2, a, n - 1, true);
				this.render(pto5, a, pto2, n - 1, true);
				this.render(pto9, pto3, pto2, n - 1, false);
				this.render(pto9, pto3, pto5, n - 1, true);
				this.render(pto4, pto5, pto3, n - 1, true);
				this.render(pto4, b, pto3, n - 1, false);
			} else {
				this.render(pto4, b, pto3, n - 1, true);
				this.render(pto4, pto5, pto3, n - 1, false);
				this.render(pto9, pto3, pto5, n - 1, false);
				this.render(pto9, pto3, pto2, n - 1, true);
				this.render(pto5, a, pto2, n - 1, false);
				this.render(pto6, pto2, a, n - 1, false);
				this.render(pto8, pto2, pto1, n - 1, true);
				this.render(pto8, pto6, pto1, n - 1, false);
				this.render(pto7, pto1, pto6, n - 1, false);
				this.render(pto7, pto1, c, n - 1, true);
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
