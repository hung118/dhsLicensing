<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<span class="ccl-error-container"></span>
<security:authorize access="hasPermission('edit-details','facility')">
	<s:if test="lstCtrl.showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="editBMAUrl" action="upload-attachment">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{editBMAUrl}" cssClass="ccl-button ajaxify {target: '#boardMembersAttachmentsSection'}">
					New PDF Upload
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="boardMemberAttachment" class="tables">
		<display:column title="Category">
			<s:property value="#attr.boardMemberAttachment.category.value"/>
		</display:column>
		<display:column title="Comment">
			<s:property value="#attr.boardMemberAttachment.commentText"/>
		</display:column>
		<display:column title="Uploaded Date" headerClass="shrinkCol">
			<s:date name="#attr.boardMemberAttachment.insertTimestamp" format="MM/dd/yyyy"/>
		</display:column>
		<s:if test="lstCtrl.showControls">
			<display:column class="shrinkCol">
				<s:url id="viewUrl" action="view-upload" namespace="/facility/information/board" includeParams="false">
					<s:param name="attachmentId" value="#attr.boardMemberAttachment.id"/>
				</s:url>
				<s:a href="%{viewUrl}" target="_blank">
					view
				</s:a>
				<security:authorize access="hasPermission('edit-details','facility')">
					<s:url id="deleteUrl" action="delete-upload" namespace="/facility/information/board" includeParams="false">
						<s:param name="attachmentId" value="#attr.boardMemberAttachment.id"/>
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
	$("#boardMemberAttachment").ccl("tableDeleteNew");
</script>
