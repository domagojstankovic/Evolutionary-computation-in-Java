package hr.fer.zemris.optjava.dz13.model;

import java.util.List;

public class Population {
	
	private List<Solution> sols;

	public Population(List<Solution> sols) {
		super();
		this.sols = sols;
	}
	
	public List<Solution> getSols() {
		return sols;
	}
	
	public Solution getSolutionAt(int index) {
		return sols.get(index);
	}
	
	public int size() {
		return sols.size();
	}
	
}
