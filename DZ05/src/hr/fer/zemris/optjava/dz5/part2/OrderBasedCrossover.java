package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class OrderBasedCrossover implements ICrossover {

	private int n;
	private double percentage;
	private boolean ratio;
	private Random rand = new Random();
	
	public OrderBasedCrossover(int n) {
		super();
		ratio = false;
		this.n = n;
	}
	
	public OrderBasedCrossover(double percentage) {
		super();
		ratio = true;
		this.percentage = percentage;
	}

	@Override
	public PermutationPair cross(Permutation parent1, Permutation parent2) {
		Set<Integer> indexes = new TreeSet<>();
		int size = parent1.size();
		if (ratio) {
			n = (int) (percentage * size);
		}
		while (indexes.size() < n) {
			indexes.add(rand.nextInt(size));
		}
		List<Integer> values1 = new ArrayList<>();
		List<Integer> values2 = new ArrayList<>();
		for (Integer index : indexes) {
			values1.add(parent1.getElementAt(index));
			values2.add(parent2.getElementAt(index));
		}
		List<Integer> indexes1 = new ArrayList<>();
		List<Integer> indexes2 = new ArrayList<>();
		for (int i = 0; i < n ; i++) {
			indexes2.add(parent2.getIndex(values1.get(i)));
			indexes1.add(parent1.getIndex(values2.get(i)));
		}
		Collections.sort(indexes1);
		Collections.sort(indexes2);
		int[] child1 = parent1.getPermutation(true);
		int[] child2 = parent2.getPermutation(true);
		for (int i = 0; i < n; i++) {
			child1[indexes1.get(i)] = values2.get(i);
			child2[indexes2.get(i)] = values1.get(i);
		}
		return new PermutationPair(new Permutation(child1), new Permutation(child2));
	}
}
