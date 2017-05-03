<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="refreshUrl" action="tab" includeParams="false" escapeAmp="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="complaintId" value="complaintId"/>
</s:url>
<script type="text/javascript">
	$("body").data("complaint.refreshUrl", "<s:property value='refreshUrl' escapeHtml='false'/>");
	$("body").data("complaint.id", "<s:property value='complaintId'/>");
</script>
<s:include value="../navigation.jsp"><s:param name="selectedTab">allegations</s:param></s:include>
<div id="allegationsContent">
	<s:action name="allegations-section" executeResult="true"/>
</div>