package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TournamentSelection implements ISelection {

	private int n;
	
	public TournamentSelection(int n) {
		super();
		this.n = n;
	}

	@Override
	public Permutation select(Population population) {
		Set<Permutation> perms = new HashSet<>();
		while (perms.size() < n) {
			perms.add(population.getRandomPermutation());
		}
		List<Permutation> list = new ArrayList<>(perms);
		Permutation perm = list.get(0);
		double max = perm.getFitness();
		for (int i = 1; i < n; i++) {
			Permutation newPerm = list.get(i);
			double fit = newPerm.getFitness();
			if (fit > max) {
				perm = newPerm;
				max = fit;
			}
		}
		return perm;
	}

}
