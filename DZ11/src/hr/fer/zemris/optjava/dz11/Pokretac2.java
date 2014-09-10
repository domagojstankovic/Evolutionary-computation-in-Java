package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.Population;
import hr.fer.zemris.generic.ga.Solution;
import hr.fer.zemris.generic.ga.alg.GA2;
import hr.fer.zemris.generic.ga.alg.IOptAlgorithm;
import hr.fer.zemris.generic.ga.crossover.ICrossover;
import hr.fer.zemris.generic.ga.crossover.UniformCrossover;
import hr.fer.zemris.generic.ga.img.ImageProvider;
import hr.fer.zemris.generic.ga.img.ThreadLocalImageProvider;
import hr.fer.zemris.generic.ga.mutation.IMutation;
import hr.fer.zemris.generic.ga.mutation.NormalMutation;
import hr.fer.zemris.generic.ga.selection.ISelection;
import hr.fer.zemris.generic.ga.selection.Tournament;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pokretac2 {

	private static final int N = 2;
	private static final Random rand = new Random();
	
	public static void main(String[] args) {
		if (args.length != 7) {
			System.out.println("Error!");
			System.exit(0);
		}
		String origPath = args[0];
		int sqrNum = Integer.parseInt(args[1]);
		int popSize = Integer.parseInt(args[2]);
		int maxiter = Integer.parseInt(args[3]);
		double minFit = Double.parseDouble(args[4]);
		String optParPath = args[5];
		String genImgPath = args[6];

		File origImgFile = new File(origPath);
		GrayScaleImage template = null;
		try {
			template = GrayScaleImage.load(origImgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int imgWidth = template.getWidth();
		int imgHeight = template.getHeight();
		ImageProvider imgProvider = new ThreadLocalImageProvider(imgWidth, imgHeight);
//		ImageProvider imgProvider = new ThreadBoundImageProvider();
		Evaluator evaluator = new Evaluator(template, imgProvider);
		Population pop = initPop(popSize, sqrNum, imgWidth, imgHeight);
		ISelection selection = new Tournament(N);
		ICrossover crossover = new UniformCrossover();
//		double probability = 0.02;
//		IMutation mutation = new ProbabilityMutation(probability, imgWidth, imgHeight);
		double sigma = 5;
		IMutation mutation = new NormalMutation(sigma, imgWidth, imgHeight);

		IOptAlgorithm alg = new GA2(maxiter, pop, minFit, popSize, imgWidth, imgHeight, selection, crossover, mutation,
				evaluator);
		GASolution<int[]> sol = alg.runAlg();
		System.out.println("The end!");

		File genImgFile = new File(genImgPath);
		GrayScaleImage genImg = evaluator.draw(sol, null);
		try {
			genImg.save(genImgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter fw = new FileWriter(optParPath);
			fw.write(sol.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Population initPop(int popSize, int sqrNum, int imgWidth, int imgHeight) {
		List<GASolution<int[]>> sols = new ArrayList<>(popSize);
		Random rand = new Random();
		for (int i = 0; i < popSize; i++) {
			int[] data = new int[1 + 5 * sqrNum];
			data[0] = rand.nextInt(256);
			for (int j = 0; j < sqrNum; j++) {
				data[5 * j + 1] = rand.nextInt(imgWidth);
				data[5 * j + 2] = rand.nextInt(imgHeight);
				data[5 * j + 3] = rand.nextInt(imgWidth - data[5 * j + 1]) + 1;
				data[5 * j + 4] = rand.nextInt(imgHeight - data[5 * j + 2]) + 1;
				data[5 * j + 5] = rand.nextInt(256);
			}
			sols.add(new Solution(data));
		}
		return new Population(sols);
	}
	
	private static Population initPop2(int popSize, int sqrNum, int imgWidth, int imgHeight) {
		List<GASolution<int[]>> sols = new ArrayList<>(popSize);
		for (int i = 0; i < popSize; i++) {
			int[] data = new int[1 + 5 * sqrNum];
			data[0] = rand.nextInt(256);
			for (int j = 0; j < sqrNum; j++) {
				data[5 * j + 1] = rand.nextInt(imgWidth);
				data[5 * j + 2] = rand.nextInt(imgHeight);
				data[5 * j + 3] = depSize(imgWidth - data[5 * j + 1], i, popSize) + 1;
				data[5 * j + 4] = depSize(imgHeight - data[5 * j + 2], i, popSize) + 1;
				data[5 * j + 5] = rand.nextInt(256);
			}
			sols.add(new Solution(data));
		}
		return new Population(sols);
	}
	
	private static int depSize(int maxVal, int i, int size) {
		double temp = (1 - (double) i / size) * 1.1;
		int pom = rand.nextInt(maxVal);
		return (int) (temp * pom);
	}

}
