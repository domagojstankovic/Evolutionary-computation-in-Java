package hr.fer.zemris.optjava.dz13.model.term;

import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Node;

public class Right extends Terminal {

	public Right(Node parent) {
		super(parent);
	}

	@Override
	public boolean visit(MapPosition mapPosition) {
		return mapPosition.turnRight();
	}
	
	@Override
	public String toString() {
		return "Right";
	}
	
	@Override
	public Node duplicate(Node parent) {
		return new Right(parent);
	}

}
