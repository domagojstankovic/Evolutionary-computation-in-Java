package hr.fer.zemris.optjava.dz2;

import java.util.Random;

public class Jednostavno {
	
	public static void main(String[] args) {
		int length = args.length;
		if (length != 2 && length != 4) {
			System.out.println("Greška!");
			System.exit(0);
		}
		int maxIter = Integer.parseInt(args[1]);
		IVector pocetnoRjesenje = odrediPocetnoRjesenje(args);
		switch (args[0]) {
			case "1a": NumOptAlgorithms.gradientDescent(new F1(), maxIter, pocetnoRjesenje, true); break;
			case "1b": NumOptAlgorithms.newtonMethod(new F1(), maxIter, pocetnoRjesenje, true); break;
			case "2a": NumOptAlgorithms.gradientDescent(new F2(), maxIter, pocetnoRjesenje, true); break;
			case "2b": NumOptAlgorithms.newtonMethod(new F2(), maxIter, pocetnoRjesenje, true); break;
			default: break;
		}
	}

	private static IVector odrediPocetnoRjesenje(String[] args) {
		if (args.length == 4) {
			return new Vector(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
		}
		Random r = new Random();
		double x = r.nextDouble() * 10 - 5;
		double y = r.nextDouble() * 10 - 5;
		return new Vector(x, y);
	}
	
}
