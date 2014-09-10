package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class BitvectorUnifNeighborhood implements INeighborhood<BitvectorSolution> {

	private Random rand = new Random();
	private int variablesNum;
	
	public BitvectorUnifNeighborhood(int variables) {
		variablesNum = variables;
	}
	
	@Override
	public BitvectorSolution randomNeighbor(BitvectorSolution current) {
		BitvectorSolution sol = current.duplicate();
		for (int i = 0; i < variablesNum; i++) {
			sol.randomize(rand);
		}
		return sol;
	}

}
