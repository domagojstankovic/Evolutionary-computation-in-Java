package hr.fer.zemris.optjava.dz13.model.sel;

import hr.fer.zemris.optjava.dz13.model.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TournamentSelection implements ISelection {
	
	private int n;
	private Random rand = new Random();
	
	public TournamentSelection(int n) {
		super();
		this.n = n;
	}

	@Override
	public Solution select(List<Solution> sols) {
		int size = sols.size();
		Set<Integer> set = new HashSet<>(n);
		while (set.size() < n) {
			set.add(rand.nextInt(size));
		}
		List<Solution> tsols = new ArrayList<>(n);
		for (Integer i : set) {
			tsols.add(sols.get(i));
		}
		return Collections.max(tsols);
	}

}
