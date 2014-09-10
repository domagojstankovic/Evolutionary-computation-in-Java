package hr.fer.zemris.optjava.dz3;

public class GeometricTempSchedule implements ITempSchedule {

	private double alpha;
//	private double tInitial;
	private double tCurrent;
	private int innerLimit;
	private int outerLimit;
	
	public GeometricTempSchedule(double alpha, double tInitial, int innerLimit, int outerLimit) {
		super();
		this.alpha = alpha;
//		this.tInitial = tInitial;
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;
		tCurrent = tInitial;
	}

	@Override
	public double getNextTemperature() {
		tCurrent *= alpha;
		return tCurrent;
	}

	@Override
	public int getInnerLoopCounter() {
		return innerLimit;
	}

	@Override
	public int getOuterLoopCounter() {
		return outerLimit;
	}

}
