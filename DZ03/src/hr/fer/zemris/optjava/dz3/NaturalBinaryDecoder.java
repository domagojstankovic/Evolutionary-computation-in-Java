package hr.fer.zemris.optjava.dz3;

public class NaturalBinaryDecoder extends BitvectorDecoder {

	public NaturalBinaryDecoder(double min, double max, int bits, int n) {
		super(min, max, bits, n);
	}

	public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins, maxs, bits, n);
	}

	@Override
	public double[] decode(BitvectorSolution solution) {
		double[] array = new double[n];
		decode(solution, array);
		return array;
	}

	@Override
	public void decode(BitvectorSolution solution, double[] field) {
		int bitsIndex = n - 1;
		int sum = 0;
		int num = bits[bitsIndex];
		int potencija = 1;
		int[] k = new int[n];
		for (int i = totalBits - 1; i >= 0; i--) {
			byte bit = solution.bits[i];
			sum += potencija * bit;
			potencija *= 2;
			num--;
			if (num == 0) {
				k[bitsIndex] = sum;
				potencija = 1;
				bitsIndex--;
				if (bitsIndex < 0) {
					break;
				}
				num = bits[bitsIndex];
				sum = 0;
			}
		}
		for (int i = 0; i < n; i++) {
			field[i] = mins[i] + k[i] * (maxs[i] - mins[i]) / (power(2, bits[i]) - 1);
		}
	}

	private int power(int base, int exp) {
		int num = 1;
		for (int i = 0; i < exp; i++) {
			num *= base;
		}
		return num;
	}

}
