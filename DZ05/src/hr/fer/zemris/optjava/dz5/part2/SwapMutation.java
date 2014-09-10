package hr.fer.zemris.optjava.dz5.part2;

import java.util.Random;

public class SwapMutation implements IMutation {

	private Random rand = new Random();
	
	public SwapMutation() {
	}
	
	@Override
	public Permutation mutate(Permutation permutation) {
		int n = permutation.size();
		int i1 = rand.nextInt(n);
		int i2 = i1;
		while (i1 == i2) {
			i2 = rand.nextInt(n);
		}
		permutation.swap(i1, i2);
		return permutation;
	}

}
