package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class NumOptAlgorithms {

	private static final int MAX_ITERACIJA = 1000;
	
	public static IVector gradientDescent(IFunction function, int maxIterations, IVector first, boolean print) {
		IVector vector = first;
		for (int i = 0; i < maxIterations; i++) {
			if (print) {
				System.out.println(vector.toString());
			}
			if (jeOptimum(function, vector)) {
				return vector;
			}
			IVector d = function.getGradient(vector).scalarMultiply(-1);
			double lambda = findLambda(function, vector, d);
			vector = vector.add(d.scalarMultiply(lambda));
		}
		return vector;
	}

	private static double findLambda(IFunction function, IVector x, IVector d) {
		double lambdaL = 0.0;
		double lambdaU = findLambdaU(function, x, d);
		return bisekcija(lambdaL, lambdaU, function, x, d);
	}

	private static double bisekcija(double l, double u, IFunction function, IVector x, IVector d) {
		int i = 0;
		double mid = 0;
		while (i < MAX_ITERACIJA) {
			mid = (l + u) / 2;
			double der = dTheta(function, x, d, mid);
			if (Math.abs(der) < 1e-6) {
				return mid;
			} else if (der < 0) {
				l = mid;
			} else {
				u = mid;
			}
			i++;
		}
		return mid;
	}

	private static double findLambdaU(IFunction function, IVector x, IVector d) {
		double lambda = 1.0;
		while (dTheta(function, x, d, lambda) < 0) {
			lambda *= 2;
		}
		return lambda;
	}
	
	private static double dTheta(IFunction function, IVector x, IVector d, double lambda) {
		IVector v = x.add(d.scalarMultiply(lambda));
		IVector g = function.getGradient(v);
		return g.multiply(d);
	}

	private static boolean jeOptimum(IFunction function, IVector vector) {
		IVector g = function.getGradient(vector);
		double eps = 0.0;
		for (int j = g.getLength() - 1; j >= 0; j--) {
			double var = g.getVariable(j);
			eps += var * var;
		}
		if (eps < 1e-3) {
			return true;
		}
		return false;
//		boolean nula = true;
//		for (int j = g.getLength() - 1; j >= 0 && nula; j--) {
//			if (Math.abs(g.getVariable(j)) > 1e-3) {
//				nula = false;
//			}
//		}
//		return nula;
	}

	public static IVector newtonMethod(IHFunction function, int maxIterations, IVector first, boolean print) {
		IVector vector = first;
		for (int i = 0; i < maxIterations; i++) {
			if (print) {
				System.out.println(vector.toString());
			}
			if (jeOptimum(function, vector)) {
				return vector;
			}
			IVector d = findTau(function, vector);
			double lambda = findLambda(function, vector, d);
			vector = vector.add(d.scalarMultiply(lambda));
		}
		return vector;
	}
	
	private static IVector findTau(IHFunction function, IVector x) {
		RealMatrix hessianMatrix = function.getHessianMatrix(x);
		RealMatrix inverse = new LUDecomposition(hessianMatrix).getSolver().getInverse();
		RealMatrix vector = getMatrix(function.getGradient(x));
		RealMatrix tau = inverse.multiply(vector).scalarMultiply(-1);
		return getVector(tau);
	}

	private static RealMatrix getMatrix(IVector vector) {
		int length = vector.getLength();
		double[][] mat = new double[length][1];
		for (int i = 0; i < length; i++) {
			mat[i][0] = vector.getVariable(i);
		}
		return MatrixUtils.createRealMatrix(mat);
	}
	
	private static IVector getVector(RealMatrix matrix) {
		int size = matrix.getRowDimension();
		double[] array = new double[size];
		for (int i = 0; i < size; i++) {
			array[i] = matrix.getEntry(i, 0);
		}
		return new Vector(array);
	}

}
