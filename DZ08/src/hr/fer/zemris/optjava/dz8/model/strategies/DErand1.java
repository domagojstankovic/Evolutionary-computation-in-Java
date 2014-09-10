package hr.fer.zemris.optjava.dz8.model.strategies;

import hr.fer.zemris.optjava.dz8.model.Population;
import hr.fer.zemris.optjava.dz8.model.Solution;
import hr.fer.zemris.optjava.dz8.model.Strategy;


public class DErand1 extends Strategy {

	private double f;
	
	public DErand1(double f) {
		super();
		this.f = f;
	}

	@Override
	public Solution mutate(Population pop, Solution target) {
		Solution[] sols = getDiffSols(3, pop, target);
		Solution sub = sols[1].add(sols[2].scalarMultiply(-1));
		return sols[0].add(sub.scalarMultiply(f));
	}

}
