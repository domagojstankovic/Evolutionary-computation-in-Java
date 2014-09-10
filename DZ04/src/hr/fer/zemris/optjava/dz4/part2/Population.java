package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

	private Solution[] solutions;

	public Population(Solution[] solutions) {
		super();
		this.solutions = solutions;
	}
	
	public Solution[] getSolutions() {
		return solutions;
	}
	
	public Solution getSolution(int index) {
		return solutions[index];
	}
	
	public int getSize() {
		return solutions.length;
	}
	
	public void sortSolutions() {
		List<Solution> list = new ArrayList<>();
		for (Solution sol : solutions) {
			sol.updateFitness();
			list.add(sol);
		}
		Collections.sort(list);
		solutions = list.toArray(solutions);
	}
	
	public void removeEmptyBins() {
		for (Solution sol : solutions) {
			sol.removeEmptyBins();
		}
	}
	
}
