package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<SingleObjectiveSolution> {

	private IDecoder<T> decoder;
	private INeighborhood<T> neighborhood;
	private T startWith;
	private IFunction function;
	private ITempSchedule schedule;
	private boolean minimize;
	private Random rand = new Random();

	public SimulatedAnnealing(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith, IFunction function,
			ITempSchedule schedule, boolean minimize) {
		super();
		this.decoder = decoder;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
		this.function = function;
		this.schedule = schedule;
		this.minimize = minimize;
	}

	@Override
	public SingleObjectiveSolution run() {
		int outer = schedule.getOuterLoopCounter();
		int inner = schedule.getInnerLoopCounter();
		double temp = schedule.getNextTemperature();
		T omega = startWith;
		omega.value = function.valueAt(decoder.decode(omega));
		omega.fitness = minimize ? -omega.value : omega.value;
		T max = omega;
		for (int i = 0; i < outer; i++) {
			for (int j = 0; j < inner; j++) {
				T omegaNew = neighborhood.randomNeighbor(omega);
				omegaNew.value = function.valueAt(decoder.decode(omegaNew));
				omegaNew.fitness = minimize ? -omegaNew.value : omegaNew.value;
				double delta = omegaNew.value - omega.value;
				if (!minimize) {
					delta = -delta;
				}
				if (delta <= 0) {
					omega = omegaNew;
				} else {
					double p = Math.exp(-delta / temp);
					if (rand.nextDouble() <= p) {
						omega = omegaNew;
					}
				}
				if (omega.compareTo(max) < 0) {
					max = omega;
				}
			}
//			System.out.println(max);
			temp = schedule.getNextTemperature();
		}
		return max;
	}

}
