package gov.utah.dts.det.service;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.ApplicationProperty;

import java.util.List;

public interface ApplicationService {

	/**
	 * /**
	 * Gets the pick list values associated with the application property with the given key.  The application property value should be a list
	 * of id's referring to primary keys of the <code>PickListValue</code>s
	 * 
	 * @param propertyKey the <code>ApplicationProperty</code> key
	 * @return a list of the <code>PickListValue</code>s associated with the application property.  This will always be non-null.  If there
	 *     are no keys associated with the <code>ApplicationProperty</code> then an empty list is returned.
	 * @throws IllegalArgumentException if <code>propertyKey</code> is null or there are no <code>ApplicationProperty</code>s with the given key
	 * @throws NumberFormatException if the <code>ApplicationPorperty</code>'s value is not a parseable list of ids
	 */
	public List<PickListValue> getPickListValuesForApplicationProperty(String propertyKey) throws IllegalArgumentException, NumberFormatException;
	
	/**
	 * Gets a single pick list value associated with the application property with the given key.  The application property value should be an
	 * id referring to the primary key of the <code>PickListValue</code>.
	 * 
	 * @param propertyKey the <code>ApplicationProperty</code> key
	 * @return the <code>PickListValue</code> referred to by the application property or null if the value is blank.
	 * @throws IllegalArgumentException if <code>propertyKey</code> is null, there are no <code>ApplicationProperty</code>s with the given key,
	 *     or there is more than one <code>PickListValue</code> associated with the key
	 * @throws NumberFormatException if the <code>ApplicationPorperty</code>'s value is not a parseable id
	 */
	public PickListValue getPickListValueForApplicationProperty(String propertyKey) throws IllegalArgumentException, NumberFormatException;
	
	/**
	 * Determines if the given pick list value is contained in the the application property with the given key.
	 * 
	 * @param value the <code>PickListValue</code> to check
	 * @param propertyKey the key for the <code>ApplicationProperty</code> to check
	 * @return whether or not the value is contained in the application property.
	 * @throws IllegalArgumentException if <code>value</code> or <code>propertyKey</code> are null or there is no <code>ApplicationProperty</code>
	 *     with the given key.
	 * @throws NumberFormatException if the <code>ApplicationProperty</code>'s value is not a parseable list of ids.
	 */
	public boolean propertyContainsPickListValue(PickListValue value, String propertyKey) throws IllegalArgumentException, NumberFormatException;
	
	/**
	 * Finds the <code>ApplicationProperty</code> with the given key.
	 * 
	 * @param propertyKey the key for the <code>ApplicationProperty</code> to find. 
	 * @return the <code>ApplicationProperty</code> or null if there is no <code>ApplicationProperty</code> with the given key.
	 */
	public ApplicationProperty findApplicationPropertyByKey(String propertyKey);
	
	/**
	 * Creates or updates an <code>ApplicationProperty</code>
	 * 
	 * @param property the <code>ApplicationProperty</code> to save
	 * @return the saved <code>ApplicationProperty</code>
	 */
	public ApplicationProperty saveApplicationProperty(ApplicationProperty property);
	
	/**
	 * Gets the value of the <code>ApplicationProperty</code> with the given key.
	 * 
	 * @param propertyKey the key for the <code>ApplicationProperty</code>.
	 * @return the value field of the associated <code>ApplicationProperty</code> or null if there is not an <code>ApplicationProperty</code>
	 *     with the given key.
	 * @throws IllegalArgumentException if the the propertyKey is null
	 */
	public String getApplicationPropertyValue(String propertyKey) throws IllegalArgumentException;
}