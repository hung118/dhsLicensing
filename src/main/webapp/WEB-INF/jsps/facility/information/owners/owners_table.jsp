<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<span class="ccl-error-container"></span>
<security:authorize access="hasPermission('edit-details','facility')">
	<s:if test="lstCtrl.showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="newOwnerUrl" action="edit-owner">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newOwnerUrl}" cssClass="ccl-button ajaxify {target: '#ownersSection'}">
					New Owner
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="owners" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="Name">
			<security:authorize access="hasPermission('edit-details','facility')">
				<s:url id="ownerEditUrl" action="edit-owner" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="owner.id" value="#attr.owners.id"/>
				</s:url>
				<s:a href="%{ownerEditUrl}" cssClass="ajaxify {target: '#ownersSection'}">
					<s:property value="#attr.owners.person.firstAndLastName"/>
				</s:a>
			</security:authorize>
			<security:authorize access="!hasPermission('edit-details','facility')">
				<s:property value="#attr.owners.person.firstAndLastName"/>
			</security:authorize>
		</display:column>
		<display:column title="Type">
			<s:if test="#attr.owners.type.equals(primaryOwnerType)">
				Primary
			</s:if>
			<s:else>
				Secondary
			</s:else>
		</display:column>
		<display:column title="Start Date" headerClass="shrinkCol">
			<s:date name="#attr.owners.startDate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="End Date" headerClass="shrinkCol">
			<s:date name="#attr.owners.endDate" format="MM/dd/yyyy"/>
		</display:column>
		<security:authorize access="hasPermission('edit-details','facility')">
			<s:if test="lstCtrl.showControls">
				<display:column class="shrinkCol">
					<s:url id="ownerDeleteUrl" action="delete-facility-person" namespace="/facility/information" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="personId" value="#attr.owners.id"/>
					</s:url>
					<s:a href="%{ownerDeleteUrl}" cssClass="ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</display:column>
			</s:if>
		</security:authorize>
	</display:table>
</s:if>
<script type="text/javascript">
	$("#owners").ccl("tableDeleteNew");
</script>