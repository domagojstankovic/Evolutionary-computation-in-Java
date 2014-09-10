package hr.fer.zemris.optjava.dz8.model;

import hr.fer.zemris.optjava.dz8.model.interfaces.IFunction;
import hr.fer.zemris.optjava.dz8.model.interfaces.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.model.interfaces.ITransferFunction;

public class ElmanNN implements IFunction {

	private int[] layers;
	private ITransferFunction[] transferFunctions;
	private IReadOnlyDataset dataset;
	private int weightsNum;
	private int neuronNum;
	private int contextSize;
	private double[] metaArray;
	private double[] context;
	
	public ElmanNN(int[] layers, ITransferFunction[] transferFunctions, IReadOnlyDataset dataset) {
		super();
		this.layers = layers;
		this.transferFunctions = transferFunctions;
		this.dataset = dataset;
		calculateWeightsNum();
		calculateNeuronNum();
		contextSize = layers[1];
		metaArray = new double[neuronNum];
		context = new double[contextSize];
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
		int conPosition = weightsNum;
		for (int i = 1; i < size; i++) { // idem po slojevima
			int preLayer = layers[i - 1];
			int thisLayer = layers[i];
			for (int j = 0; j < thisLayer; j++) { // idem po neuronima u pojedinom sloju
				double net = weights[weightsPosition];
				if (i == 1) {
					for (int k = 0; k < contextSize; k++) {
						net += context[k] * weights[conPosition];
						conPosition++;
					}
				}
				weightsPosition++;
				for (int k = 0; k < preLayer; k++) { // zbrajam i množim sve težine za pojedini neuron
					net += weights[weightsPosition] * metaArray[k + prePosition];
					weightsPosition++;
				}
				metaArray[position + j] = transferFunctions[i - 1].valueAt(net);
			}
			if (i == 1) {
				for (int k = 0; k < contextSize; k++) {
					context[k] = metaArray[position + k];
				}
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
	
	public double calcError(double[] weights) {
		int n = dataset.numberOfSamples();
		int m = dataset.numberOfOutputs();
		double sum = 0;
		double[] output = new double[m];
		int size = weights.length;
		for (int i = 0; i < contextSize; i++) {
			context[i] = weights[size - contextSize + i];
		}
		for (int s = 0; s < n; s++) {
			calcOutputAt(s, weights, output);
			for (int o = 0; o < m; o++) {
				double temp = dataset.getSampleAt(s).getOutputAt(o) - output[o];
				sum += temp * temp;
			}
		}
		return sum / n;
	}

	@Override
	public double valueAt(double[] variables) {
		return calcError(variables);
	}
	
}
