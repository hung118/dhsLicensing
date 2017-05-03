<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="document != null and document.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Document</legend>
	<s:fielderror/>
	<s:form id="documentForm" action="save-document" method="post" cssClass="ajaxify {target: '#documentsBase'} ccl-action-save">
		<s:hidden name="documentId"/>
		<ol class="fieldList">
			<li>
				<label for="name"><span class="redtext">* </span>Name:</label>
				<s:textfield id="name" name="document.name" cssClass="required"/>
			</li>
			<li>
				<label for="sortOrder">Sort Order:</label>
				<s:textfield id="sortOrder" name="document.sortOrder"/>
			</li>
			<li>
				<label for="category">Category:</label>
				<s:select id="category" name="document.category" value="document.category.id" list="categories" listKey="id" listValue="value" headerKey="-1" headerValue="None"/>
			</li>
			<li>
				<label for="context">Context:</label>
				<s:select id="context" name="document.context" value="document.context.id" list="contexts" listKey="id" listValue="value" headerKey="-1" headerValue="None"/>
			</li>
			<li>
				<label for="templateName">Template Name:</label>
				<s:textfield id="templateName" name="document.templateName"/>
			</li>
			<li>
				<label for="description">Description:</label>
				<s:textarea id="description" name="document.description"/>
			</li>
			<li>
				<s:hidden id="file" name="fileId"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="list-documents"/>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#documentsBase'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	$("#file").fileuploader({fieldLabel: "Document File"});
</script>