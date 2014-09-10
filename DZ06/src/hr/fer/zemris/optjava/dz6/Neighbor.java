package hr.fer.zemris.optjava.dz6;

public class Neighbor implements Comparable<Neighbor> {

	int index;
	double distance;
	
	public Neighbor(int index, double distance) {
		super();
		this.index = index;
		this.distance = distance;
	}

	@Override
	public int compareTo(Neighbor o) {
		double diff = distance - o.distance;
		if (diff < 0) {
			return -1;
		}
		if (diff > 0) {
			return 1;
		}
		return 0;
	}
	
	
	
}
