package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz10.algorithm.NSGAII;
import hr.fer.zemris.optjava.dz10.crossover.ICrossover;
import hr.fer.zemris.optjava.dz10.crossover.UniformCrossover;
import hr.fer.zemris.optjava.dz10.function.F1;
import hr.fer.zemris.optjava.dz10.function.F2;
import hr.fer.zemris.optjava.dz10.function.IFunction;
import hr.fer.zemris.optjava.dz10.function.Square;
import hr.fer.zemris.optjava.dz10.model.Population;
import hr.fer.zemris.optjava.dz10.model.Solution;
import hr.fer.zemris.optjava.dz10.mutation.IMutation;
import hr.fer.zemris.optjava.dz10.mutation.NormalMutation;
import hr.fer.zemris.optjava.dz10.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz10.problem.Problem;
import hr.fer.zemris.optjava.dz10.selection.CrowdingSelectionTournament;
import hr.fer.zemris.optjava.dz10.selection.ISelection;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MOOP {
	
	private static final Random rand = new Random();
	private static final double MIN = -1;
	private static final double MAX = 1;
	private static final String D_FILE = "izlaz-dec.txt";
	private static final String O_FILE = "izlaz-obj.txt";
	
	public static void main(String[] args) {
		if (args.length != 4) {
			System.err.println("Error!");
			System.exit(0);
		}
		String fja = args[0];
		int n = Integer.parseInt(args[1]);
		int maxiter = Integer.parseInt(args[2]);
		double sigma = Double.parseDouble(args[3]);
		
		int qSize = n;
		Population population = null;
		int solSize = 0;
		int fitSize = 0;
		ISelection selection = new CrowdingSelectionTournament(3);
		ICrossover crossover = new UniformCrossover();
		IMutation mutation = new NormalMutation(sigma);
		IFunction[] objectives = null;
		double[] lbound = null;
		double[] ubound = null;
		if (fja.equals("1")) {
			solSize = 4;
			fitSize = 4;
			lbound = new double[4];
			ubound = new double[4];
			for (int i = 0; i < 4; i++) {
				lbound[i] = -5;
				ubound[i] = 5;
			}
			objectives = new IFunction[4];
			for (int i = 0; i < 4; i++) {
				objectives[i] = new Square(i);
			}
		} else if (fja.equals("2")) {
			solSize = 2;
			fitSize = 2;
			lbound = new double[2];
			ubound = new double[2];
			lbound[0] = 0.1;
			ubound[0] = 1;
			lbound[1] = 0;
			ubound[1] = 5;
			objectives = new IFunction[2];
			objectives[0] = new F1();
			objectives[1] = new F2();
		} else {
			System.err.println("Error!");
			System.exit(0);
		}
		population = initPopulation(n, solSize, fitSize);
		MOOPProblem problem = new Problem(objectives, lbound, ubound, true);
		List<List<Solution>> fronts = new NSGAII(n, qSize , maxiter, population, selection, crossover, mutation, problem).run();
		printFronts(fronts);
	}
	
	public static void printFronts(List<List<Solution>> fronts) {
		try {
			FileWriter fw = new FileWriter(D_FILE);
			for (List<Solution> front : fronts) {
				for (Solution sol : front) {
					fw.append(sol.xToString() + "\n");
				}
				fw.append("------------------\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileWriter fw = new FileWriter(O_FILE);
			for (List<Solution> front : fronts) {
				for (Solution sol : front) {
					fw.append(sol.fitToString() + "\n");
				}
				fw.append("------------------\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("The End :)");
	}
	
	private static Population initPopulation(int n, int solSize, int fitSize) {
		List<Solution> list = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			list.add(initSolution(solSize, fitSize));
		}
		return new Population(list);
	}

	private static Solution initSolution(int solSize, int fitSize) {
		double[] x = new double[solSize];
		for (int i = 0; i < solSize; i++) {
			x[i] = rand.nextDouble() * (MAX - MIN) + MIN;
		}
		return new Solution(x, fitSize);
	}
	
}
