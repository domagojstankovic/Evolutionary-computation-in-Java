package hr.fer.zemris.optjava.dz13.model.term;

import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Node;

public class Move extends Terminal {

	public Move(Node parent) {
		super(parent);
	}

	@Override
	public boolean visit(MapPosition mapPosition) {
		return mapPosition.move();
	}

	@Override
	public String toString() {
		return "Move";
	}
	
	@Override
	public Node duplicate(Node parent) {
		return new Move(parent);
	}
	
}
