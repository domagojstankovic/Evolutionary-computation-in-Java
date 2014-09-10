package hr.fer.zemris.generic.ga.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.generic.ga.Population;
import hr.fer.zemris.generic.ga.Solution;
import hr.fer.zemris.generic.ga.crossover.ICrossover;
import hr.fer.zemris.generic.ga.jobs.EvaluateJob;
import hr.fer.zemris.generic.ga.mutation.IMutation;
import hr.fer.zemris.generic.ga.selection.ISelection;
import hr.fer.zemris.optjava.rng.EVOThread;

public class PGA1 implements IOptAlgorithm, Runnable {

	private static final GASolution<int[]> POISON_PILL = new Solution(new int[0]);
	
	private int maxiter;
	private Population population;
	private double minFit;
	private int popSize;
	private int imgWidth;
	private int imgHeight;
	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;
	private IGAEvaluator<int[]> evaluator;
	private GASolution<int[]> globalBest = null;
	private boolean gBestChanged;
	
	private Queue<GASolution<int[]>> r = new ConcurrentLinkedQueue<>();
	private Queue<GASolution<int[]>> q = new ConcurrentLinkedQueue<>();
	
	public PGA1(int maxiter, Population population, double minFit, int popSize, int imgWidth, int imgHeight,
			ISelection selection, ICrossover crossover, IMutation mutation, IGAEvaluator<int[]> evaluator) {
		super();
		this.maxiter = maxiter;
		this.population = population;
		this.minFit = minFit;
		this.popSize = popSize;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.evaluator = evaluator;
	}
	
	@Override
	public GASolution<int[]> runAlg() {
		Thread thread = new AlgRunThread(this, imgWidth, imgHeight);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return globalBest;
	}

	private GASolution<int[]> algorithmRun() {
		int procNum = Runtime.getRuntime().availableProcessors();
		Thread[] threads = new Thread[procNum];
		for (int i = 0; i < procNum; i++) {
			threads[i] = new EVOThread(new EvaluateJob(r, q, evaluator, POISON_PILL), imgWidth, imgHeight);
		}
		for (int i = 0; i < procNum; i++) {
			threads[i].start();
		}
		algorithm();
		for (int i = 0; i < procNum; i++) {
			r.add(POISON_PILL);
		}
		for (int i = 0; i < procNum; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
		}
		return globalBest;
	}
	
	private GASolution<int[]> algorithm() {
		GASolution<int[]> best = null;
		for (int iter = 0; iter < maxiter; iter++) {
			gBestChanged = false;
			evaluate();
			best = Collections.min(population.getSols());
			if (globalBest == null || best.compareTo(globalBest) < 0) {
				globalBest = best;
				gBestChanged = true;
			}
			if (Math.abs(globalBest.fitness) < minFit) {
				return globalBest;
			}
			if (gBestChanged) {
				System.out.println((iter + 1) + ": " + best.fitness);
			}
			
			List<GASolution<int[]>> newPop = new ArrayList<>(popSize);
			for (int i = 0; i < popSize; i++) {
				GASolution<int[]> parent1 = selection.select(population);
				GASolution<int[]> parent2 = selection.select(population);
				GASolution<int[]> child = crossover.cross(parent1, parent2);
				child = mutation.mutate(child);
				newPop.add(child);
			}
		}
		evaluate();
		best = Collections.min(population.getSols());
		if (best.compareTo(globalBest) < 0) {
			globalBest = best;
		}
		return globalBest;
	}
	
	private void evaluate() {
		for (int i = 0; i < popSize; i++) {
			r.add(population.getSolAt(i));
		}
		while (q.size() < popSize);
		q.clear();
	}

	@Override
	public void run() {
		algorithmRun();
	}

}
