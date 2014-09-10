package hr.fer.zemris.generic.ga.crossover;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class UniformCrossover implements ICrossover {

	@Override
	public GASolution<int[]> cross(GASolution<int[]> par1, GASolution<int[]> par2) {
		int[] data1 = par1.getData();
		int[] data2 = par2.getData();
		int[] child = new int[data1.length];
		int size = (data1.length - 1) / 5;
		IRNG rand = RNG.getRNG();
		child[0] = rand.nextBoolean() ? data1[0] : data2[0];
		for (int i = 0; i < size; i++) {
			if (rand.nextBoolean()) {
				copy(i * 5 + 1, child, data1);
			} else {
				copy(i * 5 + 1, child, data2);
			}
		}
		return new Solution(child);
	}
	
	private void copy(int index, int[] dest, int[] src) {
		for (int i = 0; i < 5; i++) {
			dest[i + index] = src[i + index];
		}
	}

}
