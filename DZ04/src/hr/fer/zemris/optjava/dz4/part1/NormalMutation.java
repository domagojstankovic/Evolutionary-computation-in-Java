package hr.fer.zemris.optjava.dz4.part1;

import java.util.Random;

public class NormalMutation implements IMutation {

	private double s;
	
	public NormalMutation(double s) {
		this.s = s;
	}

	@Override
	public Solution mutate(Solution solution) {
		Random rand = new Random();
		double[] array = solution.getSolution(true);
		int size = array.length;
		for (int i = 0; i < size; i++) {
			array[i] += rand.nextGaussian() * s;
		}
		return new Solution(array, false);
	}

}
