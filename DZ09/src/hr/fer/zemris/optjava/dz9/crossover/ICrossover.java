package hr.fer.zemris.optjava.dz9.crossover;

import hr.fer.zemris.optjava.dz9.model.Solution;

public interface ICrossover {

	public Solution cross(Solution parent1, Solution parent2);
	
}
