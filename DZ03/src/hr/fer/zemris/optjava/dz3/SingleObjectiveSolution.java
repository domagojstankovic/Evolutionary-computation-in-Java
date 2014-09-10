package hr.fer.zemris.optjava.dz3;

public class SingleObjectiveSolution implements Comparable<SingleObjectiveSolution> {
	
	public double fitness;
	public double value;
	
	public SingleObjectiveSolution() {
	}

	@Override
	public int compareTo(SingleObjectiveSolution o) {
		double dif = o.fitness - fitness;
		if (dif < 0) {
			return -1;
		}
		if (dif > 0) {
			return 1;
		}
		return 0;
	}
	
	
}
