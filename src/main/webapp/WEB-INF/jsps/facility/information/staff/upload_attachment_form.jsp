<%@ taglib prefix="s" uri="/struts-tags"%>

<script type='text/javascript'>
		$(function() {
			init();
		});

	function init() {
		$('#directorAttachmentattachment').fileupload({
			type: "POST",
			url: $(this).attr("action"),
			dateType: 'html',
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
				var target = "#directorsAttachmentsSection";	// hard code!
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
	<legend>Upload Director Attachment</legend>
	<s:actionerror/>
	<s:fielderror/>
	<s:form id="directorAttachmentForm" action="save-upload" method="post" enctype="multipart/form-data" cssClass="ajaxify-upload {target: '#directorsAttachmentsSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="boardMemberAttachment.attachmentType"/>
		<ol class="fieldList">
			<li>
				<label for="directorAttachmentcategory">Category:</label>			
				<s:select id="directorAttachmentcategory" name="directorAttachment.category.id" list="categories" 
						  listKey="id" listValue="value" headerKey="-1" headerValue="- Select category -" />
						  
			</li>
			<li>
				<label for="directorAttachmentcommentText">Comment:</label>
				<s:textarea id="directorAttachmentcommentText" name="directorAttachment.commentText"/>
				<div id="dirCharCount" class="charCount" style="margin-left:11em;"></div>
			</li>
			<li>
				<label for="directorAttachmentattachment">Attachment:</label>
				<s:file id="directorAttachmentattachment" name="attachment"/>
			</li>
			<li class="submit">
				<s:url id="attachmentUploadCancelUrl" action="directors-attachments-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="director.id" value="director.id"/>
				</s:url>
				<s:a id="attachmentUploadUrlId" href="%{attachmentUploadCancelUrl}" cssClass="ajaxify {target: '#directorsAttachmentsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="directorsAttachmentsSection">
		<s:include value="directors_attachments_table.jsp"/>
	</div>
</fieldset>
<script type="text/javascript">
	$("#directorAttachmentcommentText").charCounter(4000, {container: "#directorAttachmentForm #dirCharCount"});
</script>
