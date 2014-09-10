package hr.fer.zemris.generic.ga.jobs;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;

import java.util.Queue;

public class EvaluateJob implements Runnable {

	private Queue<GASolution<int[]>> r;
	private Queue<GASolution<int[]>> q;
	private IGAEvaluator<int[]> evaluator;
	private GASolution<int[]> poisonPill;

	public EvaluateJob(Queue<GASolution<int[]>> r, Queue<GASolution<int[]>> q, IGAEvaluator<int[]> evaluator,
			GASolution<int[]> poisonPill) {
		super();
		this.r = r;
		this.q = q;
		this.evaluator = evaluator;
		this.poisonPill = poisonPill;
	}

	@Override
	public void run() {
		while (true) {
			GASolution<int[]> sol = r.poll();
			if (sol == null) {
				continue;
			}
			if (sol == poisonPill) {
				break;
			}
			evaluator.evaluate(sol);
			q.add(sol);
		}
	}

}
