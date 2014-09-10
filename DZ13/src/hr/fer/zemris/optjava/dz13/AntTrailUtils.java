package hr.fer.zemris.optjava.dz13;

import java.util.Random;

import hr.fer.zemris.optjava.dz13.model.Node;
import hr.fer.zemris.optjava.dz13.model.func.Function;
import hr.fer.zemris.optjava.dz13.model.func.IfFoodAhead;
import hr.fer.zemris.optjava.dz13.model.func.Prog2;
import hr.fer.zemris.optjava.dz13.model.func.Prog3;
import hr.fer.zemris.optjava.dz13.model.term.Left;
import hr.fer.zemris.optjava.dz13.model.term.Move;
import hr.fer.zemris.optjava.dz13.model.term.Right;
import hr.fer.zemris.optjava.dz13.model.term.Terminal;

public class AntTrailUtils {

	private static final Random rand = new Random();
	
	public static Node generateInitialTree(int maxDepth, int maxNodes, boolean grow) {
		Node temp = getRandomFunction(null);
		int chCount = temp.childrenCount();
		for (int i = 0; i < chCount; i++) {
			Node t = generateRandomTree(temp, maxDepth - 1, maxNodes, grow);
			maxNodes -= t.getSize();
			((Function) temp).addChild(t);
		}
		return temp;
	}
	
	public static Node generateRandomTree(Node parent, int maxDepth, int maxNodes, boolean grow) {
		if (maxDepth <= 1 || maxNodes <= 4) {
			return getRandomTerminal(parent);
		}
		Node temp = grow ? getRandomNode(parent) : getRandomFunction(parent);
		int chCount = temp.childrenCount();
		maxNodes -= chCount;
		if (chCount > 0) {
			for (int i = 0; i < chCount; i++) {
				Node t = generateRandomTree(temp, maxDepth - 1, maxNodes, grow);
				maxNodes -= t.getSize();
				((Function) temp).addChild(t);
			}
		}
		return temp;
	}
	
	public static Node getRandomNode(Node parent) {
		int t = rand.nextInt(2);
		switch (t) {
		case 0:
			return getRandomFunction(parent);
		case 1:
			return getRandomTerminal(parent);
		default:
			return null;
		}
	}
	
	public static Function getRandomFunction(Node parent) {
		int t = rand.nextInt(3);
		switch (t) {
		case 0:
			return new IfFoodAhead(parent);
		case 1:
			return new Prog2(parent);
		case 2:
			return new Prog3(parent);
		default:
			return null;
		}
	}
	
	public static Terminal getRandomTerminal(Node parent) {
		int t = rand.nextInt(3);
		switch (t) {
		case 0:
			return new Left(parent);
		case 1:
			return new Move(parent);
		case 2:
			return new Right(parent);
		default:
			return null;
		}
	}
	
	public static Node chooseRandomNode(Node root) {
		int chCount = root.childrenCount();
		if (chCount == 0) {
			return root;
		}
		int[] array = new int[chCount];
		int sum = 1;
		for (int i = 0; i < chCount; i++) {
			int temp = root.getChild(i).getSize();
			array[i] = temp;
			sum += temp;
		}
		int num = rand.nextInt(sum);
		if (num == 0) {
			return root;
		}
		num--;
		for (int i = 0; i < chCount; i++) {
			if (num < array[i]) {
				return chooseRandomNode(root.getChild(i));
			}
			num -= array[i];
		}
		return root.getChild(chCount - 1);
	}
	
	public static Node findRoot(Node node) {
		Node temp = node;
		while (temp.getParent() != null) {
			temp = temp.getParent();
		}
		return temp;
	}
	
}
