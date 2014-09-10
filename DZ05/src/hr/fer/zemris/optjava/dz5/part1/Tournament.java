package hr.fer.zemris.optjava.dz5.part1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tournament implements ISelection {

	private int n;
	private boolean max;
	
	public Tournament(int n, boolean max) {
		super();
		this.n = n;
		this.max = max;
	}

	@Override
	public Genotype select(Population population) {
		Set<Genotype> gens = new HashSet<>();
		while (gens.size() < n) {
			gens.add(population.getRandomGenotype());
		}
		List<Genotype> list = new ArrayList<>(gens);
		Genotype gen = list.get(0);
		double extreme = gen.getFitness();
		if (max) {
			for (int i = 1; i < n; i++) {
				Genotype newGen = list.get(i);
				double fit = newGen.getFitness();
				if (fit > extreme) {
					gen = newGen;
					extreme = fit;
				}
			}
		} else {
			for (int i = 1; i < n; i++) {
				Genotype newGen = list.get(i);
				double fit = newGen.getFitness();
				if (fit < extreme) {
					gen = newGen;
					extreme = fit;
				}
			}
		}
		return gen;
	}

}
