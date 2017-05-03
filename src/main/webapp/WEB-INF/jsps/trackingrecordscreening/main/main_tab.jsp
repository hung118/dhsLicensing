<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
  <tiles:putAttribute name="baseDivId" value="mainBase"/>
  <tiles:putAttribute name="body">
      <s:action name="edit-trsMain" namespace="/trackingrecordscreening/main" executeResult="true">
      	<s:param name="screeningId" value="screeningId" />
      </s:action>
  </tiles:putAttribute>
</tiles:insertDefinition>
