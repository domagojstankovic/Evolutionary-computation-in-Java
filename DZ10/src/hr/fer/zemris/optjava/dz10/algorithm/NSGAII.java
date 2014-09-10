package hr.fer.zemris.optjava.dz10.algorithm;

import hr.fer.zemris.optjava.dz10.crossover.ICrossover;
import hr.fer.zemris.optjava.dz10.model.MOUtils;
import hr.fer.zemris.optjava.dz10.model.Population;
import hr.fer.zemris.optjava.dz10.model.Solution;
import hr.fer.zemris.optjava.dz10.mutation.IMutation;
import hr.fer.zemris.optjava.dz10.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz10.selection.ISelection;

import java.util.ArrayList;
import java.util.List;

public class NSGAII implements IOptAlgorithm {

	private int popSize;
	private int childPopSize;
	private int maxiter;
	private Population population;
	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;
	private MOOPProblem problem;
	
	public NSGAII(int popSize, int childPopSize, int maxiter, Population population, ISelection selection,
			ICrossover crossover, IMutation mutation, MOOPProblem problem) {
		super();
		this.popSize = popSize;
		this.childPopSize = childPopSize;
		this.maxiter = maxiter;
		this.population = population;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.problem = problem;
	}

	@Override
	public List<List<Solution>> run() {
		for (int i = 0; i < popSize; i++) {
			Solution sol = population.getSolAt(i);
			problem.evaluateSolution(sol .getX(), sol.getFit());
		}
		MOUtils.nonDominatedSort(population.getSols());
		MOUtils.updateDistance(population.getSols());
		for (int iter = 0; iter < maxiter; iter++) {
			List<Solution> sols = population.getSols();
			MOUtils.nonDominatedSort(sols);
			Solution[] children = selection.select(population, childPopSize * 2);
			Solution[] mutants = new Solution[childPopSize];
			for (int i = 0; i < childPopSize * 2; i += 2) {
				Solution mut = crossover.cross(children[i], children[i + 1]);
				mut = mutation.mutate(mut);
				problem.evaluateSolution(mut.getX(), mut.getFit());
				mutants[i / 2] = mut;
			}
			List<Solution> rt = new ArrayList<>(popSize + childPopSize);
			for (int i = 0; i < popSize; i++) {
				rt.add(sols.get(i));
			}
			for (int i = 0; i < childPopSize; i++) {
				rt.add(mutants[i]);
			}
			MOUtils.updateDistance(rt);
			MOUtils.crowdingSort(rt);
			List<Solution> newPop = new ArrayList<>(popSize);
			for (int i = 0; i < popSize; i++) {
				newPop.add(rt.get(childPopSize + popSize - i - 1));
			}
			population = new Population(newPop);
		}
		return MOUtils.nonDominatedSort(population.getSols());
	}

}
