<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<fieldset>
	<legend>Documents</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:form action="edit-document" namespace="/admin/documents" method="get" cssClass="ajaxify {target: '#documentsBase'}">
				<s:submit value="New Document"/>
			</s:form>
		</div>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="documents" class="tables">
			<display:column title="Name">
				<s:url id="editDocumentUrl" action="edit-document" namespace="/admin/documents" includeParams="false">
					<s:param name="documentId" value="#attr.documents.id"/>
				</s:url>
				<s:a href="%{editDocumentUrl}" cssClass="ajaxify {target: '#documentsBase'}">
					<s:property value="#attr.documents.name"/>
				</s:a>
			</display:column>
			<display:column title="Context">
				<s:property value="#attr.documents.context.value"/>
			</display:column>
			<display:column>
				<s:url id="deleteDocumentUrl" action="delete-document" namespace="/admin/documents" includeParams="false">
					<s:param name="documentId" value="#attr.documents.id"/>
				</s:url>
				<s:a href="%{deleteDocumentUrl}" cssClass="ajaxify {target: '#documentsBase'} ccl-action-delete">
					delete
				</s:a>
			</display:column>
		</display:table>
	</s:if>
</fieldset>