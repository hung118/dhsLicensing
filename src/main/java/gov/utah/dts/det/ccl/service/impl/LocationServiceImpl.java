package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.LocationDao;
import gov.utah.dts.det.ccl.model.Location;
import gov.utah.dts.det.ccl.service.LocationService;
import gov.utah.dts.det.query.SortBy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("locationService")
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;
	
	private Map<LocationKey, String> countyMap = null;
	
	@Override
	public Location loadById(Long id) {
		return locationDao.load(id);
	}
	
	@Override
	public Location saveLocation(Location location) {
		if (countyMap != null) {
			countyMap.clear();
		}
		return locationDao.save(location);
	}
	
	@Override
	public void deleteLocation(Location location) {
		if (countyMap != null) {
			countyMap.clear();
		}
		locationDao.delete(location);
	}
	
	@Override
	public List<Location> getLocations(SortBy sortBy, int page, int resultsPerPage) {
		return locationDao.getLocations(sortBy, page, resultsPerPage);
	}
	
	@Override
	public int getLocationsCount() {
		return locationDao.getLocationsCount();
	}
	
	@Override
	public List<String> getAllZipCodes() {
		return locationDao.getAllZipCodes();
	}
	
	@Override
	public List<Location> getLocationsForZipCode(String zipCode) {
		return locationDao.getLocationsForZipCode(zipCode);
	}
	
	@Override
	public List<Location> searchLocationsByZipCode(String query) {
		return locationDao.searchLocationsByZipCode(query);
	}
	
	@Override
	public String getCounty(String city, String state, String zipCode) {
		if (countyMap == null) {
			List<Location> locations = locationDao.getAllLocations();
			countyMap = new HashMap<LocationKey, String>();
			for (Location loc : locations) {
				countyMap.put(new LocationKey(loc.getCity(), loc.getState(), loc.getZipCode()), loc.getCounty());
			}
		}
		return countyMap.get(new LocationKey(city, state, zipCode));
	}
	
	@Override
	public void evict(Object entity) {
		locationDao.evict(entity);
	}
	
	@SuppressWarnings("serial")
	private static class LocationKey implements Serializable {
		
		private String city;
		private String state;
		private String zipCode;
		
		public LocationKey(String city, String state, String zipCode) {
			this.city = city;
			this.state = state;
			this.zipCode = zipCode;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((city == null) ? 0 : city.hashCode());
			result = prime * result + ((state == null) ? 0 : state.hashCode());
			result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof LocationKey)) {
				return false;
			}
			LocationKey other = (LocationKey) obj;
			if (city == null) {
				if (other.city != null) {
					return false;
				}
			} else if (!city.equals(other.city)) {
				return false;
			}
			if (state == null) {
				if (other.state != null) {
					return false;
				}
			} else if (!state.equals(other.state)) {
				return false;
			}
			if (zipCode == null) {
				if (other.zipCode != null) {
					return false;
				}
			} else if (!zipCode.equals(other.zipCode)) {
				return false;
			}
			return true;
		}
	}
}