package hr.fer.zemris.optjava.dz4.part2;

import java.util.LinkedList;
import java.util.List;

public class CustomMutation implements IMutation {

	private int removeNumber;
	
	public CustomMutation(int removeNumber) {
		super();
		this.removeNumber = removeNumber;
	}

	@Override
	public Solution mutate(Solution sol) {
		int n = removeNumber <= sol.getSize() ? removeNumber : sol.getSize();
		List<Integer> sticks = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			Bin bin = sol.removeRandomBin();
			sticks.addAll(bin.getSticks());
		}
		return FirstFit.fit(sol, sticks);
	}

}
