package hr.fer.zemris.optjava.dz9.crossover;

import hr.fer.zemris.optjava.dz9.model.Solution;

import java.util.Random;

public class BLXalpha implements ICrossover {

	private double alpha;
	
	public BLXalpha(double alpha) {
		super();
		this.alpha = alpha;
	}

	@Override
	public Solution cross(Solution parent1, Solution parent2) {
		double[] par1 = parent1.getX();
		double[] par2 = parent2.getX();
		int size = par1.length;
		double[] child = new double[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			double r1 = par1[i];
			double r2 = par2[i];
			double add = alpha * (r2 - r1);
			double l1 = r1 - add;
			double l2 = r2 + add;
			child[i] = rand.nextDouble() * (l2 - l1) + l1;
		}
		return new Solution(child, parent1.fitSize());
	}

}
