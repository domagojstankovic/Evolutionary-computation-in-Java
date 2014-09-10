package hr.fer.zemris.optjava.dz5.part1;

import java.util.HashSet;
import java.util.Set;

public class RAPGA implements IOptAlgorithm {

	private IFunction function;
	private ISelection selection1;
	private ISelection selection2;
	private ICrossover crossover;
	private IMutation mutation;
	private Population population;
	private int maxIter;
	private int minPopSize;
	private int maxPopSize;
	private int maxEffort;
	private ICompFactor compFactor;
	
	public RAPGA(IFunction function, ISelection selection1, ISelection selection2, ICrossover crossover,
			IMutation mutation, Population population, int maxIter, int minPopSize, int maxPopSize, int maxEffort,
			ICompFactor compFactor) {
		super();
		this.function = function;
		this.selection1 = selection1;
		this.selection2 = selection2;
		this.crossover = crossover;
		this.mutation = mutation;
		this.population = population;
		this.maxIter = maxIter;
		this.minPopSize = minPopSize;
		this.maxPopSize = maxPopSize;
		this.maxEffort = maxEffort;
		this.compFactor = compFactor;
	}

	@Override
	public Genotype run() {
		population.updateFitness(function);
		for (int i = 0; i < maxIter; i++) {
			Genotype best = population.getBest();
			System.out.println((i + 1) + ": " + best.getOnesNum() + ": " + best.getFitness());
			Set<Genotype> pop = new HashSet<>();
			double compFactor = this.compFactor.getCompFactor();
			int total = 0;
			
			try {
				while (continueCondition(pop.size(), total)) {
					total++;
					Genotype parent1 = selection1.select(population);
					Genotype parent2 = selection2.select(population);
					Genotype child = crossover.cross(parent1, parent2);
					child = mutation.mutate(child);
					double fit = function.getValue(child);
					child.setFitness(fit);
					double fit1 = parent1.getFitness();
					double fit2 = parent2.getFitness();
					if (fit2 < fit1) {
						double temp = fit2;
						fit2 = fit1;
						fit1 = temp;
					}
					if (fit > fit1 + compFactor * (fit2 - fit1)) {
						pop.add(child);
					}
				}
			} catch (MaxEffortException e) {
				pop.add(best);
				return new Population(pop).getBest();
			}
			pop.add(best);
			population = new Population(pop);
		}
		return population.getBest();
	}
	
	private boolean continueCondition(int popSize, int total) throws MaxEffortException {
		if (popSize >= maxPopSize) {
			return false;
		}
		if (total < maxEffort) {
			return true;
		}
		if (popSize < minPopSize) {
			throw new MaxEffortException();
		}
		return false;
	}

}
