package hr.fer.zemris.optjava.dz6;

public class Position {

	double x;
	double y;
	
	public Position(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getDistance(Position another) {
		double a = x - another.x;
		double b = y - another.y;
		return Math.sqrt(a * a + b * b);
	}
	
}
