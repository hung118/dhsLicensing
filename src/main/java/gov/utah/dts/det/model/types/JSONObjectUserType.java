package gov.utah.dts.det.model.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectUserType implements UserType {

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return deepCopy(cached);
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return nullSafeJSONObjectBuilder(value);
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		Object deepCopy = deepCopy(value);
		if (deepCopy != null) {
			return deepCopy.toString();
		}
		return null;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == null) {
			return (y == null);
		}
		return (x.equals(y));
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		assert (x != null);
		return ((JSONObject) x).toString().hashCode();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor si, Object owner) throws HibernateException, SQLException {
		String json = (String) StringType.INSTANCE.get(rs, names[0],si);
		return nullSafeJSONObjectBuilder(json);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor si) throws HibernateException, SQLException {
		if (value == null) {
			StringType.INSTANCE.set(st, null, index, si);
		} else {
			StringType.INSTANCE.set(st, ((JSONObject) value).toString(), index, si);
		}
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return deepCopy(original);
	}

	@Override
	public Class<JSONObject> returnedClass() {
		return JSONObject.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] {
			StringType.INSTANCE.sqlType()
		};
	}
	
	private static JSONObject nullSafeJSONObjectBuilder(Object value) {
		try {
			return ((value != null) ? new JSONObject(value.toString()) : null);
		} catch (JSONException je) {
			throw new RuntimeException(je);
		}
	}
	}
