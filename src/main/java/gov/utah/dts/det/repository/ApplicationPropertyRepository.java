package gov.utah.dts.det.repository;

import gov.utah.dts.det.ccl.model.ApplicationProperty;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationPropertyRepository extends JpaRepository<ApplicationProperty, String> {
	
	public ApplicationProperty findByName(String name);
}