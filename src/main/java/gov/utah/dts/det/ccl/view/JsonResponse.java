package gov.utah.dts.det.ccl.view;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonResponse {

	private Integer status;
	private Object responseObject;

	public JsonResponse(Integer status) {
		this.status = status;
	}
	
	public JsonResponse(Integer status, Object responseObject) {
		this.status = status;
		this.responseObject = responseObject;
	}

	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Object getResponseObject() {
		return responseObject;
	}
	
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	
	public String getResponseAsString() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(responseObject);
	}
}