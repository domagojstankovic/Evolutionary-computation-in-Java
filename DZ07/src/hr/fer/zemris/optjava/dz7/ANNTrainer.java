package hr.fer.zemris.optjava.dz7;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.optjava.dz7.model.Dataset;
import hr.fer.zemris.optjava.dz7.model.FFANN;
import hr.fer.zemris.optjava.dz7.model.Particle;
import hr.fer.zemris.optjava.dz7.model.Sample;
import hr.fer.zemris.optjava.dz7.model.SigmoidTransferFunction;
import hr.fer.zemris.optjava.dz7.model.Solution;
import hr.fer.zemris.optjava.dz7.model.TournamentSelection;
import hr.fer.zemris.optjava.dz7.model.interfaces.IOptAlgorithm;
import hr.fer.zemris.optjava.dz7.model.interfaces.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.model.interfaces.ISelection;
import hr.fer.zemris.optjava.dz7.model.interfaces.ITransferFunction;

public class ANNTrainer {

	private static final int TOUR_N = 3;
	private static final int D = 10;
	private static final int BETA = 10;
	
	public static void main(String[] args) {
		if (args.length != 5) {
			System.err.println("Error!");
			System.exit(0);
		}
		String file = args[0];
		String alg = args[1];
		int n = Integer.parseInt(args[2]);
		double merr = Double.parseDouble(args[3]);
		int maxiter = Integer.parseInt(args[4]);
		IReadOnlyDataset dataset = loadData(file);
		int[] layerCount = new int[]{4, 3, 3};
		int size = layerCount.length - 1;
		ITransferFunction[] transferFunctions = new ITransferFunction[size];
		for (int i = 0; i < size; i++) {
			transferFunctions[i] = new SigmoidTransferFunction();
		}
		FFANN ffann = new FFANN(layerCount, transferFunctions, dataset);
		IOptAlgorithm algorithm = null;
		if (alg.equalsIgnoreCase("clonalg")) {
			ISelection selection = new TournamentSelection(TOUR_N);
			ClonAlg algor = new ClonAlg(ffann, n, D, BETA, false, merr, maxiter, ClonAlg.initPop(n, ffann.getWeightsCount()), selection, ffann.getWeightsCount());
			Solution sol = algor.run();
			ffann.calcError(sol.getWeights());
			System.out.println("Error: " + Math.abs(sol.getFitness()));
			System.out.println("Match count: " + ffann.getOkSum() + "/" + dataset.numberOfSamples());
			System.out.println(sol.toString());
			System.exit(0);
		} else if (alg.equalsIgnoreCase("pso-a")) {
			algorithm = new PSO_A(PSO.createInitialPopulation(n, ffann.getWeightsCount()), n, merr, maxiter, ffann.getWeightsCount(), ffann, false);
		} else if (alg.startsWith("pso-b-")) {
			int d = Integer.parseInt(alg.substring(6));
			algorithm = new PSO_B(PSO.createInitialPopulation(n, ffann.getWeightsCount()), n, merr, maxiter, ffann.getWeightsCount(), ffann, false, d);
		} else {
			System.err.println("Error! Illegall algorithm!");
			System.exit(0);
		}
		Particle sol = algorithm.run();
		ffann.calcError(sol.getBestPosition());
		System.out.println("Error: " + Math.abs(sol.getBestFitness()));
//		System.out.println("Error: " + ffann.calcError(sol.getBestPosition()));
		System.out.println("Match count: " + ffann.getOkSum() + "/" + dataset.numberOfSamples());
		System.out.println(sol.toString());
	}
	
	private static Dataset loadData(String fileName) {
		Path path = Paths.get(fileName);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			System.err.println("Error reading file!");
			System.exit(0);
		}
		int size = lines.size();
		Sample[] samples = new Sample[size];
		for (int i = 0; i < size; i++) {
			samples[i] = parseLine(lines.get(i));
		}
		return new Dataset(samples);
	}

	private static Sample parseLine(String line) {
		String[] parts = line.split(":");
		String[] inputParts = parts[0].substring(1, parts[0].length() - 1).split(",");
		String[] outputParts = parts[1].substring(1, parts[1].length() - 1).split(",");
		int inputSize = inputParts.length;
		double[] input = new double[inputSize];
		for (int i = 0; i < inputSize; i++) {
			input[i] = Double.parseDouble(inputParts[i]);
		}
		int outputSize = outputParts.length;
		int[] output = new int[outputSize];
		for (int i = 0; i < outputSize; i++) {
			output[i] = Integer.parseInt(outputParts[i]);
		}
		return new Sample(input, output);
	}

}
