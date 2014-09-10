package hr.fer.zemris.optjava.dz7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.model.Pop;
import hr.fer.zemris.optjava.dz7.model.Solution;
import hr.fer.zemris.optjava.dz7.model.interfaces.IFunction;
import hr.fer.zemris.optjava.dz7.model.interfaces.ISelection;

public class ClonAlg {
	
	private static final double INIT_MIN = -1;
	private static final double INIT_MAX = 1;
	private static final double RO = 0.8;
	private static final double KONST = 0.15;
	private static final int KONST2 = 10;
	
	private IFunction function;
	private int n;
	private int d;
	private int beta;
	private boolean max;
	private double merr;
	private int maxiter;
	private Pop population;
	private ISelection selection;
	private int psize;
	private Solution best;

	public ClonAlg(IFunction function, int n, int d, int beta, boolean max, double merr, int maxiter,
			Pop population, ISelection selection, int psize) {
		super();
		this.function = function;
		this.n = n;
		this.d = d;
		this.beta = beta;
		this.max = max;
		this.merr = merr;
		this.maxiter = maxiter;
		this.population = population;
		this.selection = selection;
		this.psize = psize;
	}

	private Solution determineBest(Pop population) {
		return Collections.max(population.getSols());
	}
	
	public Solution run() {
		for (int i = 0; i < maxiter; i++) {
			updateFitness(population.getSols());
			best = determineBest(population);
			System.out.println(i + ": " + Math.abs(best.getFitness()));
			if (Math.abs(best.getFitness()) < merr) {
				return best;
			}
			List<Solution> pt = new ArrayList<>(n);
			for (int j = 0; j < n; j++) {
				pt.add(selection.select(population, true));
			}
			List<Solution> pclo = clonePop();
			List<Solution> phyp = hypermutation(pclo);
			updateFitness(phyp);
			population = new Pop(phyp);
			best = determineBest(population);
			if (Math.abs(best.getFitness()) < merr) {
				return best;
			}
			pt = new ArrayList<>(d);
			for (int j = 0; j < d; j++) {
				pt.add(selection.select(population, false));
			}
			Pop pbirth = initPop(d, psize);
			for (int j = 0; j < d; j++) {
				Solution sol = pt.get(j);
				Solution repl = pbirth.getSolAt(j);
				List<Solution> list = population.getSols();
				int index = list.indexOf(sol);
				list.set(index, repl);
			}
		}
		updateFitness(population.getSols());
		return determineBest(population);
		
	}
	
	private void updateFitness(List<Solution> sols) {
		int size = sols.size();
		for (int i = 0; i < size; i++) {
			Solution sol = sols.get(i);
			double fitness = function.valueAt(sol.getWeights());
			if (!max) {
				fitness = -fitness;
			}
			sol.setFitness(fitness);
		}
	}
	
	private List<Solution> hypermutation(List<Solution> pclo) {
		double min;
		double max;
		Random rand = new Random();
		int size = pclo.size();
		for (int i = 0; i < size; i++) {
			double fit = pclo.get(i).getFitness();
			min = 1 - fit * KONST;
			max = -min;
			int f = ((int) Math.exp(RO * fit * KONST2)) % size;
			for (int j = 0; j < f; j++) {
				int index = rand.nextInt(size);
				double val = pclo.get(i).getWeightAt(index);
				pclo.get(i).setValueAt(index, val + (rand.nextDouble() * (max - min) + min));
			}
		}
		return pclo;
	}

	private List<Solution> clonePop() {
		List<Solution> pop = population.getSols();
		Collections.sort(pop);
		Collections.reverse(pop);
		List<Solution> pclo = new ArrayList<>();
		int size = pop.size();
		for (int i = 0; i < size; i++) {
			int dup = (beta * n) / (i + 1);
			for (int j = 0; j < dup; j++) {
				pclo.add(pop.get(i));
			}
		}
		return pclo;
	}
	
	public static Pop initPop(int size, int psize) {
		Random rand = new Random();
		List<Solution> sols = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			double[] weights = new double[psize];
			for (int j = 0; j < psize; j++) {
				weights[j] = rand.nextDouble() * (INIT_MAX - INIT_MIN) + INIT_MIN;
			}
			sols.add(new Solution(weights));
		}
		return new Pop(sols);
	}
	
}
