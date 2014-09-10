package hr.fer.zemris.optjava.dz10.crossover;

import hr.fer.zemris.optjava.dz10.model.Solution;

public interface ICrossover {

	public Solution cross(Solution parent1, Solution parent2);
	
}
