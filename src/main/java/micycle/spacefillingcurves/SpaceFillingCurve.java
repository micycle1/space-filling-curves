package micycle.spacefillingcurves;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;

public abstract class SpaceFillingCurve extends Fractal {

	protected int contadorDePoints = 0;
	protected int N = 1;
	protected List<Coordinate> todosLosPoints = new ArrayList<>(500);
	protected boolean drawTriangles = false;
	protected boolean relleno = false;
	protected boolean conf = true;
	protected List<Coordinate> points = null;

	protected SpaceFillingCurve(double width, double height) {
		super(width, height);
		this.points = new ArrayList<>();
	}

	protected SpaceFillingCurve(int width, int height, List<Coordinate> points) {
		super(width, height);
		this.points = points;
	}

	protected void calcularInterseccion(Coordinate a, Coordinate pm2, Coordinate c, Coordinate pm1, Coordinate median) {
		double determinant;
		double[] m = new double[2];
		double[] k = new double[2];

		if ((a.x - pm2.x) == 0) {
			m[0] = 1.0E40;
		} else {
			m[0] = (a.y - pm2.y) / (a.x - pm2.x);
		}

		if ((c.x - pm1.x) == 0) {
			m[1] = 1.0E40;
		} else {
			m[1] = (c.y - pm1.y) / (c.x - pm1.x);
		}

		k[0] = m[0] * a.x - a.y;
		k[1] = m[1] * c.x - c.y;
		determinant = (m[1] - m[0]);
		median.y = (((m[0] * k[1]) - (m[1] * k[0])) / determinant);
		median.x = ((k[1] - k[0]) / determinant);
	}

	protected void calcularMedianas(Coordinate a, Coordinate b, Coordinate c) {
		Coordinate pm1 = new Coordinate(0, 0);
		Coordinate pm2 = new Coordinate(0, 0);
		Coordinate mediana = new Coordinate(0, 0);

		pm1.x = ((a.x + b.x) / 2);
		pm1.y = ((a.y + b.y) / 2);
		pm2.x = ((b.x + c.x) / 2);
		pm2.y = ((b.y + c.y) / 2);

		this.calcularInterseccion(a, pm2, c, pm1, mediana);

		todosLosPoints.add(new Coordinate(mediana.x, mediana.y));

		this.contadorDePoints++;
	}

	protected void drawTriangle(Coordinate a, Coordinate b, Coordinate c) {
		this.addLineSegment(a, b);
		this.addLineSegment(b, c);
		this.addLineSegment(c, a);
	}

	protected void pintar(boolean sw) {
		if (sw) {
			if (!this.relleno) {
				for (int i = 1; i < this.contadorDePoints / 2; i++) {
					this.addLineSegment(this.todosLosPoints.get(i - 1), this.todosLosPoints.get(i));
				}

				for (int i = this.contadorDePoints / 2 + 1; i < this.contadorDePoints; i++) {
					this.addLineSegment(this.todosLosPoints.get(i - 1), this.todosLosPoints.get(i));
				}

				this.addLineSegment(this.todosLosPoints.get(0), this.todosLosPoints.get(this.contadorDePoints / 2));
			}
		} else if (!this.relleno) {
			for (int i = 1; i < this.contadorDePoints / 4; i++) {
				this.addLineSegment(this.todosLosPoints.get(i - 1), this.todosLosPoints.get(i));
			}

			for (int i = this.contadorDePoints / 4 + 1; i < this.contadorDePoints / 2; i++) {
				this.addLineSegment(this.todosLosPoints.get(i - 1), this.todosLosPoints.get(i));
			}

			for (int i = this.contadorDePoints / 2 + 1; i < 3 * this.contadorDePoints / 4; i++) {
				this.addLineSegment(this.todosLosPoints.get(i - 1), this.todosLosPoints.get(i));
			}

			for (int i = 3 * this.contadorDePoints / 4 + 1; i < this.contadorDePoints; i++) {
				this.addLineSegment(this.todosLosPoints.get(i - 1), this.todosLosPoints.get(i));
			}
		}
	}

	public abstract void start();

	public void setN(int N) {
		this.N = N;
	}
}
