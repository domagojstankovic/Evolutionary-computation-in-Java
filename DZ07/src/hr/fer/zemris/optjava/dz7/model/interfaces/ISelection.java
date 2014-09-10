package hr.fer.zemris.optjava.dz7.model.interfaces;

import hr.fer.zemris.optjava.dz7.model.Pop;
import hr.fer.zemris.optjava.dz7.model.Solution;

public interface ISelection {
	
	public Solution select(Pop population, boolean best);
	
}
