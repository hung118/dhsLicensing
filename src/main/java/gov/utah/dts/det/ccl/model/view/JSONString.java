package gov.utah.dts.det.ccl.model.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

@SuppressWarnings("serial")
@Embeddable
public class JSONString implements Serializable {

	@Column(name = "JSON_STRING")
	private String jsonString;
	
	public JSONString() {
		
	}
	
	public JSONString(String json) {
		this.jsonString = json;
	}
	
	public String getJsonString() {
		return jsonString;
	}
	
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
	public Object getValue(String key) throws JSONException {
		JSONTokener tokener = new JSONTokener(jsonString);
		JSONObject jsonObject = new JSONObject(tokener);
		return jsonObject.get(key);
	}
	
	//TODO: implement setValue
}