package hr.fer.zemris.optjava.dz9.distance;

import hr.fer.zemris.optjava.dz9.model.Population;
import hr.fer.zemris.optjava.dz9.model.Solution;

public interface IDistance {
	
	public double distance(Solution a, Solution b);
	
	public void changePopulation(Population population);
	
}
