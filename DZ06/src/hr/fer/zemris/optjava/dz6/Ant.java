package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ant implements Comparable<Ant> {

	private int n;
	private List<Integer> nodes = new ArrayList<>();
	private double totalDistance = 0;
	private double alpha;
	private Random rand = new Random();
	
	public Ant(Integer startNode, int n, double alpha) {
		nodes.add(startNode);
		this.n = n;
		this.alpha = alpha;
	}
	
	public Ant(int n, double alpha) {
		nodes.add(rand.nextInt(n));
		this.n = n;
		this.alpha = alpha;
	}
	
	public void findNextNode(double[][] distance, int[][] candidates, double[][] tau, double[][] eta) {
		if (nodes.size() >= n) {
			addFinalDistance(distance);
			return;
		}
		int curr = getCurrentNode();
		int node = chooseCandidates(candidates[curr], tau, eta);
		if (node >= 0) {
			add(node, distance[curr][node]);
		} else {
			List<Integer> others = new ArrayList<>();
			for (Integer i = 0; i < n; i++) {
				others.add(i);
			}
			others.removeAll(nodes);
			int i = chooseIndex(others, tau, eta);
			add(i, distance[curr][i]);
		}
	}
	
	public int chooseCandidates(int[] candidates, double[][] tau, double[][] eta) {
		List<Integer> ints = new ArrayList<>();
		for (int i : candidates) {
			if (nodes.indexOf(i) < 0) {
				ints.add(i);
			}
		}
		if (!ints.isEmpty()) {
			return chooseIndex(ints, tau, eta);
		}
		return -1;
	}
	
	private int chooseIndex(List<Integer> indexes, double[][] tau, double[][] eta) {
		double total = 0;
		int curr = getCurrentNode();
		int size = indexes.size();
		List<Double> taus = new ArrayList<>(size);
		for (Integer i : indexes) {
			double temp = Math.pow(tau[curr][i], alpha) * eta[curr][i];
			taus.add(temp);
			total += temp;
		}
		double num = rand.nextDouble();
		double temp = 0;
		for (int i = 0; i < size; i++) {
			temp += taus.get(i) / total;
			if (num <= temp) {
				return indexes.get(i);
			}
		}
		return -1;
	}
	
	public int getCurrentNode() {
		return nodes.get(nodes.size() - 1);
	}
	
	public void add(int index, double distance) {
		nodes.add(index);
		totalDistance += distance;
	}
	
	public void addFinalDistance(double[][] distance) {
		totalDistance += distance[nodes.get(0)][nodes.get(nodes.size() - 1)];
	}
	
	public Integer getNode(int index) {
		return nodes.get(index);
	}
	
	public double getTotalDistance() {
		return totalDistance;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		int size = nodes.size();
		if (size > 0) {
			sb.append(nodes.get(0));
			for (int i = 1; i < size; i++) {
				sb.append(", " + (nodes.get(i) + 1));
			}
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int compareTo(Ant o) {
		double diff = totalDistance - o.getTotalDistance();
		if (diff < 0) {
			return -1;
		}
		if (diff > 0) {
			return 1;
		}
		return 0;
	}
	
}
