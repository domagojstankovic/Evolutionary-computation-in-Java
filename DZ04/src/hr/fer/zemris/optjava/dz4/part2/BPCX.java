package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BPCX implements ICrossover {

	private Random rand = new Random();
	
	@Override
	public Solution cross(Solution parent1, Solution parent2) {
		Solution child = parent1.copy();
		Solution temp = parent2.copy();
		int num = rand.nextInt(parent2.getSize());
		List<Integer> sticks = new ArrayList<>();
		Set<Bin> addBins = new HashSet<>();
		for (int i = 0; i < num; i++) {
			Bin bin = temp.removeRandomBin();
			addBins.add(bin);
			sticks.addAll(bin.getSticks());
		}
		Set<Bin> markedBins = new HashSet<>();
		List<Integer> remainingSticks = new ArrayList<>();
		boolean removed = false;
		for (Bin bin : child.getBins()) {
			removed = false;
			List<Integer> removedSticks = new LinkedList<>();
			for (Integer i : sticks) {
				boolean success = bin.remove(i);
				if (success) {
					removedSticks.add(i);
					removed = true;
				}
			}
			for (Integer i : removedSticks) {
				sticks.remove(i);
			}
			if (removed) {
				remainingSticks.addAll(bin.getSticks());
				markedBins.add(bin);
			}
		}
		child.getBins().removeAll(markedBins);
		child.addBins(addBins);
		child.removeEmptyBins();
		return FirstFit.fit(child, remainingSticks);
	}

}
