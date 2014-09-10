package hr.fer.zemris.optjava.dz10.function;

public class F2 implements IFunction {

	public F2() {
	}
	
	@Override
	public double valueAt(double[] variables) {
		return (1 + variables[1]) / variables[0];
	}

}
