package gov.utah.dts.det.repository;

import gov.utah.dts.det.admin.model.PickListValue;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PickListValueRepository extends JpaRepository<PickListValue, Long> {

	public List<PickListValue> findByPickListName(String pickListName, Sort sort);
	
	public PickListValue findByPickListNameAndValue(String pickListName, String pickListValue, Sort sort);
	
	@Query("select plv from PickListValue plv where plv.pickList.defaultValue = plv.id and plv.pickList.name = :pickListName")
	public PickListValue findDefaultPickListValue(@Param("pickListName") String pickListName);
	
	@Query("select plv from PickListValue plv where plv.pickList.name = :pickListName and upper(plv.value) like upper(:searchValue || '%') order by plv.sortOrder, plv.value")
	public List<PickListValue> searchPickListByName(@Param("pickListName") String pickListName, @Param("searchValue") String searchValue);
}