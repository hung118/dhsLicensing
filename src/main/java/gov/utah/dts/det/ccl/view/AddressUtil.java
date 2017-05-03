package gov.utah.dts.det.ccl.view;

import gov.utah.dts.det.ccl.model.Location;
import gov.utah.dts.det.ccl.service.LocationService;
import gov.utah.dts.det.util.spring.AppContext;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

//@Component
public class AddressUtil {
	
	private AddressUtil() {
		
	}
	
	/*@Autowired
	private LocationService locationService;
	
	public void updateAddress(Address address) {
		if (address == null) {
			return;
		}
		String city = null;
		String state = null;
		if (address.getZipCode() != null) {
			List<Location> locations = locationService.getLocationsForZipCode(address.getZipCode());
			if (locations.size() == 1) {
				city = locations.get(0).getCity();
				state = locations.get(0).getState();
			} else if (locations.size() > 1) {
				for (Location location : locations) {
					if (location.getCity().equals(address.getCity())) {
						city = location.getCity();
						state = location.getState();
					}
				}
			}
		}
		address.setCity(city);
		address.setState(state);
	}*/
	
	public static final List<Location> getLocations(Address address) {
		List<Location> locations = new ArrayList<Location>();
		if (address != null && StringUtils.isNotEmpty(address.getZipCode())) {
			LocationService locationService = (LocationService) AppContext.getApplicationContext().getBean("locationService");
			locations = locationService.getLocationsForZipCode(address.getZipCode());
		}
		return locations;
	}
}