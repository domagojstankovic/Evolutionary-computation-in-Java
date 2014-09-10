package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class EquationsSystem implements IHFunction {

	private Equation[] equations;
	
	public EquationsSystem(Equation ... equations) {
		super();
		this.equations = equations;
	}

	@Override
	public int getVariablesCount() {
		return equations.length;
	}

	@Override
	public double getValue(IVector vector) {
		double eps = 0.0;
		for (int i = equations.length - 1; i >= 0; i--) {
			double value = equations[i].getValue(vector);
			eps += value * value;
		}
		return eps;
	}

	@Override
	public IVector getGradient(IVector vector) {
		int length = equations.length;
		double[] data = new double[length];
		for (int i = 0; i < length; i++) {
			data[i] = 0;
			for (int j = 0; j < length; j++) {
				double k = equations[j].getKoef(i);
				double eps = equations[j].getValue(vector);
				data[i] += k * eps;
			}
			data[i] *= 2;
		}
		return new Vector(data);
//		IVector v = equations[0].getGradient(vector);
//		for (int i = equations.length - 1; i > 0; i--) {
//			v = v.add(equations[i].getGradient(vector));
//		}
//		return v;
	}

	@Override
	public RealMatrix getHessianMatrix(IVector vector) {
		int size = equations.length;
		RealMatrix matrix = MatrixUtils.createRealMatrix(size, size);
		
		for (int i = 0; i < size; i++) {
			for (int j = i; j < size; j++) {
				double sum = 0.0;
				for (int k = 0; k < size; k++) {
					double x = equations[k].getKoef(i);
					double y = equations[k].getKoef(j);
					sum += x * y;
				}
				sum *= 2;
				matrix.setEntry(i, j, sum);
				matrix.setEntry(j, i, sum);
			}
		}
		
		return matrix;
	}

}
