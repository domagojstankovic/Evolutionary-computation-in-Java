package hr.fer.zemris.optjava.dz4.part1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Tournament implements ISelection {

	private int n;
	
	public Tournament(int n) {
		this.n = n;
	}

	@Override
	public Solution select(Population population) {
		Solution[] solutions = population.getSolutions(false);
		List<Solution> list = new LinkedList<>();
		for (Solution sol : solutions) {
			list.add(sol);
		}
		Random rand = new Random();
		List<Solution> tournament = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int index = rand.nextInt(list.size());
			tournament.add(list.get(index));
			list.remove(index);
		}
		int index = 0;
		double min = tournament.get(0).getFitness();
		int size = tournament.size();
		for (int i = 1; i < size; i++) {
			if (tournament.get(i).getFitness() < min) {
				min = tournament.get(i).getFitness();
				index = i;
			}
		}
		return tournament.get(index);
	}

}
