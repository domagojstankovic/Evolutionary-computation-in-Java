package hr.fer.zemris.optjava.dz7.model;

public class Sample {
	
	private double[] input;
	private int[] output;
	
	public Sample(double[] input, int[] output) {
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
	
	public int[] getOutput() {
		return output;
	}
	
	public int getOutputAt(int index) {
		return output[index];
	}
	
}
