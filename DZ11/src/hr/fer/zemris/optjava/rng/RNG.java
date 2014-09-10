package hr.fer.zemris.optjava.rng;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RNG {
	
	private static IRNGProvider rngProvider;
	
	static {
		Properties properties = new Properties();
		InputStream is = RNG.class.getClassLoader().getResourceAsStream("rng-config.properties"); // Zašto se to tako radi??
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String className = properties.getProperty("rng-provider");
		try {
			rngProvider = (IRNGProvider) RNG.class.getClassLoader().loadClass(className).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
	
}
