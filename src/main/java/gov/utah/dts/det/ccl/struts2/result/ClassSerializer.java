package gov.utah.dts.det.ccl.struts2.result;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class ClassSerializer extends JsonSerializer<Class<?>> {

	@Override
	public void serialize(Class<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(value.getSimpleName());
	}
}