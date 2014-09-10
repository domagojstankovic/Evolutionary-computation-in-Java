package hr.fer.zemris.generic.ga.mutation;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class OneBreakpointMutation implements IMutation {

	@Override
	public GASolution<int[]> mutate(GASolution<int[]> chr) {
		int[] data = chr.getData();
		int size = data.length;
		int[] child = new int[size];
		IRNG rand = RNG.getRNG();
		int index = rand.nextInt(0, size - 1);
		for (int i = 0; i <= index; i++) {
			child[i + size - index - 1] = data[i];
		}
		for (int i = index + 1; i < size; i++) {
			child[i - index - 1] = data[i];
		}
		return new Solution(child);
	}

}
