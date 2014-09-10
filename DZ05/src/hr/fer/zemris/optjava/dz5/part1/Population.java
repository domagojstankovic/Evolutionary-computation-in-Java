package hr.fer.zemris.optjava.dz5.part1;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Population {
	
	private Set<Genotype> genotypes;
	private Random rand = new Random();

	public Population(Set<Genotype> genotypes) {
		super();
		this.genotypes = genotypes;
	}
	
	public Population(int popSize, int n) {
		genotypes = new HashSet<>();
		while (genotypes.size() < popSize) {
			genotypes.add(new Genotype(n));
		}
	}
	
	public Set<Genotype> getGenotypes(boolean copyPop, boolean copyGen) {
		if (!copyPop) {
			return genotypes;
		}
		if (!copyGen) {
			return new HashSet<>(genotypes);
		}
		Set<Genotype> gens = new HashSet<>();
		for (Genotype gen : genotypes) {
			gens.add(gen.duplicate());
		}
		return gens;
	}
	
	public int size() {
		return genotypes.size();
	}
	
	public Set<Genotype> getGenotypes(boolean copy) {
		return getGenotypes(copy, copy);
	}
	
	public Genotype getRandomGenotype() {
		int num = rand.nextInt(genotypes.size());
		for (Genotype gen : genotypes) {
			if (num <= 0) {
				return gen;
			}
			num--;
		}
		return null;
	}
	
	public void updateFitness(IFunction function) {
		for (Genotype gen : genotypes) {
			gen.setFitness(function.getValue(gen));
		}
	}
	
	public Genotype getBest() {
		Genotype best = null;
		for (Genotype gen : genotypes) {
			if (best == null) {
				best = gen;
				continue;
			}
			if (gen.getFitness() > best.getFitness()) {
				best = gen;
			}
		}
		return best;
	}
	
}
