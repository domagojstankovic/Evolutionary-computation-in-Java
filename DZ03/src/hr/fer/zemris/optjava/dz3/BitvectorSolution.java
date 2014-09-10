package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class BitvectorSolution extends SingleObjectiveSolution {
	
	public byte[] bits;
	
	public BitvectorSolution(int n) {
		bits = new byte[n];
		randomize(new Random());
	}
	
	public BitvectorSolution newLikeThis() {
		return new BitvectorSolution(bits.length);
	}
	
	public BitvectorSolution duplicate() {
		int n = bits.length;
		BitvectorSolution sol = new BitvectorSolution(n);
		for (int i = 0; i < n; i++) {
			sol.bits[i] = bits[i];
		}
		return sol;
	}
	
	public void randomize(Random rand) {
		int size = bits.length;
		int index = rand.nextInt(size);
		bits[index] = (byte) (1 - bits[index]);
//		rand.nextBytes(bits);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = bits.length;
		for (int i = 0; i < size; i++) {
			sb.append(bits[i]);
		}
		return sb.toString();
	}
	
}
