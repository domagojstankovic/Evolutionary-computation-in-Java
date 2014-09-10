package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SASEGASA implements IOptAlgorithm {

	private static final int N_TOURNAMENT = 2;
	private static final double CROSS_PERCENTAGE = 0.2;
	private static final double MAX_SEL_PRESS = 50;
	private static final double SUCC_RATIO = 0.4;
	private static final int MAX_ITER = 1000;
	
	private int unitNum;
	private int subPopNum;
	private Task task;
	
	private IFunction function;
	private ISelection selection = new TournamentSelection(N_TOURNAMENT);
	private ICrossover crossover = new OrderBasedCrossover(CROSS_PERCENTAGE);
	private IMutation mutation = new SwapMutation();
	
	
	public SASEGASA(int unitNum, int subPopNum, Task task) {
		super();
		this.unitNum = unitNum;
		this.subPopNum = subPopNum;
		this.task = task;
		function = new QuadraticAssignment(task);
	}

	@Override
	public Permutation run() {
		List<Permutation> units = new ArrayList<>(unitNum);
		int n = task.getN();
		for (int i = 0; i < unitNum; i++) {
			Permutation perm = new Permutation(n);
			perm.setFitness(-function.getValue(perm));
			units.add(perm);
		}
		while (subPopNum > 0) {
			Permutation best = Collections.max(units);
			System.out.println(best.toString() + " : " + (-best.getFitness()));
			Population[] populations = new Population[subPopNum];
			int offset = 0;
			for (int i = 0; i < subPopNum; i++) {
				int additional = i < unitNum % subPopNum ? 1 : 0;
				int num = unitNum / subPopNum + additional;
				Permutation[] perms = new Permutation[num];
				for (int j = 0; j < num; j++) {
					perms[j] = units.get(j + offset);
				}
				offset += num;
				populations[i] = new Population(perms);
			}
			List<OffspringSelection> tasks = new ArrayList<>(subPopNum);
			for (Population pop : populations) {
				tasks.add(new OffspringSelection(new LinearCompFactor(MAX_ITER), function, selection, crossover, mutation, MAX_SEL_PRESS, SUCC_RATIO, MAX_ITER, pop));
			}
			ExecutorService executor = Executors.newFixedThreadPool(subPopNum);
			try {
				List<Future<Population>> futures = executor.invokeAll(tasks);
				units.clear();
				for (Future<Population> fut : futures) {
					Population pop = fut.get();
					Permutation[] perms = pop.getPermutations(false);
					List<Permutation> list = Arrays.asList(perms);
					units.addAll(list);
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			executor.shutdown();
			subPopNum--;
		}
		return Collections.max(units);
	}

}
