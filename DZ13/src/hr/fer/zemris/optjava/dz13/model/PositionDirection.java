package hr.fer.zemris.optjava.dz13.model;

public class PositionDirection {

	public int x;
	public int y;
	public Direction direction;
	
	public PositionDirection() {
		x = y = 0;
		direction = Direction.RIGHT;
	}
	
	public PositionDirection(int x, int y, Direction direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ") " + direction;
	}
	
}
