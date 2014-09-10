package hr.fer.zemris.optjava.dz5.part1;

public class GeneticAlgorithm {
	
	private static final int N = 5;
	private static final int INITIAL_SIZE = 250;
	private static final int MAX_ITER = 100000;
	private static final int MIN_POP_SIZE = 100;
	private static final int MAX_POP_SIZE = 500;
	private static final int MAX_SEL_PRESS = 20000;
	private static final double MUT_PERCENTAGE = 0.01;
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Error!");
			System.exit(0);
		}
		int n = Integer.parseInt(args[0]);
		
		IFunction function = new OneMax();
		ISelection selection1 = new Tournament(N, true);
		ISelection selection2 = new RandomSelection();
//		ISelection selection2 = new Tournament(N, true);
		ICrossover crossover = new OneBreakpointCrossover();
		IMutation mutation = new Mutation(MUT_PERCENTAGE);
		Population population = new Population(INITIAL_SIZE, n);
		ICompFactor compFactor = new LinearCompFactor(MAX_ITER);
		Genotype solution = new RAPGA(function, selection1, selection2, crossover, mutation, population, MAX_ITER, MIN_POP_SIZE, MAX_POP_SIZE, MAX_SEL_PRESS, compFactor).run();
		System.out.println("Solution:");
		System.out.println(solution.toString());
		System.out.println("Fitness: " + solution.getFitness());
		int k = solution.getOnesNum();
		System.out.println("Error: " + n + " - " + k + " = " + (n - k));
	}
	
}
