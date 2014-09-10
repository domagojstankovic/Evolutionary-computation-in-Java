package hr.fer.zemris.optjava.rng;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.img.ImageProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

public class EVOThread extends Thread implements IRNGProvider, ImageProvider {

	private IRNG rng = new RNGRandomImpl();
	private GrayScaleImage image;
	
	public EVOThread(int w, int h) {
		image = new GrayScaleImage(w, h);
	}
	
	public EVOThread(Runnable target, String name, int w, int h) {
		super(target, name);
		image = new GrayScaleImage(w, h);
	}

	public EVOThread(Runnable target) {
		super(target);
	}
	
	public EVOThread(Runnable target, int w, int h) {
		super(target);
		image = new GrayScaleImage(w, h);
	}

	public EVOThread(String name, int w, int h) {
		super(name);
		image = new GrayScaleImage(w, h);
	}

	public EVOThread(ThreadGroup group, Runnable target, String name, long stackSize, int w, int h) {
		super(group, target, name, stackSize);
		image = new GrayScaleImage(w, h);
	}

	public EVOThread(ThreadGroup group, Runnable target, String name, int w, int h) {
		super(group, target, name);
		image = new GrayScaleImage(w, h);
	}

	public EVOThread(ThreadGroup group, Runnable target, int w, int h) {
		super(group, target);
		image = new GrayScaleImage(w, h);
	}

	public EVOThread(ThreadGroup group, String name, int w, int h) {
		super(group, name);
		image = new GrayScaleImage(w, h);
	}

	@Override
	public IRNG getRNG() {
		return rng;
	}

	@Override
	public GrayScaleImage getImage() {
		return image;
	}

}
