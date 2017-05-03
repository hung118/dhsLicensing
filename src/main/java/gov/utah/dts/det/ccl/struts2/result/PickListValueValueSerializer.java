package gov.utah.dts.det.ccl.struts2.result;

import gov.utah.dts.det.admin.model.PickListValue;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class PickListValueValueSerializer extends JsonSerializer<PickListValue> {

	@Override
	public void serialize(PickListValue value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(value.getValue());
	}
}