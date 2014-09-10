package hr.fer.zemris.trisat;

public class SATFormulaStats {

	private SATFormula formula = null;
	private double[] post = null;
	private BitVector assignment = null;
	private int numberOfSatisfied = 0;

	private static final double percentageConstantUp = 0.01;
	private static final double percentageConstantDown = 0.1;
	private static final int percentageUnitAmount = 50;

	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		int size = formula.getNumberOfClauses();
		post = new double[size];
		for (int i = 0; i < size; i++) {
			post[i] = 0;
		}
	}

	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		this.assignment = assignment;
		int number = 0;
		int n = formula.getNumberOfClauses();
		for (int i = 0; i < n; i++) {
			if (formula.getClause(i).isSatisfied(assignment)) {
				number++;
				if (updatePercentages) {
					post[i] += (1 - post[i]) * percentageConstantUp;
				}
			} else if (updatePercentages) {
				post[i] += (-post[i]) * percentageConstantDown;
			}
		}
		numberOfSatisfied = number;
	}

	public int getNumberOfSatisfied() {
		return numberOfSatisfied;
	}

	public boolean isSatisfied() {
		return numberOfSatisfied == formula.getNumberOfClauses();
	}

	public double getPercentageBonus() {
		double z = getNumberOfSatisfied();
		int size = formula.getNumberOfClauses();
		for (int i = 0; i < size; i++) {
			double temp = percentageUnitAmount * (1 - post[i]);
			if (formula.getClause(i).isSatisfied(assignment)) {
				z += temp;
			} else {
				z -= temp;
			}
		}
		return z;
	}

	public double getPercentage(int index) {
		return post[index];
	}

}
