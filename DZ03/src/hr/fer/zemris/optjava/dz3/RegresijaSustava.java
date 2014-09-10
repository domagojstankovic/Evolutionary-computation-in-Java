package hr.fer.zemris.optjava.dz3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RegresijaSustava {

	private static final int MIN = -10;
	private static final int MAX = 10;
	private static final int VARIABLES_NUM = 6;
	private static final double ALPHA = 0.98;
	private static final double INITIAL_TEMP = 100;
	private static final int INNER_LIMIT = 1000;
	private static final int OUTER_LIMIT = 2000;
	private static final boolean MINIMIZE = true;
	private static final double DELTA = 0.1;
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Greška!");
			System.exit(0);
		}
		String filePath = args[0].trim();
		String method = args[1].trim();
		
		List<String> lines = null;
		try {
			Path path = Paths.get(filePath);
			Charset c = StandardCharsets.ISO_8859_1;
			lines = Files.readAllLines(path, c);
		} catch (IOException e) {
			System.out.println("Nedostaje datoteka!");
			System.exit(0);
		}
		
		List<Funkcija> equations = parseFile(lines);
		Funkcija[] equationsArray = new Funkcija[equations.size()];
		equationsArray = equations.toArray(equationsArray);
		
		IFunction function = new Sustav(equationsArray);
		double[] deltas = fillDeltas(VARIABLES_NUM, DELTA);
		ITempSchedule schedule = new GeometricTempSchedule(ALPHA, INITIAL_TEMP, INNER_LIMIT, OUTER_LIMIT);
		
		if (method.equalsIgnoreCase("decimal")) {
			IDecoder<DoubleArraySolution> decoder = new PassThroughDecoder();
			INeighborhood<DoubleArraySolution> neighborhood = new DoubleArrayUnifNeighborhood(deltas);
			DoubleArraySolution startWith = new DoubleArraySolution(VARIABLES_NUM);
			SingleObjectiveSolution sol = new SimulatedAnnealing<DoubleArraySolution>(decoder, neighborhood , startWith , function, schedule, MINIMIZE).run();
			System.out.println("Rješenje: " + sol);
			System.out.println("Greška: " + sol.value);
			return;
		}
		
		String[] parts = method.split(":");
		String algorithm = parts[0].trim();
		if (!algorithm.equalsIgnoreCase("binary")) {
			System.out.println("Greška! Zadani algoritam ne postoji!");
			System.exit(0);
		}
		int n = Integer.parseInt(parts[1]);
		IDecoder<BitvectorSolution> decoder = new NaturalBinaryDecoder(MIN, MAX, n, VARIABLES_NUM);
		INeighborhood<BitvectorSolution> neighborhood = new BitvectorUnifNeighborhood(VARIABLES_NUM);
		BitvectorSolution startWith = new BitvectorSolution(VARIABLES_NUM * n);
		SingleObjectiveSolution sol = new SimulatedAnnealing<BitvectorSolution>(decoder, neighborhood, startWith, function, schedule, MINIMIZE).run();
		BitvectorSolution bvsol = (BitvectorSolution) sol;
		System.out.println("Rješenje: " + sol + " " + arrayDisplay(decoder.decode(bvsol)));
		System.out.println("Greška: " + sol.value);
	}
	
	private static String arrayDisplay(double[] array) {
		StringBuilder sb = new StringBuilder("(");
		int size = array.length;
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(array[i]);
		}
		sb.append(")");
		return sb.toString();
	}
	
	private static double[] fillDeltas(int n, double delta) {
		double[] array = new double[n];
		for (int i = 0; i < n; i++) {
			array[i] = delta;
		}
		return array;
	}

	private static List<Funkcija> parseFile(List<String> lines) {
		List<Funkcija> equations = new ArrayList<>();
		for (String line : lines) {
			if (line.startsWith("#")) {
				continue;
			}
			String str = line.trim().substring(1, line.length() - 1);
			String[] koefsStr = str.split(",");
			Funkcija equation = getKoefs(koefsStr);
			equations.add(equation);
		}
		return equations;
	}
	
	private static Funkcija getKoefs(String[] koefsStr) {
		int length = koefsStr.length - 1;
		double[] koefs = new double[length];
		for (int i = 0; i < length; i++) {
			koefs[i] = Double.parseDouble(koefsStr[i].trim());
		}
		double solution = Double.parseDouble(koefsStr[koefsStr.length - 1].trim());
		return new Funkcija(koefs, solution);
	}

}
