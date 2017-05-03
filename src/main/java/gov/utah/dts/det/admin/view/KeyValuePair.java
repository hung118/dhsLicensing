package gov.utah.dts.det.admin.view;

public class KeyValuePair {

	private Long id;
	private String name;
	private String value;
	private boolean active;
	private boolean defaultValue;
	
	public KeyValuePair() {
		
	}
	
	public KeyValuePair(String name, String value) {
		this(null, name, value, null, null);
	}
	
	public KeyValuePair(String name, String value, Boolean active) {
		this(null, name, value, active, null);
	}
	
	public KeyValuePair(String name, String value, Boolean active, Boolean defaultValue) {
		this(null, name, value, active, defaultValue);
	}
	
	public KeyValuePair(Long id, String value) {
		this(id, null, value, null, null);
	}
	
	public KeyValuePair(Long id, String value, Boolean active) {
		this(id, null, value, active, null);
	}
	
	public KeyValuePair(Long id, String value, Boolean active, Boolean defaultValue) {
		this(id, null, value, active, defaultValue);
	}
	
	protected KeyValuePair(Long id, String name, String value, Boolean active, Boolean defaultValue) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.active = active == null ? true : active.booleanValue();
		this.defaultValue = defaultValue == null ? false : defaultValue.booleanValue();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		if (name == null) {
			return id.toString();
		}
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
}