package gov.utah.dts.det.admin.dao;

import gov.utah.dts.det.admin.model.PickListValueRelationship;
import gov.utah.dts.det.admin.model.PickListValueRelationshipPk;
import gov.utah.dts.det.admin.view.KeyValuePair;
import gov.utah.dts.det.dao.AbstractBaseDao;

import java.util.List;

public interface PickListValueRelationshipDao extends AbstractBaseDao<PickListValueRelationship, PickListValueRelationshipPk> {

	List<PickListValueRelationship> getAllChildPickListValuesInRelationship(String relationshipType);

	List<KeyValuePair> getChildValuesForPickListValueAsKeyValuePairs(Long pickListValueId, String relationshipType,
			boolean includeOnlyActive);
}