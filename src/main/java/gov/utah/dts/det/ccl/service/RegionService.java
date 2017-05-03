package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Address;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Region;

import java.util.List;
import java.util.Set;

public interface RegionService {
	
	public Region loadRegion(Long regionId);
	
	public Region saveRegion(Region region);
	
	public void deleteRegion(Region region);
	
	public Set<Region> getRegions(boolean fetchPeople);
	
	public List<Person> getPeopleInRegion(Long regionId);

	public List<Person> getSpecialistsInAllRegions();
	
	public List<Person> getSpecialistsNotAssignedToRegion(Long id);

	public Region getRegionForSpecialist(Long specialistId);
	
	public Region getRegionForAddress(Address address);
}