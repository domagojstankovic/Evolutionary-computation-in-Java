package hr.fer.zemris.optjava.dz8.model.strategies;

import java.util.Collections;

import hr.fer.zemris.optjava.dz8.model.Population;
import hr.fer.zemris.optjava.dz8.model.Solution;
import hr.fer.zemris.optjava.dz8.model.Strategy;

public class DEbest1 extends Strategy {

	private double f;
	
	public DEbest1(double f) {
		super();
		this.f = f;
	}

	@Override
	public Solution mutate(Population pop, Solution target) {
		Solution best = Collections.max(pop.getSols());
		Solution[] sols = getDiffSols(2, pop, target);
		Solution diff = sols[0].add(sols[1].scalarMultiply(-1));
		return best.add(diff.scalarMultiply(f));
	}

}
