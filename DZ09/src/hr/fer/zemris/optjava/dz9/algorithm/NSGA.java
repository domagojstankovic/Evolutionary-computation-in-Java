package hr.fer.zemris.optjava.dz9.algorithm;

import hr.fer.zemris.optjava.dz9.crossover.ICrossover;
import hr.fer.zemris.optjava.dz9.distance.IDistance;
import hr.fer.zemris.optjava.dz9.function.SH;
import hr.fer.zemris.optjava.dz9.model.MOUtils;
import hr.fer.zemris.optjava.dz9.model.Population;
import hr.fer.zemris.optjava.dz9.model.Solution;
import hr.fer.zemris.optjava.dz9.mutation.IMutation;
import hr.fer.zemris.optjava.dz9.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz9.selection.ISelection;

import java.util.ArrayList;
import java.util.List;

public class NSGA implements IOptAlgorithm {

	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;
	private MOOPProblem problem;
	private IDistance distance;
	private SH sh;
	private Population population;
	private int maxiter;
	private int popSize;
	private double mFactor;
	
	public NSGA(ISelection selection, ICrossover crossover, IMutation mutation, MOOPProblem problem,
			IDistance distance, SH sh, Population population, int maxiter, int popSize, double mFactor) {
		super();
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.problem = problem;
		this.distance = distance;
		this.sh = sh;
		this.population = population;
		this.maxiter = maxiter;
		this.popSize = popSize;
		this.mFactor = mFactor;
	}

	@Override
	public List<List<Solution>> run() {
		updateFitness(population);
		for (int iter = 0; iter < maxiter; iter++) {
			List<List<Solution>> fronts = MOUtils.nonDominatedSort(population.getSols());
			List<Solution> newSols = new ArrayList<>(popSize);
			double startFit = popSize;
			distance.changePopulation(population);
			for (List<Solution> front : fronts) {
				int size = front.size();
				double[][] matrix = new double[size][size];
				for (int i = 0; i < size; i++) {
					for (int j = i; j < size; j++) {
						double dist = distance.distance(front.get(i), front.get(j));
						matrix[j][i] = matrix[i][j] = sh.valueAt(dist);
					}
				}
				double m = 0;
				for (int i = 0; i < size; i++) {
					double nc = 0.0;
					for (int j = 0; j < size; j++) {
						nc += matrix[i][j];
					}
					double val = startFit / nc;
					if (i == 0) {
						m = val;
					} else if (val < m) {
						m = val;
					}
					front.get(i).setFitness(val);
				}
				startFit = m * mFactor;
			}
			Solution[] sols = selection.select(population, popSize * 2);
			for (int i = 0; i < popSize * 2; i += 2) {
				Solution child = crossover.cross(sols[i], sols[i + 1]);
				Solution mutant = mutation.mutate(child);
				updateFitness(mutant);
				newSols.add(mutant);
			}
			if (iter == maxiter - 1) {
				return fronts;
			}
			population = new Population(newSols);
		}
//		updateFitness(population);
//		return MOUtils.nonDominatedSort(population.getSols());
		return null;
	}
	
	private void updateFitness(Population pop) {
		int n = pop.size();
		for (int i = 0; i < n; i++) {
			updateFitness(pop.getSolAt(i));
		}
	}
	
	private void updateFitness(Solution sol) {
		problem.evaluateSolution(sol.getX(), sol.getFit());
	}

}
