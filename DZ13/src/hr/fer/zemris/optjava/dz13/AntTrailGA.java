package hr.fer.zemris.optjava.dz13;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import hr.fer.zemris.optjava.dz13.alg.GP;
import hr.fer.zemris.optjava.dz13.alg.IOptAlgorithm;
import hr.fer.zemris.optjava.dz13.cross.BreakpointCrossover;
import hr.fer.zemris.optjava.dz13.cross.ICrossover;
import hr.fer.zemris.optjava.dz13.model.AntTrailMap;
import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Node;
import hr.fer.zemris.optjava.dz13.model.Population;
import hr.fer.zemris.optjava.dz13.model.Solution;
import hr.fer.zemris.optjava.dz13.model.eval.Evaluator;
import hr.fer.zemris.optjava.dz13.model.eval.IEvaluator;
import hr.fer.zemris.optjava.dz13.model.sel.ISelection;
import hr.fer.zemris.optjava.dz13.model.sel.TournamentSelection;
import hr.fer.zemris.optjava.dz13.mut.IMutation;
import hr.fer.zemris.optjava.dz13.mut.SwitchMutation;
import hr.fer.zemris.optjava.dz13.visual.DisplayFrame;

public class AntTrailGA {

	private static final double PLAGIARY_PUNISHMENT = 0.9;
	private static final int N_TOURNAMENT = 7;
	private static final int MAX_DEPTH_INIT = 6;
	private static final int MAX_DEPTH = 20;
	private static final int MAX_NODES = 200;
	private static final int MAX_STEPS = 600;

	public static void main(String[] args) {
		if (args.length != 5) {
			System.err.println("Error!");
			System.exit(0);
		}
		String mapPath = args[0];
		int maxiter = Integer.parseInt(args[1]);
		int popSize = Integer.parseInt(args[2]);
		int minFit = Integer.parseInt(args[3]);
		String bestPath = args[4];
		AntTrailMap trailMap = parseMap(mapPath);
		IEvaluator evaluator = new Evaluator(new MapPosition(trailMap, MAX_STEPS));
		ISelection selection = new TournamentSelection(N_TOURNAMENT);
		ICrossover crossover = new BreakpointCrossover(MAX_DEPTH, MAX_NODES);
		IMutation mutation = new SwitchMutation(MAX_DEPTH, MAX_NODES);
		Population initPop = initializePopulation(evaluator, popSize, MAX_DEPTH_INIT, MAX_NODES);
		IOptAlgorithm alg = new GP(maxiter, initPop.size(), minFit, initPop, evaluator, selection, crossover, mutation, PLAGIARY_PUNISHMENT);
		Solution sol = alg.runAlgorithm();
		writeToFile(bestPath, sol);
		
		MapPosition position = new MapPosition(trailMap, MAX_STEPS);
		while (sol.getRoot().visit(position));
		display(position);
	}

	private static void display(final MapPosition position) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				startGUI(position);
			}
		});
	}

	protected static void startGUI(MapPosition position) {
		new DisplayFrame(position).setVisible(true);
	}

	private static Population initializePopulation(IEvaluator evaluator, int popSize, int maxDepth, int maxNodes) {
		List<Solution> sols = new ArrayList<>(popSize);
		int num = popSize / (maxDepth - 1) / 2;
		Node root;
		Solution sol;
		for (int i = 2; i <= maxDepth; i++) {
			for (int j = 0; j < num; j++) {
//				root = AntTrailUtils.generateRandomTree(null, i, maxNodes, true);
				root = AntTrailUtils.generateInitialTree(maxDepth, maxNodes, true);
				sol = new Solution(root);
				evaluator.evaluate(sol);
				sols.add(sol);
//				root = AntTrailUtils.generateRandomTree(null, i, maxNodes, false);
				root = AntTrailUtils.generateInitialTree(maxDepth, maxNodes, false);
				sol = new Solution(root);
				evaluator.evaluate(sol);
				sols.add(sol);
			}
		}
		return new Population(sols);
	}

	private static AntTrailMap parseMap(String mapPath) {
		Path path = Paths.get(mapPath);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] wh = lines.get(0).split("x");
		int w = Integer.parseInt(wh[0]);
		int h = Integer.parseInt(wh[1]);
		boolean[][] map = new boolean[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				char c = lines.get(i + 1).charAt(j);
				map[i][j] = (c == '1' ? true : false);
			}
		}
		return new AntTrailMap(map);
	}
	
	private static void writeToFile(String filePath, Solution sol) {
		try {
			FileWriter fw = new FileWriter(filePath);
			fw.write(sol.getRoot().toString() + "\nFitness: " + sol.getFitness() + "\nNodes count: " + sol.getRoot().getSize());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
