package hr.fer.zemris.optjava.dz4.part1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RouletteWheel implements ISelection {

	public RouletteWheel() {
	}
	
	@Override
	public Solution select(Population population) {
		Solution[] solutions = population.getSolutions(true);
		List<Solution> list = Arrays.asList(solutions);
		Collections.sort(list);
		double worstFit = list.get(0).getFitness();
		double sum = 0.0;
		for (Solution sol : list) {
			double diff = worstFit - sol.getFitness();
			sum += diff;
			sol.setRelativeFitness(diff);
		}
		Random rand = new Random();
		double num = rand.nextDouble() * sum;
		double temp = 0.0;
		for (Solution sol : list) {
			temp += sol.getRelativeFitness();
			if (num <= temp) {
				return sol;
			}
		}
		return list.get(list.size() - 1);
	}

}
