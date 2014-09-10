package hr.fer.zemris.optjava.dz10.model;

import java.util.Comparator;

public class CrowdSortComparator implements Comparator<Solution> {

	@Override
	public int compare(Solution o1, Solution o2) {
		if (o1.getFront() != o2.getFront()) {
			return o2.getFront() - o1.getFront();
		}
		double diff = o1.getDistance() - o2.getDistance();
		if (diff < 0) {
			return -1;
		}
		if (diff > 0) {
			return 1;
		}
		return 0;
	}

}
