package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Location;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface LocationService {

	public Location loadById(Long id);
	
	public Location saveLocation(Location location);
	
	public void deleteLocation(Location location);
	
	public List<Location> getLocations(SortBy sortBy, int page, int resultsPerPage);
	
	public int getLocationsCount();
	
	public List<String> getAllZipCodes();
	
	public List<Location> getLocationsForZipCode(String zipCode);
	
	public List<Location> searchLocationsByZipCode(String query);
	
	public String getCounty(String city, String state, String zipCode);
	
	public void evict(final Object entity);
}