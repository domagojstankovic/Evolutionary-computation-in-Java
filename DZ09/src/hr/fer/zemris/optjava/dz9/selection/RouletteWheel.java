package hr.fer.zemris.optjava.dz9.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz9.model.Population;
import hr.fer.zemris.optjava.dz9.model.Solution;

public class RouletteWheel implements ISelection {

	private Random rand = new Random();
	
	@Override
	public Solution[] select(Population population, int n) {
		List<Solution> sols = population.getSols();
		double sum = 0.0;
		for (Solution sol : sols) {
			sum += sol.getFitness();
		}
		int size = population.size();
		Solution[] array = new Solution[n];
		for (int i = 0; i < n; i++) {
			double num = rand.nextDouble();
			double val = 0;
			for (int j = 0; j < size; j++) {
				val += sols.get(j).getFitness() / sum;
				if (num <= val ) {
					array[i] = sols.get(j);
					break;
				}
			}
		}
		return array;
	}

}
