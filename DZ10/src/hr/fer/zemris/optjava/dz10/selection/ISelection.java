package hr.fer.zemris.optjava.dz10.selection;

import hr.fer.zemris.optjava.dz10.model.Population;
import hr.fer.zemris.optjava.dz10.model.Solution;

public interface ISelection {

	public Solution[] select(Population population, int n);
	
}
