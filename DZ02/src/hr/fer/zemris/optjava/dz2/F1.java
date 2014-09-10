package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class F1 implements IHFunction {

	@Override
	public int getVariablesCount() {
		return 2;
	}

	@Override
	public double getValue(IVector vector) {
		if (vector.getLength() != 2) {
			throw new IllegalArgumentException();
		}
		double x1 = vector.getVariable(0);
		double x2 = vector.getVariable(1);
		return x1 * x1 + (x2 - 1) * (x2 - 1);
	}

	@Override
	public IVector getGradient(IVector vector) {
		double x1 = 2 * vector.getVariable(0);
		double x2 = 2 * (vector.getVariable(1) - 1);
		return new Vector(x1, x2);
	}

	@Override
	public RealMatrix getHessianMatrix(IVector vector) {
		double[][] data = {{2, 0},{0, 2}};
		RealMatrix matrix = MatrixUtils.createRealMatrix(data);
		return matrix;
	}

}
