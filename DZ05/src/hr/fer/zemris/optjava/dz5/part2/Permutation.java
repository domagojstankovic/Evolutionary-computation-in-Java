package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Permutation implements Comparable<Permutation> {

	private int[] permutation;
	private double fitness;

	public Permutation(int[] permutation) {
		super();
		this.permutation = permutation;
	}
	
	public Permutation(int n) {
		permutation = new int[n];
		Random rand = new Random();
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		for (int i = 0; i < n; i++) {
			permutation[i] = list.remove(rand.nextInt(list.size()));
		}
	}
	
	public int[] getPermutation(boolean copy) {
		return copy ? copy(permutation) : permutation;
	}
	
	public int getElementAt(int index) {
		return permutation[index];
	}
	
	public int[] copy(int[] array) {
		int size = array.length;
		int[] newArray = new int[size];
		for (int i = 0; i < size; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	
	public Permutation duplicate() {
		return new Permutation(copy(permutation));
	}
	
	public int size() {
		return permutation.length;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public int getIndex(int element) {
		int size = permutation.length;
		for (int i = 0; i < size; i++) {
			if (permutation[i] == element) {
				return i;
			}
		}
		return size - 1;
	}
	
	public void swap(int i1, int i2) {
		int temp = permutation[i1];
		permutation[i1] = permutation[i2];
		permutation[i2] = temp;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		int n = permutation.length;
		sb.append(permutation[0]);
		for (int i = 1; i < n; i++) {
			sb.append(", " + permutation[i]);
		}
		sb.append(")");
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(permutation);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Permutation)) {
			return false;
		}
		Permutation another = (Permutation) obj;
		int n = permutation.length;
		for (int i = 0; i < n; i++) {
			if (permutation[i] != another.getElementAt(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int compareTo(Permutation o) {
		double fit2 = o.getFitness();
		if (fitness < fit2) {
			return -1;
		}
		if (fitness > fit2) {
			return 1;
		}
		return 0;
	}
	
}
