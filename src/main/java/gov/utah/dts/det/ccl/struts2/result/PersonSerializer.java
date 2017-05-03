package gov.utah.dts.det.ccl.struts2.result;

import gov.utah.dts.det.ccl.model.Person;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class PersonSerializer extends JsonSerializer<Person> {

	@Override
	public void serialize(Person value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(value.getFirstAndLastName());
	}
}