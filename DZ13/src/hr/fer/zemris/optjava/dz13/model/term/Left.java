package hr.fer.zemris.optjava.dz13.model.term;

import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Node;

public class Left extends Terminal {

	public Left(Node parent) {
		super(parent);
	}

	@Override
	public boolean visit(MapPosition mapPosition) {
		return mapPosition.turnLeft();
	}
	
	@Override
	public String toString() {
		return "Left";
	}
	
	@Override
	public Node duplicate(Node parent) {
		return new Left(parent);
	}

}
