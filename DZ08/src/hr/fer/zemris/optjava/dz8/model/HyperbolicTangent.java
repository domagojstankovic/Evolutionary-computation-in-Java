package hr.fer.zemris.optjava.dz8.model;

import hr.fer.zemris.optjava.dz8.model.interfaces.ITransferFunction;

public class HyperbolicTangent implements ITransferFunction {

	@Override
	public double valueAt(double x) {
		return 1 - 2 / (Math.exp(x) + 1);
	}

}
