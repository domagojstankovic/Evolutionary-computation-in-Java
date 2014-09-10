package hr.fer.zemris.optjava.dz9.problem;

public interface MOOPProblem {
	
	public int getNumberOfObjectives();
	
	public void evaluateSolution(double[] solution, double[] objectives);
	
}
