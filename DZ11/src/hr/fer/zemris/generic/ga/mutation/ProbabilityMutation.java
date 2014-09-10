package hr.fer.zemris.generic.ga.mutation;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class ProbabilityMutation implements IMutation {

	private double probability;
	private int imgWidth;
	private int imgHeight;
	
	public ProbabilityMutation(double probability, int imgWidth, int imgHeight) {
		super();
		this.probability = probability;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}

	@Override
	public GASolution<int[]> mutate(GASolution<int[]> chr) {
		int[] data = chr.getData().clone();
		IRNG rand = RNG.getRNG();
		if (rand.nextDouble() <= probability) {
			data[0] = rand.nextInt(0, 256);
		}
		int size = data.length;
		int n = size / 5;
		for (int i = 0; i < n; i++) {
			if (rand.nextDouble() <= probability) data[i * 5 + 1] = rand.nextInt(0, imgWidth);
			if (rand.nextDouble() <= probability) data[i * 5 + 2] = rand.nextInt(0, imgHeight);
			if (rand.nextDouble() <= probability) data[i * 5 + 3] = rand.nextInt(1, imgWidth - data[i * 5 + 1] + 1);
			if (rand.nextDouble() <= probability) data[i * 5 + 4] = rand.nextInt(1, imgHeight - data[i * 5 + 2] + 1);
			if (rand.nextDouble() <= probability) data[i * 5 + 5] = rand.nextInt(0, 256);
		}
		return new Solution(data);
	}

}
