package hr.fer.zemris.optjava.dz4.part1;

public class Funkcija implements IFunction {

	double[] variables;
	double y;
	
	public Funkcija(double[] variables, double y) {
		int length = variables.length;
		if (length != 5) {
			throw new IllegalArgumentException();
		}
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = variables[i];
		}
		this.variables = array;
		this.y = y;
	}
	
	@Override
	public double valueAt(double[] variables) {
		double x1 = this.variables[0];
		double x2 = this.variables[1];
		double x3 = this.variables[2];
		double x4 = this.variables[3];
		double x5 = this.variables[4];
		double a = variables[0];
		double b = variables[1];
		double c = variables[2];
		double d = variables[3];
		double e = variables[4];
		double f = variables[5];
		double r1 = a * x1;
		double r2 = b * x1 * x1 * x1 * x2;
		double r3 = c * Math.exp(d * x3) * (1 + Math.cos(e * x4));
		double r4 = f * x4 * x5 * x5;
		return r1 + r2 + r3 + r4 - y;
	}

}
