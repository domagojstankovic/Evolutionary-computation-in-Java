package hr.fer.zemris.optjava.dz8.model;

import java.util.Arrays;

public class Solution implements Comparable<Solution> {
	
	private double[] sol;
	private double fitness;
	
	public Solution(double[] sol, double fitness) {
		super();
		this.sol = sol;
		this.fitness = fitness;
	}
	
	public Solution(double[] sol) {
		super();
		this.sol = sol;
	}
	
	public int size() {
		return sol.length;
	}
	
	public double getSolAt(int index) {
		return sol[index];
	}
	
	public double[] getSol() {
		return sol;
	}
	
	public void setSol(double[] sol) {
		this.sol = sol;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public Solution scalarMultiply(double scalar) {
		int n = size();
		double[] array = new double[n];
		for (int i = 0; i < n; i++) {
			array[i] = sol[i] * scalar;
		}
		return new Solution(array);
	}
	
	public Solution add(Solution other) {
		int n = size();
		double[] array = new double[n];
		for (int i = 0; i < n; i++) {
			array[i] = sol[i] + other.getSolAt(i);
		}
		return new Solution(array);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(sol);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Solution)) {
			return false;
		}
		Solution other = (Solution) obj;
		int n = sol.length;
		if (n != other.size()) {
			return false;
		}
		for (int i = 0; i < n; i++) {
			if (Math.abs(sol[i] - other.getSolAt(i)) > 1e-6) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int compareTo(Solution o) {
		double diff = fitness - o.fitness;
		if (diff < 0) {
			return -1;
		}
		if (diff > 0) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		sb.append(sol[0]);
		int size = size();
		for (int i = 1; i < size; i++) {
			sb.append(", " + sol[i]);
		}
		sb.append("]");
		return sb.toString();
	}
	
}
