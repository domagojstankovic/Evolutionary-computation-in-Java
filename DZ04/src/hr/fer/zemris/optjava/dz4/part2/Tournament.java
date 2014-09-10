package hr.fer.zemris.optjava.dz4.part2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Tournament implements ISelection {

	private int n;
	private Random rand = new Random();
	
	public Tournament(int n) {
		super();
		this.n = n;
	}

	@Override
	public Solution select(Population population, boolean min) {
		Solution[] sols = getNSolutions(population);
		return findExtreme(sols, min);
	}

	private Solution[] getNSolutions(Population population) {
		Set<Integer> set = new HashSet<>();
		int size = population.getSize();
		while (set.size() < n) {
			set.add(rand.nextInt(size));
		}
		Solution[] sols = new Solution[n];
		int i = 0;
		for (Integer index : set) {
			sols[i] = population.getSolution(index);
			i++;
		}
		return sols;
	}
	
	private Solution findExtreme(Solution[] sols, boolean min) {
		Solution extreme = sols[0];
		for (int i = 1; i < n; i++) {
			if (min) {
				if (sols[i].compareTo(extreme) > 0) {
					extreme = sols[i];
				}
			} else {
				if (sols[i].compareTo(extreme) < 0) {
					extreme = sols[i];
				}
			}
		}
		return extreme;
	}

}
