package hr.fer.zemris.optjava.dz2;

public class Equation implements IFunction {
	
	private Polynomial polynomial;
	private double solution;
	
	public Equation(Polynomial polynomial, double solution) {
		super();
		this.polynomial = polynomial;
		this.solution = solution;
	}
	
	public Polynomial getPolynomial() {
		return polynomial;
	}
	
	public double getSolution() {
		return solution;
	}

	@Override
	public int getVariablesCount() {
		return polynomial.getVariablesCount();
	}

	@Override
	public double getValue(IVector vector) {
		return polynomial.getValue(vector) - solution;
	}

	@Override
	public IVector getGradient(IVector vector) {
		return polynomial.getGradient(vector);
	}

//	@Override
//	public RealMatrix getHessianMatrix(IVector vector) {
//		return polynomial.getHessianMatrix(vector);
//	}
	
	public double getKoef(int index) {
		return polynomial.getKoef(index);
	}

}
