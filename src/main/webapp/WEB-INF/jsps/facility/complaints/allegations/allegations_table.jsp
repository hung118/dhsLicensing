<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="showControls">${param.showControls == null ? false : param.showControls}</s:set>
<s:set var="complaint" value="complaint" scope="request"/>
<s:set var="editable" value="%{false}"/>
<security:authorize access="hasPermission('allegation-entry', 'complaint')">
	<s:if test="#attr.showControls == 'true'">
		<s:set var="editable" value="%{true}"/>
	</s:if>
</security:authorize>
<s:if test="#editable">
	<div class="topControls">
		<div class="mainControls">
			<s:form action="edit-allegation" method="get" cssClass="ajaxify {target: '#allegationsContent'}">
				<s:hidden name="facilityId"/>
				<s:hidden name="complaintId"/>
				<s:submit value="New Allegation"/>
			</s:form>
		</div>
	</div>
</s:if>
<s:if test="!allegations.isEmpty">
	<display:table name="allegations" id="allegations" class="tables">
		<display:column title="Rule #" class="nowrap">
			<s:if test="#editable">
				<s:url id="editAllegationUrl" action="edit-allegation" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="allegation.id" value="#attr.allegations.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#allegationsContent'}" href="%{editAllegationUrl}">
					<ccl:rule ruleId="#attr.allegations.rule.id" format="RNUM"/>
				</s:a>
			</s:if>
			<s:else>
				<ccl:rule ruleId="#attr.allegations.rule.id" format="RNUM"/>
			</s:else>
		</display:column>
		<display:column title="Rule Description">
			<ccl:rule ruleId="#attr.allegations.rule.id" format="RDESC"/>
		</display:column>
		<display:column title="Outcome">
			<s:if test="#attr.allegations.substantiated">
				<strong>Substantiated:</strong>
				<s:if test="#attr.allegations.declarativeStatement != null">
					<span class="description"><s:property value="#attr.allegations.declarativeStatement"/></span>
				</s:if>
				<s:if test="#attr.allegations.additionalInformation != null">
					<span class="description"><s:property value="#attr.allegations.additionalInformation"/></span>
				</s:if>
				<s:if test="#attr.allegations.supportingEvidence == null">
					<br/><span class="redtext">Supporting Evidence Not Entered.</span>
				</s:if>
				<s:else>
					<br/><strong>Supporting Evidence:</strong> <span class="description"><s:property value="#attr.allegations.supportingEvidence"/></span>
				</s:else>
			</s:if>
			<s:else>
				Not Substantiated
			</s:else>
			<s:if test="#attr.allegations.underAppeal">
				<br/><span class="redtext">One or more of the findings on the complaint investigation are under appeal.</span>
			</s:if>
		</display:column>
		<s:if test="#editable">
			<display:column>
				<s:url id="deleteAllegationUrl" action="delete-allegation" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="allegation.id" value="#attr.allegations.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#allegationsContent'} ccl-action-delete" href="%{deleteAllegationUrl}">
					Delete
				</s:a>
			</display:column>
		</s:if>
	</display:table>
</s:if>