package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirstFit {

	public static Solution fit(Solution sol, List<Integer> sticks) {
		Collections.sort(sticks);
		Collections.reverse(sticks);
		Set<Bin> set = sol.getBins();
		List<Bin> bins = new ArrayList<>(set);
		Collections.sort(bins);
		Collections.reverse(bins);
		
		boolean added = false;
		for (Integer i : sticks) {
			added = false;
			for (Bin bin : bins) {
				if (bin.add(i)) {
					added = true;
					break;
				}
			}
			if (!added) {
				bins.add(new Bin(i, sol.getCapacity()));
			}
		}
		return new Solution(new HashSet<>(bins), sol.getCapacity());
	}
	
}
