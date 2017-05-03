(function($) {
	
	var methods = {
		init : function(options) {
			var settings = {
				errTmpl: "<div><ul class='ccl-table-errors errorList'>{{each valErrors}}<li>${$value}</li>{{/each}}</ul></div>",
				baseTmpl: "#ccl-table-tmpl",
				confirmDelete: true,
				hideRowAddButton: true,
				showListControls: false,
				showRange: false,
				showPaging: false,
				showSorter: false
			};
			
			if (options) {
				$.extend(settings, options);
			}
			
			return this.each(function() {
				var $this = $(this);
				var editingItem = null;
				
				$(settings.baseTmpl).tmpl(settings).appendTo($this);
				
				_updateTable();
					
				function _updateTable() {
					var i = 0;
					$this.find(".ccl-table-row").each(function() {
						if (i % 2 == 0) {
							$(this).removeClass("even").addClass("odd");
						} else {
							$(this).removeClass("odd").addClass("even");
						}
						i++;
					});
					
					if (i > 0) {
						$this.find(".ccl-table").show();
					} else {
						$this.find(".ccl-table").hide();
					}
				}
				
				function _deleteItem(tmplItem) {
					$(tmplItem.nodes).remove();
					_updateTable();
				}
				
				function _showForm() {
					var formContainer = $this.find(".ccl-table-form-container");
					formContainer.empty();
					if (settings.hideRowAddButton) {
						$(".ccl-table-row-add").hide();
					}
					$(settings.formTmpl).tmpl(editingItem != null ? editingItem.data : null).appendTo(formContainer);
				}
				
				$this.delegate(".ccl-table-row-delete", "click", function() {
					var _link = $(this);
					var _tmplItem = $.tmplItem(this);
					
					var data = {
						url: _link.attr("href"),
						dataType: 'json',
						beforeSend: function() {
							dialogOpen(_link);
						},
						success: function(data) {
							if (data.response == "success") {
								_deleteItem(_tmplItem);
							}
							dialogClose();
						}
					};
					
					if (settings.confirmDelete) {
						$(".ccl-confirm-delete-dialog").dialog({
							resizable: false,
							modal: true,
							closeOnEscape: false,
							buttons: {
								"Delete": function() {
									$(this).dialog("close");
									$.ajax(data);
								},
								Cancel: function() {
									$(this).dialog("close");
								}
							}	
						});
					} else {
						$.ajax(data);
					}
					
					return false;
				});
				$this.delegate(".ccl-table-row-add", "click", function() {
					editingItem = null;
					_showForm();
					
					return false;
				});
				$this.delegate(".ccl-table-row-save", "click", function() {
					$this.find(".ccl-table-errors").remove();
					var _form = $this.find(".ccl-table-form");
					
					$.post(_form.attr("action"), _form.serialize(), function(data) {
						if (data.response == "success") {
							var container = editingItem == null ? $this.find(".ccl-table-body") : editingItem.nodes;
							$(settings.rowTmpl).tmpl(data.item).insertAfter(container);
							if (editingItem != null) {
								$(editingItem.nodes).remove();
							}
							$this.find(".ccl-table-form-container").empty();
							$this.find(".ccl-table-row-add").show();
							_updateTable();
						} else {
							$(settings.errTmpl).tmpl(data).prependTo(".ccl-table-form-container");
						}
					});
					return false;
				});
				$this.delegate(".ccl-table-row-edit-cancel", "click", function() {
					$this.find(".ccl-table-form-container").empty();
					$this.find(".ccl-table-row-add").show();
					editingItem = null;
					return false;
				});
				$this.delegate(".ccl-table-row-edit", "click", function() {
					editingItem = $.tmplItem(this);
					_showForm();
					return false;
				});
				
				$this.delegate(".ccl-list-controls-form", "submit", function() {
					dialogOpen($(this));
					$.post($(this).attr("action"), $(this).serialize(), function(data) {
						$this.empty();
						$.extend(true, settings, data);
						$(settings.baseTmpl).tmpl(settings).appendTo($this);
						dialogClose();
					}, "json");
					return false;
				});
			});
		}
	};
	
	$.fn.cclTable = function(method, options) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === "object" || !method) {
			return methods.tableDelete.init.apply(this, arguments);
		} else {
			$.error("Method " + method + " does not exist on jQuery.cclTable");
		}
	};
})(jQuery);