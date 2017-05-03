package gov.utah.dts.det.ccl.documents;

import gov.utah.dts.det.ccl.struts2.result.ClassSerializer;

import java.util.Collections;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Input {

	private final String id;
	private final String label;
	private final Class<?> type;
	private final boolean required;
	private final Map<Object, String> options;
	private Object value;
	private InputDisplayType displayType;
	
	public Input(String id, String label, Object defaultValue, Class<?> type, boolean required, InputDisplayType displayType) {
		this(id, label, defaultValue, type, required, null, displayType);
	}
	
	public Input(String id, String label, Object defaultValue, Class<?> type, boolean required, Map<? extends Object, String> options, InputDisplayType displayType) {
		if (id == null) {
			throw new IllegalArgumentException("Input id must not be null.");
		}
		if (type == null) {
			throw new IllegalArgumentException("Input type must not be null.");
		}
		this.id = id;
		this.label = label;
		this.value = defaultValue;
		this.type = type;
		this.required = required;
		this.options = options == null ? null : Collections.unmodifiableMap(options);
		this.displayType = displayType == null ? InputDisplayType.TEXTFIELD : displayType;
	}

	public String getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	@JsonSerialize(using = ClassSerializer.class)
	public Class<?> getType() {
		return type;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public Map<Object, String> getOptions() {
		return options;
	}
	
	public InputDisplayType getDisplayType() {
		return displayType;
	}
	
	public Object getValue() {
		return value;
	}
	
	/*public void setValue(Object value) {
		if (!value.getClass().isAssignableFrom(type)) {
			throw new IllegalArgumentException(value + " is not a valid " + type);
		}
		this.value = value;
	}*/
	
	@Override
	public String toString() {
		return "Input Id: " + id.toString() + " Display Type: " + displayType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((displayType == null) ? 0 : displayType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + (required ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Input)) {
			return false;
		}
		Input other = (Input) obj;
		if (displayType == null) {
			if (other.displayType != null) {
				return false;
			}
		} else if (!displayType.equals(other.displayType)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		if (options == null) {
			if (other.options != null) {
				return false;
			}
		} else if (!options.equals(other.options)) {
			return false;
		}
		if (required != other.required) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
}