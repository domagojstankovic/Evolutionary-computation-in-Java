package hr.fer.zemris.optjava.dz13.model;

import java.util.ArrayList;
import java.util.List;

public class MapPosition {
	
	private AntTrailMap map;
	private AntTrailMap backup;
	private int x = 0;
	private int y = 0;
	private int w;
	private int h;
	private Direction direction = Direction.RIGHT;
	private int collected = 0;
	private int maxSteps;
	private int steps = 0;
	private List<PositionDirection> path = new ArrayList<>();
	
	public MapPosition(AntTrailMap map, int maxSteps) {
		super();
		this.map = map.duplicate();
		this.maxSteps = maxSteps;
		backup = map;
		w = map.getWidth();
		h = map.getHeight();
		path.add(new PositionDirection());
	}
	
	public boolean move() {
		steps++;
		if (steps >= maxSteps) {
			return false;
		}
		if (foodAhead()) {
			collected++;
		}
		if (direction.equals(Direction.RIGHT)) {
			y++;
			if (y >= h) {
				y = 0;
			}
		}
		if (direction.equals(Direction.LEFT)) {
			y--;
			if (y < 0) {
				y = h - 1;
			}
		}
		if (direction.equals(Direction.UP)) {
			x--;
			if (x < 0) {
				x = w - 1;
			}
		}
		if (direction.equals(Direction.DOWN)) {
			x++;
			if (x >= w) {
				x = 0;
			}
		}
		map.resetTrailAt(x, y);
		path.add(new PositionDirection(x, y, direction));
		return true;
	}
	
	public boolean turnLeft() {
		steps++;
		if (steps >= maxSteps) {
			return false;
		}
		if (direction.equals(Direction.RIGHT)) {
			direction = Direction.UP;
		} else if (direction.equals(Direction.LEFT)) {
			direction = Direction.DOWN;
		} else if (direction.equals(Direction.UP)) {
			direction = Direction.LEFT;
		} else if (direction.equals(Direction.DOWN)) {
			direction = Direction.RIGHT;
		}
		path.add(new PositionDirection(x, y, direction));
		return true;
	}
	
	public boolean turnRight() {
		steps++;
		if (steps >= maxSteps) {
			return false;
		}
		if (direction.equals(Direction.RIGHT)) {
			direction = Direction.DOWN;
		} else if (direction.equals(Direction.LEFT)) {
			direction = Direction.UP;
		} else if (direction.equals(Direction.UP)) {
			direction = Direction.RIGHT;
		} else if (direction.equals(Direction.DOWN)) {
			direction = Direction.LEFT;
		}
		path.add(new PositionDirection(x, y, direction));
		return true;
	}
	
	public boolean foodAhead() {
		if (direction.equals(Direction.RIGHT)) {
			return map.getTrailAt(x, (y >= h - 1 ? 0 : y + 1));
		}
		if (direction.equals(Direction.LEFT)) {
			return map.getTrailAt(x, (y <= 0 ? h - 1 : y - 1));
		}
		if (direction.equals(Direction.UP)) {
			return map.getTrailAt((x <= 0 ? w - 1 : x - 1), y);
		}
		if (direction.equals(Direction.DOWN)) {
			return map.getTrailAt((x >= w - 1 ? 0 : x + 1), y);
		}
		return false;
	}

	public void reset() {
		x = 0;
		y = 0;
		steps = 0;
		collected = 0;
		direction = Direction.RIGHT;
		map = backup.duplicate();
		path.clear();
		path.add(new PositionDirection());
	}
	
	public int getCollected() {
		return collected;
	}
	
	public boolean trailAt(int x, int y) {
		return map.getTrailAt(x, y);
	}
	
	public boolean originalTrailAt(int x, int y) {
		return backup.getTrailAt(x, y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public List<PositionDirection> getPath() {
		return path;
	}
	
	public PositionDirection getPathAt(int index) {
		return path.get(index);
	}
	
	public int getPathSize() {
		return path.size();
	}
	
	public AntTrailMap getBackupDuplicate() {
		return backup.duplicate();
	}
	
	public int getMaxSteps() {
		return maxSteps;
	}
	
}
