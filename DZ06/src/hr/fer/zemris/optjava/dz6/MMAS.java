package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MMAS implements IOptAlgorithm {

	private final int n;
	private final int k;
	private final int l;
	private final int maxIter;
	private double[][] distance;
	private double[][] eta;
	private int[][] candidates;
	private double tauMin;
	private double tauMax;
	private double a;
	private final double alpha;
	private final double ro;
	private double[][] tau;
	
	public MMAS(int n, int k, int l, int maxIter, double[][] distance, double[][] eta, double alpha, double ro) {
		super();
		this.n = n;
		this.k = k;
		this.l = l;
		this.maxIter = maxIter;
		this.distance = distance;
		this.eta = eta;
		this.alpha = alpha;
		this.ro = ro;
		a = (double) n / k;
		tau = new double[n][n];
		initializeTau();
		fillCandidates();
	}

	@Override
	public Ant run() {
		Ant bestSoFar = null;
		final int limit = maxIter / 2;
		int stagnationCounter = 0;
		int div = maxIter / 50;
		final int stagnationLimit = div < 10 ? 10 : div;
		for (int i = 0; i < maxIter; i++) {
			List<Ant> ants = new ArrayList<>(l);
			for (int j = 0; j < l; j++) {
				Ant ant = new Ant(n, alpha);
				for (int k = 0; k < n; k++) {
					ant.findNextNode(distance, candidates, tau, eta);
				}
				ants.add(ant);
			}
			Ant iterBest = Collections.min(ants);
			stagnationCounter++;
			if (stagnationCounter >= stagnationLimit) {
				stagnationCounter = 0;
				tauMax = 1 / (ro * bestSoFar.getTotalDistance());
				tauMin = tauMax / a;
				resetTau();
			}
			if (bestSoFar == null || iterBest.compareTo(bestSoFar) < 0) {
				bestSoFar = iterBest;
				stagnationCounter = 0;
			}
			System.out.println((i + 1) + ": " + bestSoFar.getTotalDistance());
			evaporate();
			if (i < limit) {
				refreshTau(iterBest);
			} else {
				refreshTau(bestSoFar);
			}
		}
		return bestSoFar;
	}
	
	private void resetTau() {
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				tau[i][j] = tau[j][i] = tauMax;
			}
		}
	}

	private void refreshTau(Ant best) {
		int i1 = best.getNode(0);
		int i2 = best.getNode(n - 1);
		final double koef = 1 / best.getTotalDistance();
		double temp = tau[i1][i2] + koef ;
		tau[i1][i2] = tau[i2][i1] = temp < tauMax ? temp : tauMax;
		for (int i = 1; i < n; i++) {
			i1 = best.getNode(i - 1);
			i2 = best.getNode(i);
			temp = tau[i1][i2] + koef;
			tau[i1][i2] = tau[i2][i1] = temp < tauMax ? temp : tauMax;
		}
	}
	
	private void evaporate() {
		final double koef = 1 - ro;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				double temp = tau[i][j] * koef;
				tau[i][j] = tau[j][i] = temp < tauMin ? tauMin : temp;
			}
		}
	}
	
	private void initializeTau() {
		final boolean greedy = true;
		double totalDistance = greedy ? greedyDistance() : sequentialDistance();;
		tauMax = n / totalDistance;
		tauMin = tauMax / a;
		resetTau();
	}
	
	private double sequentialDistance() {
		double totalDistance = 0;
		int num = new Random().nextInt(n);
		for (int i = 0; i < n; i++) {
			totalDistance += distance[(num + i + 1) % n][(num + i) % n];
		}
		return totalDistance;
	}
	
	private double greedyDistance() {
		Random rand = new Random();
		Integer index = rand.nextInt(n);
		Integer startIndex = index;
		double totalDistance = 0;
		Set<Integer> indexes = new HashSet<>(n);
		for (Integer i = 0; i < n; i++) {
			indexes.add(i);
		}
		indexes.remove(startIndex);
		while (!indexes.isEmpty()) {
			Iterator<Integer> iter = indexes.iterator();
			Integer currBest = iter.next();
			double min = distance[index][currBest];
			while (iter.hasNext()) {
				Integer curr = iter.next();
				double currMin = distance[index][curr];
				if (currMin < currBest) {
					currBest = curr;
					min = currMin;
				}
			}
			index = currBest;
			totalDistance += min;
			indexes.remove(currBest);
		}
		return totalDistance + distance[index][startIndex];
	}

	private void fillCandidates() {
		candidates = new int[n][k];
		for (int i = 0; i < n; i++) {
			List<Neighbor> all = new ArrayList<>(n);
			for (int j = 0; j < n; j++) {
				all.add(new Neighbor(j, distance[i][j]));
			}
			Collections.sort(all);
			for (int j = 0; j < k; j++) {
				candidates[i][j] = all.get(j + 1).index;
			}
		}
	}

}
