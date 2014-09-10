package hr.fer.zemris.optjava.dz4.part2;

import java.util.LinkedList;
import java.util.List;

public class Bin implements Comparable<Bin> {

	private List<Integer> sticks = new LinkedList<>();
	private int capacity;
	
	public Bin(int capacity) {
		this.capacity = capacity;
	}
	
	public Bin(Integer[] sticks, int capacity) {
		for (Integer i : sticks) {
			this.sticks.add(i);
		}
		this.capacity = capacity;
	}
	
	public Bin(Integer stick, int capacity) {
		sticks.add(stick);
		this.capacity = capacity;
	}
	
	public boolean add(Integer stick) {
		if (getFill() + stick > capacity) {
			return false;
		}
		sticks.add(stick);
		return true;
	}
	
//	public Integer[] getSticks() {
//		Integer[] array = new Integer[sticks.size()];
//		return sticks.toArray(array);
//	}
	
	public List<Integer> getSticks() {
		return sticks;
	}
	
	public boolean remove(Integer stick) {
		return sticks.remove(stick);
	}
	
	public int getFill() {
		int sum = 0;
		for (Integer i : sticks) {
			sum += i;
		}
		return sum;
	}
	
	public boolean isEmpty() {
		return sticks.isEmpty();
	}
	
	public double getRatio() {
		double value = (double) getFill() / capacity;
		return value * value;
	}

	@Override
	public int compareTo(Bin o) {
		return getFill() - o.getFill();
	}
	
	public Bin copy() {
		Integer[] newSticks = new Integer[sticks.size()];
		return new Bin(sticks.toArray(newSticks), capacity);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		int size = sticks.size();
		if (size <= 0) {
			return "[ ]";
		}
		sb.append(sticks.get(0));
		for (int i = 1; i < size; i++) {
			sb.append(", " + sticks.get(i));
		}
		sb.append("]");
		return sb.toString();
	}
}
