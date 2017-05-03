package gov.utah.dts.det.ccl.actions.home.alerts;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONException;

public class InspectionTypesIterator implements Iterator<String> {

	private int count = 0;
	private JSONArray jsonArray;
	
	public InspectionTypesIterator(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	
	@Override
	public boolean hasNext() {
		return count < jsonArray.length();
	}
	
	@Override
	public String next() {
		String elem = null;
		try {
			elem = jsonArray.getString(count);
		} catch (JSONException je) {
			throw new NoSuchElementException();
		}
		count++;
		return elem;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove method not supported on this iterator");
	}
}