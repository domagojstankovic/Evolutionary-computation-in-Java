package hr.fer.zemris.optjava.dz8.model;

import java.util.ArrayList;
import java.util.List;

public class Population {
	
	private List<Solution> sols;

	public Population(List<Solution> sols) {
		super();
		this.sols = sols;
	}
	
	public Population() {
		sols = new ArrayList<>();
	}
	
	public List<Solution> getSols() {
		return sols;
	}
	
	public Solution getSolAt(int index) {
		return sols.get(index);
	}
	
	public int size() {
		return sols.size();
	}
	
	public void add(Solution sol) {
		sols.add(sol);
	}
	
}
