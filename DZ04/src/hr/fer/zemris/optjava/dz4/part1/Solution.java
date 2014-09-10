package hr.fer.zemris.optjava.dz4.part1;

public class Solution implements Comparable<Solution> {
	
	private final double[] solution;
	private double fitness;
	private double relativeFitness = 0;

	public Solution(double[] solution, boolean copy) {
		this.solution = copy ? copy(solution) : solution;
	}
	
	public double[] getSolution(boolean copy) {
		return copy ? copy(solution) : solution;
	}
	
	private static double[] copy(double[] array) {
		int size = array.length;
		double[] newArray = new double[size];
		for (int i = 0; i < size; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getRelativeFitness() {
		return relativeFitness;
	}
	
	public void setRelativeFitness(double relativeFitness) {
		this.relativeFitness = relativeFitness;
	}

	@Override
	public String toString() {
		return arrayDisplay(solution);
	}
	
	private static String arrayDisplay(double[] array) {
		StringBuilder sb = new StringBuilder("(");
		int size = array.length;
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(array[i]);
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public int compareTo(Solution o) {
		double diff = fitness - o.getFitness();
		if (diff > 0) {
			return -1;
		}
		if (diff < 0) {
			return 1;
		}
		return 0;
	}
	
}
