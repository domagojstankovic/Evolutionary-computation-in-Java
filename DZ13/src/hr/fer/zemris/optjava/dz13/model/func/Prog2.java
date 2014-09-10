package hr.fer.zemris.optjava.dz13.model.func;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Node;

public class Prog2 extends Function {

	public Prog2(Node parent) {
		super(parent, new ArrayList<Node>(2));
	}

	public Prog2(Node parent, List<Node> children) {
		super(parent, children);
	}

	@Override
	public boolean visit(MapPosition mapPosition) {
		boolean b = children.get(0).visit(mapPosition);
		if (b) {
			return children.get(1).visit(mapPosition);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Prog2(" + children.get(0).toString() + ", " + children.get(1).toString() + ")";
	}

	@Override
	public Node duplicate(Node parent) {
		Node temp = new Prog2(parent);
		temp.addChild(getChild(0).duplicate(temp));
		temp.addChild(getChild(1).duplicate(temp));
		return temp;
	}
	
	@Override
	public int childrenCount() {
		return 2;
	}
	
}
