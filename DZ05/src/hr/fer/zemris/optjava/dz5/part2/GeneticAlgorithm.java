package hr.fer.zemris.optjava.dz5.part2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GeneticAlgorithm {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Error!");
			System.exit(0);
		}
		
		String filePath = args[0];
		int unitNum = Integer.parseInt(args[1]);
		int subPopNum = Integer.parseInt(args[2]);
		
		Task task = parseFile(filePath);
		
		Permutation solution = new SASEGASA(unitNum, subPopNum, task).run();
		System.out.println("-----------------------------------------");
		System.out.println("Solution: " + solution.toString());
		System.out.println("Fitness: " + (-solution.getFitness()));
		
	}

	private static Task parseFile(String filePath) {
		Path path = Paths.get(filePath);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return extractTask(lines);
	}

	private static Task extractTask(List<String> lines) {
		int n = Integer.parseInt(lines.get(0).trim());
		int[][] distances = new int[n][n];
		for (int i = 0; i < n; i++) {
			parseLine(lines.get(i + 2), distances, i);
		}
		int[][] cost = new int[n][n];
		for (int i = 0; i < n; i++) {
			parseLine(lines.get(i + n + 3), cost, i);
		}
		return new Task(n, distances, cost);
	}

	private static void parseLine(String line, int[][] matrix, int row) {
		String[] parts = line.split("\\s+");
		int i = 0;
		for (String part : parts) {
			if (part.trim().isEmpty()) {
				continue;
			}
			matrix[row][i] = Integer.parseInt(part);
			i++;
		}
	}
	
}
