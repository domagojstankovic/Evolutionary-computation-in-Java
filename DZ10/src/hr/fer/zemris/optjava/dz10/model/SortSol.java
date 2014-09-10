package hr.fer.zemris.optjava.dz10.model;

public class SortSol implements Comparable<SortSol> {
	
	private int criteria;
	private Solution sol;
	
	public SortSol(int criteria, Solution sol) {
		super();
		this.criteria = criteria;
		this.sol = sol;
	}

	public int getCriteria() {
		return criteria;
	}
	
	public void setCriteria(int criteria) {
		this.criteria = criteria;
	}
	
	public Solution getSol() {
		return sol;
	}

	@Override
	public int compareTo(SortSol o) {
		double diff = sol.getFitAt(criteria) - o.getSol().getFitAt(o.criteria);
		if (diff < 0) {
			return -1;
		}
		if (diff > 0) {
			return 1;
		}
		return 0;
	}
	
}
