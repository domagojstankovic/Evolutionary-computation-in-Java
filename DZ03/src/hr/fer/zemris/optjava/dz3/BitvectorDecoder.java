package hr.fer.zemris.optjava.dz3;

public abstract class BitvectorDecoder implements IDecoder<BitvectorSolution> {

	protected double[] mins;
	protected double[] maxs;
	protected int[] bits;
	protected int n;
	protected int totalBits = 0;
	
	public BitvectorDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super();
		this.mins = mins;
		this.maxs = maxs;
		this.bits = bits;
		this.n = n;
		for (int i = 0; i < n; i++) {
			totalBits += bits[i];
		}
	}
	
	public BitvectorDecoder(double min, double max, int bits, int n) {
		this.n = n;
		mins = new double[n];
		maxs = new double[n];
		this.bits = new int[n];
		for (int i = 0; i < n; i++) {
			mins[i] = min;
			maxs[i] = max;
			this.bits[i] = bits;
		}
		totalBits = n * bits;
	}

	public int getTotalBits() {
		return totalBits;
	}
	
	public int getDimensions() {
		return n;
	}
	
	@Override
	public abstract double[] decode(BitvectorSolution solution);

	@Override
	public abstract void decode(BitvectorSolution solution, double[] field);
	
}
