package hr.fer.zemris.trisat;

import java.util.Random;

public class MutableBitVector extends BitVector {
	
	public MutableBitVector(boolean ... bits) {
		vector = bits;
	}
	
	public MutableBitVector(int n) {
		Random rand = new Random();
		vector = new boolean[n];
		for (int i = 0; i < n; i++) {
			int broj = rand.nextInt(2);
			vector[i] = broj == 1 ? true : false;
		}
	}
	
	public void set(int index, boolean value) {
		vector[index] = value;
	}
	
	public MutableBitVector increase() {
		for (int i = vector.length - 1; i >= 0; i--) {
			vector[i] = !vector[i];
			if (vector[i] == true) {
				break;
			}
		}
		return this;
	}
	
}
