package hr.fer.zemris.optjava.dz4.part1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Population {
	
	private Solution[] solutions;

	public Population(Solution[] solutions, boolean copy) {
		this.solutions = copy ? copy(solutions) : solutions;
	}
	
	public Solution[] getSolutions(boolean copy) {
		return copy ? copy(solutions) : solutions;
	}
	
	public Solution getSolution(int index) {
		return solutions[index];
	}
	
	private static Solution[] copy(Solution[] array) {
		int size = array.length;
		Solution[] newArray = new Solution[size];
		for (int i = 0; i < size; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	
	public void sortSolutions() {
		List<Solution> list = Arrays.asList(solutions);
		Collections.sort(list);
		solutions = list.toArray(solutions);
	}
	
	public void updateFitness(IFunction function) {
		for (Solution sol : solutions) {
			sol.setFitness(function.valueAt(sol.getSolution(false)));
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = solutions.length;
		for (int i = 0; i < size - 1; i++) {
			sb.append(solutions[i] + "\n");
		}
		sb.append(solutions[size - 1]);
		return sb.toString();
	}
	
}
