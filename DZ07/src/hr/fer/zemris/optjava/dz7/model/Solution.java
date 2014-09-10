package hr.fer.zemris.optjava.dz7.model;

public class Solution implements Comparable<Solution> {
	
	private double[] weights;
	private double fitness;
	
	public Solution(double[] weights) {
		super();
		this.weights = weights;
	}
	
	public Solution(double[] weights, double fitness) {
		super();
		this.weights = weights;
		this.fitness = fitness;
	}

	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double[] getWeights() {
		return weights;
	}
	
	public double getWeightAt(int index) {
		return weights[index];
	}
	
	public void setValueAt(int index, double value) {
		weights[index] = value;
	}
	
	public int size() {
		return weights.length;
	}

	@Override
	public int compareTo(Solution o) {
		double diff = fitness - o.fitness;
		if (diff < 0) {
			return -1;
		} else if (diff > 0) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		sb.append(weights[0]);
		int size = size();
		for (int i = 1; i < size; i++) {
			sb.append(", " + weights[i]);
		}
		sb.append("]");
		return sb.toString();
	}
	
}
