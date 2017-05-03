package gov.utah.dts.det.ccl.view.components;

import gov.utah.dts.det.ccl.model.Location;
import gov.utah.dts.det.ccl.view.AddressUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.util.ValueStack;

public class Address extends UIBean {

	public static final String TEMPLATE = "address";
//	private static String[] LOCATION_FIELD_NAMES = {"city", "state", "zipCode", "county"};
	
	public Address(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }
	
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}
	
	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		
		List<Location> locations = AddressUtil.getLocations((gov.utah.dts.det.ccl.view.Address) parameters.get("nameValue"));
		if (locations.size() > 0) {
			JSONArray locArr = new JSONArray();
			for (Location loc : locations) {
				try {
					JSONObject jobj = new JSONObject();
					jobj.put("city", loc.getCity());
					jobj.put("state", loc.getState());
					jobj.put("zipCode", loc.getZipCode());
					jobj.put("county", loc.getCounty());
					locArr.put(jobj);
				} catch (JSONException je) {}
			}
			addParameter("locationsStr", locArr.toString());
		}
		if (locations.size() == 1) {
			addParameter("county", locations.get(0).getCounty());
		}
	}

    @SuppressWarnings("unchecked")
	@Override
	protected Class getValueClassType() {
        return null; // don't convert nameValue to anything, we need the raw value
    }
}