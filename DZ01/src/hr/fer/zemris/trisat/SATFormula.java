package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.List;

public class SATFormula {
	
	private int numberOfVariables;
	private Clause[] clauses = null;
	
	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.numberOfVariables = numberOfVariables;
		this.clauses = clauses;
	}
	
	public int getNumberOfVariables() {
		return numberOfVariables;
	}
	
	public int getNumberOfClauses() {
		return clauses.length;
	}
	
	public Clause getClause(int index) {
		return clauses[index];
	}
	
	public boolean isSatisfied(BitVector assignment) {
		for (Clause clause : clauses) {
			boolean satisfied = clause.isSatisfied(assignment);
			if (satisfied == false) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Clause clause : clauses) {
			sb.append("(");
			sb.append(clause.toString());
			sb.append(")");
		}
		return sb.toString();
	}
	
	public static SATFormula getClauses(List<String> lines) {
		String line = lines.get(0);
		String[] parts = line.split("\\s+");
		if (!parts[0].equals("p")) {
			throw new IllegalArgumentException("Prvi red mora počinjati s 'p' !");
		}
		int variableNum = Integer.parseInt(parts[2]);
		int clausesNum = Integer.parseInt(parts[3]);
		List<Clause> clauses = new ArrayList<>();
		for (int i = 0; i < clausesNum; i++) {
			line = lines.get(i + 1);
			parts = line.split("\\s+");
			int[] indexes = parseIndexes(parts);
			clauses.add(new Clause(indexes));
		}
		Clause[] array = new Clause[clauses.size()];
		array = clauses.toArray(array);
		return new SATFormula(variableNum, array);
	}
	
	private static int[] parseIndexes(String[] parts) {
		int length = parts.length - 1;
		int[] field = new int[length];
		for (int i = 0; i < length; i++) {
			field[i] = Integer.parseInt(parts[i]);
		}
		return field;
	}
	
}
