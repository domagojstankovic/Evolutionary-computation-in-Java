package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class PrijenosnaSustav implements IHFunction {

	private PrijenosnaFja[] funkcije;
	
	public PrijenosnaSustav(PrijenosnaFja[] funkcije) {
		this.funkcije = funkcije;
	}

	@Override
	public int getVariablesCount() {
		return funkcije[0].getVariablesCount();
	}

	@Override
	public double getValue(IVector vector) {
		double eps = 0.0;
		for (int i = funkcije.length - 1; i >= 0; i--) {
			double value = funkcije[i].getValue(vector);
			eps += value * value;
		}
		return eps;
	}

	@Override
	public IVector getGradient(IVector vector) {
		double[] data = new double[6];
		int size = funkcije.length;
		double c = vector.getVariable(2);
		double d = vector.getVariable(3);
		double e = vector.getVariable(4);
		for (int i = 0; i < 6; i++) {
			data[i] = 0.0;
		}
		for (int i = 0; i < size; i++) {
			double x1 = funkcije[i].getVariable(1);
			double x2 = funkcije[i].getVariable(2);
			double x3 = funkcije[i].getVariable(3);
			double x4 = funkcije[i].getVariable(4);
			double x5 = funkcije[i].getVariable(5);
			double eps = funkcije[i].getValue(vector);
			double cos = 1 + Math.cos(e * x4);
			double exp = Math.exp(d * x3);
			data[0] += 2 * eps * x1;
			data[1] += 2 * eps * x1 * x1 * x1 * x2;
			data[2] += 2 * eps * exp * cos;
			data[3] += 2 * eps * c * exp * cos * x3;
			data[4] += -2 * eps * c * exp * Math.sin(e * x4) * x4;
			data[5] += 2 * eps * x4 * x5 * x5;
		}
		return new Vector(data).unitVector();
	}

	@Override
	public RealMatrix getHessianMatrix(IVector vector) {
		int size = funkcije.length;
		int length = vector.getLength();
		double[][] data = new double[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				data[i][j] = 0.0;
			}
		}
		double c = vector.getVariable(2);
		double d = vector.getVariable(3);
		double e = vector.getVariable(4);
		for (int i = 0; i < size; i++) {
			double x1 = funkcije[i].getVariable(1);
			double x2 = funkcije[i].getVariable(2);
			double x3 = funkcije[i].getVariable(3);
			double x4 = funkcije[i].getVariable(4);
			double x5 = funkcije[i].getVariable(5);
			double eps = funkcije[i].getValue(vector);
			double cos = 1 + Math.cos(e * x4);
			double sin = Math.sin(e * x4);
			double exp = Math.exp(d * x3);
			data[0][0] += 2 * x1 * x1;
			data[0][1] += 2 * x1 * x1 * x1 * x1 * x2;
			data[0][2] += 2 * x1 * exp * cos;
			data[0][3] += 2 * x1 * c * exp * cos * x3;
			data[0][4] += -2 * x1 * c * exp * sin * x4;
			data[0][5] += 2 * x1 * x4 * x5 * x5;
			data[1][1] += 2 * x1 * x1 * x1 * x1 * x1 * x1 * x2 * x2;
			data[1][2] += 2 * x1 * x1 * x1 * x2 * exp * cos;
			data[1][3] += 2 * x1 * x1 * x1 * x2 * c * exp * cos * x3;
			data[1][4] += -2 * x1 * x1 * x1 * x2 * c * exp * sin * x4;
			data[1][5] += 2 * x1 * x1 * x1 * x2 * x4 * x5 * x5;
			data[2][2] += 2 * exp * exp * cos * cos;
			data[2][3] += 2 * exp * x3 * cos * (eps + c * exp * cos);
			data[2][4] += -2 * exp * sin * x4 * (c * exp * cos + eps);
			data[2][5] += 2 * exp * cos * x4 * x5 * x5;
			data[3][3] += 2 * c * x3 * x3 * exp * cos * (c * exp * cos + eps);
			data[3][4] += -2 * c * x3 * x4 * sin * exp * (c * exp * cos + eps);
			data[3][5] += 2 * c * exp * cos * x3 * x4 * x5 * x5;
			data[4][4] += -2 * c * exp * x4 * x4 * (-c * exp * sin * sin + eps * (cos - 1));
			data[4][5] += -2 * c * exp * sin * x4 * x4 * x5 * x5;
			data[5][5] += 2 * x4 * x4 * x5 * x5 * x5 * x5;
		}
		for (int i = 0; i < length; i++) {
			for (int j = i + 1; j < length; j++) {
				data[j][i] = data[i][j];
			}
		}
		return MatrixUtils.createRealMatrix(data);
	}

}
