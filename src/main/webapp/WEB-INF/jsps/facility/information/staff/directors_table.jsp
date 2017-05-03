<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<span class="ccl-error-container"></span>
<security:authorize access="hasPermission('edit-details','facility')">
	<s:if test="lstCtrl.showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="newDirectorUrl" action="directors-screened-people-list">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newDirectorUrl}" cssClass="ccl-button ajaxify {target: '#directorsSection'}">
					New Director
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="directors" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="First Name">
			<security:authorize access="hasPermission('edit-details','facility')">
				<s:url id="directorEditUrl" action="edit-director" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="director.id" value="#attr.directors.id"/>
				</s:url>
				<s:a href="%{directorEditUrl}" cssClass="ajaxify {target: '#directorsSection'}">
					<s:property value="#attr.directors.person.firstAndLastName"/>
				</s:a>
			</security:authorize>
			<security:authorize access="!hasPermission('edit-details','facility')">
				<s:property value="#attr.directors.person.firstAndLastName"/>
			</security:authorize>
		</display:column>
		<display:column title="Type">
			<s:if test="#attr.directors.type.equals(firstDirectorType)">
				1st
			</s:if>
			<s:else>
				2nd
			</s:else>
		</display:column>
		<display:column title="Start Date" headerClass="shrinkCol">
			<s:date name="#attr.directors.startDate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="End Date" headerClass="shrinkCol">
			<s:date name="#attr.directors.endDate" format="MM/dd/yyyy"/>
		</display:column>

		<security:authorize access="hasPermission('edit-details','facility')">
			<s:if test="lstCtrl.showControls">
				<display:column class="shrinkCol">
					<s:url id="directorDeleteUrl" action="delete-facility-person" namespace="/facility/information" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="personId" value="#attr.directors.id"/>
					</s:url>
					<s:a href="%{directorDeleteUrl}" cssClass="ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</display:column>
			</s:if>
		</security:authorize>
	</display:table>
</s:if>
<script type="text/javascript">
	$("#directors").ccl("tableDeleteNew");
</script>