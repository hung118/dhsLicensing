<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="complaint" value="complaint" scope="request"/>
<ol class="fieldList">
	<s:include value="unlicensed_investigation_detail_fields.jsp"/>
	<security:authorize access="hasPermission('investigation-entry', 'complaint') or hasPermission('follow-up-entry', 'complaint')">
		<li class="submit">
			<security:authorize access="hasPermission('investigation-entry', 'complaint')">
				<s:form action="edit-investigation" method="get" cssClass="ajaxify {target: '#investigationSection'}">
					<s:hidden name="facilityId"/>
					<s:hidden name="complaintId"/>
					<s:submit value="Edit"/>
				</s:form>
			</security:authorize>
			<security:authorize access="hasPermission('follow-up-entry', 'complaint')">
				<s:form action="edit-follow-up" method="get" cssClass="ajaxify {target: '#investigationSection'}">
					<s:hidden name="facilityId"/>
					<s:hidden name="complaintId"/>
					<s:submit value="New Follow Up"/>
				</s:form>
			</security:authorize>
		</li>
	</security:authorize>
</ol>
<div id="followUpSection">
	<s:include value="unlicensed_follow_ups_table.jsp">
		<s:param name="showControls">true</s:param>
	</s:include>
</div>