package hr.fer.zemris.optjava.dz4.part2;

public class BinPacking implements IOptAlgorithm {

	private ISelection selectionGood;
	private ISelection selectionBad;
	private ICrossover crossover;
	private IMutation mutation;
	private int maxIter;
	private Population population;
	private int okSize;
	private boolean p;
	
	public BinPacking(ISelection selectionGood, ISelection selectionBad, ICrossover crossover, IMutation mutation,
			int maxIter, Population population, int okSize, boolean p) {
		super();
		this.selectionGood = selectionGood;
		this.selectionBad = selectionBad;
		this.crossover = crossover;
		this.mutation = mutation;
		this.maxIter = maxIter;
		this.population = population;
		this.okSize = okSize;
		this.p = p;
	}

	@Override
	public Solution run() {
		int bestSize = population.getSolution(0).getSize();
		System.out.println("0: " + bestSize);
		for (int i = 0; i < maxIter; i++) {
			population.removeEmptyBins();
			population.sortSolutions();
			int currentSize = population.getSolution(0).getSize();
			if (currentSize < bestSize) {
				bestSize = currentSize;
				System.out.println((i + 1) + ": " + bestSize);
			}
			if (currentSize <= okSize) {
				return population.getSolution(0);
			}
			Solution goodSol1 = selectionGood.select(population, false);
			Solution goodSol2 = selectionGood.select(population, false);
			Solution child = crossover.cross(goodSol1, goodSol2);
			child = mutation.mutate(child);
			Solution badSol = selectionBad.select(population, true);
			if (p) {
				if (badSol.compareTo(child) > 0) {
					badSol.setBins(child.getBins());
				}
			} else {
				badSol.setBins(child.getBins());
			}
		}
		population.sortSolutions();
		return population.getSolution(0);
	}

}
