package hr.fer.zemris.optjava.dz13.model.func;

import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Node;

import java.util.List;

public abstract class Function extends Node {

	public Function(Node parent, List<Node> children) {
		super(parent);
		this.children = children;
	}

	protected List<Node> children;
	
	@Override
	public int childrenCount() {
		return children.size();
	}
	
	@Override
	public abstract boolean visit(MapPosition mapPosition);
	
	@Override
	public Node getChild(int index) {
		return children.get(index);
	}

	@Override
	public void setChild(Node child, int index) {
		addSize(-children.get(index).getSize());
		children.set(index, child);
		addSize(child.getSize());
	}
	
	@Override
	public void addChild(Node child) {
		children.add(child);
//		addSize(child.getSize());
		addSize(1);
	}
	
	@Override
	public abstract Node duplicate(Node parent);
	
	@Override
	public void replace(Node oldChild, Node newChild) {
		int index = children.indexOf(oldChild);
		setChild(newChild, index);
	}
	
}
