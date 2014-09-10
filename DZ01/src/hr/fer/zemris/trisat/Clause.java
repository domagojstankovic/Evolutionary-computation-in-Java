package hr.fer.zemris.trisat;

public class Clause {
	
	private int[] indexes;
	
	public Clause(int[] indexes) {
		this.indexes = indexes;
	}
	
	public int getSize() {
		return indexes.length;
	}
	
	public int getLiteral(int index) {
		return indexes[index];
	}
	
	public boolean isSatisfied(BitVector assignment) {
		for (int i : indexes) {
			int index = Math.abs(i);
			boolean value = assignment.get(index - 1);
			if ((value == true && i > 0) || (value == false && i < 0)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int length = indexes.length;
		for (int i = 0; i < length; i++) {
			if (i > 0) {
				sb.append(" + ");
			}
			sb.append(indexes[i]);
		}
		
		return sb.toString();
	}
	
}
