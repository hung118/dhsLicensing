package gov.utah.dts.det.ccl.view;

import gov.utah.dts.det.util.CompareUtils;

public class KeyValuePair implements Comparable<KeyValuePair> {

	private String key;
	private String value;
	private boolean active = true;
	
	private Long id;
	private String label;
	
	public KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public KeyValuePair(String key, String value, boolean active) {
		this.key = key;
		this.value = value;
		this.active = active;
	} 
	
	public KeyValuePair(Long id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isActive() {
		return active;
	}
	
	@Override
	public int compareTo(KeyValuePair o) {
		if (this == o) {
			return 0;
		}
		
		if (active && !o.active) {
			return -1;
		} else if (!active && o.active) {
			return 1;
		}
		
		return CompareUtils.nullSafeComparableCompare(value, o.value, false);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}