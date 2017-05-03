(function($) {
	
	var methods = {
		tableDelete : function(options) {
			var settings = {
				"errorTemplate" : "<ul class='errorList'>{{each errors}}<li>${$value}</li>{{/each}}</ul>",
				"errorContainer" : "#errors"
			};
			
			if (options) {
				$.extend(settings, options);
			}
			
			return this.each(function() {
				var _table = $(this);
				
				var _oddEvenClass = function() {
					var rows = _table.children("tr");
					if (rows.size() == 0) {
						rows = _table.children("tbody").children("tr");
					}
					
					var i = 0;
					rows.each(function() {
						if (i % 2 == 0) {
							$(this).removeClass("odd").removeClass("even").addClass("odd");
						} else {
							$(this).removeClass("odd").removeClass("even").addClass("even");
						}
						i++;
					});
				};
				
				$(this).find("a.ccl-delete-link").bind("click.ccl", function() {
					var elem = $(this);
					var href = $(this).attr("href");
					$(".ccl-confirm-delete-dialog").dialog({
						resizable: false,
						modal: true,
						closeOnEscape: false,
						buttons: {
							"Delete": function() {
								$(this).dialog("close");
								$.ajax({
									url: href,
									dataType: "json",
									beforeSend: function() {
										$(".ccl-deleting-dialog").dialog({width: "auto", closeOnEscape: false, modal: true, resizable: false,
											open: function(event, ui) {
												$(this).parent().children(".ui-dialog-titlebar").hide();
												$(".embedded").css("visibility", "hidden");
											},
											close: function(event, ui) {
												$(".embedded").css("visibility", "visible");
											}
										});
									},
									success: function(data) {
										if (data.response == "success") {
											elem.closest("tr").remove();
											_oddEvenClass();
										} else {
											$(settings.errorContainer).empty();
											$.tmpl(settings.errorTemplate, data).appendTo(settings.errorContainer);
										}
										$(".ccl-dialog").dialog("close");
									}
								});
							},
							Cancel: function() {
								$(this).dialog("close");
							}
						}	
					});
					
					return false;
				});
			});
		},
		tableDeleteNew : function(options) {
			var settings = {
				"errorTemplate" : "#ccl-error-tmpl",
				"errorContainer" : ".ccl-error-container"
			};
			
			if (options) {
				$.extend(settings, options);
			}
			
			return this.each(function() {
				var _table = $(this);
				
				var _oddEvenClass = function() {
					var rows = _table.children("tr");
					if (rows.size() == 0) {
						rows = _table.children("tbody").children("tr");
					}
					
					var i = 0;
					rows.each(function() {
						if (i % 2 == 0) {
							$(this).removeClass("odd").removeClass("even").addClass("odd");
						} else {
							$(this).removeClass("odd").removeClass("even").addClass("even");
						}
						i++;
					});
				};
				
				$(this).find("a.ccl-delete-link").bind("click.ccl", function() {
					$(this).closest("fieldset").find(settings.errorContainer).empty();
					var elem = $(this);
					var href = $(this).attr("href");
					$(".ccl-confirm-delete-dialog").dialog({
						resizable: false,
						modal: true,
						closeOnEscape: false,
						buttons: {
							"Delete": function() {
								$(this).dialog("close");
								$.ajax({
									url: href,
									dataType: "json",
									beforeSend: function() {
										$(".ccl-deleting-dialog").dialog({width: "auto", closeOnEscape: false, modal: true, resizable: false,
											open: function(event, ui) {
												$(this).parent().children(".ui-dialog-titlebar").hide();
												$(".embedded").css("visibility", "hidden");
											},
											close: function(event, ui) {
												$(".embedded").css("visibility", "visible");
											}
										});
									},
									success: function() {
										elem.closest("tr").remove();
										_oddEvenClass();
										$(".ccl-dialog").dialog("close");
									},
									error: function(jqXhr) {
										var errorsParsed = $.parseJSON(jqXhr.responseText);
										if (!(errorsParsed instanceof Array)) {
											errorsParsed = new Array(errorsParsed);
										}
										var data = {
											errors: errorsParsed 
										};
										$(settings.errorTemplate).tmpl(data).appendTo(settings.errorContainer);
										$(".ccl-dialog").dialog("close");
									}
								});
							},
							Cancel: function() {
								$(this).dialog("close");
							}
						}	
					});
					
					return false;
				});
			});
		},
		ajaxifyJson : function(options) {
			var settings = {
				"errorTemplate" : "#ccl-error-tmpl",
				"errorContainer" : ".ccl-error-container"
			};
			
			if (options) {
				$.extend(settings, options);
			}
			
			return this.each(function() {
				var _this = $(this);
				_this.bind("click.ccl", function() {
					var _errorContainer = _this.closest("fieldset").find(settings.errorContainer);
					if (_errorContainer.size() == 0) {
						_errorContainer = _this.closest(".ccl-container").find(settings.errorContainer);
					}
					_errorContainer.empty();
					$.ajax({
						url: _this.attr("href"),
						dataType: "json",
						beforeSend: function() {
							if (settings.beforeSend) {
								settings.beforeSend();
							}
							dialogOpen(_this);
						},
						success: function() {
							if (settings.success) {
								settings.success();
							}
						},
						error: function(jqXhr) {
							var errorsParsed = $.parseJSON(jqXhr.responseText);
							if (!(errorsParsed instanceof Array)) {
								errorsParsed = new Array(errorsParsed);
							}
							var data = {
								errors: errorsParsed
							};
							$(settings.errorTemplate).tmpl(data).appendTo(_errorContainer);
						},
						complete: function() {
							dialogClose();
						}
					});
					
					return false;
				});
			});
		},
		addressAutoComplete : function(locations) {
			function location(locations) {
				var locs = new Array();
				
				if (locations != null) {
					for (var i = 0; i < locations.length; i++) {
						var locArr = locs[locations[i].zipCode];
						if (!locArr) {
							locs[locations[i].zipCode] = new Array(locations[i]);
						} else {
							locArr[locArr.length] = locations[i];
						}
					}
				}
				
				this.getLocations = function(zipCode) {
					if (!zipCode || zipCode.length < 5) {
						return null;
					}
					var _locArr = locs[zipCode];
					if (_locArr != null) {
						return _locArr;
					} else {
						$.ajax({url: context + "/components/address/zip-code-detail.action", dataType: 'json', data: {zipCode: zipCode},
								async: false, success: function(response) {
							if (response != null && response.locations.length > 0) {
								locs[zipCode] = response.locations;
							} else {
								locs[zipCode] = new Array();
							}
						}});
						return locs[zipCode];
					}
				}
			}
			
			var location = new location(locations);
			
			return this.each(function() {
				var _this = $(this);
				var _previousKnown = false;
				
				var _handleInvalidZip = function() {
					var parent = _getParent();
					if (_previousKnown) {
						var cityInput = _buildCityInput();
						parent.find(".ccl-address-state").val("").removeAttr("readonly");
						parent.find(".ccl-address-county").html("");
						parent.find(".ccl-address-county-container").hide();
					}
					_previousKnown = false;
				}
				
				var _handleUnknownZip = function() {
					var parent = _getParent();
					if (_previousKnown) {
						var cityInput = _buildCityInput();
						parent.find(".ccl-address-state").val("").removeAttr("readonly");
						parent.find(".ccl-address-county").html("");
						parent.find(".ccl-address-county-container").hide();
					}
					parent.find(".ccl-address-city").focus();
					_previousKnown = false;
				}
				
				var _handleLocations = function(locs) {
					var parent = _getParent();
					if (locs.length == 1) {
						var input = parent.find(".ccl-address-city");
						$("<input id='" + input.attr("id") + "' type='text' name='" + input.attr("name") + "' class='" + input.attr("class") + "'/>").appendTo(input.parent());
						input.remove();
						
						parent.find(".ccl-address-city").val(locs[0].city).attr("readonly", "readonly");
						parent.find(".ccl-address-state").val(locs[0].state).attr("readonly", "readonly");
						parent.find(".ccl-address-county").html(locs[0].county);
						parent.find(".ccl-address-county-container").show();
					} else {
						parent.find(".ccl-address-state").val("").attr("readonly", "readonly");
						parent.find(".ccl-address-county").html("");
						parent.find(".ccl-address-county-container").hide();
						var input = parent.find(".ccl-address-city");
						var html = "<select id='" + input.attr("id") + "' name='" + input.attr("name") + "' class='" + input.attr("class") + "'>";
						for (var i = 0; i < locs.length; i++) {
							html += "<option value='" + locs[i].city + "'" + (input.val() == locs[i].city ? "selected='selected'" : "") + ">" + locs[i].city + "</option>";
						}
						html += "</select>";
						$(html).appendTo(input.parent());
						input.remove();
						
						parent.find(".ccl-address-city").focus();
						
						$("#" + input.attr("id")).change(function() {
							for (var i = 0; i < locs.length; i++) {
								if ($(this).val() == locs[i].city) {
									parent.find(".ccl-address-state").val(locs[i].state);
									parent.find(".ccl-address-county").html(locs[i].county);
									parent.find(".ccl-address-county-container").show();
								}
							}
						}).change();
					}
					_previousKnown = true;
				}
				
				var _getParent = function() {
					return _this.closest("ol");
				}
				
				var _buildCityInput = function() {
					var input = _getParent().find(".ccl-address-city");
					if (input.is("input")) {
						input.removeAttr("readonly").val("");
						return input;
					} else {
						$("<input id='" + input.attr("id") + "' type='text' name='" + input.attr("name") + "' class='" + input.attr("class") + "'/>").appendTo(input.parent());
						input.remove();
						return parent.find(".ccl-address-city");
					}
				}
				
				$(this).change(function() {
					var zip = $(this).val();
					if (!zip || zip.length < 5) {
						_handleInvalidZip();
					} else {
						if (zip.length > 5) {
							zip = zip.substring(0, 5);
						}
						var locArr = location.getLocations(zip);
						if (locArr.length == 0) {
							_handleUnknownZip();
						} else {
							_handleLocations(locArr);
						}
					}
				}).change();
				
				var cityInput = _getParent().find(".ccl-address-city");
				if (cityInput.is("select")) {
					cityInput.change();
				}
			});
		}
	};
	
	$.fn.ccl = function(method, options) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === "object" || !method) {
			return methods.tableDelete.init.apply(this, arguments);
		} else {
			$.error("Method " + method + " does not exist on jQuery.ccl");
		}
	};
	
	$.widget("ui.reports", {
		options: {
		},
		
		_create: function() {
			var self = this,
				o = self.options,
				el = self.element;
			
			self._displayDocumentList();
		},
		
		destroy: function() {
			el.unbind("click.reports");
		},
		
		_setOption: function(option, value) {
			$.Widget.prototype._setOption.apply(this, arguments);
			
			var el = this.element;
			switch(option) {
			}
		},
		
		_displayDocumentList: function() {
			var self = this
				el = self.element;
			
			self._openLoadingDialog();
			self._retrieveDocumentListData(function(data, textStatus, jqXHR) {
				var html = self._buildDocumentListHtml(data);
				
				el.html(html);
				
				el.find(".print-doc-input-link").bind("click.reports", function() {
					self._displayInputs($(this));
					return false;
				});
				
				self._closeLoadingDialog();
			});
		},
		
		_retrieveDocumentListData: function(callback) {
			var self = this;
			
			self._clearErrors();
		
			$.ajax({
				url: context + "/docs/available-docs.action",
				data: $.param({contexts: ['reports']}, true),
				traditional: true,
				success: function(data, textStatus, jqXHR) {
					callback(data, textStatus, jqXHR);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					self._handleError(jqXHR, textStatus, errorThrown);
				}
			});
		},
		
		_buildDocumentListHtml: function(data) {
			var categories = new Array();
			for (var i = 0; i < data.length; i++) {
				var doc = data[i];
				if (!doc.category) {
					doc.category = "Additional Documents";
				}
				var category = categories[doc.category];
				if (category) {
					category.documents[category.documents.length] = doc;
				} else {
					category = {
						name: doc.category,
						documents: [doc]
					}
					categories[category.name] = category;
				}
			}
			var categoryData = new Array
			for (key in categories) {
				categoryData[categoryData.length] = categories[key];
			}
			var templateData = {
				categories: categoryData	
			};
			
			return Mustache.to_html($("#print-list-tmpl").html(), templateData);
		},
		
		_retrieveInputs: function(link, callback) {
			var self = this;

			self._clearErrors();
			
			$.ajax({
				url: context + "/docs/input.action",
				data: link.metadata(),
				traditional: true,
				success: function(data, textStatus, jqXHR) {
					callback(data, textStatus, jqXHR);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					self._handleError(jqXHR, textStatus, errorThrown);
				}
			});
		},
		
		_displayInputs: function(link) {
			var self = this;
			
			self._openLoadingDialog();
			self._retrieveInputs(link, function(data, textStatus, jqXHR) {
				var html = self._buildInputHtml(link.html(), data);

				$(".ccl-generic-dialog").empty().html(html);
				
				$(".ccl-generic-dialog").empty().html(html).dialog({
					resizable: false,
					modal: true,
					closeOnEscape: true,
					width: 700,
					height: 500,
					title: "Report Fields",
					buttons: {}
				});
				
				$(".ccl-generic-dialog .print-doc-input-print").bind("click.reports", function() {
					self._renderDocument();
					return false;
				});
				
				$(".ccl-generic-dialog .print-doc-input-cancel").bind("click.reports", function() {
					self._cancelInputs();
					return false;
				});
				
				self._closeLoadingDialog();
			});
		},
		
		_buildInputHtml: function(tmplName, data) {
			var tmplData = {
				name: tmplName,
				hidden: [],
				fields: []
			};
			for (var i = 0; i < data.length; i++) {
				if (data[i].displayType == 'HIDDEN') {
					tmplData.hidden[tmplData.hidden.length] = {name : data[i].id, value: data[i].value};
				} else {
					var field = null;
					
					var cssClass = data[i].required ? "required" : "";
					
					var options = [];
					var opts = data[i].options;
					if (opts) {
						for (var key in opts) {
							if (opts.hasOwnProperty(key)) {
								var option = {
									id: data[i].id + key,
									name: data[i].id,
									label: opts[key],
									value: key,
									cssClass: cssClass
								};
								options[options.length] = option;
							}
						}
					}
					
					var td = {
						id: "in-" + data[i].id,
						label: data[i].label,
						name: data[i].id,
						value: data[i].value,
						cssClass: cssClass,
						required: data[i].required,
						options: options
					};
					var selector = "#print-input-" + data[i].displayType.toLowerCase() + "-tmpl";
					
					tmplData.fields[tmplData.fields.length] = Mustache.to_html($(selector).html(), td);
				}
			}
			return Mustache.to_html($("#print-input-tmpl").html(), tmplData);
		},
		
		_cancelInputs: function() {
			var self = this;
			
			$(".ccl-generic-dialog").dialog("close");
		},
		
		_renderDocument: function() {
			var self = this;

			self._clearErrors();
			
			$.ajax({
				url: context + "/docs/render.action",
				data: $("#print-input-form").serialize(),
				dataType: "json",
				success: function(data, textStatus, jqXHR) {
					var url = context + "/docs/download-file.action?attachment=true&fileId=" + data.id,
						iframe = $("#hidden-downloader");
					if (iframe.size() == 0) {
						$("<iframe></iframe>").attr("id", "hidden-downloader").css("visibility", "hidden").appendTo("body");
						iframe = $("#hidden-downloader");
					}
					iframe.attr("src", url);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					self._handleError(jqXHR, textStatus, errorThrown);
				}
			});
		},
		
		_handleError: function(jqXHR, textStatus, errorThrown) {
			var self = this;

			self._clearErrors();
			
			var errors = new Array();
			var respObj = convertResponseToJson(jqXHR);
			
			if (respObj.errors) {
				for (key in respObj.errors) {
					errors[errors.length] = respObj.errors[key];
				}
			}
			if (respObj.fieldErrors) {
				for (key in respObj.fieldErrors) {
					errors[errors.length] = respObj.fieldErrors[key];
					self._highlightField(key)
				}
			}
			
			self._addErrors(errors);
			
			self._closeLoadingDialog();
		},
		
		_clearErrors: function() {
			$(".ccl-generic-dialog .print-errors").empty();
			$(".ccl-generic-dialog .inputerror").removeClass("inputerror");
		},
		
		_addErrors: function(errors) {
			var self = this,
				list = $("<ul></ul>").addClass("ccl-error-list");
			for (var i = 0; i < errors.length; i++) {
				$("<li>" + errors[i] + "</li>").appendTo(list);
			}
			$(".ccl-generic-dialog .print-errors").append(list);
		},
		
		_highlightField: function(field) {
			$("#in-" + field).addClass("inputerror");
		},
		
		_openLoadingDialog: function() {
			$(".ccl-loading-dialog").dialog({width: "auto", closeOnEscape: false, modal: true, resizable: false,
				open: function(event, ui) {
					$(this).parent().children(".ui-dialog-titlebar").hide();
					$(".embedded").css("visibility", "hidden");
				},
				close: function(event, ui) {
					$(".embedded").css("visibility", "visible");
				}
			});
		},
		
		_closeLoadingDialog: function() {
			$(".ccl-loading-dialog").dialog("close");
		}
	});
})(jQuery);