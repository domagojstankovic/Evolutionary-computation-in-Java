package hr.fer.zemris.optjava.dz8.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Strategy {
	
	private Random rand = new Random();
	
	public abstract Solution mutate(Population pop, Solution target);

	protected Solution[] getDiffSols(int n, Population pop, Solution target) {
		int size = pop.size();
		Set<Solution> set = new HashSet<>(n + 1);
		set.add(target);
		while (set.size() < n + 1) {
			set.add(pop.getSolAt(rand.nextInt(size)));
		}
		Solution[] sols = new Solution[n];
		int index = 0;
		for (Solution tmp : set) {
			if (tmp.equals(target)) {
				continue;
			}
			sols[index] = tmp;
			index++;
		}
		return sols;
	}

}
