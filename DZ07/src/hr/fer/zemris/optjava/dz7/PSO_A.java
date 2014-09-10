package hr.fer.zemris.optjava.dz7;

import java.util.Random;

import hr.fer.zemris.optjava.dz7.model.Particle;
import hr.fer.zemris.optjava.dz7.model.Population;
import hr.fer.zemris.optjava.dz7.model.interfaces.IFunction;

public class PSO_A extends PSO {

	public PSO_A(Population population, int popSize, double merr, int maxiter, int particleSize, IFunction function, boolean max) {
		super(population, popSize, merr, maxiter, particleSize, function, max);
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
			for (int j = 0; j < particleSize; j++) {
				double comp1 = C1 * u1 * (particle.getBestPositionAt(j) - particle.getCurrPositionAt(j));
				double comp2 = C2 * u2 * (best.getBestPositionAt(j) - particle.getCurrPositionAt(j));
				velocity[j] = w * particle.getVelocityAt(j) + comp1 + comp2;
			}
			particle.setVelocity(velocity);
		}
	}

}
