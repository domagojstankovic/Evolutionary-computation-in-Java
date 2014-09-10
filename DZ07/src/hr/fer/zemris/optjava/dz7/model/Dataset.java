package hr.fer.zemris.optjava.dz7.model;

import hr.fer.zemris.optjava.dz7.model.interfaces.IReadOnlyDataset;

public class Dataset implements IReadOnlyDataset {

	private Sample[] samples;
	
	public Dataset(Sample[] samples) {
		super();
		this.samples = samples;
	}

	@Override
	public int numberOfSamples() {
		return samples.length;
	}

	@Override
	public int numberOfInputs() {
		return samples[0].inputSize();
	}

	@Override
	public int numberOfOutputs() {
		return samples[0].outputSize();
	}

	@Override
	public Sample getSampleAt(int index) {
		return samples[index];
	}

}
