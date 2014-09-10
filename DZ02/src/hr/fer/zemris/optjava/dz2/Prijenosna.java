package hr.fer.zemris.optjava.dz2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Prijenosna {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Greška!");
			System.exit(0);
		}
		String method = args[0];
		int maxIter = Integer.parseInt(args[1]);
		String filePath = args[2];
		
		List<String> lines = null;
		try {
			Path path = Paths.get(filePath);
			Charset c = StandardCharsets.ISO_8859_1;
			lines = Files.readAllLines(path, c);
		} catch (IOException e) {
			System.out.println("Nedostaje datoteka!");
			System.exit(0);
		}
		List<PrijenosnaFja> equations = parseFile(lines);
		PrijenosnaFja[] equationsArray = new PrijenosnaFja[equations.size()];
		equationsArray = equations.toArray(equationsArray);
		IHFunction function = new PrijenosnaSustav(equationsArray);
		int num = 6;
		IVector first = randomVector(num);
		IVector solution = null;
		boolean print = false;
		switch (method) {
			case "grad": solution = NumOptAlgorithms.gradientDescent(function, maxIter, first, print); break;
			case "newton": solution = NumOptAlgorithms.newtonMethod(function, maxIter, first, print); break;
			default: break;
		}
		System.out.println("Rješenje: " + solution.toString());
		System.out.println("Greška: " + function.getValue(solution));
	}
	
	private static List<PrijenosnaFja> parseFile(List<String> lines) {
		List<PrijenosnaFja> equations = new ArrayList<>();
		for (String line : lines) {
			if (line.startsWith("#")) {
				continue;
			}
			String str = line.trim().substring(1, line.length() - 1);
			String[] koefsStr = str.split(",");
			PrijenosnaFja equation = getKoefs(koefsStr);
			equations.add(equation);
		}
		return equations;
	}

	private static PrijenosnaFja getKoefs(String[] koefsStr) {
		int length = koefsStr.length - 1;
		double[] koefs = new double[length];
		for (int i = 0; i < length; i++) {
			koefs[i] = Double.parseDouble(koefsStr[i].trim());
		}
		double solution = Double.parseDouble(koefsStr[koefsStr.length - 1].trim());
		return new PrijenosnaFja(koefs, solution);
	}
	
	private static IVector randomVector(int variableNum) {
		Random rand = new Random();
		double[] array = new double[variableNum];
		for (int i = 0; i < variableNum; i++) {
			array[i] = rand.nextDouble() * 10 - 5;
		}
		return new Vector(array);
	}

}
