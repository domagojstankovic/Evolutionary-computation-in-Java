package hr.fer.zemris.optjava.dz13.model.func;

import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Node;

import java.util.ArrayList;
import java.util.List;

public class IfFoodAhead extends Function {

	public IfFoodAhead(Node parent) {
		super(parent, new ArrayList<Node>(2));
	}
	
	public IfFoodAhead(Node parent, List<Node> children) {
		super(parent, children);
	}

	@Override
	public boolean visit(MapPosition mapPosition) {
		if (mapPosition.foodAhead()) {
			return children.get(0).visit(mapPosition);
		} else {
			return children.get(1).visit(mapPosition);
		}
	}
	
	@Override
	public String toString() {
		return "IfFoodAhead(" + children.get(0).toString() + ", " + children.get(1).toString() + ")";
	}
	
	@Override
	public Node duplicate(Node parent) {
		Node temp = new IfFoodAhead(parent);
		temp.addChild(getChild(0).duplicate(temp));
		temp.addChild(getChild(1).duplicate(temp));
		return temp;
	}
	
	@Override
	public int childrenCount() {
		return 2;
	}

}
