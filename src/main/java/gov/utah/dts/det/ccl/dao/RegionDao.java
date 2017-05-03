package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.dao.AbstractBaseDao;

import java.util.Set;

public interface RegionDao extends AbstractBaseDao<Region, Long> {

	public Set<Region> getRegions(boolean fetchPeople);
	
	public void updateRegions();
}