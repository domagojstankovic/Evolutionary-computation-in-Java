package hr.fer.zemris.optjava.dz5.part2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {

	private Permutation[] permutations;
	private Random rand = new Random();
	
	public Population(Permutation[] permutations) {
		super();
		this.permutations = permutations;
	}
	
	public Permutation[] getPermutations(boolean copy) {
		return copy ? copy(permutations, true) : permutations;
	}
	
	public int size() {
		return permutations.length;
	}
	
	public Permutation getBest() {
		List<Permutation> list = Arrays.asList(permutations);
		return Collections.max(list);
	}
	
	public Permutation[] copy(Permutation[] permutations, boolean copy) {
		int size = permutations.length;
		Permutation[] newPer = new Permutation[size];
		for (int i = 0; i < size; i++) {
			newPer[i] = copy ? permutations[i].duplicate() : permutations[i];
		}
		return newPer;
	}
	
	public Permutation getRandomPermutation() {
		return permutations[rand.nextInt(permutations.length)];
	}
	
}
