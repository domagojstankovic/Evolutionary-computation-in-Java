package hr.fer.zemris.optjava.dz8;

import java.util.Collections;
import hr.fer.zemris.optjava.dz8.model.Population;
import hr.fer.zemris.optjava.dz8.model.Solution;
import hr.fer.zemris.optjava.dz8.model.Strategy;
import hr.fer.zemris.optjava.dz8.model.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz8.model.interfaces.IFunction;
import hr.fer.zemris.optjava.dz8.model.interfaces.IOptAlgorithm;

public class DE implements IOptAlgorithm {

	private IFunction function;
	private int n;
	private double merr;
	private int maxiter;
	private boolean max;
	private Population pop;
	private Solution best;
	private Strategy strategy;
	private ICrossover crossover;
	private Solution lowerBound;
	private Solution upperBound;
	
	public DE(IFunction function, int n, double merr, int maxiter, boolean max, Population pop, Strategy strategy,
			ICrossover crossover, Solution lowerBound, Solution upperBound) {
		super();
		this.function = function;
		this.n = n;
		this.merr = merr;
		this.maxiter = maxiter;
		this.max = max;
		this.pop = pop;
		this.strategy = strategy;
		this.crossover = crossover;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	@Override
	public Solution run() {
		updateFitness();
		double lastFit = 0;
		for (int i = 0; i < maxiter; i++) {
			determineBest();
			if (Math.abs(best.getFitness()) < merr) {
				return best;
			}
			double currFit = Math.abs(best.getFitness());
			if (currFit != lastFit) {
				System.out.println((i + 1) + ": " + currFit);
				lastFit = currFit;
			}
			Population newPop = new Population();
			for (int j = 0; j < n; j++) {
				Solution target = pop.getSolAt(j);
				Solution mutant = updateFitness(strategy.mutate(pop, target));
				Solution trial = crossover.cross(mutant, target);
				checkBounds(trial);
				updateFitness(trial);
				boolean trialGreater = trial.getFitness() >= target.getFitness();
				if (trialGreater) {
					newPop.add(trial);
				} else {
					newPop.add(target);
				}
			}
			pop = newPop;
		}
		updateFitness();
		determineBest();
		return best;
	}
	
	private void checkBounds(Solution mutant) {
		double[] sol = mutant.getSol();
		double[] low = lowerBound.getSol();
		double[] upp = upperBound.getSol();
		int n = sol.length;
		for (int i = 0; i < n; i++) {
			if (sol[i] < low[i]) {
				sol[i] = low[i];
			} else if (sol[i] > upp[i]) {
				sol[i] = upp[i];
			}
		}
	}

	private void determineBest() {
//		Solution iterbest = Collections.max(pop.getSols());
//		if (best == null || iterbest.getFitness() >= best.getFitness()) {
//			best = iterbest;
//		}
		best = Collections.max(pop.getSols());
	}
	
	private Solution updateFitness(Solution sol) {
		double value = function.valueAt(sol.getSol());
		value = max ? value : -value;
		sol.setFitness(value);
		return sol;
	}
	
	private void updateFitness() {
		int size = pop.size();
		for (int i = 0; i < size; i++) {
			Solution sol = pop.getSolAt(i);
			double val = function.valueAt(sol.getSol());
			sol.setFitness(max ? val : -val);
		}
	}

}
