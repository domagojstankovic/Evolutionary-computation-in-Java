package hr.fer.zemris.optjava.dz8.model;

public class Sample {
	
	private double[] input;
	private double[] output;
	
	public Sample(double[] input, double[] output) {
		super();
		this.input = input;
		this.output = output;
	}
	
	public int inputSize() {
		return input.length;
	}
	
	public int outputSize() {
		return output.length;
	}
	
	public double[] getInput() {
		return input;
	}
	
	public double[] getOutput() {
		return output;
	}
	
	public double getOutputAt(int index) {
		return output[index];
	}
	
}
