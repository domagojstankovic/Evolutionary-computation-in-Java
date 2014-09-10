package hr.fer.zemris.optjava.dz13.model;

public class AntTrailMap {
	
	private boolean[][] trail;
	
	public AntTrailMap(boolean[][] trail) {
		super();
		this.trail = trail;
	}
	
	public void resetTrailAt(int x, int y) {
		trail[x][y] = false;
	}
	
	public boolean[][] getTrail() {
		return trail;
	}
	
	public boolean getTrailAt(int x, int y) {
		return trail[x][y];
	}
	
	public int getWidth() {
		return trail.length;
	}
	
	public int getHeight() {
		return trail[0].length;
	}
	
	public AntTrailMap duplicate() {
		int w = getWidth();
		int h = getHeight();
		boolean[][] t = new boolean[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				t[i][j] = trail[i][j];
			}
		}
		return new AntTrailMap(t);
	}
	
}
