package hr.fer.zemris.generic.ga.img;

import hr.fer.zemris.art.GrayScaleImage;

public class ThreadLocalImageProvider implements ImageProvider {

	private ThreadLocal<GrayScaleImage> local;
	private int w;
	private int h;
	
	public ThreadLocalImageProvider(int w, int h) {
		super();
		local = new ThreadLocal<>();
		this.w = w;
		this.h = h;
	}

	@Override
	public GrayScaleImage getImage() {
		GrayScaleImage temp = local.get();
		if (temp != null) {
			return temp;
		}
		temp = new GrayScaleImage(w, h);
		local.set(temp);
		return temp;
	}

}
