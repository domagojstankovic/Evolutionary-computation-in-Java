package hr.fer.zemris.optjava.dz13.model.sel;

import java.util.List;

import hr.fer.zemris.optjava.dz13.model.Solution;

public interface ISelection {
	
	public Solution select(List<Solution> sols);
	
}
