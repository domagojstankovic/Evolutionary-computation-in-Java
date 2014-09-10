package hr.fer.zemris.optjava.dz7.model.interfaces;

import hr.fer.zemris.optjava.dz7.model.Sample;

public interface IReadOnlyDataset {
	
	public int numberOfSamples();
	
	public int numberOfInputs();
	
	public int numberOfOutputs();
	
	public Sample getSampleAt(int index);
	
}
