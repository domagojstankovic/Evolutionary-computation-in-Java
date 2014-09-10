package hr.fer.zemris.optjava.dz13.cross;

import hr.fer.zemris.optjava.dz13.model.Solution;

public interface ICrossover {
	
	public Solution[] cross(Solution parent1, Solution parent2);
	
}
