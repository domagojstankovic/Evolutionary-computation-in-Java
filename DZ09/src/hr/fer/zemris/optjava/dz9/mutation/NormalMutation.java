package hr.fer.zemris.optjava.dz9.mutation;

import hr.fer.zemris.optjava.dz9.model.Solution;

import java.util.Random;

public class NormalMutation implements IMutation {

	private double s;
	
	public NormalMutation(double s) {
		this.s = s;
	}

	@Override
	public Solution mutate(Solution solution) {
		Random rand = new Random();
		double[] array = solution.getX().clone();
		int size = array.length;
		for (int i = 0; i < size; i++) {
			array[i] += rand.nextGaussian() * s;
		}
		return new Solution(array, solution.fitSize());
	}

}
