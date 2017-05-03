<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<s:if test="!sectionRenders.isEmpty">
	<s:iterator value="sectionRenders" status="row">
		<li class="<s:if test='!#row.first'>clearLeft fieldMargin</s:if>">
			<s:url id="printUrl" namespace="/file" action="preview">
				<s:param name="file.id" value="file.id"/>
			</s:url>
			<a href="<s:property value='%{printUrl}'/>" target="_blank">
				<s:property value="metadataMap['versionDate']"/>
			</a>
		</li>
	</s:iterator>
</s:if>