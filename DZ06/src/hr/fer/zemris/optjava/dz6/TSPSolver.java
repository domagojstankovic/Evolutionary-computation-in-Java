package hr.fer.zemris.optjava.dz6;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TSPSolver {

	private static final double ALPHA = 1;
	private static final double BETA = 2;
	private static final double RO = 0.02;

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Error");
			System.exit(0);
		}
		String file = args[0];
		int k = Integer.parseInt(args[1]);
		int l = Integer.parseInt(args[2]);
		int maxIter = Integer.parseInt(args[3]);
		
		List<Position> positions = getPositions(file);
		int size = positions.size();
		double[][] distance = new double[size][size];
		double[][] eta = new double[size][size];
		fillMatrices(positions, BETA, distance, eta);
		
		Ant best = new MMAS(size, k, l, maxIter, distance, eta, ALPHA, RO).run();
		System.out.println("------------------------------------------------");
		System.out.println("Best solution:");
		System.out.println(best.toString());
		System.out.println("Total distance:");
		System.out.println(best.getTotalDistance());
	}
	
	private static void fillMatrices(List<Position> positions, double beta, double[][] distances, double[][] eta) {
		int size = positions.size();
		for (int i = 0; i < size - 1; i++) {
			distances[i][i] = 0;
			eta[i][i] = Double.POSITIVE_INFINITY;
			for (int j = i + 1; j < size; j++) {
				double distance = positions.get(i).getDistance(positions.get(j));
				distances[i][j] = distances[j][i] = distance;
				eta[i][j] = eta[j][i] = 1 / Math.pow(distance, beta);
			}
		}
	}
	
	private static List<Position> getPositions(String filePath) {
		Path path = Paths.get(filePath);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Error");
			System.exit(0);
		}
		boolean positionsStarted = false;
		List<Position> positions = new ArrayList<>();
		for (String line : lines) {
			if (!positionsStarted) {
				if (line.startsWith("NODE_COORD_SECTION") || line.startsWith("DISPLAY_DATA_SECTION")) {
					positionsStarted = true;
				}
				continue;
			}
			if (line.startsWith("EOF")) {
				return positions;
			}
			String[] parts = line.split("\\s+");
			double x = Double.parseDouble(parts[1]);
			double y = Double.parseDouble(parts[2]);
			positions.add(new Position(x, y));
		}
		return positions;
	}
	
}
