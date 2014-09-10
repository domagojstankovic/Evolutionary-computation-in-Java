package hr.fer.zemris.optjava.dz4.part1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm implements IOptAlgorithm {

	private static final String FILE_PATH = "zad-prijenosna.txt";
	private static double ALPHA = 0.3;
	private static final int SOLUTION_SIZE = 6;
	private static final double MIN = -10;
	private static final double MAX = 10;
	// NAJBOLJI PARAMETRI: 500 0.001 10000 tournament:3 0.01
	// RJEŠENJE: (7, -3, 2, 1, +-3, 3)
	// ODGOVORI: algoritam radi bolje za:
	// - veće populacije
	// - za turnirske selekcije (za manji n)
	// - za manji sigma
	private IFunction function;
	private ISelection selection;
	private IMutation mutation;
	private ICrossover crossover;
	private double minErrVal;
	private int maxIter;
	private int popSize;
	private Population population;

	public GeneticAlgorithm(IFunction function, ISelection selection, IMutation mutation, ICrossover crossover,
			double minErrVal, int maxIter, int popSize, int solSize, Population startWith) {
		super();
		this.function = function;
		this.selection = selection;
		this.mutation = mutation;
		this.crossover = crossover;
		this.minErrVal = minErrVal;
		this.maxIter = maxIter;
		this.popSize = popSize;
		population = startWith == null ? generateRandomPopulation(popSize, solSize, MIN, MAX) : startWith;
	}
	
	private Population generateRandomPopulation(int popSize, int solSize, double min, double max) {
		Solution[] solutions = new Solution[popSize];
		Random rand = new Random();
		for (int i = 0; i < popSize; i++) {
			double[] array = new double[solSize];
			for (int j = 0; j < solSize; j++) {
				array[j] = rand.nextDouble() * (max - min) + min;
			}
			solutions[i] = new Solution(array, false);
		}
		return new Population(solutions, false);
	}

	@Override
	public Solution run() {
		for (int i = 0; i < maxIter; i++) {
			population.updateFitness(function);
			population.sortSolutions();
			System.out.println("Generation: " + (i + 1));
//			System.out.println(population.toString());
			System.out.println(population.getSolution(popSize - 1));
			System.out.println(population.getSolution(popSize - 1).getFitness());
//			System.out.println(population.getSolution(popSize / 2));
//			System.out.println(population.getSolution(popSize / 2).getFitness());
			System.out.println("****************************************");
			Solution sol = population.getSolution(popSize - 1);
			if (function.valueAt(sol.getSolution(false)) <= minErrVal) {
				return sol;
			}
			int n = popSize - 2;
			Solution[] newGeneration = new Solution[popSize];
			newGeneration[popSize - 1] = population.getSolution(popSize - 1);
			newGeneration[popSize - 2] = population.getSolution(popSize - 2);
			for (int j = 0; j < n; j++) {
				Solution par1 = selection.select(population);
				Solution par2 = selection.select(population);
				Solution child = crossover.cross(par1, par2);
				child = mutation.mutate(child);
				newGeneration[j] = child;
			}
			population = new Population(newGeneration, false);
		}
		population.updateFitness(function);
		population.sortSolutions();
		return population.getSolution(popSize - 1);
	}
	
	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Greška!");
			System.exit(0);
		}
		String popSizeStr = args[0];
		String minValStr = args[1];
		String maxIterStr = args[2];
		String selectionStr = args[3];
		String sigmaStr = args[4];
		
		int popSize = Integer.parseInt(popSizeStr);
		double minErrVal = Double.parseDouble(minValStr);
		int maxIter = Integer.parseInt(maxIterStr);
		double sigma = Double.parseDouble(sigmaStr);
		
		ISelection selection = getSelection(selectionStr);
		IFunction function = getFunction(FILE_PATH);
		IMutation mutation = new NormalMutation(sigma);
		ICrossover crossing = new BLXalpha(ALPHA);
		
		Solution sol = new GeneticAlgorithm(function, selection, mutation, crossing, minErrVal, maxIter, popSize, SOLUTION_SIZE, null).run();
		System.out.println("Rješenje: " + sol);
		System.out.println("Greška: " + function.valueAt(sol.getSolution(false)));
	}
	
	private static ISelection getSelection(String selectionStr) {
		selectionStr = selectionStr.trim();
		if (selectionStr.equalsIgnoreCase("rouletteWheel")) {
			return new RouletteWheel();
		}
		String[] parts = selectionStr.split(":");
		if (!parts[0].equalsIgnoreCase("tournament")) {
			throw new IllegalArgumentException();
		}
		int n = Integer.parseInt(parts[1]);
		return new Tournament(n);
	}

	private static IFunction getFunction(String filePath) {
		List<String> lines = null;
		try {
			Path path = Paths.get(filePath);
			Charset c = StandardCharsets.ISO_8859_1;
			lines = Files.readAllLines(path, c);
		} catch (IOException e) {
			System.out.println("Nedostaje datoteka!");
			System.exit(0);
		}
		
		List<Funkcija> equations = parseFile(lines);
		Funkcija[] equationsArray = new Funkcija[equations.size()];
		equationsArray = equations.toArray(equationsArray);
		
		return new Sustav(equationsArray);
	}

	private static List<Funkcija> parseFile(List<String> lines) {
		List<Funkcija> equations = new ArrayList<>();
		for (String line : lines) {
			if (line.startsWith("#")) {
				continue;
			}
			String str = line.trim().substring(1, line.length() - 1);
			String[] koefsStr = str.split(",");
			Funkcija equation = getKoefs(koefsStr);
			equations.add(equation);
		}
		return equations;
	}
	
	private static Funkcija getKoefs(String[] koefsStr) {
		int length = koefsStr.length - 1;
		double[] koefs = new double[length];
		for (int i = 0; i < length; i++) {
			koefs[i] = Double.parseDouble(koefsStr[i].trim());
		}
		double solution = Double.parseDouble(koefsStr[koefsStr.length - 1].trim());
		return new Funkcija(koefs, solution);
	}

}
