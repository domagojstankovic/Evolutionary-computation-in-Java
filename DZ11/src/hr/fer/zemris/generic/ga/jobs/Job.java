package hr.fer.zemris.generic.ga.jobs;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.generic.ga.alg.GA1;

import java.util.Queue;

public class Job implements Runnable {

	private IGAEvaluator<int[]> evaluator;
	private Queue<GASolution<int[]>> r;
	private Queue<GASolution<int[]>> q;
	
	public Job(IGAEvaluator<int[]> evaluator, Queue<GASolution<int[]>> r, Queue<GASolution<int[]>> q) {
		super();
		this.evaluator = evaluator;
		this.r = r;
		this.q = q;
	}

	@Override
	public void run() {
		while (true) {
			GASolution<int[]> sol = r.poll();
			if (sol != null) {
				if (sol == GA1.POISON_PILL) {
					break;
				}
				evaluator.evaluate(sol);
				q.add(sol);
			}
		}
	}

}
