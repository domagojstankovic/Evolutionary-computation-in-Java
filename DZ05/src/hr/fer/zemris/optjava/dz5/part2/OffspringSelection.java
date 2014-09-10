package hr.fer.zemris.optjava.dz5.part2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

public class OffspringSelection implements Callable<Population> {

	private ICompFactor compFactor;
	private IFunction function;
	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;
	private double maxSelPress;
	private double succRatio;
	private int maxIter;
	private Population population;
	private double actSelPress = 1.0;
	
	private Random rand = new Random();
	
	public OffspringSelection(ICompFactor compFactor, IFunction function, ISelection selection, ICrossover crossover,
			IMutation mutation, double maxSelPress, double succRatio, int maxIter, Population population) {
		super();
		this.compFactor = compFactor;
		this.function = function;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.maxSelPress = maxSelPress;
		this.succRatio = succRatio;
		this.maxIter = maxIter;
		this.population = population;
	}

	@Override
	public Population call() throws Exception {
		for (int i = 0; i < maxIter && actSelPress < maxSelPress; i++) {
			int n = population.size();
			double compFactor = this.compFactor.getCompFactor();
			Set<Permutation> pop = new HashSet<>();
			Set<Permutation> pool = new HashSet<>();
			while ((pop.size() < n * succRatio) && (pop.size() + pool.size() < n * maxSelPress)) {
				Permutation parent1 = selection.select(population);
				Permutation parent2 = selection.select(population);
				PermutationPair children = crossover.cross(parent1, parent2);
				Permutation child1 = mutation.mutate(children.getPermutation1());
				child1.setFitness(-function.getValue(child1));
				Permutation child2 = mutation.mutate(children.getPermutation2());
				child2.setFitness(-function.getValue(child2));
				Permutation child = child1.compareTo(child2) >= 0 ? child1 : child2;
				double fit1 = parent1.getFitness();
				double fit2 = parent2.getFitness();
				if (fit2 < fit1) {
					double temp = fit2;
					fit2 = fit1;
					fit1 = temp;
				}
				if (child.getFitness() > fit1 + (fit2 - fit1) * compFactor) {
					pop.add(child);
				} else {
					pool.add(child);
				}
			}
			actSelPress = (double) (pop.size() + pool.size()) / n;
			Permutation[] perms = new Permutation[n];
			int index = 1;
			perms[0] = population.getBest();
			for (Permutation p : pop) {
				if (index >= n) {
					break;
				}
				perms[index] = p;
				index++;
			}
			for (Permutation p : pool) {
				if (index >= n) {
					break;
				}
				perms[index] = p;
				index++;
			}
			int length = pool.size();
			while (index < n) {
				int num = rand.nextInt(length);
				for (Permutation p : pool) {
					if (num <= 0) {
						perms[index] = p;
					}
					num--;
				}
				index++;
			}
			population = new Population(perms);
		}
		return population;
	}
	
	
	
}
