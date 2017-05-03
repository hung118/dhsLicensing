package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Location;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface LocationDao extends AbstractBaseDao<Location, Long> {
	
	public List<Location> getAllLocations();

	public List<Location> getLocations(SortBy sortBy, int page, int resultsPerPage);
	
	public int getLocationsCount();
	
	public List<String> getAllZipCodes();
	
	public List<Location> getLocationsForZipCode(String zipCode);
	
	public List<Location> searchLocationsByZipCode(String query);
}