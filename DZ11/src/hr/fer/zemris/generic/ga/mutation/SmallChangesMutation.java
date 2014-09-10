package hr.fer.zemris.generic.ga.mutation;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class SmallChangesMutation implements IMutation {

	private int colorInterval;
	private int sizeInterval;
	private int w;
	private int h;
	private final IRNG rand = RNG.getRNG();
	
	public SmallChangesMutation(int colorInterval, int sizeInterval, int w, int h) {
		super();
		this.colorInterval = colorInterval;
		this.sizeInterval = sizeInterval;
		this.w = w;
		this.h = h;
	}

	@Override
	public GASolution<int[]> mutate(GASolution<int[]> chr) {
		int[] data = chr.getData().clone();
		int size = data.length;
		int num = (size - 1) / 5;
		data[0] = getChange(data[0], colorInterval) % 256;
		for (int i = 0; i < num; i++) {
			int temp;
			temp = getChange(data[i * 5 + 1], sizeInterval);
			if (temp < 0) temp = 0;
			if (temp >= w) temp = w - 1;
			data[i * 5 + 1] = temp;
			
			temp = getChange(data[i * 5 + 2], sizeInterval);
			if (temp < 0) temp = 0;
			if (temp >= h) temp = h - 1;
			data[i * 5 + 2] = temp;
			
			temp = getChange(data[i * 5 + 3], sizeInterval);
			if (temp < 1) temp = 1;
			if (temp + data[i * 5 + 1] >= w) temp = w - 1;
			data[i * 5 + 3] = temp;
			
			temp = getChange(data[i * 5 + 4], sizeInterval);
			if (temp < 1) temp = 1;
			if (temp + data[i * 5 + 2] >= h) temp = h - 1;
			data[i * 5 + 4] = temp;
			
			temp = getChange(data[i * 5 + 5], colorInterval);
			if (temp < 0) temp = 0;
			if (temp >= 256) temp = 255;
			data[i * 5 + 5] = temp;
		}
		return new Solution(data);
	}
	
	private int getChange(int data, int interval) {
		return rand.nextInt(data - interval / 2, data + interval / 2);
	}

}
