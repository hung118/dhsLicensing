<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	var unlicensedAction = "<s:url action='save-new-unlicensed-facility'/>";
	var licensedAction = "<s:url action='save-new-facility'/>";
</script>
<script type="text/javascript" src="<s:property value="scriptsDir"/>/facilitycreate<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>