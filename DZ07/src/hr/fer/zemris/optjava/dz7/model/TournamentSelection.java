package hr.fer.zemris.optjava.dz7.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz7.model.interfaces.ISelection;

public class TournamentSelection implements ISelection {

	private int n;
	
	public TournamentSelection(int n) {
		super();
		this.n = n;
	}

	@Override
	public Solution select(Pop population, boolean best) {
		Random rand = new Random();
		Set<Integer> set = new HashSet<>();
		int size = population.size();
		while (set.size() < n) {
			set.add(rand.nextInt(size));
		}
		List<Solution> sols = new ArrayList<>(n);
		for (Integer i : set) {
			sols.add(population.getSolAt(i));
		}
		return best ? Collections.max(sols) : Collections.min(sols);
	}

}
