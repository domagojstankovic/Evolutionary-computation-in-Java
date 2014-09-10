package hr.fer.zemris.optjava.dz13.model.func;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Node;

public class Prog3 extends Function {

	public Prog3(Node parent) {
		super(parent, new ArrayList<Node>(3));
	}

	public Prog3(Node parent, List<Node> children) {
		super(parent, children);
	}

	@Override
	public boolean visit(MapPosition mapPosition) {
		boolean b =	children.get(0).visit(mapPosition);
		if (b) {
			b = children.get(1).visit(mapPosition);
			if (b) {
				return children.get(2).visit(mapPosition);
			}
			return false;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Prog3(" + children.get(0).toString() + ", " + children.get(1).toString() + ", " + children.get(2).toString() + ")";
	}
	
	@Override
	public Node duplicate(Node parent) {
		Node temp = new Prog3(parent);
		temp.addChild(getChild(0).duplicate(temp));
		temp.addChild(getChild(1).duplicate(temp));
		temp.addChild(getChild(2).duplicate(temp));
		return temp;
	}
	
	@Override
	public int childrenCount() {
		return 3;
	}

}
