package hr.fer.zemris.optjava.dz13.model;

public class Solution implements Comparable<Solution> {

	private Node root;
	private int fitness;
	
	public Solution(Node root) {
		super();
		this.root = root;
	}
	
	public Solution(Node root, int fitness) {
		super();
		this.root = root;
		this.fitness = fitness;
	}

	public int getFitness() {
		return fitness;
	}
	
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	
	public Node getRoot() {
		return root;
	}
	
	public void punish(double p) {
		fitness *= p;
	}
	
	@Override
	public int compareTo(Solution o) {
		return Integer.compare(fitness, o.fitness);
	}
	
	public Solution duplicate() {
		return new Solution(root.duplicate(null), fitness);
	}
	
	@Override
	public String toString() {
		return "Fitness: " + fitness;
	}
	
}
