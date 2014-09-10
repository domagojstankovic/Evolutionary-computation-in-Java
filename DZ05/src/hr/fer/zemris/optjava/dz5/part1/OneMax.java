package hr.fer.zemris.optjava.dz5.part1;

public class OneMax implements IFunction {

	public OneMax() {
	}
	
	@Override
	public double getValue(Genotype genotype) {
		int n = genotype.size();
		int k = genotype.getOnesNum();
		if (k <= 0.8 * n) {
			return (double) k / n;
		}
		if (k <= 0.9 * n) {
			return 0.8;
		}
		return 2 * k / (double) n - 1;
	}

}
