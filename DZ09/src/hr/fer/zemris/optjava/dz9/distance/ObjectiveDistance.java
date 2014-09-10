package hr.fer.zemris.optjava.dz9.distance;

import java.util.List;

import hr.fer.zemris.optjava.dz9.model.Population;
import hr.fer.zemris.optjava.dz9.model.Solution;

public class ObjectiveDistance implements IDistance {

	private int n;
	private double[] max;
	private double[] min;
	
	public ObjectiveDistance(int n) {
		super();
		this.n = n;
		max = new double[n];
		min = new double[n];
	}

	@Override
	public double distance(Solution a, Solution b) {
		int n = a.size();
		double sum = 0.0;
		for (int i = 0; i < n; i++) {
			double value = (a.getFitAt(i) - b.getFitAt(i)) / (max[i] - min[i]);
			sum += value * value;
		}
		return Math.sqrt(sum);
	}

	@Override
	public void changePopulation(Population population) {
		List<Solution> sols = population.getSols();
		Solution sol = sols.get(0);
		for (int i = 0; i < n; i++) {
			max[i] = min[i] = sol.getFitAt(i);
		}
		int size = sols.size();
		for (int i = 1; i < size; i++) {
			for (int j = 0; j < n; j++) {
				double val = sols.get(i).getFitAt(j);
				if (val > max[j]) {
					max[j] = val;
				}
				if (val < min[j]) {
					min[j] = val;
				}
			}
		}
	}

}
