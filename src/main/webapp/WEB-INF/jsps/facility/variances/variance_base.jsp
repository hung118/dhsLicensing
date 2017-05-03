<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<input type="hidden" class="print-ctx-data" name="docctx" value="doc-ctx.variance"/>
<s:hidden cssClass="print-ctx-attr" name="ds.variance" value="%{varianceId}"/>
<div id="requestSection">
	<s:include value="request_detail.jsp"/>
</div>
<security:authorize access="hasPermission('view-licensor-outcome','variance')">
	<div id="licensorOutcomeSection">
		<s:include value="licensor_outcome_detail.jsp"/>
	</div>
</security:authorize>
<security:authorize access="hasPermission('view-manager-outcome','variance')">
	<div id="supervisorOutcomeSection">
		<s:include value="supervisor_outcome_detail.jsp"/>
	</div>
</security:authorize>
<security:authorize access="hasPermission('view-outcome','variance')">
	<div id="outcomeSection">
		<s:include value="outcome_detail.jsp"/>
	</div>
</security:authorize>
<div id="VARIANCE_NotesSection">
	<s:action name="notes-list" namespace="/note" executeResult="true">
		<s:param name="objectId" value="varianceId"/>
		<s:param name="noteType" value="%{'VARIANCE'}"/>
		<s:param name="disableRange" value="true"/>
	</s:action>
</div>