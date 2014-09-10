package hr.fer.zemris.optjava.dz13.mut;

import hr.fer.zemris.optjava.dz13.AntTrailUtils;
import hr.fer.zemris.optjava.dz13.model.Node;
import hr.fer.zemris.optjava.dz13.model.Solution;

public class SwitchMutation implements IMutation {

	private int maxDepth;
	private int maxNodes;
	
	public SwitchMutation(int maxDepth, int maxNodes) {
		super();
		this.maxDepth = maxDepth;
		this.maxNodes = maxNodes;
	}

	@Override
	public Solution mutate(Solution solution) {
		Node root = solution.getRoot();
		Node temp = root;
		while (root == temp) {
			temp = AntTrailUtils.chooseRandomNode(root);
		}
		int depth = maxDepth - temp.getDepth();
		Node newChild = AntTrailUtils.generateRandomTree(null, depth, maxNodes - root.getSize() + temp.getSize(), true);
		temp.getParent().replace(temp, newChild);
		return solution;
	}

}
