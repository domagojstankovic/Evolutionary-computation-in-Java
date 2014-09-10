package hr.fer.zemris.optjava.dz9.model;

import java.util.Arrays;

public class Solution implements Comparable<Solution> {
	
	private double[] x;
	private double[] fit;
	private double fitness;
	
	public Solution(double[] x, double[] fit) {
		super();
		this.x = x;
		this.fit = fit;
	}
	
	public Solution(double[] x, int fitSize) {
		super();
		this.x = x;
		this.fit = new double[fitSize];
	}
	
	public int size() {
		return x.length;
	}
	
	public int fitSize() {
		return fit.length;
	}
	
	public double[] getX() {
		return x;
	}
	
	public double getXAt(int index) {
		return x[index];
	}
	
	public double[] getFit() {
		return fit;
	}
	
	public double getFitAt(int index) {
		return fit[index];
	}
	
	public void setFit(double[] fit) {
		this.fit = fit;
	}
	
	public void setFitAt(int index, double fit) {
		this.fit[index] = fit;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
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
		return Arrays.toString(x) + " --- " + Arrays.toString(fit);
	}
	
	public String xToString() {
		return Arrays.toString(x);
	}
	
	public String fitToString() {
//		return Arrays.toString(fit);
		StringBuilder sb = new StringBuilder("[");
		sb.append(Math.abs(fit[0]));
		int size = size();
		for (int i = 1; i < size; i++) {
			sb.append(", " + Math.abs(fit[i]));
		}
		sb.append("]");
		return sb.toString();
	}
	
}
