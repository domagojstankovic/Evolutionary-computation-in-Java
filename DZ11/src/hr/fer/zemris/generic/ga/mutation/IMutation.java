package hr.fer.zemris.generic.ga.mutation;

import hr.fer.zemris.generic.ga.GASolution;

public interface IMutation {
	
	public GASolution<int[]> mutate(GASolution<int[]> chr);
	
}
