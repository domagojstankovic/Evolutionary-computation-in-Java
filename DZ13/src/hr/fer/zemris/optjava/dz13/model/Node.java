package hr.fer.zemris.optjava.dz13.model;

public abstract class Node {
	
	private int size = 1;
	private Node parent;
	private int depth = 0;
	
	public Node(Node parent) {
		super();
		this.parent = parent;
		depth = parent == null ? 0 : parent.getDepth() + 1;
	}

	public abstract boolean visit(MapPosition mapPosition);
	
	public abstract int childrenCount();
	
	public abstract Node getChild(int index);
	
	public abstract void setChild(Node child, int index);
	
	public abstract void addChild(Node child);
	
	public abstract Node duplicate(Node parent);
	
	public abstract void replace(Node oldChild, Node newChild);
	
	public void addSize(int num) {
		size += num;
		if (parent == null) {
			return;
		}
		parent.addSize(num);
	}
	
	public int getSize() {
		return size;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public void incDepth() {
		depth++;
	}
	
}
