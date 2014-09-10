package hr.fer.zemris.optjava.dz2;

public interface IVector {
	
	public int getLength();
	
	public double getVariable(int index);
	
	public IVector scalarMultiply(double scalar);
	
	public IVector add(IVector anotherVector);
	
	public double multiply(IVector anotherVector);
	
	public IVector unitVector();
	
}
