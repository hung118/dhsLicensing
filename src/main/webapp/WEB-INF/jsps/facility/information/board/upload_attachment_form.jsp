<%@ taglib prefix="s" uri="/struts-tags"%>

	<script type='text/javascript'>
		$(function() {
			init();
		});

	function init() {
		$('#boardMemberAttachmentattachment').fileupload({
			type: "POST",
			url: $(this).attr("action"),
			dataType: 'html',
			//This is supposedly how you can delay upload till button click, but if enabled it is ignoring beforeSend and success
			//		add: function (e, data) {
			//			$("#attachmentSubmit").click(function () {
			//				// validate file...
			//				if(true) {
			//					data.submit();
			//				}	
			//			});
			//		},
			//		Could be used to display additional message
			//		done:function (e, data) {
			//            alert('Upload finished.');
			//       },
			beforeSend: function() {
				var obj = $(this);
				dialogOpen(obj);
			},
			success: function(data) {
				var target = "#boardMembersAttachmentsSection";	// hard code!
				$(target).html(data);
				var input = $(target + " :input").filter(":visible");
				if (input.size() > 0) {
					input.get(0).focus();
				}
				dialogClose();
			}
		});
	}
</script>
<fieldset>
	<legend>Upload Board Member Attachment (PDF)</legend>
	<s:fielderror/>
	<s:form id="boardMemberAttachmentForm" action="save-upload" method="post" enctype="multipart/form-data" cssClass="ajaxify-upload {target: '#boardMembersAttachmentsSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="boardMemberAttachment.attachmentType"/>
		<ol class="fieldList">
			<li>
				<label for="boardMemberAttachmentcategory">Category:</label>			
				<s:select id="boardMemberAttachmentcategory" name="boardMemberAttachment.category.id" list="categories" 
						  listKey="id" listValue="value" headerKey="" headerValue="- Select category -" />
						  
			</li>
			<li>
				<label for="boardMemberAttachmentcommentText">Comment:</label>
				<s:textarea id="boardMemberAttachmentcommentText" name="boardMemberAttachment.commentText"/>
				<div id="bmCharCount" class="charCount" style="margin-left:11em;"></div>
			</li>
			<li>
				<label for="boardMemberAttachmentattachment">Attachment:</label>
				<s:file id="boardMemberAttachmentattachment" name="attachment"/>
			</li>
			<li class="submit">
				<s:url id="attachmentUploadCancelUrl" action="board-members-attachments-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="attachmentUploadUrlId" href="%{attachmentUploadCancelUrl}" cssClass="ajaxify {target: '#boardMembersAttachmentsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="boardMembersAttachmentsSection">
		<s:include value="board_members_attachments_table.jsp"/>
	</div>
</fieldset>
<script type="text/javascript">
	$("#boardMemberAttachmentcommentText").charCounter(4000, {container: "#boardMemberAttachmentForm #bmCharCount"});
</script>
