package hr.fer.zemris.optjava.dz13.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz13.cross.ICrossover;
import hr.fer.zemris.optjava.dz13.model.Population;
import hr.fer.zemris.optjava.dz13.model.Solution;
import hr.fer.zemris.optjava.dz13.model.eval.IEvaluator;
import hr.fer.zemris.optjava.dz13.model.sel.ISelection;
import hr.fer.zemris.optjava.dz13.mut.IMutation;

public class GP implements IOptAlgorithm {

	private int maxiter;
	private int popSize;
	private int minFit;
	private Population population;
	private Solution best = null;
	private IEvaluator evaluator;
	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;
	private double plagiaryPunishment;
	
	private Random rand = new Random();
	
	public GP(int maxiter, int popSize, int minFit, Population population, IEvaluator evaluator,
			ISelection selection, ICrossover crossover, IMutation mutation, double plagiaryPunishment) {
		super();
		this.maxiter = maxiter;
		this.popSize = popSize;
		this.minFit = minFit;
		this.population = population;
		this.evaluator = evaluator;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.plagiaryPunishment = plagiaryPunishment;
	}

	@Override
	public Solution runAlgorithm() {
		for (int iter = 0; iter < maxiter; iter++) {
			best = determineBest(population);
			System.out.println((iter + 1) + ": " + best.getFitness());
			if (best.getFitness() >= minFit) {
				return best;
			}
			List<Solution> newSols = new ArrayList<>(popSize);
			newSols.add(best);
			for (int i = 1; i < popSize; i++) {
				double perc = rand.nextDouble();
				if (perc <= 0.01) {
					// reprodukcija
					Solution sol = selection.select(population.getSols());
					sol.punish(plagiaryPunishment);
					newSols.add(sol);
				} else if (perc <= 0.15) {
					// mutacija
					Solution sol = selection.select(population.getSols()).duplicate();
					int oldFit = sol.getFitness();
					sol = mutation.mutate(sol);
					int newFit = evaluator.evaluate(sol);
					if (oldFit == newFit) {
						sol.punish(plagiaryPunishment);
					}
					newSols.add(sol);
				} else {
					// križanje
					Solution parent1 = selection.select(population.getSols());
					Solution parent2 = selection.select(population.getSols());
					Solution[] solArray = crossover.cross(parent1, parent2);
					Solution child1 = solArray[0];
					evaluator.evaluate(child1);
					if (parent1.getFitness() == child1.getFitness()) {
						child1.punish(plagiaryPunishment);
					}
					Solution child2 = solArray[1];
					evaluator.evaluate(child2);
					if (parent2.getFitness() == child2.getFitness()) {
						child2.punish(plagiaryPunishment);
					}
					newSols.add(child1);
					newSols.add(child2);
				}
			}
			population = new Population(newSols);
		}
		return best;
	}
	
	private static Solution determineBest(Population pop) {
		return Collections.max(pop.getSols());
	}

}
