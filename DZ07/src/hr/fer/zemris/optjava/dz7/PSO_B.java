package hr.fer.zemris.optjava.dz7;

import java.util.Random;

import hr.fer.zemris.optjava.dz7.model.Particle;
import hr.fer.zemris.optjava.dz7.model.Population;
import hr.fer.zemris.optjava.dz7.model.interfaces.IFunction;

public class PSO_B extends PSO {

	private int d;
	
	public PSO_B(Population population, int popSize, double merr, int maxiter, int particleSize, IFunction function, boolean max, int d) {
		super(population, popSize, merr, maxiter, particleSize, function, max);
		this.d = d;
	}

	@Override
	protected void updateVelocity(int epoch) {
		double w = W_START + (W_END - W_START) * epoch / maxiter;
		Random rand = new Random();
		for (int i = 0; i < popSize; i++) {
			double[] velocity = new double[particleSize];
			Particle particle = population.getParticleAt(i);
			double u1 = rand.nextDouble();
			double u2 = rand.nextDouble();
			Particle localBest = getLocalBest(i);
			for (int j = 0; j < particleSize; j++) {
				double comp1 = C1 * u1 * (localBest.getBestPositionAt(j) - particle.getCurrPositionAt(j));
				double comp2 = C2 * u2 * (best.getBestPositionAt(j) - particle.getCurrPositionAt(j));
				velocity[j] = w * particle.getVelocityAt(j) + comp1 + comp2;
			}
			particle.setVelocity(velocity);
		}
	}

	private Particle getLocalBest(int index) {
		int ibest = (index - d + popSize) % popSize;
		double max = population.getParticleAt(ibest).getBestFitness();
		for (int i = index - d + 1; i <= index + d; i++) {
			double fit = population.getParticleAt((i + popSize) % popSize).getBestFitness();
			if (fit > max) {
				max = fit;
				ibest = i;
			}
		}
		return population.getParticleAt((ibest + popSize) % popSize);
	}

}
