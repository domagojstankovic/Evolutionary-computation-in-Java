package hr.fer.zemris.optjava.dz8;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz8.model.Dataset;
import hr.fer.zemris.optjava.dz8.model.ElmanNN;
import hr.fer.zemris.optjava.dz8.model.HyperbolicTangent;
import hr.fer.zemris.optjava.dz8.model.Population;
import hr.fer.zemris.optjava.dz8.model.Sample;
import hr.fer.zemris.optjava.dz8.model.Solution;
import hr.fer.zemris.optjava.dz8.model.Strategy;
import hr.fer.zemris.optjava.dz8.model.TDNN;
import hr.fer.zemris.optjava.dz8.model.crossovers.ExponentialCrossover;
import hr.fer.zemris.optjava.dz8.model.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz8.model.interfaces.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.model.interfaces.ITransferFunction;
import hr.fer.zemris.optjava.dz8.model.strategies.DEbest1;
import hr.fer.zemris.optjava.dz8.model.strategies.DErand2;

public class ANNTrainer {

	private static final double MIN = -1;
	private static final double MAX = 1;
	private static final int LIMIT = 600;
	private static final double F = 1.4;
	private static final double CR = 0.45;
	private static final double LOWER_BOUND = -10;
	private static final double UPPER_BOUND = 10;
	
	public static void main(String[] args) {
		if (args.length != 5) {
			System.err.println("Error!");
			System.exit(0);
		}
		String file = args[0];
		String net = args[1];
		int n = Integer.parseInt(args[2]);
		double merr = Double.parseDouble(args[3]);
		int maxiter = Integer.parseInt(args[4]);
		String[] parts = net.split("-");
		String[] count = parts[1].split("x");
		int sz = count.length;
		int[] layers = new int[sz];
		for (int i = 0; i < sz; i++) {
			layers[i] = Integer.parseInt(count[i]);
		}
		
		if (parts[0].equalsIgnoreCase("tdnn")) { // ***TDNN***
			IReadOnlyDataset dataset = loadData(file, layers[0], LIMIT);
			ITransferFunction[] tf = new ITransferFunction[sz - 1];
			for (int i = 0; i < sz - 1; i++) {
				tf[i] = new HyperbolicTangent();
			}
			TDNN tdnn = new TDNN(layers, tf, dataset);
			int weightsCount = tdnn.getWeightsCount();
			// ***
			Strategy strategy = new DErand2(F);
			// ***
			ICrossover crossover = new ExponentialCrossover(CR);
			double[] lb = new double[weightsCount];
			double[] ub = new double[weightsCount];
			Arrays.fill(lb, LOWER_BOUND);
			Arrays.fill(ub, UPPER_BOUND);
			Solution lowerBound = new Solution(lb);
			Solution upperBound = new Solution(ub);
			Solution sol = new DE(tdnn, n, merr, maxiter, false, initPop(n, weightsCount), strategy, crossover, lowerBound, upperBound).run();
			System.out.println("Error: " + Math.abs(sol.getFitness()));
			System.out.println(sol.toString());
		} else if (parts[0].equalsIgnoreCase("elman")) { // ***ELMAN***
			if (layers[0] != 1 || layers[sz - 1] != 1) {
				System.err.println("NN invalid input and output!");
				System.exit(0);
			}
			IReadOnlyDataset dataset = loadData(file, LIMIT);
			ITransferFunction[] tf = new ITransferFunction[sz - 1];
			for (int i = 0; i < sz - 1; i++) {
				tf[i] = new HyperbolicTangent();
			}
			ElmanNN elman = new ElmanNN(layers, tf, dataset);
			int weightsCount = elman.getWeightsCount();
			// ***
			Strategy strategy = new DEbest1(F);
			// ***
			ICrossover crossover = new ExponentialCrossover(CR);
			int conSize = layers[1];
			int size = weightsCount + conSize * conSize + conSize;
			double[] lb = new double[size];
			double[] ub = new double[size];
			Arrays.fill(lb, LOWER_BOUND);
			Arrays.fill(ub, UPPER_BOUND);
			Solution lowerBound = new Solution(lb);
			Solution upperBound = new Solution(ub);
			Solution sol = new DE(elman, n, merr, maxiter, false, initPop(n, size), strategy, crossover, lowerBound, upperBound).run();
			System.out.println("Error: " + Math.abs(sol.getFitness()));
			System.out.println(sol.toString());
		} else { // ***ERROR***
			System.err.println("Net error!");
			System.exit(0);
		}
	}
	
	private static Population initPop(int n, int size) {
		Random rand = new Random();
		List<Solution> sols = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			double[] array = new double[size];
			for (int j = 0; j < size; j++) {
				array[j] = rand.nextDouble() * (MAX - MIN) + MIN;
			}
			sols.add(new Solution(array));
		}
		return new Population(sols);
	}
	
	private static IReadOnlyDataset loadData(String fileName, int limit) {
		return loadData(fileName, 1, limit);
	}
	
	private static IReadOnlyDataset loadData(String fileName, int inputNum, int limit) {
		Path path = Paths.get(fileName);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			System.err.println("Error reading file!");
			System.exit(0);
		}
		int size = lines.size();
		if (limit < 0 || limit > size) {
			limit = size;
		}
		double[] nums = new double[size];
		for (int i = 0; i < size; i++) {
			nums[i] = Double.parseDouble(lines.get(i).trim());
		}
		double max = findExtreme(nums, true);
		double min = findExtreme(nums, false);
		Sample[] samples = new Sample[limit];
		for (int i = 0; i < limit; i++) {
			double[] in = new double[inputNum];
			for (int j = 0; j < inputNum; j++) {
				in[j] = f(nums[j + i], max, min);
			}
			double[] out = new double[1];
			out[0] = f(nums[i + inputNum], max, min);
			samples[i] = new Sample(in, out);
		}
		return new Dataset(samples);
	}

	private static double findExtreme(double[] nums, boolean max) {
		double ext = nums[0];
		int n = nums.length;
		for (int i = 1; i < n; i++) {
			if (max) {
				if (nums[i] > ext) {
					ext = nums[i];
				}
			} else {
				if (nums[i] < ext) {
					ext = nums[i];
				}
			}
		}
		return ext;
	}
	
	private static double f(double x, double max, double min) {
		return (MAX - MIN) * (x - min) / (max - min) + MIN;
	}

}
