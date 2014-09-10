package hr.fer.zemris.optjava.dz8.model.interfaces;

import hr.fer.zemris.optjava.dz8.model.Sample;

public interface IReadOnlyDataset {
	
	public int numberOfSamples();
	
	public int numberOfInputs();
	
	public int numberOfOutputs();
	
	public Sample getSampleAt(int index);
	
}
