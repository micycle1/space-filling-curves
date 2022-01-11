package org.fractals;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;

public abstract class SpaceFillingCurve extends Fractal {
	protected int contadorDePuntos = 0;
	protected int N = 1;
	protected Coordinate todosLosPuntos[] = new Coordinate[8 * 1024 * 1024];
	protected boolean malla = true;
	protected boolean relleno = false;
	protected boolean conf = true;
	protected double perimetro = 0;
	protected double area = 0;
	protected ArrayList<Coordinate> puntos = null;
	protected Color curveColor = new Color(0, 0, 220);

	public SpaceFillingCurve(int width, int height) {
		super(width,height);
		this.puntos = new ArrayList<Coordinate>();
	}

	public SpaceFillingCurve(int width, int height, ArrayList<Coordinate> puntos) {
		super(width, height);
		this.puntos = puntos;
	}

	protected void calcularInterseccion(Coordinate a, Coordinate pm2, Coordinate c, Coordinate pm1, Coordinate mediana) {
		double determinante;
		double m[] = new double[2];
		double k[] = new double[2];

		if ((a.x - pm2.x) == 0) {
			m[0] = Math.pow(10, 40);
		} else {
			m[0] = (a.y - pm2.y) / (a.x - pm2.x);
		}

		if ((c.x - pm1.x) == 0) {
			m[1] = Math.pow(10, 40);
		} else {
			m[1] = (c.y - pm1.y) / (c.x - pm1.x);
		}

		k[0] = m[0] * a.x - a.y;
		k[1] = m[1] * c.x - c.y;
		determinante = (m[1] - m[0]);
		mediana.y = (((m[0] * k[1]) - (m[1] * k[0])) / determinante);
		mediana.x = ((k[1] - k[0]) / determinante);
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

		this.todosLosPuntos[this.contadorDePuntos] = new Coordinate(mediana.x, mediana.y);

		this.contadorDePuntos++;
	}

	protected void dibujarTriangulo(Coordinate a, Coordinate b, Coordinate c, int n) {
		float stroke = 0.25f;
//		this.dibujarLinea(a, b, Color.LIGHT_GRAY, stroke);
//		this.dibujarLinea(b, c, Color.LIGHT_GRAY, stroke);
//		this.dibujarLinea(c, a, Color.LIGHT_GRAY, stroke);
	}

	protected void pintar(boolean sw) {
		float stroke = 1f;
//		if (sw) {
//			if (!this.relleno) {
//				for (int i = 1; i < this.contadorDePuntos / 2; i++) {
//					// try{Thread.sleep(10);}catch(Exception e){}
//					this.dibujarLinea(this.todosLosPuntos[i - 1], this.todosLosPuntos[i], curveColor, stroke);
//				}
//
//				for (int i = this.contadorDePuntos / 2 + 1; i < this.contadorDePuntos; i++) {
//					// try{Thread.sleep(10);}catch(Exception e){}
//					this.dibujarLinea(this.todosLosPuntos[i - 1], this.todosLosPuntos[i], curveColor, stroke);
//				}
//
//				this.dibujarLinea(this.todosLosPuntos[0], this.todosLosPuntos[this.contadorDePuntos / 2], curveColor, stroke);
//			}
//		} else if (!this.relleno) {
//			for (int i = 1; i < this.contadorDePuntos / 4; i++) {
//				this.dibujarLinea(this.todosLosPuntos[i - 1], this.todosLosPuntos[i], curveColor, stroke);
//			}
//
//			for (int i = this.contadorDePuntos / 4 + 1; i < this.contadorDePuntos / 2; i++) {
//				this.dibujarLinea(this.todosLosPuntos[i - 1], this.todosLosPuntos[i], curveColor, stroke);
//			}
//
//			for (int i = this.contadorDePuntos / 2 + 1; i < 3 * this.contadorDePuntos / 4; i++) {
//				this.dibujarLinea(this.todosLosPuntos[i - 1], this.todosLosPuntos[i], curveColor, stroke);
//			}
//
//			for (int i = 3 * this.contadorDePuntos / 4 + 1; i < this.contadorDePuntos; i++) {
//				this.dibujarLinea(this.todosLosPuntos[i - 1], this.todosLosPuntos[i], curveColor, stroke);
//			}
//		}
	}

	public abstract void start();

	public void setN(int N) {
		this.N = N;
	}

	public void setPuntos(ArrayList<Coordinate> puntos) {
		this.puntos = puntos;
	}
}