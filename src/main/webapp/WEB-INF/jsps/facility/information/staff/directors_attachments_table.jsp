<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<span class="ccl-error-container"></span>
<security:authorize access="hasPermission('edit-details','facility')">
	<s:if test="lstCtrl.showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="editDAUrl" action="upload-attachment">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{editDAUrl}" cssClass="ccl-button ajaxify {target: '#directorsAttachmentsSection'}">
					New Upload
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="directorAttachment" class="tables">
		<display:column title="Category">
			<s:property value="#attr.directorAttachment.category.value"/>
		</display:column>
		<display:column title="Comment">
			<s:property value="#attr.directorAttachment.commentText"/>
		</display:column>
		<display:column title="Content Type">
			<s:property value="#attr.directorAttachment.attachmentContentType"/>
		</display:column>
		<display:column title="Uploaded Date" headerClass="shrinkCol">
			<s:date name="#attr.directorAttachment.insertTimestamp" format="MM/dd/yyyy"/>
		</display:column>
		<s:if test="lstCtrl.showControls">
			<display:column class="shrinkCol">
				<s:url id="viewUrl" action="view-upload" namespace="/facility/information/staff" includeParams="false">
					<s:param name="attachmentId" value="#attr.directorAttachment.id"/>
				</s:url>
				<s:a href="%{viewUrl}" target="_blank">
					view
				</s:a>
				<security:authorize access="hasPermission('edit-details','facility')">
					<s:url id="deleteUrl" action="delete-upload" namespace="/facility/information/staff" includeParams="false">
						<s:param name="attachmentId" value="#attr.directorAttachment.id"/>
						<s:param name="facilityId" value="facilityId"/>
					</s:url>
					<s:a href="%{deleteUrl}" cssClass="ccl-action-delete ccl-delete-link">
						delete
					</s:a>										
				</security:authorize>
			</display:column>		
		</s:if>
	</display:table>
</s:if>

<script type="text/javascript">
	$("#directorAttachment").ccl("tableDeleteNew");
</script>
