package hr.fer.zemris.generic.ga.img;

import hr.fer.zemris.art.GrayScaleImage;

public class ThreadBoundImageProvider implements ImageProvider {

	@Override
	public GrayScaleImage getImage() {
		return ((ImageProvider) Thread.currentThread()).getImage();
	}

}
