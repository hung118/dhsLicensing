package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.RegionDao;
import gov.utah.dts.det.ccl.model.Address;
import gov.utah.dts.det.ccl.model.Location;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.service.LocationService;
import gov.utah.dts.det.ccl.service.RegionService;
import gov.utah.dts.det.ccl.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("regionService")
public class RegionServiceImpl implements RegionService {
	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Region loadRegion(Long regionId) {
		return regionDao.load(regionId);
	}
	
	@Override
	public Region saveRegion(Region region) {
		region = regionDao.save(region);
		regionDao.updateRegions();
		return region;
	}
	
	@Override
	public void deleteRegion(Region region) {
		regionDao.delete(region);
	}
	
	@Override
	public Set<Region> getRegions(boolean fetchPeople) {
		return regionDao.getRegions(fetchPeople);
	}
	
	@Override
	public List<Person> getPeopleInRegion(Long regionId) {
		List<Person> peopleInRegion = new ArrayList<Person>();
		Region region = loadRegion(regionId);
		peopleInRegion.add(region.getOfficeSpecialist());
		peopleInRegion.addAll(region.getLicensingSpecialists());
		
		Collections.sort(peopleInRegion);
		return peopleInRegion;
	}

	@Override
	public List<Person> getSpecialistsInAllRegions() {
		List<Person> people = new ArrayList<Person>();
		Set<Region> regions = getRegions(true);
		for (Region region : regions) {
			Set<Person> specialists = region.getLicensingSpecialists();
			for (Person person : specialists) {
				if (person.isActive() && !people.contains(person)) {
					people.add(person);
				}
			}
		}
		Collections.sort(people);
		return people;
	}

	@Override
	public List<Person> getSpecialistsNotAssignedToRegion(Long id) {
		HashMap<Long, Person> map = new HashMap<Long, Person>();
		Set<Person> temp = userService.getPeople(RoleType.ROLE_LICENSOR_SPECIALIST, true, true, false);
		if (temp != null && temp.size() > 0) {
			// Build a map of all licensing specialists
			for (Person p : temp) {
				if (!map.containsKey(p.getId())) {
					map.put(p.getId(), p);
				}
			}
			Set<Region> regions = getRegions(true);
			// Process all region licensing specialists
			for (Region region : regions) {
				// Don't remove specialists assigned to the specified region
				if (id == null || !id.equals(region.getId())) {
					Set<Person> regionSpecialists = region.getLicensingSpecialists();
					for (Person p : regionSpecialists) {
						// If specialist is found in map of all specialists, remove it from map
						if (map.containsKey(p.getId())) {
							map.remove(p.getId());
						}
					}
				}
			}
		}
		return new ArrayList<Person>(map.values());
	}

	@Override
	public Region getRegionForSpecialist(Long specialistId) {
		if (specialistId != null) {
			Set<Region> regions = getRegions(true);
			for (Region r : regions) {
				for (Person p : r.getLicensingSpecialists()) {
					if (p.getId().equals(specialistId)) {
						return r;
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public Region getRegionForAddress(Address address) {
		Region r = null;
		if (address != null) {
			List<Location> locations = locationService.getLocationsForZipCode(address.getZipCode());
			for (Location loc : locations) {
				if (r == null || (!loc.getRegion().getId().equals(r.getId()) && loc.getCity().equals(address.getCity())
						&& loc.getState().equals(address.getState()))) {
					r = loc.getRegion();
				}
			}
		}
		return r;
	}
}