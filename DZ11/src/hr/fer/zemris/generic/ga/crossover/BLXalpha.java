package hr.fer.zemris.generic.ga.crossover;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class BLXalpha implements ICrossover {

	private double alpha;
//	private int imgWidth;
//	private int imgHeight;

	public BLXalpha(double alpha) {
		super();
		this.alpha = alpha;
	}
	// Ne radi!!!
	@Override
	public GASolution<int[]> cross(GASolution<int[]> par1, GASolution<int[]> par2) {
		IRNG rand = RNG.getRNG();
		int[] data1 = par1.getData();
		int[] data2 = par2.getData();
		int size = data1.length;
		int[] child = new int[size];
		int n = size / 5;
		for (int i = 0; i < n ; i++) {
			int r1 = data1[i];
			int r2 = data2[i];
			if (r1 > r2) {
				int temp = r1;
				r1 = r2;
				r2 = temp;
			}
			double add = alpha * (r2 - r1);
			int l1 = (int) (r1 - add);
			int l2 = (int) (r2 + add);
			child[i] = rand.nextInt(0, l2 - l1 + 1) + l1;
		}
		return new Solution(child);
	}

}
