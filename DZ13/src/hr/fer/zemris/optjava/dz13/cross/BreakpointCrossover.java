package hr.fer.zemris.optjava.dz13.cross;

import hr.fer.zemris.optjava.dz13.AntTrailUtils;
import hr.fer.zemris.optjava.dz13.model.Node;
import hr.fer.zemris.optjava.dz13.model.Solution;

public class BreakpointCrossover implements ICrossover {

	private int maxDepth;
	private int maxNodes;

	public BreakpointCrossover(int maxDepth, int maxNodes) {
		super();
		this.maxDepth = maxDepth;
		this.maxNodes = maxNodes;
	}

	@Override
	public Solution[] cross(Solution parent1, Solution parent2) {
		Solution par1 = null;
		Solution par2 = null;
		do {
			par1 = parent1.duplicate();
			par2 = parent2.duplicate();
			Node root1 = par1.getRoot();
			Node root2 = par2.getRoot();
			Node temp1 = null;
			do {
				temp1 = AntTrailUtils.chooseRandomNode(root1);
			} while (temp1.getParent() == null);
			Node temp2 = null;
			do {
				temp2 = AntTrailUtils.chooseRandomNode(root2);
			} while (temp2.getParent() == null);
			Node tpar1 = temp1.getParent();
			Node tpar2 = temp2.getParent();
			tpar1.replace(temp1, temp2);
			tpar2.replace(temp2, temp1);
		} while (par1.getRoot().getSize() > maxNodes || par2.getRoot().getSize() > maxNodes
				|| par1.getRoot().getDepth() > maxDepth || par2.getRoot().getDepth() > maxDepth);
		return new Solution[] { par1, par2 };
	}
}
