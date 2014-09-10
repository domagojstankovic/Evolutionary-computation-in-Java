package hr.fer.zemris.optjava.dz5.part1;

public class RandomSelection implements ISelection {

	@Override
	public Genotype select(Population population) {
		return population.getRandomGenotype();
	}

}
