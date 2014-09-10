package hr.fer.zemris.optjava.dz3;

public interface INeighborhood<T extends SingleObjectiveSolution> {
	
	public T randomNeighbor(T current);
	
}
