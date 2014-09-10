package hr.fer.zemris.optjava.dz9.problem;

import hr.fer.zemris.optjava.dz9.function.IFunction;

public class Problem implements MOOPProblem {

	private IFunction[] objectives;
	private double[] lbound;
	private double[] ubound;
	private boolean min;
	
	public Problem(IFunction[] objectives, double[] lbound, double[] ubound, boolean min) {
		super();
		this.objectives = objectives;
		this.lbound = lbound;
		this.ubound = ubound;
		this.min = min;
	}

	@Override
	public int getNumberOfObjectives() {
		return objectives.length;
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) {
		bound(solution);
		int n = this.objectives.length;
		for (int i = 0; i < n; i++) {
			double val = this.objectives[i].valueAt(solution) * (min ? -1 : 1);
			objectives[i] = val ;
		}
	}
	
	private void bound(double[] solution) {
		int n = solution.length;
		for (int i = 0; i < n; i++) {
			if (solution[i] < lbound[i]) {
				solution[i] = lbound[i];
			}
			if (solution[i] > ubound[i]) {
				solution[i] = ubound[i];
			}
		}
	}

}
