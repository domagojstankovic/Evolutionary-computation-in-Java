package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.model.Particle;
import hr.fer.zemris.optjava.dz7.model.Population;
import hr.fer.zemris.optjava.dz7.model.interfaces.IFunction;
import hr.fer.zemris.optjava.dz7.model.interfaces.IOptAlgorithm;

public abstract class PSO implements IOptAlgorithm {

	private static final double INIT_MIN = -1;
	private static final double INIT_MAX = 1;
	private static final double INIT_V_MIN = -0.2;
	private static final double INIT_V_MAX = 0.2;
	protected static final double W_START = 0.9;
	protected static final double W_END = 0.4;
	protected static final double C1 = 2.05;
	protected static final double C2 = 2.05;
	
	protected Population population;
	protected int popSize;
	protected double merr;
	protected int maxiter;
	protected int particleSize;
	protected IFunction function;
	protected boolean max;
	protected Particle best;
	
	public PSO(Population population, int popSize, double merr, int maxiter, int particleSize, IFunction function, boolean max) {
		super();
		this.population = population;
		this.popSize = popSize;
		this.merr = merr;
		this.maxiter = maxiter;
		this.particleSize = particleSize;
		this.function = function;
		this.max = max;
	}

	protected Particle determineBest() {
		List<Particle> list = Arrays.asList(population.getParticles());
		return Collections.max(list);
	}

	@Override
	public Particle run() {
		for (int i = 0; i < maxiter; i++) {
			updatePosition();
			updateFitness(i == 0 ? true : false);
			best = determineBest();
			System.out.println((i + 1) + ": " + Math.abs(best.getBestFitness()));
			if (Math.abs(best.getBestFitness()) < merr) {
				return best;
			}
			updateVelocity(i);
		}
		updatePosition();
		updateFitness(false);
		return determineBest();
	}
	
	protected void updateFitness(boolean firstTime) {
		Particle[] particles = population.getParticles();
		for (Particle particle : particles) {
			double value = function.valueAt(particle.getCurrPosition());
			value = max ? value : -value;
			particle.setCurrFitness(value);
			if (firstTime) {
				particle.setBestPosition(particle.getCurrPosition());
				particle.setBestFitness(value);
			} else if (value > particle.getBestFitness()) {
				particle.setBestPosition(particle.getCurrPosition());
				particle.setBestFitness(value);
			}
		}
	}
	
	private void updatePosition() {
		for (int i = 0; i < popSize; i++) {
			Particle particle = population.getParticleAt(i);
			for (int j = 0; j < particleSize; j++) {
				particle.setCurrPositionAt(j, particle.getCurrPositionAt(j) + particle.getVelocityAt(j));
			}
		}
	}
	
	protected abstract void updateVelocity(int epoch);
	
	public static Population createInitialPopulation(int popSize, int particleSize) {
		Random rand = new Random();
		Particle[] particles = new Particle[popSize];
		for (int i = 0; i < popSize; i++) {
			double[] position = new double[particleSize];
			double[] velocity = new double[particleSize];
			for (int j = 0; j < particleSize; j++) {
				position[j] = rand.nextDouble() * (INIT_MAX - INIT_MIN) + INIT_MIN;
				velocity[j] = rand.nextDouble() * (INIT_V_MAX - INIT_V_MIN) + INIT_V_MIN;
			}
			particles[i] = new Particle(position, velocity);
		}
		return new Population(particles);
	}

}
