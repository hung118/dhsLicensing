<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="inspection" value="inspection" scope="request"/>
<s:set var="editable" value="%{false}"/>
<security:authorize access="(hasAnyRole('ROLE_SUPER_ADMIN') or isOwner(request.getAttribute('inspection'))) and request.getAttribute('inspection').state.name() != 'FINALIZED'">
	<s:set var="editable" value="%{true}"/>
</security:authorize>
<fieldset>
	<legend>Follow Ups and Corrections</legend>
	<p>
		The following findings were followed up on by this inspection.  If the finding was corrected on this inspection that is also noted.
	</p>
	<ol class="ccl-insp-foll-up-list">
		<s:set name="inspectionId" value="-1"/>
		<s:iterator value="matrix">
			<s:if test="#inspectionId == -1">
				<s:property value="%{'<li>'}" escapeHtml="false"/>
				<h3>From <strong><s:property value="inspectionDate"/></strong> <strong><s:property value="primaryInspectionType.value"/></strong><s:iterator value="otherInspectionTypes">, <s:property value="value"/></s:iterator> Inspection</h3>
				<s:property value="%{'<ol>'}" escapeHtml="false"/>
				<s:set name="inspectionId" value="inspectionId"/>
			</s:if>
			<s:elseif test="!#inspectionId.equals(inspectionId)">
				<s:property value="%{'</ol></li><li>'}" escapeHtml="false"/>
				<h3>From <strong><s:property value="inspectionDate"/></strong> <strong><s:property value="primaryInspectionType.value"/></strong><s:iterator value="otherInspectionTypes">, <s:property value="value"/></s:iterator> Inspection</h3>
				<s:property value="%{'<ol>'}" escapeHtml="false"/>
				<s:set name="inspectionId" value="inspectionId"/>
			</s:elseif>
			<ccl:rule ruleId="ruleId" format="RNUM - RDESC"/><s:if test="corrected"><strong><s:if test="correctedByThis">, Correction verified</s:if><s:else>, Corrected on site but followed up on for maintenance</s:else></strong></s:if><br/>
		</s:iterator>
		<s:property value="matrix.empty ? '' : '</ol></li>'" escapeHtml="false"/>
		<s:if test="#editable">
			<li class="submit">
				<s:url id="followUpEditUrl" action="edit-follow-up-matrix" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="inspectionId" value="inspectionId"/>
				</s:url>
				<s:a href="%{followUpEditUrl}" cssClass="ccl-button ajaxify {target: '#followUpSection'}">
					Edit
				</s:a>
			</li>
		</s:if>
	</ol>
</fieldset>