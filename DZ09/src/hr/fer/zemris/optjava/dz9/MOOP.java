package hr.fer.zemris.optjava.dz9;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithm.IOptAlgorithm;
import hr.fer.zemris.optjava.dz9.algorithm.NSGA;
import hr.fer.zemris.optjava.dz9.crossover.ICrossover;
import hr.fer.zemris.optjava.dz9.crossover.UniformCrossover;
import hr.fer.zemris.optjava.dz9.distance.DecisionDistance;
import hr.fer.zemris.optjava.dz9.distance.IDistance;
import hr.fer.zemris.optjava.dz9.distance.ObjectiveDistance;
import hr.fer.zemris.optjava.dz9.function.F1;
import hr.fer.zemris.optjava.dz9.function.F2;
import hr.fer.zemris.optjava.dz9.function.IFunction;
import hr.fer.zemris.optjava.dz9.function.SH;
import hr.fer.zemris.optjava.dz9.function.Square;
import hr.fer.zemris.optjava.dz9.model.Population;
import hr.fer.zemris.optjava.dz9.model.Solution;
import hr.fer.zemris.optjava.dz9.mutation.IMutation;
import hr.fer.zemris.optjava.dz9.mutation.NormalMutation;
import hr.fer.zemris.optjava.dz9.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz9.problem.Problem;
import hr.fer.zemris.optjava.dz9.selection.ISelection;
import hr.fer.zemris.optjava.dz9.selection.RouletteWheel;

public class MOOP {
	
	private static final Random rand = new Random();
	private static final double MIN = -1;
	private static final double MAX = 1;
	private static final double ALPHA = 2;
	private static final double M_FACTOR = 0.99;
	private static final String D_FILE = "izlaz-dec.txt";
	private static final String O_FILE = "izlaz-obj.txt";

	public static void main(String[] args) {
		if (args.length != 6) {
			System.err.println("Parameters error!");
			System.exit(0);
		}
		String fja = args[0];
		int n = Integer.parseInt(args[1]);
		String vrsta = args[2];
		int maxiter = Integer.parseInt(args[3]);
		double oshare = Double.parseDouble(args[4]);
		double sigma = Double.parseDouble(args[5]);
		MOOPProblem problem = null;
		int decisionSize = 0;
		int objectiveSize = 0;
		if (fja.equals("1")) {
			decisionSize = 4;
			objectiveSize = 4;
			IFunction[] objectives = new IFunction[4];
			for (int i = 0; i < 4; i++) {
				objectives[i] = new Square(i);
			}
			double[] lbound = new double[4];
			for (int i = 0; i < 4; i++) {
				lbound[i] = -5;
			}
			double[] ubound = new double[4];
			for (int i = 0; i < 4; i++) {
				ubound[i] = 5;
			}
			problem = new Problem(objectives, lbound, ubound, true);
		} else if (fja.equals("2")) {
			decisionSize = 2;
			objectiveSize = 2;
			IFunction[] objectives = new IFunction[2];
			objectives[0] = new F1();
			objectives[1] = new F2();
			double[] lbound = new double[2];
			lbound[0] = 0.1;
			lbound[1] = 0.0;
			double[] ubound = new double[2];
			ubound[0] = 1;
			ubound[1] = 5;
			problem = new Problem(objectives, lbound, ubound, true);
		} else {
			System.err.println("Error!");
			System.exit(0);
		}
		IDistance distance = null;
		if (vrsta.equalsIgnoreCase("decision-space")) {
			distance = new DecisionDistance(decisionSize);
		} else if (vrsta.equalsIgnoreCase("objective-space")) {
			distance = new ObjectiveDistance(objectiveSize);
		} else {
			System.err.println("Error!");
			System.exit(0);
		}
		ISelection selection = new RouletteWheel();
		ICrossover crossover = new UniformCrossover();
		IMutation mutation = new NormalMutation(sigma);
		SH sh = new SH(oshare, ALPHA);
		IOptAlgorithm alg = new NSGA(selection, crossover, mutation, problem, distance , sh, initPopulation(n, decisionSize, objectiveSize), maxiter, n, M_FACTOR);
		List<List<Solution>> fronts = alg.run();
		printFronts(fronts);
	}
	
	public static void printFronts(List<List<Solution>> fronts) {
//		int i = 0;
//		for (List<Solution> front : fronts) {
//			i++;
//			System.out.println("Front " + i + ":");
//			for (Solution sol : front) {
//				System.out.println("  " + sol.toString());
//			}
//		}
		
		
//		System.out.println("Decision:");
//		for (List<Solution> front : fronts) {
//			i++;
//			System.out.println("Front " + i + ":");
//			for (Solution sol : front) {
//				System.out.println("  " + sol.xToString());
//			}
//		}
//		System.out.println();
//		System.out.println("******************************************");
//		System.out.println("Objective:");
//		i = 0;
//		for (List<Solution> front : fronts) {
//			i++;
//			System.out.println("Front " + i + ":");
//			for (Solution sol : front) {
//				System.out.println("  " + sol.fitToString());
//			}
//		}
		
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
