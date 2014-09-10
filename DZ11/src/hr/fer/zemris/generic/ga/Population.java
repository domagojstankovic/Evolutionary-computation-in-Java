package hr.fer.zemris.generic.ga;

import java.util.List;

public class Population {
	
	private List<GASolution<int[]>> sols;

	public Population(List<GASolution<int[]>> sols) {
		super();
		this.sols = sols;
	}
	
	public List<GASolution<int[]>> getSols() {
		return sols;
	}
	
	public GASolution<int[]> getSolAt(int index) {
		return sols.get(index);
	}
	
	public int size() {
		return sols.size();
	}
	
}
