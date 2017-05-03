<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:property value="scriptsDir"/>/facility<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.ui.facilityautocomplete<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
<script type="text/javascript">
	var facilityId = "<s:property value='facilityId'/>";
</script>
<s:include value="../templates/document_template_javascript.jsp"/>