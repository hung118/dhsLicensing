package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.model.StateChange;
import gov.utah.dts.det.ccl.repository.StateChangeRepository;
import gov.utah.dts.det.ccl.service.StateObjectService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("stateObjectService")
public class StateObjectServiceImpl implements StateObjectService {

	@Autowired
	private StateChangeRepository stateChangeRepository;
	
	@Override
	public List<StateChange> getObjectStateChanges(Long objectId) {
		return stateChangeRepository.findByStateObjectId(objectId);
	}
}