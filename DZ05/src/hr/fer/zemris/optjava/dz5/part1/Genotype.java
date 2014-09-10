package hr.fer.zemris.optjava.dz5.part1;

import java.util.Arrays;
import java.util.Random;

public class Genotype {

	private byte[] genotype;
	private Random rand = new Random();
	private double fitness;

	public Genotype(byte[] genotype, boolean copy) {
		this.genotype = copy ? copy(genotype) : genotype;
	}
	
	public Genotype(int n) {
		genotype = new byte[n];
		for (int i = 0; i < n; i++) {
			genotype[i] = (byte) rand.nextInt(2);
		}
	}
	
	public byte[] copy(byte[] genotype) {
		int n = genotype.length;
		byte[] newGen = new byte[n];
		for (int i = 0; i < n; i++) {
			newGen[i] = genotype[i];
		}
		return newGen;
	}
	
	public Genotype duplicate() {
		return new Genotype(genotype, true);
	}
	
	public byte getElementAt(int index) {
		return genotype[index];
	}
	
	public byte[] getGenotype(boolean copy) {
		return copy ? copy(genotype) : genotype;
	}
	
	public int size() {
		return genotype.length;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public int getOnesNum() {
		int sum = 0;
		for (byte b : genotype) {
			sum += b;
		}
		return sum;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(genotype);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Genotype)) {
			return false;
		}
		Genotype another = (Genotype) obj;
		int n = genotype.length;
		if (n != another.size()) {
			return false;
		}
		for (int i = 0; i < n; i++) {
			if (genotype[i] != another.getElementAt(i)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		int n = genotype.length;
		sb.append(genotype[0]);
		for (int i = 1; i < n; i++) {
			sb.append(", " + genotype[i]);
		}
		sb.append("]");
		return sb.toString();
	}
	
}
