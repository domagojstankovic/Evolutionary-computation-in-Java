package hr.fer.zemris.optjava.dz10.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz10.model.CrowdSortComparator;
import hr.fer.zemris.optjava.dz10.model.Population;
import hr.fer.zemris.optjava.dz10.model.Solution;

public class CrowdingSelectionTournament implements ISelection {

	private int num;
	private Random rand = new Random();
	
	public CrowdingSelectionTournament(int num) {
		super();
		this.num = num;
	}

	@Override
	public Solution[] select(Population population, int n) {
		Solution[] sols = new Solution[n];
		for (int i = 0; i < n; i++) {
			Set<Integer> set = new HashSet<>(num);
			int size = population.size();
			while (set.size() < num) {
				set.add(rand.nextInt(size));
			}
			sols[i] = winner(population, set);
		}
		return sols;
	}

	private Solution winner(Population population, Set<Integer> set) {
		int size = set.size();
		List<Solution> coll = new ArrayList<>(size);
		for (Integer i : set) {
			coll.add(population.getSolAt(i));
		}
		return Collections.max(coll, new CrowdSortComparator());
	}

}
