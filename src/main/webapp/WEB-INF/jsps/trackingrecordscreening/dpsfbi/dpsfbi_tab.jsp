<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="tab">
  <tiles:putAttribute name="baseDivId" value="dpsFbiBase"/>
  <tiles:putAttribute name="body">
      <s:action name="edit-trsdpsfbi" namespace="/trackingrecordscreening/dpsfbi" executeResult="true">
        <s:param name="screeningId" value="screeningId" />
      </s:action>
    <div id="dpsfbiNotesSection">
      <s:set var="formSection">#dpsfbiNotesSection</s:set>
      <s:action name="administer-notes" namespace="/trackingrecordscreening/main" executeResult="true">
        <s:param name="screeningId" value="screeningId" />
        <s:param name="formSection" value="%{formSection}" />
        <s:param name="editable" value="true" />
      </s:action>
    </div>
      
  </tiles:putAttribute>
</tiles:insertDefinition>