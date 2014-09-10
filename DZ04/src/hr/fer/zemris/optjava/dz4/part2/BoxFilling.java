package hr.fer.zemris.optjava.dz4.part2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class BoxFilling {
	
	// Primjer dobrih argumenata:
	// kutije/problem-20-50-5.dat 100 3 3 true 100 50 2
	
	public static void main(String[] args) {
		if (args.length != 8) {
			System.out.println("Greška!");
			System.exit(0);
		}
		String filePath = args[0];
		int popSize = Integer.parseInt(args[1]);
		int n = Integer.parseInt(args[2]);
		int m = Integer.parseInt(args[3]);
		boolean p = Boolean.parseBoolean(args[4]);
		int maxIter = Integer.parseInt(args[5]);
		int okSize = Integer.parseInt(args[6]);
		int mutationNumber = Integer.parseInt(args[7]);
		
		int slash = filePath.lastIndexOf("/");
		int backslash = filePath.lastIndexOf("\\");
		int index = slash < backslash ? backslash : slash;
		
		String[] parts = filePath.substring(index + 1).split("-");
		int height = Integer.parseInt(parts[1]);
		int length = Integer.parseInt(parts[2]);
		
		List<Integer> sticks = parseFile(filePath);
		Solution[] sols = new Solution[popSize];
		for (int i = 0; i < popSize; i++) {
			sols[i] = new Solution(sticks, height);
		}
		Population population = new Population(sols);
		population.sortSolutions();
		ISelection selN = new Tournament(n);
		ISelection selM = new Tournament(m);
		ICrossover crossover = new BPCX();
		IMutation mutation = new CustomMutation(mutationNumber);
		
		Solution solution = new BinPacking(selN, selM, crossover, mutation, maxIter, population, okSize, p).run();
		
		System.out.println("*******************");
		System.out.println("Rješenje: ");
		System.out.print(solution.toString());
		System.out.println("Greška: " + solution.getSize() + " - " + length + " = " + (solution.getSize() - length));
	}

	private static List<Integer> parseFile(String filePath) {
		Path path = Paths.get(filePath);
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			System.out.println("Datoteka ne postoji!");
			System.exit(0);
		}
		String line = new String(bytes, StandardCharsets.ISO_8859_1).trim();
		line = line.substring(1, line.length() - 1);
		String[] parts = line.split(",");
		int size = parts.length;
		List<Integer> stickSizes = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			stickSizes.add(Integer.valueOf(parts[i].trim()));
		}
		return stickSizes;
	}
	
}
