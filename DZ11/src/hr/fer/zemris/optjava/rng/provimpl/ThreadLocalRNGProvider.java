package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

public class ThreadLocalRNGProvider implements IRNGProvider {

	private ThreadLocal<IRNG> local;
	
	public ThreadLocalRNGProvider() {
		local = new ThreadLocal<IRNG>();
	}
	
	@Override
	public IRNG getRNG() {
		IRNG temp = local.get();
		if (temp != null) {
			return temp;
		}
		temp = new RNGRandomImpl();
		local.set(temp);
		return temp;
	}

}
