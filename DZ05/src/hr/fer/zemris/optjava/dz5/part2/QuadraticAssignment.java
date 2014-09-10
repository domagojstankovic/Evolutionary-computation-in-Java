package hr.fer.zemris.optjava.dz5.part2;

public class QuadraticAssignment implements IFunction {

	private Task task;
	
	public QuadraticAssignment(Task task) {
		super();
		this.task = task;
	}

	@Override
	public double getValue(Permutation permutation) {
		int n = task.getN();
		double sum = 0.0;
		int[][] distances = task.getDistances();
		int[][] cost = task.getCost();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sum += cost[i][j] * distances[permutation.getElementAt(i)][permutation.getElementAt(j)];
			}
		}
		return sum;
	}
}
