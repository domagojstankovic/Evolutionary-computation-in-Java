package hr.fer.zemris.generic.ga.jobs;

import java.util.Queue;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.generic.ga.Population;
import hr.fer.zemris.generic.ga.crossover.ICrossover;
import hr.fer.zemris.generic.ga.mutation.IMutation;
import hr.fer.zemris.generic.ga.selection.ISelection;

public class CompleteJob implements Runnable {

	private int childrenNum;
	private Population population;
	private Queue<GASolution<int[]>> q;
	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;
	private IGAEvaluator<int[]> evaluator;
	
	public CompleteJob(int childrenNum, Population population, Queue<GASolution<int[]>> q, ISelection selection,
			ICrossover crossover, IMutation mutation, IGAEvaluator<int[]> evaluator) {
		super();
		this.childrenNum = childrenNum;
		this.population = population;
		this.q = q;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.evaluator = evaluator;
	}

	@Override
	public void run() {
		for (int i = 0; i < childrenNum; i++) {
			GASolution<int[]> parent1 = selection.select(population);
			GASolution<int[]> parent2 = selection.select(population);
			GASolution<int[]> child = crossover.cross(parent1, parent2);
			child = mutation.mutate(child);
			evaluator.evaluate(child);
			q.add(child);
		}
	}
	
}
