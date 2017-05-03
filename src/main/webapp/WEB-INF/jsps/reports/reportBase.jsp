<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
    $(document).ready(function() {
        $("#reportForwardForm").submit();
    });
</script>
<fieldset>
	<legend>Forwarding to Report</legend>
	<s:form id="reportForwardForm" action="%{destination}" method="post" cssClass="ajaxify {target: '#maincontent'}">
        <security:authorize access="hasAnyRole('ROLE_LICENSOR_SPECIALIST')">
    	    <s:hidden name="specialistId" value="%{userId}"/>
        </security:authorize>
        <security:authorize access="hasAnyRole('ROLE_BACKGROUND_SCREENING')">
            <s:hidden name="technicianId" value="%{userId}"/>
        </security:authorize>
	</s:form>
</fieldset>
