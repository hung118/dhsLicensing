package gov.utah.dts.det.ccl.model;

public interface ChangeLoggedEntity {
	
	/**
	 * Gets the properties that require changes to be logged.
	 * 
	 * @return a list of the properties that should be logged when updated.
	 */
	public String[] getLoggedProperties();
}