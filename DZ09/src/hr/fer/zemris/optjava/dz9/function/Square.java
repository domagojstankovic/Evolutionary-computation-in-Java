package hr.fer.zemris.optjava.dz9.function;

public class Square implements IFunction {

	private int index;
	
	public Square(int index) {
		super();
		this.index = index;
	}

	@Override
	public double valueAt(double[] variables) {
		return variables[index] * variables[index];
	}

}
