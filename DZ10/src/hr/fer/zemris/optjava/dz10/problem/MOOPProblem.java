package hr.fer.zemris.optjava.dz10.problem;

public interface MOOPProblem {
	
	public int getNumberOfObjectives();
	
	public void evaluateSolution(double[] solution, double[] objectives);
	
}
