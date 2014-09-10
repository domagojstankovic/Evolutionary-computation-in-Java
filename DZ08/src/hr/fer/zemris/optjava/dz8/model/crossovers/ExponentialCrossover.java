package hr.fer.zemris.optjava.dz8.model.crossovers;

import java.util.Random;

import hr.fer.zemris.optjava.dz8.model.Solution;
import hr.fer.zemris.optjava.dz8.model.interfaces.ICrossover;

public class ExponentialCrossover implements ICrossover {

	private double cr;
	private Random rand = new Random();
	
	public ExponentialCrossover(double cr) {
		super();
		this.cr = cr;
	}

	@Override
	public Solution cross(Solution mutant, Solution target) {
		int n = mutant.size();
		int index = rand.nextInt(n);
		double[] test = new double[n];
		double[] m = mutant.getSol();
		double[] t = target.getSol();
		test[index] = m[index];
		boolean passed = false;
		for (int i = 1; i < n; i++) {
			int ind = (i + index) % n;
			if (passed) {
				test[ind] = t[ind];
			} else {
				if (rand.nextDouble() <= cr) {
					test[ind] = m[ind];
				} else {
					test[ind] = t[ind];
					passed = true;
				}
			}
		}
		return new Solution(test);
	}

}
