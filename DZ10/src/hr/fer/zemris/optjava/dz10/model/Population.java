package hr.fer.zemris.optjava.dz10.model;

import java.util.ArrayList;
import java.util.List;

public class Population {

	private List<Solution> sols;

	public Population(List<Solution> sols) {
		super();
		this.sols = sols;
	}
	
	public int size() {
		return sols.size();
	}
	
	public Population() {
		this.sols = new ArrayList<>();
	}
	
	public List<Solution> getSols() {
		return sols;
	}
	
	public Solution getSolAt(int index) {
		return sols.get(index);
	}
	
}
