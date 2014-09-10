package hr.fer.zemris.optjava.dz7.model;

import hr.fer.zemris.optjava.dz7.model.interfaces.IFunction;
import hr.fer.zemris.optjava.dz7.model.interfaces.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.model.interfaces.ITransferFunction;

public class FFANN implements IFunction {

	private int[] layers;
	private ITransferFunction[] transferFunctions;
	private IReadOnlyDataset dataset;
	private int weightsNum;
	private int neuronNum;
	private double[] metaArray;
	
	public FFANN(int[] layers, ITransferFunction[] transferFunctions, IReadOnlyDataset dataset) {
		super();
		this.layers = layers;
		this.transferFunctions = transferFunctions;
		this.dataset = dataset;
		calculateWeightsNum();
		calculateNeuronNum();
		metaArray = new double[neuronNum];
	}

	private void calculateNeuronNum() {
		int size = layers.length;
		for (int i = 0; i < size; i++) {
			neuronNum += layers[i];
		}
	}

	private void calculateWeightsNum() {
		int size = layers.length;
		for (int i = 1; i < size; i++) {
			weightsNum += layers[i];
			weightsNum += layers[i - 1] * layers[i];
		}
	}

	public int getWeightsCount() {
		return weightsNum;
	}
	
	public void calcOutputs(double[] inputs, double[] weights, double[] outputs) {
		int size = inputs.length;
		for (int i = 0; i < size; i++) {
			metaArray[i] = inputs[i];
		}
		int position = size;
		size = layers.length;
		int prePosition = 0;
		int weightsPosition = 0;
		for (int i = 1; i < size; i++) { // idem po slojevima
			int preLayer = layers[i - 1];
			int thisLayer = layers[i];
			for (int j = 0; j < thisLayer; j++) { // idem po neuronima u pojedinom sloju
				double net = weights[weightsPosition];
				weightsPosition++;
				for (int k = 0; k < preLayer; k++) { // zbrajam i množim sve težine za pojedini neuron
					net += weights[weightsPosition] * metaArray[k + prePosition];
					weightsPosition++;
				}
				metaArray[position + j] = transferFunctions[i - 1].valueAt(net);
			}
			position += thisLayer;
			prePosition += preLayer;
		}
		size = outputs.length;
		for (int i = 0; i < size; i++) {
			outputs[size - 1 - i] = metaArray[neuronNum - 1 - i];
		}
	}
	
	public int datasetSize() {
		return dataset.numberOfSamples();
	}
	
	private void calcOutputAt(int index, double[] weights, double[] output) {
		calcOutputs(dataset.getSampleAt(index).getInput(), weights, output);
	}
	
	private int okSum;
	
	public int getOkSum() {
		return okSum;
	}
	
	public double calcError(double[] weights) {
		int n = dataset.numberOfSamples();
		int m = dataset.numberOfOutputs();
		double sum = 0;
		double[] output = new double[m];
		okSum = 0;
		for (int s = 0; s < n; s++) {
			calcOutputAt(s, weights, output);
			boolean ok = true;
			for (int o = 0; o < m; o++) {
				double temp = dataset.getSampleAt(s).getOutputAt(o) - output[o];
				if (Math.abs(temp) > 0.5) {
					ok = false;
				}
				sum += temp * temp;
			}
			if (ok) {
				okSum++;
			}
		}
		return sum / n;
	}

	@Override
	public double valueAt(double[] variables) {
		return calcError(variables);
	}
	
}
