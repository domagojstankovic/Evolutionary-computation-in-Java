package hr.fer.zemris.optjava.dz13.visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import hr.fer.zemris.optjava.dz13.model.AntTrailMap;
import hr.fer.zemris.optjava.dz13.model.Direction;
import hr.fer.zemris.optjava.dz13.model.MapPosition;

import javax.swing.JComponent;

public class AntTrailComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	private MapPosition position;
	private int width;
	private int height;
	private final int cellDim = 18;
	private int w;
	private int h;
	private final int offset = 15;
	private int index = 0;
	private AntTrailMap map;
	private int maxSteps;

	public AntTrailComponent(MapPosition position) {
		super();
		this.position = position;
		map = position.getBackupDuplicate();
		maxSteps = position.getPathSize();
		width = position.getW();
		height = position.getH();
		w = width * cellDim;
		h = height * cellDim;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// paint grid
		int temp;
		for (int i = 0; i <= height; i++) {
			temp = i * cellDim + offset;
			g.drawLine(temp, offset, temp, w - 1 + offset);
		}
		for (int i = 0; i <= width; i++) {
			temp = i * cellDim + offset;
			g.drawLine(offset, temp, h - 1 + offset, temp);
		}

		// paint trails
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				boolean b = map.getTrailAt(i, j);
				if (b) {
					int x = i * cellDim + offset;
					int y = j * cellDim + offset;
					g.fillOval(y, x, cellDim, cellDim);
				}
			}
		}

		// draw ant position
		int x = position.getPathAt(index).y;
		int y = position.getPathAt(index).x;
		map.resetTrailAt(y, x);
		g.setColor(Color.RED);
		Polygon p = getPolygon(position.getPathAt(index).direction, cellDim);
		p.translate(x * cellDim + offset, y * cellDim + offset);
		g.fillPolygon(p);
	}

	private Polygon getPolygon(Direction direction, int dim) {
		Polygon p = new Polygon();
		int d2 = dim / 2;
		int d3 = dim / 3;
		if (direction.equals(Direction.RIGHT)) {
			p.addPoint(0, d3);
			p.addPoint(2 * d3, d3);
			p.addPoint(2 * d3, 0);
			p.addPoint(dim, d2);
			p.addPoint(2 * d3, dim);
			p.addPoint(2 * d3, 2 * d3);
			p.addPoint(0, 2 * d3);
		} else if (direction.equals(Direction.LEFT)) {
			p.addPoint(dim, d3);
			p.addPoint(d3, d3);
			p.addPoint(d3, 0);
			p.addPoint(0, d2);
			p.addPoint(d3, dim);
			p.addPoint(d3, 2 * d3);
			p.addPoint(dim, 2 * d3);
		} else if (direction.equals(Direction.UP)) {
			p.addPoint(d3, dim);
			p.addPoint(d3, d3);
			p.addPoint(0, d3);
			p.addPoint(d2, 0);
			p.addPoint(dim, d3);
			p.addPoint(2 * d3, d3);
			p.addPoint(2 * d3, dim);
		} else {
			p.addPoint(d3, 0);
			p.addPoint(d3, 2 * d3);
			p.addPoint(0, 2 * d3);
			p.addPoint(d2, dim);
			p.addPoint(dim, 2 * d3);
			p.addPoint(2 * d3, 2 * d3);
			p.addPoint(2 * d3, 0);
		}
		return p;
	}

	public void reset() {
		map = position.getBackupDuplicate();
		index = 0;
		repaint();
	}

	public boolean next() {
		if (isDone()) {
			return false;
		}
		index++;
		repaint();
		return true;
	}

	public boolean isDone() {
		if (index >= maxSteps - 1) {
			return true;
		}
		return false;
	}

}
