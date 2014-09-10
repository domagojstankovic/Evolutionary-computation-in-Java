package hr.fer.zemris.optjava.dz2;

public class Polynomial implements IFunction {

	private double[] koef;
	
	public Polynomial(double ... koef) {
		int length = koef.length;
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = koef[i];
		}
		this.koef = array;
	}
	
	@Override
	public int getVariablesCount() {
		return koef.length;
	}

	@Override
	public double getValue(IVector vector) {
		int length = koef.length;
		if (length != vector.getLength()) {
			throw new IllegalArgumentException();
		}
		double sum = 0.0;
		for (int i = 0; i < length; i++) {
			sum += 	koef[i] * vector.getVariable(i);
		}
		return sum;
	}

	@Override
	public IVector getGradient(IVector vector) {
		int length = koef.length;
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = koef[i];
		}
		return new Vector(array);
	}

//	@Override
//	public RealMatrix getHessianMatrix(IVector vector) {
//		int size = vector.getLength();
//		double[][] data = new double[size][size];
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				data[i][j] = 0;
//			}
//		}
//		return MatrixUtils.createRealMatrix(data);
//	}
	
	public double getKoef(int index) {
		return koef[index];
	}

}
