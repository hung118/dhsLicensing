<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authorize access="hasPermission('edit-details','facility')">
	<s:if test="showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="newAssocFacUrl" action="edit-associated-facility">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newAssocFacUrl}" cssClass="ccl-button ajaxify {target: '#associatedFacilitiesSection'}">
					New Association
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!associatedFacilities.isEmpty">
	<display:table name="associatedFacilities" id="associatedFacilities" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="Facility Name">
			<s:if test="hasFacilityEntityPermission('edit-details', #attr.associatedFacilities.child.id)">
				<s:url id="facilityEditUrl" action="edit-facility" namespace="/facility" includeParams="false">
					<s:param name="facilityId" value="#attr.associatedFacilities.child.id"/>
				</s:url>
				<a href="<s:property value='facilityEditUrl'/>" target="_blank">
					<s:property value="#attr.associatedFacilities.child.name" />
				</a>
			</s:if>
			<s:else>
				<s:property value="#attr.associatedFacilities.child.name"/>
			</s:else>
		</display:column>
		<display:column title="Address">
			<s:component template="addressdisplay.ftl">
				<s:param name="address" value="#attr.associatedFacilities.child.locationAddress"/>
			</s:component>
		</display:column>
		<display:column title="Begin Date" headerClass="shrinkCol">
			<s:date name="#attr.associatedFacilities.beginDate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="End Date" headerClass="shrinkCol">
			<s:date name="#attr.associatedFacilities.endDate" format="MM/dd/yyyy"/>
		</display:column>
		<security:authorize access="hasPermission('edit-details','facility')">
			<display:column class="shrinkCol">
				<s:url id="associationEditUrl" action="edit-associated-facility" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="associatedFacility.id" value="#attr.associatedFacilities.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#associatedFacilitiesSection'}" href="%{associationEditUrl}">
					edit
				</s:a>
				|
				<s:url id="associationDeleteUrl" action="delete-associated-facility" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="associatedFacility.id" value="#attr.associatedFacilities.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#associatedFacilitiesSection'} ccl-action-delete" href="%{associationDeleteUrl}">
					delete
				</s:a>
			</display:column>
		</security:authorize>
	</display:table>
</s:if>