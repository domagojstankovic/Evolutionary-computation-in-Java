package hr.fer.zemris.optjava.dz9.model;

import java.util.ArrayList;
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
		while (remaining) {
			remaining = false;
			List<Solution> list = new ArrayList<>();
			List<Integer> indexes = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				if (dom[i] == 0) {
					remaining = true;
					list.add(sols.get(i));
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
		}
		return fronts;
	}
	
//	public static void main(String[] args) {
//		List<Solution> sols = new ArrayList<>();
//		double[] a = new double[]{1,1};
//		sols.add(new Solution(a , new double[]{-6,-4}));
//		sols.add(new Solution(a , new double[]{-5,-2}));
//		sols.add(new Solution(a , new double[]{-4,-1}));
//		sols.add(new Solution(a , new double[]{-3,-3}));
//		sols.add(new Solution(a , new double[]{-2,-2}));
//		sols.add(new Solution(a , new double[]{-0.9307287683034239, -1.0830691144638422}));
//		sols.add(new Solution(a , new double[]{-0.9138100090163771, -1.10321506470375}));
//		List<List<Solution>> fronts = nonDominatedSort(sols);
//		MOOP.printFronts(fronts);
//	}
	
}
