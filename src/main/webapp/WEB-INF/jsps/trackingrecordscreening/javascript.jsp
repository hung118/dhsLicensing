<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:property value="scriptsDir"/>/trackingrecordscreening<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.ui.facilityautocomplete<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.ui.countryautocomplete<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
<script type="text/javascript">
  var facilityId = "<s:property value='facilityId'/>";
  var screeningId = "<s:property value='screeningId'/>";
  var personId = "<s:property value='personId'/>";
</script>
<s:include value="../templates/document_template_javascript.jsp"/>