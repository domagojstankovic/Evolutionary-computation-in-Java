package hr.fer.zemris.optjava.dz2;

public class Vector implements IVector {

	private double[] variables;
	
	public Vector(double ... variables) {
		int length = variables.length;
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = variables[i];
		}
		this.variables = array;
	}
	
	@Override
	public int getLength() {
		return variables.length;
	}

	@Override
	public double getVariable(int index) {
		return variables[index];
	}

	@Override
	public IVector scalarMultiply(double scalar) {
		int length = variables.length;
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = scalar * variables[i];
		}
		return new Vector(array);
	}

	@Override
	public IVector add(IVector anotherVector) {
		int length = variables.length;
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = anotherVector.getVariable(i) + variables[i];
		}
		return new Vector(array);
	}

	@Override
	public double multiply(IVector anotherVector) {
		int length = variables.length;
		if (length != anotherVector.getLength()) {
			throw new IllegalArgumentException();
		}
		double m = 0;
		for (int i = 0; i < length; i++) {
			m += variables[i] * anotherVector.getVariable(i);
		}
		return m;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		int length = variables.length;
		for (int i = 0; i < length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(variables[i]);
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public IVector unitVector() {
		double sum = 0.0;
		for (int i = 0; i < 6; i++) {
			sum += variables[i] * variables[i];
		}
		sum = Math.sqrt(sum);
		double[] array = new double[6];
		for (int i = 0; i < 6; i++) {
			array[i] = variables[i] / sum;
		}
		return new Vector(array);
	}

}
