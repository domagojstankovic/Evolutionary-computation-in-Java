package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution> {

	private double[] deltas;
	private Random rand = new Random();
	
	public DoubleArrayUnifNeighborhood(double[] deltas) {
		super();
		this.deltas = deltas;
	}

	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution current) {
		DoubleArraySolution sol = current.duplicate();
		int size = deltas.length;
		double[] mins = new double[size];
		double[] maxs = new double[size];
		for (int i = 0; i < size; i++) {
			double value = current.values[i];
			mins[i] = value - deltas[i];
			maxs[i] = value + deltas[i];
		}
		sol.randomize(rand, mins, maxs);
		return sol;
	}

}
