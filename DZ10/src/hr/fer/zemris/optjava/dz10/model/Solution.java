package hr.fer.zemris.optjava.dz10.model;

import java.util.Arrays;

public class Solution {
	
	private double[] x;
	private double[] fit;
	private double distance;
	private int front;
	
	public Solution(double[] x, double[] fit) {
		super();
		this.x = x;
		this.fit = fit;
	}
	
	public Solution(double[] x, int fitSize) {
		super();
		this.x = x;
		this.fit = new double[fitSize];
	}
	
	public int size() {
		return x.length;
	}
	
	public int fitSize() {
		return fit.length;
	}
	
	public double[] getX() {
		return x;
	}
	
	public double getXAt(int index) {
		return x[index];
	}
	
	public double[] getFit() {
		return fit;
	}
	
	public double getFitAt(int index) {
		return fit[index];
	}
	
	public void setFit(double[] fit) {
		this.fit = fit;
	}
	
	public void setFitAt(int index, double fit) {
		this.fit[index] = fit;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void addDistance(double distance) {
		this.distance += distance;
	}
	
	public int getFront() {
		return front;
	}
	
	public void setFront(int front) {
		this.front = front;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(x) + " --- " + Arrays.toString(fit);
	}
	
	public String xToString() {
		String str = Arrays.toString(x);
		str = "(" + str.substring(1, str.length() - 1) + ")";
		return str;
	}
	
	public String fitToString() {
//		return Arrays.toString(fit);
		StringBuilder sb = new StringBuilder("(");
		sb.append(Math.abs(fit[0]));
		int size = size();
		for (int i = 1; i < size; i++) {
			sb.append(", " + Math.abs(fit[i]));
		}
		sb.append(")");
		return sb.toString();
	}
	
}
