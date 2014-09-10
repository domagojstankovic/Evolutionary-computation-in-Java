package hr.fer.zemris.optjava.dz7.model;

import hr.fer.zemris.optjava.dz7.model.interfaces.ITransferFunction;

public class SigmoidTransferFunction implements ITransferFunction {

	@Override
	public double valueAt(double x) {
		return 1 / (1 + Math.exp(-x));
	}

}
