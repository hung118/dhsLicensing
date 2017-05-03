package gov.utah.dts.det.ccl.view;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.view.KeyValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.ValidationAware;

public class ViewUtils {
	
	private static List<KeyValuePair> times;

	private ViewUtils() {
		
	}
	
	public static String replaceNewlines(String input) {
		return input.replaceAll("\n", "<br/>");
	}
	
	public static KeyValuePair getDefaultKeyValuePairId(List<KeyValuePair> keyValuePairs) {
		for (KeyValuePair kvp : keyValuePairs) {
			if (kvp.isDefaultValue()) {
				return kvp;
			}
		}
		return null;
	}
	
	public static List<KeyValuePair> getTimes() {
		if (times == null) {
			times = new ArrayList<KeyValuePair>();
			for (int i = 0; i < 2; i++) {
				String amPm = i == 0 ? "AM" : "PM";
				for (int j = 0; j < 12; j++) {
					String hour = j == 0 ? "12" : Integer.toString(j);
					for (int k = 0; k < 4; k++) {
						StringBuilder time = new StringBuilder();
						time.append(hour);
						time.append(':');
						time.append(k == 0 ? "00" : k * 15);
						time.append(' ');
						time.append(amPm);
						String value = time.toString();
						if (hour.length() < 2) {
							time.insert(0, "0"); 
						}
						times.add(new KeyValuePair(time.toString(), value));
					}
				}
			}
		}
		return times;
	}
	
	public static <T extends PickListValue> void managePickListCollection(List<T> newValues, List<T> modelCollection) {
		Map<Long, T> tempMap = new HashMap<Long, T>();
		if (newValues != null) {
			for (T value : newValues) {
				tempMap.put(value.getId(), value);
			}
		}
		
		//iterate through all values removing the ones that are not still attached
		for (Iterator<T> itr = modelCollection.iterator(); itr.hasNext();) {
			T value = itr.next();
			T current = tempMap.remove(value.getId());
			if (current == null) {
				//the item is not in the current list so remove it
				itr.remove();
			}
		}
		
		for (T value : tempMap.values()) {
			modelCollection.add(value);
		}
	}
	
	public static void addActionErrors(ValidationAware validationAware, TextProvider textProvider, List<String> errorCodes) {
		for (String errorCode : errorCodes) {
			String viewErrorCode = textProvider.getText(errorCode);
			if (viewErrorCode != null) {
				if (viewErrorCode.startsWith("fmt")) {
					String[] pieces = viewErrorCode.split(",");
					String[] args = new String[pieces.length - 2];
					for (int i = 2, j = 0; i < pieces.length; i++, j++) {
						args[j] = textProvider.getText(pieces[i]);
					}
					validationAware.addActionError(textProvider.getText(pieces[1], args));
				} else {
					validationAware.addActionError(viewErrorCode);
				}
			} else {
				validationAware.addActionError(errorCode);
			}
		}
	}
	
	public static void addFieldErrors(ValidationAware validationAware, Map<String, String> errors) {
		for (Entry<String, String> error : errors.entrySet()) {
			validationAware.addFieldError(error.getKey(), error.getValue());
		}
	}
	
	public static JsonResponse getJsonResponse(ValidationAware validationAware) {
		if (validationAware.hasErrors()) {
			Map<String, Object> response = new HashMap<String, Object>();
			if (validationAware.hasActionErrors()) {
				response.put("errors", validationAware.getActionErrors());
			}
			if (validationAware.hasFieldErrors()) {
				response.put("fieldErrors", validationAware.getFieldErrors());
			}
			return new JsonResponse(400, response);
		} else {
			return new JsonResponse(200);
		}
	}
	
	public static JsonResponse getErrorResponse(int errorCode, String error) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errorList = new ArrayList<String>();
		errorList.add(error);
		response.put("errors", errorList);
		return new JsonResponse(errorCode, response); 
	}
}