package hr.fer.zemris.optjava.dz5.part1;

import java.util.Random;

public class Mutation implements IMutation {

	private double perc;
	private Random rand = new Random();
	
	public Mutation(double perc) {
		super();
		this.perc = perc;
	}

	@Override
	public Genotype mutate(Genotype genotype) {
		byte[] array = genotype.getGenotype(true);
		int size = array.length;
		for (int i = 0; i < size; i++) {
			if (rand.nextDouble() < perc) {
				array[i] = (byte) (1 - array[i]);
			}
		}
		return new Genotype(array, false);
	}

}
