package hr.fer.zemris.generic.ga.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.generic.ga.Population;
import hr.fer.zemris.generic.ga.crossover.ICrossover;
import hr.fer.zemris.generic.ga.jobs.CompleteJob;
import hr.fer.zemris.generic.ga.mutation.IMutation;
import hr.fer.zemris.generic.ga.selection.ISelection;
import hr.fer.zemris.optjava.rng.EVOThread;

public class GA2 implements IOptAlgorithm, Runnable {

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

	public GA2(int maxiter, Population population, double minFit, int popSize, int imgWidth, int imgHeight,
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

	private GASolution<int[]> algorithmRun() {
		GASolution<int[]> best = null;
		for (int iter = 0; iter < maxiter; iter++) {
			gBestChanged = false;

			population = makeChildren();

			best = Collections.min(population.getSols());
			if (globalBest == null || best.compareTo(globalBest) < 0) {
				globalBest = best;
				gBestChanged = true;
			}
			if (Math.abs(globalBest.fitness) < minFit) {
				return globalBest;
			}
			if (gBestChanged) {
//				System.out.println((iter + 1) + ": " + best.fitness + " -> " + best.toString());
				System.out.println((iter + 1) + ": " + best.fitness);
			}
		}
		best = Collections.min(population.getSols());
		if (best.compareTo(globalBest) < 0) {
			globalBest = best;
		}
		return globalBest;
	}

	private Population makeChildren() {
		int procNum = Runtime.getRuntime().availableProcessors();
		Queue<GASolution<int[]>> q = new ConcurrentLinkedQueue<>();
		Thread[] threads = new Thread[procNum];
		int gNum = popSize / procNum;
		int mod = popSize % procNum;
		for (int i = 0; i < procNum; i++) {
			int childrenNum = gNum + (i < mod ? 1 : 0);
			threads[i] = new EVOThread(
					new CompleteJob(childrenNum, population, q, selection, crossover, mutation, evaluator), imgWidth,
					imgHeight);
		}
		for (int i = 0; i < procNum; i++) {
			threads[i].start();
		}
		for (int i = 0; i < procNum; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			;
		}
		List<GASolution<int[]>> sols = new ArrayList<>(q);
		return new Population(sols);
	}

	@Override
	public void run() {
		algorithmRun();
	}

	@Override
	public GASolution<int[]> runAlg() {
		return algorithmRun();
	}

}
