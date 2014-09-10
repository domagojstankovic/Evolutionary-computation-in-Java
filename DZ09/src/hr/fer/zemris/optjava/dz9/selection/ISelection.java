package hr.fer.zemris.optjava.dz9.selection;

import hr.fer.zemris.optjava.dz9.model.Population;
import hr.fer.zemris.optjava.dz9.model.Solution;

public interface ISelection {

	public Solution[] select(Population population, int n);
	
}
