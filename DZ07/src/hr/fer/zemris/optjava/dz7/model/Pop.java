package hr.fer.zemris.optjava.dz7.model;

import java.util.List;

public class Pop {
	
	private List<Solution> sols;

	public Pop(List<Solution> sols) {
		super();
		this.sols = sols;
	}
	
	public List<Solution> getSols() {
		return sols;
	}
	
	public int size() {
		return sols.size();
	}
	
	public Solution getSolAt(int index) {
		return sols.get(index);
	}
	
}
