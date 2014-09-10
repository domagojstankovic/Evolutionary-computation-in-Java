package hr.fer.zemris.optjava.dz10.function;

public class F1 implements IFunction {

	public F1() {
	}
	
	@Override
	public double valueAt(double[] variables) {
		return variables[0];
	}

}
