package hr.fer.zemris.trisat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

public class TriSATSolver {

	private static final int numberOfBest = 2;
	private static final int NUMBER_OF_ITERATIONS = 100000;
	
	public static void main(String[] args) {
		if (args.length != 2) {
			greska();
		}

		int algNum = -1;
		try {
			algNum = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex) {
			greska();
		}

		File file = new File(args[1]);
		if (!file.exists()) {
			greska();
		}

		List<String> lines = null;
		try {
			lines = filterFile(file);
		} catch (IOException e) {
			greska();
		}

		SATFormula formula = SATFormula.getClauses(lines);

		if (algNum == 1) {
			algoritam1(formula);
		} else if (algNum == 2) {
			algoritam2(formula);
		} else if (algNum == 3) {
			algoritam3(formula);
		}
	}

	private static BitVector algoritam1(SATFormula formula) {
		int n = formula.getNumberOfVariables();
		boolean[] array = new boolean[n];
		for (int i = 0; i < n; i++) {
			array[i] = false;
		}
		BitVector pocetni = new BitVector(array);
		MutableBitVector vector = new MutableBitVector(array);
		BitVector solution = null;
		do {
			if (formula.isSatisfied(vector)) {
				System.out.println(vector.toString());
				solution = vector;
			}
			vector.increase();
		} while (!vector.equals(pocetni));
		return solution;
	}

	private static BitVector algoritam2(SATFormula formula) {
		BitVector vector = new BitVector(formula.getNumberOfVariables());
		for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
			SATFormulaStats vstat = new SATFormulaStats(formula);
			vstat.setAssignment(vector, true);
			if (vstat.isSatisfied()) {
				System.out.println("Zadovoljivo: " + vector);
				return vector;
			}
			BitVectorNGenerator generator = new BitVectorNGenerator(vector);
			MutableBitVector[] neighborhood = generator.createNeighborhood();
			int size = neighborhood.length;
			int[] fitness = new int[size];
			int max = 0;
			for (int j = 0; j < size; j++) {
				SATFormulaStats st = new SATFormulaStats(formula);
				st.setAssignment(neighborhood[j], true);
				fitness[j] = st.getNumberOfSatisfied();
				if (st.isSatisfied()) {
					System.out.println("Zadovoljivo: " + neighborhood[j].toString());
					return neighborhood[j];
				}
				if (max < fitness[j]) {
					max = fitness[j];
				}
			}
			if (max < vstat.getNumberOfSatisfied()) {
				System.out.println("Neuspjeh! Lokalni optimum!");
				return null;
			}
			List<MutableBitVector> solutions = new ArrayList<>();
			for (int j = 0; j < size; j++) {
				if (fitness[j] >= max) {
					solutions.add(neighborhood[j]);
				}
			}
			int rand = new Random().nextInt(solutions.size());
			vector = solutions.get(rand);
		}
		System.out.println("Neuspjeh! Prekoračen broj dozvoljenih iteracija!");
		return null;
	}

	private static BitVector algoritam3(SATFormula formula) {
		BitVector vector = new BitVector(formula.getNumberOfVariables());
		SATFormulaStats stats = new SATFormulaStats(formula);
		for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
			stats.setAssignment(vector, true);
			if (stats.isSatisfied()) {
				System.out.println("Zadovoljivo: " + vector);
				return vector;
			}
			BitVectorNGenerator generator = new BitVectorNGenerator(vector);
			Map<Double, MutableBitVector> map = new HashMap<>();
			for (MutableBitVector susjed : generator) {
				stats.setAssignment(susjed, false);
				double fit = stats.getPercentageBonus();
				map.put(fit, susjed);
			}
			List<Double> sorted = new ArrayList<>(map.keySet());
			Collections.sort(sorted);
			Collections.reverse(sorted);
			int num = 0;
			List<MutableBitVector> list = new ArrayList<>();
			for (Double d : sorted) {
				num++;
				list.add(map.get(d));
				if (num >= numberOfBest) {
					break;
				}
			}
			vector = list.get(new Random().nextInt(list.size()));
		}
		System.out.println("Neuspjeh! Prekoračen broj dozvoljenih iteracija!");
		return null;
	}

	private static List<String> filterFile(File file) throws IOException {
		List<String> lines = Files.readAllLines(file.toPath(),
				Charset.forName("UTF-8"));
		List<String> list = new ArrayList<>();
		for (String string : lines) {
			if (string.startsWith("c")) {
				continue;
			}
			if (string.startsWith("%")) {
				break;
			}
			list.add(string.trim());
		}
		return list;
	}

	private static void greska() {
		System.out.println("Greška u unosu parametara komandne linije!");
		System.exit(-1);
	}

}
