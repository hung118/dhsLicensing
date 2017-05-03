package gov.utah.dts.det.query;

public class WhereClause {

	private StringBuilder queryString;
	private boolean previousClauses = false;
	
	public WhereClause(StringBuilder queryString) {
		this.queryString = queryString;
	}
	
	public void addClause(String clause) {
		if (previousClauses) {
			queryString.append(" and ");
		}
		queryString.append(clause);
		previousClauses = true;
	}
	
	public boolean hasClauses() {
		return previousClauses;
	}
}
