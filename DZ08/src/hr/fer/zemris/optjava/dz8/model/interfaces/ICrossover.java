package hr.fer.zemris.optjava.dz8.model.interfaces;

import hr.fer.zemris.optjava.dz8.model.Solution;

public interface ICrossover {
	
	public Solution cross(Solution mutant, Solution target);
	
}
