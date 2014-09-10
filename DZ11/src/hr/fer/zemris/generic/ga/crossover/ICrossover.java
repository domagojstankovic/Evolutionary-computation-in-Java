package hr.fer.zemris.generic.ga.crossover;

import hr.fer.zemris.generic.ga.GASolution;

public interface ICrossover {
	
	public GASolution<int[]> cross(GASolution<int[]> par1, GASolution<int[]> par2); 
	
}
