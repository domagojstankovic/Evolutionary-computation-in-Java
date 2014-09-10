package hr.fer.zemris.generic.ga.selection;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.Population;

public interface ISelection {
	
	public GASolution<int[]> select(Population pop);
	
}
