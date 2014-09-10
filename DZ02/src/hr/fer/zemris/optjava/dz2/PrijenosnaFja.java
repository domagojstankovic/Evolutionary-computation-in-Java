package hr.fer.zemris.optjava.dz2;

public class PrijenosnaFja implements IFunction {

	double[] variables;
	double y;
	
	public PrijenosnaFja(double[] variables, double y) {
		int length = variables.length;
		if (length != 5) {
			throw new IllegalArgumentException();
		}
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = variables[i];
		}
		this.variables = array;
		this.y = y;
	}

	@Override
	public int getVariablesCount() {
		return variables.length;
	}

	@Override
	public double getValue(IVector vector) {
//		int size = variables.length;
//		if (vector.getLength() != size) {
//			throw new IllegalArgumentException();
//		}
		double x1 = variables[0];
		double x2 = variables[1];
		double x3 = variables[2];
		double x4 = variables[3];
		double x5 = variables[4];
		double a = vector.getVariable(0);
		double b = vector.getVariable(1);
		double c = vector.getVariable(2);
		double d = vector.getVariable(3);
		double e = vector.getVariable(4);
		double f = vector.getVariable(5);
		double r1 = a * x1;
		double r2 = b * x1 * x1 * x1 * x2;
		double r3 = c * Math.exp(d * x3) * (1 + Math.cos(e * x4));
		double r4 = f * x4 * x5 * x5;
		return r1 + r2 + r3 + r4 - y;
	}

	@Override
	public IVector getGradient(IVector vector) {
//		double x1 = variables[0];
//		double x2 = variables[1];
//		double x3 = variables[2];
//		double x4 = variables[3];
//		double x5 = variables[4];
//		double a = vector.getVariable(0);
//		double b = vector.getVariable(1);
//		double c = vector.getVariable(2);
//		double d = vector.getVariable(3);
//		double e = vector.getVariable(4);
//		double f = vector.getVariable(5);
//		double[] data = new double[5];
//		// derivirano po krivim varijablama ali se svejedno ne koristi!!
//		data[0] = a + 3 * b * x2 * x1 * x1;
//		data[1] = b * x1 * x1 * x1;
//		data[2] = c * Math.exp(d * x3) * (1 + Math.cos(e * x4)) * d;
//		data[3] = -c * Math.exp(d * x3) * Math.sin(e * x4) * e + f * x5 * x5;
//		data[4] = 2 * f * x4 * x5;
//		return new Vector(data);
		return null;
	}
	
	public double getVariable(int index) {
		return variables[index - 1];
	}

}
