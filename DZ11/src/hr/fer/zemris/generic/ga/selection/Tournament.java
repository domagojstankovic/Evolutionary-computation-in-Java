package hr.fer.zemris.generic.ga.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.Population;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Tournament implements ISelection {

	private int n;
	
	public Tournament(int n) {
		super();
		this.n = n;
	}

	@Override
	public GASolution<int[]> select(Population pop) {
		List<GASolution<int[]>> sols = pop.getSols();
		int size = sols.size();
		Set<Integer> set = new HashSet<>(size);
		IRNG rand = RNG.getRNG();
		while (set.size() < n) {
			set.add(rand.nextInt(0, size));
		}
		List<GASolution<int[]>> selection = new ArrayList<>(n);
		for (Integer i : set) {
			selection.add(sols.get(i));
		}
		return Collections.max(selection);
	}

}
