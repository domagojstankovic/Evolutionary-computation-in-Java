package hr.fer.zemris.trisat;

import java.util.Iterator;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {
	
	private BitVector vector = null;
	
	public BitVectorNGenerator(BitVector assignment) {
		vector = assignment;
	}
	
	@Override
	public Iterator<MutableBitVector> iterator() {
		return new Iterator<MutableBitVector>() {
			
			private int i = 0;
			
			@Override
			public boolean hasNext() {
				if (i < vector.getSize()) {
					return true;
				}
				return false;
			}

			@Override
			public MutableBitVector next() {
				MutableBitVector v = vector.copy();
				v.set(i, !vector.get(i));
				i++;
				return v;
			}

			@Override
			public void remove() {
			}
		};
	}
	
	public MutableBitVector[] createNeighborhood() {
		int length = vector.getSize();
		MutableBitVector[] v = new MutableBitVector[length];
		int i = 0;
		for (MutableBitVector m : this) {
			v[i] = m;
			i++;
		}
		return v;
	}

}
