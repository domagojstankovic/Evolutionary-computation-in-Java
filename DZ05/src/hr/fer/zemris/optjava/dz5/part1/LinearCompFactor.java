package hr.fer.zemris.optjava.dz5.part1;

public class LinearCompFactor implements ICompFactor {

	private double tempFactor = 0.0;
	private double inc;
	
	public LinearCompFactor(int maxIter) {
		inc = (double) 1 / maxIter;
	}

	@Override
	public double getCompFactor() {
		if (tempFactor > 1) {
			return 1;
		}
		double retFactor = tempFactor;
		tempFactor += inc;
		return retFactor;
	}

}
