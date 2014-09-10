package hr.fer.zemris.optjava.dz13.model.term;

import hr.fer.zemris.optjava.dz13.model.MapPosition;
import hr.fer.zemris.optjava.dz13.model.Node;

public abstract class Terminal extends Node {

	public Terminal(Node parent) {
		super(parent);
	}

	@Override
	public abstract boolean visit(MapPosition mapPosition);

	@Override
	public int childrenCount() {
		return 0;
	}
	
	@Override
	public Node getChild(int index) {
		throw new UnsupportedOperationException("Terminal node");
	}
	
	@Override
	public void addChild(Node child) {
		throw new UnsupportedOperationException("Terminal node");
	}
	
	@Override
	public void setChild(Node child, int index) {
		throw new UnsupportedOperationException("Terminal node");
	}
	
	@Override
	public abstract Node duplicate(Node parent);
	
	@Override
	public void replace(Node oldChild, Node newChild) {
		throw new UnsupportedOperationException("Terminal node");
	}
	
}
