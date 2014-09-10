package hr.fer.zemris.optjava.dz2;

public interface IFunction {
	
	public int getVariablesCount();
	
	public double getValue(IVector vector);
	
	public IVector getGradient(IVector vector);
	
}
