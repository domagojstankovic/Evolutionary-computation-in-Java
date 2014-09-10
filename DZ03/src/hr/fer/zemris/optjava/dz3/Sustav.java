package hr.fer.zemris.optjava.dz3;

public class Sustav implements IFunction {

private Funkcija[] funkcije;
	
	public Sustav(Funkcija[] funkcije) {
		this.funkcije = funkcije;
	}
	
	@Override
	public double valueAt(double[] variables) {
		double eps = 0.0;
		for (int i = funkcije.length - 1; i >= 0; i--) {
			double value = funkcije[i].valueAt(variables);
			eps += value * value;
		}
		return eps;
	}

}
