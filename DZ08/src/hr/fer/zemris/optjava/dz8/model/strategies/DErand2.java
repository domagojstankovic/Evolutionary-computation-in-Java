package hr.fer.zemris.optjava.dz8.model.strategies;

import hr.fer.zemris.optjava.dz8.model.Population;
import hr.fer.zemris.optjava.dz8.model.Solution;
import hr.fer.zemris.optjava.dz8.model.Strategy;

public class DErand2 extends Strategy {

	private double f;
	
	public DErand2(double f) {
		super();
		this.f = f;
	}

	@Override
	public Solution mutate(Population pop, Solution target) {
		Solution[] sols = getDiffSols(5, pop, target);
		Solution add = sols[1].add(sols[2]);
		Solution sub = sols[3].add(sols[4]).scalarMultiply(-1);
		return sols[0].add(add.add(sub).scalarMultiply(f));
	}
	
}
