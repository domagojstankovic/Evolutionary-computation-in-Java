package hr.fer.zemris.optjava.dz9.function;

public class SH {

	private double oshare;
	private double alpha;
	
	public SH(double oshare, double alpha) {
		super();
		this.oshare = oshare;
		this.alpha = alpha;
	}

	public double valueAt(double d) {
		if (d > oshare) {
			return 0;
		}
		return 1 - Math.pow(d / oshare, alpha);
	}

}
