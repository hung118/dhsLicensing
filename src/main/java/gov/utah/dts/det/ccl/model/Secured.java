package gov.utah.dts.det.ccl.model;

import java.util.Date;

public interface Secured {
	
	/**
	 * Gets the id of the owner of this object.
	 * 
	 * @return The id of the owner of this object.
	 */
	public Long getOwnerId();

	/**
	 * Gets whether or not this object is editable.
	 * 
	 * @return true if this object is editable, false otherwise.
	 */
	public boolean isEditable();
	
	/**
	 * Sets whether or not this object is editable.
	 * 
	 * @param editable whether or not this object should be editable.
	 */
	public void setEditable(boolean editable);
	
	/**
	 * Gets the number of days this is to remain editable by non-admins and non-managers.
	 * 
	 * @return The number of days before this object to no longer editable by non-admins and non-managers.
	 */
	public int getDaysUntilUneditable();
	
	/**
	 * Gets the date to base the security calculation off of.  This will normally be the date this object was created.
	 * The security service will calculate the date this object becomes uneditable by taking this date and adding the number
	 * of days from <code>getDaysUntilUneditable</code> to the date.  If today's date is after the calculated date then the
	 * object will not be editable by non-admins and non-managers.
	 * 
	 * @return The date to base the security calculation off of.
	 */
	public Date getSecurityDate();
}