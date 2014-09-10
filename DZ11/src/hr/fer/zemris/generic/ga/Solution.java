package hr.fer.zemris.generic.ga;

import java.util.Arrays;

public class Solution extends GASolution<int[]> {

	public Solution(int[] data) {
		super(data);
	}

	@Override
	public GASolution<int[]> duplicate() {
		return new Solution(Arrays.copyOf(data, data.length));
	}
	
	@Override
	public String toString() {
		return Arrays.toString(data);
	}

}
