package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Finding;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FindingDao extends JpaRepository<Finding, Long> {

}