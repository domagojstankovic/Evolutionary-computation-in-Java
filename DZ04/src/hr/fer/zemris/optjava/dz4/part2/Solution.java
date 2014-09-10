package hr.fer.zemris.optjava.dz4.part2;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Solution implements Comparable<Solution> {
	
	private Set<Bin> bins;
	private double fitness;
	private int capacity;
	private Random rand = new Random();
	
	public Solution(int capacity) {
		bins = new HashSet<>();
		this.capacity = capacity;
	}
	
	public Solution(Set<Bin> bins, int capacity) {
		super();
		this.bins = bins;
		this.capacity = capacity;
		updateFitness();
	}

	public Solution(List<Integer> sticks, int capacity) {
		this(capacity);
		bins.add(new Bin(sticks.get(0), capacity));
		int size = sticks.size();
		for (int i = 1; i < size; i++) {
			int num = rand.nextInt(bins.size());
			for (Bin bin : bins) {
				if (num <= 0) {
					boolean b = bin.add(sticks.get(i));
					if (!b) {
						bins.add(new Bin(sticks.get(i), capacity));
					}
					break;
				}
				num--;
			}
		}
	}
	
	public void addBin(Bin bin) {
		bins.add(bin);
		updateFitness();
	}
	
	public void addBins(Set<Bin> bins) {
		this.bins.addAll(bins);
		updateFitness();
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void updateFitness() {
		double sum = 0.0;
		for (Bin bin : bins) {
			sum += bin.getRatio();
		}
		fitness = sum / bins.size();
	}
	
	public Set<Bin> getBins() {
		return bins;
	}
	
	public void setBins(Set<Bin> bins) {
		this.bins = bins;
		updateFitness();
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public Bin getRandomBin() {
		int num = rand.nextInt(bins.size());
		for (Bin bin : bins) {
			if (num <= 0) {
				return bin;
			}
			num--;
		}
		return null;
	}
	
	public int getSize() {
		return bins.size();
	}
	
	public void removeEmptyBins() {
		Set<Bin> set = new HashSet<>();
		for (Bin bin : bins) {
			if (!bin.isEmpty()) {
				set.add(bin);
			}
		}
		bins = set;
		updateFitness();
	}
	
	public Bin removeRandomBin() {
		int num = rand.nextInt(bins.size());
		Bin chosenBin = null;
		for (Bin bin : bins) {
			if (num <= 0) {
				chosenBin = bin;
				break;
			}
			num--;
		}
		bins.remove(chosenBin);
		updateFitness();
		return chosenBin;
	}
	
	public Solution copy() {
		Set<Bin> newBins = new HashSet<>();
		for (Bin bin : bins) {
			newBins.add(bin.copy());
		}
		return new Solution(newBins, capacity);
	}

	@Override
	public int compareTo(Solution o) {
		double diff = fitness - o.fitness;
		if (diff > 0) {
			return -1;
		}
		if (diff < 0) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Bin bin : bins) {
			sb.append(bin.toString() + "\n");
		}
		return sb.toString();
	}
	
}
