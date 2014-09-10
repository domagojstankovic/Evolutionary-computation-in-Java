package hr.fer.zemris.optjava.dz5.part1;

import java.util.Random;

public class OneBreakpointCrossover implements ICrossover {

	private Random rand = new Random();
	
	public OneBreakpointCrossover() {
	}
	
	@Override
	public Genotype cross(Genotype parent1, Genotype parent2) {
		int n = parent1.size();
		int breakpoint = rand.nextInt(n - 1);
		byte[] array = new byte[n];
		for (int i = 0; i < n; i++) {
			if (i <= breakpoint) {
				array[i] = parent1.getElementAt(i);
			} else {
				array[i] = parent2.getElementAt(i);
			}
		}
		return new Genotype(array, false);
	}

}
