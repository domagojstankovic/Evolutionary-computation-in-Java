package hr.fer.zemris.optjava.dz7.model;

public class Population {

	private Particle[] particles;

	public Population(Particle[] particles) {
		super();
		this.particles = particles;
	}
	
	public Particle[] getParticles() {
		return particles;
	}
	
	public Particle getParticleAt(int index) {
		return particles[index];
	}
	
	public int size() {
		return particles.length;
	}
	
}
