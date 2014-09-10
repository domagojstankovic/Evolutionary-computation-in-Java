package hr.fer.zemris.optjava.dz7.model;

public class Particle implements Comparable<Particle> {
	
	private double[] currPosition;
	private double[] bestPosition;
	private double[] velocity;
	private double currFitness;
	private double bestFitness;
	
	public Particle(double[] initialPosition, double[] velocity) {
		super();
		bestPosition = currPosition = initialPosition;
		this.velocity = velocity;
	}
	
	public int size() {
		return bestPosition.length;
	}
	
	public double[] getCurrPosition() {
		return currPosition;
	}
	
	public double[] getBestPosition() {
		return bestPosition;
	}
	
	public double getCurrPositionAt(int index) {
		return currPosition[index];
	}
	
	public double getBestPositionAt(int index) {
		return bestPosition[index];
	}
	
	public void setBestPosition(double[] bestPosition) {
		this.bestPosition = bestPosition;
	}
	
	public void setCurrPosition(double[] currPosition) {
		this.currPosition = currPosition;
	}
	
	public void setCurrPositionAt(int index, double position) {
		this.currPosition[index] = position;
	}
	
	public double[] getVelocity() {
		return velocity;
	}
	
	public double getVelocityAt(int index) {
		return velocity[index];
	}
	
	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}
	
	public double getCurrFitness() {
		return currFitness;
	}
	
	public void setCurrFitness(double currFitness) {
		this.currFitness = currFitness;
	}
	
	public double getBestFitness() {
		return bestFitness;
	}
	
	public void setBestFitness(double bestFitness) {
		this.bestFitness = bestFitness;
	}

	@Override
	public int compareTo(Particle o) {
		double diff = bestFitness - o.bestFitness;
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
		sb.append(bestPosition[0]);
		int size = size();
		for (int i = 1; i < size; i++) {
			sb.append(", " + bestPosition[i]);
		}
		sb.append("]");
		return sb.toString();
	}
	
}
