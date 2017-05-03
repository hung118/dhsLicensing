<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<span class="ccl-error-container"></span>
<security:authorize access="hasPermission('edit-details','facility')">
	<s:if test="lstCtrl.showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="editBMUrl" action="edit-board-member">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{editBMUrl}" cssClass="ccl-button ajaxify {target: '#boardMembersSection'}">
					New Board Member
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="boardMembers" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="Name">
			<security:authorize access="hasPermission('edit-details','facility')">
				<s:url id="boardMemberEditUrl" action="edit-board-member" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="boardMember.id" value="#attr.boardMembers.id"/>
				</s:url>
				<s:a href="%{boardMemberEditUrl}" cssClass="ajaxify {target: '#boardMembersSection'}">
					<s:property value="#attr.boardMembers.person.firstAndLastName"/>
				</s:a>
			</security:authorize>
			<security:authorize access="!hasPermission('edit-details','facility')">
				<s:property value="#attr.boardMembers.person.firstAndLastName"/>
			</security:authorize>
		</display:column>
		<display:column title="Start Date">
			<s:date name="#attr.boardMembers.startDate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="End Date">
			<s:date name="#attr.boardMembers.endDate" format="MM/dd/yyyy"/>
		</display:column>
		<security:authorize access="hasPermission('edit-details','facility')">
			<s:if test="lstCtrl.showControls">
				<display:column>
					<s:url id="bmDeleteUrl" action="delete-facility-person" namespace="/facility/information" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="personId" value="#attr.boardMembers.id"/>
					</s:url>
					<s:a href="%{bmDeleteUrl}" cssClass="ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</display:column>
			</s:if>
		</security:authorize>
	</display:table>
</s:if>
<script type="text/javascript">
	$("#boardMembers").ccl("tableDeleteNew");
</script>