package hr.fer.zemris.optjava.dz10.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MOUtils {
	
	private MOUtils() {
	}
	
	public static int domination(Solution sol1, Solution sol2) {
		int n = sol1.fitSize();
		int g1 = 0;
		int g2 = 0;
		for (int i = 0; i < n; i++) {
			if (sol1.getFitAt(i) < sol2.getFitAt(i)) {
				g2++;
			} else if (sol1.getFitAt(i) > sol2.getFitAt(i)) {
				g1++;
			}
		}
		if ((g1 > 0 && g2 > 0) || (g1 == 0 && g2 == 0)) {
			return 0;
		} else if (g1 > 0 && g2 == 0) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public static List<List<Solution>> nonDominatedSort(List<Solution> sols) {
		int n = sols.size();
		List<List<Integer>> s = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			s.add(new ArrayList<Integer>());
		}
		int[] dom = new int[n];
		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				int result = domination(sols.get(i), sols.get(j));
				if (result == 0) {
					continue;
				}
				if (result == -1) {
					s.get(i).add(j);
					dom[j]++;
				} else {
					s.get(j).add(i);
					dom[i]++;
				}
			}
		}
		List<List<Solution>> fronts = new ArrayList<>();
		boolean remaining = true;
		int front = 0;
		while (remaining) {
			remaining = false;
			List<Solution> list = new ArrayList<>();
			List<Integer> indexes = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				if (dom[i] == 0) {
					remaining = true;
					Solution sol = sols.get(i);
					sol.setFront(front);
					list.add(sol);
					indexes.add(i);
					dom[i] = -1;
				}
			}
			if (remaining) {
				int size = indexes.size();
				for (int i = 0; i < size; i++) {
					int index = indexes.get(i);
					List<Integer> curr = s.get(index);
					int size2 = curr.size();
					for (int j = 0; j < size2; j++) {
						dom[curr.get(j)]--;
					}
				}
				fronts.add(list);
			}
			front++;
		}
		return fronts;
	}
	
	public static void updateDistance(List<Solution> sols) {
		int m = sols.get(0).fitSize();
		int size = sols.size();
		List<SortSol> sortSols = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Solution sol = sols.get(i);
			sol.setDistance(0);
			sortSols.add(new SortSol(0, sol));
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < size; j++) {
				sortSols.get(j).setCriteria(i);
			}
			Collections.sort(sortSols);
			double min = sortSols.get(0).getSol().getFitAt(i);
			double max = sortSols.get(size - 1).getSol().getFitAt(i);
			sortSols.get(0).getSol().setDistance(Double.POSITIVE_INFINITY);
			sortSols.get(size - 1).getSol().setDistance(Double.POSITIVE_INFINITY);
			for (int j = 1; j < size - 1; j++) {
				double val = Math.abs(sols.get(j + 1).getFitAt(i) - sols.get(j - 1).getFitAt(i)) / (max - min);
				sortSols.get(j).getSol().addDistance(val);
			}
		}
	}
	
	public static void crowdingSort(List<Solution> sols) {
		Collections.sort(sols, new CrowdSortComparator());
	}
	
}
