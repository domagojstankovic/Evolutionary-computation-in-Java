package hr.fer.zemris.optjava.dz3;

public interface IDecoder<T extends SingleObjectiveSolution> {
	
	public double[] decode(T solution);
	
	public void decode(T solution, double[] field);
	
}
