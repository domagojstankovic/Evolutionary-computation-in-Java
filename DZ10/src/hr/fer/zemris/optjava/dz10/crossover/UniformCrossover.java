package hr.fer.zemris.optjava.dz10.crossover;

import java.util.Random;

import hr.fer.zemris.optjava.dz10.model.Solution;

public class UniformCrossover implements ICrossover {

	private Random rand = new Random();
	
	@Override
	public Solution cross(Solution parent1, Solution parent2) {
		int n = parent1.size();
		double[] sol = new double[n];
		for (int i = 0; i < n; i++) {
			sol[i] = rand.nextBoolean() ? parent1.getXAt(i) : parent2.getXAt(i);
		}
		return new Solution(sol, parent1.fitSize());
	}

}
