package hr.fer.zemris.generic.ga;

public abstract class GASolution<T> implements Comparable<GASolution<T>> {

	protected T data;
	public double fitness;
	
	public GASolution(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}
	
	public abstract GASolution<T> duplicate();
	
	@Override
	public int compareTo(GASolution<T> o) {
		return -Double.compare(this.fitness, o.fitness);
	}
	
}
