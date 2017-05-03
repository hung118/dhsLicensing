<%@ taglib prefix="s" uri="/struts-tags"%>

	<script type='text/javascript'>
		$(function() {
			init();
		});

	function init() {
		$('#checklistAttachment').fileupload({
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
				var target = "#inspectionsBase";	// hard code!
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
	<legend>Upload Inspection Checklist Attachment (PDF)</legend>
	<s:fielderror/>
	<s:actionerror/>
	<s:form id="inspectionChecklistAttachmentForm" action="save-upload" method="post" enctype="multipart/form-data" cssClass="ajaxify-upload {target: '#inspectionsBase'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="inspectionId"/>
		<s:hidden name="checklistId"/>
		<ol class="fieldList">
			<li>
				<label for="checklistAttachment">Attachment:</label>
				<s:file id="checklistAttachment" name="file"/>
			</li>
		</ol>
	</s:form>
</fieldset>
