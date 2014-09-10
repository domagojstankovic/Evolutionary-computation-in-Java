package hr.fer.zemris.trisat;

import java.util.Random;

public class BitVector {
	
	protected boolean[] vector = null;
	
	public BitVector(Random rand, int numberOfBits) {
		vector = new boolean[numberOfBits];
		for (int i = 0; i < numberOfBits; i++) {
			int broj = rand.nextInt(2);
			vector[i] = broj == 1 ? true : false;
		}
	}
	
	public BitVector(boolean ... bits) {
		int length = bits.length;
		vector = new boolean[length];
		for (int i = 0; i < length; i++) {
			vector[i] = bits[i];
		}
	}
	
	public BitVector(int n) {
		this(new Random(), n);
	}
	
	public boolean get(int index) {
		return vector[index];
	}
	
	public int getSize() {
		return vector.length;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (boolean b : vector) {
			sb.append(b == true ? 1 : 0);
		}
		return sb.toString();
	}
	
	public MutableBitVector copy() {
		int size = vector.length;
		boolean[] array = new boolean[size];
		for (int i = 0; i < size; i++) {
			array[i] = vector[i];
		}
		return new MutableBitVector(array);
	}
	
	@Override
	public boolean equals(Object obj) {
		BitVector v = (BitVector) obj;
		int l1 = getSize();
		int l2 = v.getSize();
		if (l1 != l2) {
			return false;
		}
		for (int i = 0; i < l1; i++) {
			if (get(i) != v.get(i)) {
				return false;
			}
		}
		return true;
	}
	
}
