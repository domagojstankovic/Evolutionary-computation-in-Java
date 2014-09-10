package hr.fer.zemris.optjava.dz5.part2;

public class Task {

	private int n;
	private int[][] distances;
	private int[][] cost;
	
	public Task(int n, int[][] distances, int[][] cost) {
		super();
		this.n = n;
		this.distances = distances;
		this.cost = cost;
	}
	
	public int getN() {
		return n;
	}
	
	public int[][] getDistances() {
		return distances;
	}
	
	public int[][] getCost() {
		return cost;
	}
	
}
