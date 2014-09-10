package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class DoubleArraySolution extends SingleObjectiveSolution {
	
	public double[] values;
	private static final int MIN = -5;
	private static final int MAX = 5;
	
	public DoubleArraySolution(int n) {
		values = new double[n];
		randomize(new Random(), MIN, MAX);
	}
	
	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(values.length);
	}
	
	public DoubleArraySolution duplicate() {
		int n = values.length;
		DoubleArraySolution sol = new DoubleArraySolution(n);
		for (int i = 0; i < n; i++) {
			sol.values[i] = values[i];
		}
		return sol;
	}
	
	public void randomize(Random rand, double[] mins, double[] maxs) {
		int size = values.length;
		for (int i = 0; i < size; i++) {
			values[i] = rand.nextDouble() * (maxs[i] - mins[i]) + mins[i];
		}
	}
	
	public void randomize(Random rand, double min, double max) {
		int size = values.length;
		for (int i = 0; i < size; i++) {
			values[i] = rand.nextDouble() * (max - min) + min;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		int size = values.length;
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(values[i]);
		}
		sb.append(")");
		return sb.toString();
	}
	
}
