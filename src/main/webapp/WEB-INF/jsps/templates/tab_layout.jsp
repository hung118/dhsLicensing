<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertAttribute name="javascript" ignore="true"/>
<tiles:importAttribute name="baseDivId"/>
<div id="${baseDivId}">
	<tiles:insertAttribute name="body"/>
</div>