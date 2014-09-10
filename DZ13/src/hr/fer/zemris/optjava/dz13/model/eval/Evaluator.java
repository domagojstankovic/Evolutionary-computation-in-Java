package hr.fer.zemris.optjava.dz13.model.eval;

import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Solution;

public class Evaluator implements IEvaluator {

	private MapPosition mapPosition;
	
	public Evaluator(MapPosition mapPosition) {
		super();
		this.mapPosition = mapPosition;
	}

	@Override
	public int evaluate(Solution solution) {
		while (solution.getRoot().visit(mapPosition));
		int fit = mapPosition.getCollected();
		solution.setFitness(fit);
		mapPosition.reset();
		return fit;
	}

}
