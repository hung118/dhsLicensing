(function($) {
	$.widget("ui.fileuploader", {
		options: {
			fieldLabel: "Upload File"
		},
		
		_create: function() {
			var self = this,
				o = self.options,
				el = self.element.addClass("ui-fileuploader-input"),
				preview = $("<div></div").addClass("ui-fileuploader-preview"),
				legend = $("<legend>" + o.fieldLabel + "</legend>"),
				btns = $("<a href='#' class='ui-fileuploader-upl ccl-button'>Upload</a> <span class='ccl-fileuploader-links'><a href='#' class='ui-fileuploader-rem'>Remove</a> | <a href='#' class='ui-fileuploader-down'>Download</a></span>"),
				con = $("<fieldset></fieldset>").addClass("ui-fileuploader-fieldset").append(legend).append(preview).append(btns).insertAfter(el);
			
			new AjaxUpload(con.find(".ui-fileuploader-upl"), {
				action: context + "/admin/upload-file.action",
				// File upload name
				name: "upload",
				// Submit file after selection
				autoSubmit: true,
				responseType: "json",
				onSubmit : function(file, ext) {
					// change button text, when user selects file			
					//button.text('Uploading');
					// If you want to allow uploading only 1 file at time,
					// you can disable upload button
//					$("." + sectionClass + " .preview").hide();
//					$("." + sectionClass + " .loadingMessage").show();
//					this.disable();
				},
				// Fired when file upload is completed
				// WARNING! DO NOT USE "FALSE" STRING AS A RESPONSE!
				// @param file basename of uploaded file
				// @param response server response
				onComplete: function(file, response) {
					self._setNewFile(response.file.id);
//					handlePreview(sectionClass, response.fileId);
//					$("." + sectionClass + " .loadingMessage").hide();
//					$("." + sectionClass + " .preview").show();
//					this.enable();
				}
			});
			
			con.find(".ui-fileuploader-down").bind("click.ccl-fileuploader", function() {
				$(this).attr("href", context + "/docs/download-file.action?fileId=" + el.val());
				return true;
			});
	
			con.find(".ui-fileuploader-rem").bind("click.ccl-fileuploader", function() {
				el.val(null);
				self._noFileSelected();
				return false;
			});
			
			if (el.val()) {
				self._previewFile(el.val());
			} else {
				self._noFileSelected();
			}
		},
		
		destroy: function() {
			this.element.siblings(".ui-fileuploader-fieldset").remove();
		},
		
		_setOption: function(option, value) {
			$.Widget.prototype._setOption.apply(this, arguments);
		},
		
		_setNewFile: function(fileId) {
			var self = this,
				o = self.options,
				el = self.element;
			
			el.val(fileId);
			self._previewFile(fileId);
			el.next().find(".ccl-fileuploader-links").show();
		},
		
		_noFileSelected: function() {
			var self = this,
			o = self.options,
			el = self.element,
			con = el.siblings(".ui-fileuploader-fieldset");
			
			con.find(".ui-fileuploader-preview").empty();
			con.find(".ccl-fileuploader-links").hide();
		},
		
		_previewFile: function(fileId) {
			var self = this,
				o = self.options,
				el = self.element,
				con = el.siblings(".ui-fileuploader-fieldset").find(".ui-fileuploader-preview");
			
			con.empty();
			if (fileId) {
				var dataUrl = context + "/admin/preview-file.action?fileId=" + fileId + "#toolbar=0&navpanes=0scrollbars=0&view=fitH";
				$("<object class='embedded pdf-object' width='100%' height='250' style='z-index: -1;' tabindex='-1' data='" + dataUrl + "'>You must install Adobe Reader in order to view this file.</object>")
					.appendTo(con);
			}
		}
	});
})(jQuery);