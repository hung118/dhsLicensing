(function($) {
	$.widget("ui.facilityautocomplete", {
		options: {
			facilityId: null,
			showFacilityDetails: true,
			selectFacility: null
		},
		
		_create: function() {
			var self = this,
				o = self.options,
				el = self.element.addClass("ui-facilityautocomplete-input"),
				hidden = $("<input type='hidden'/>").attr("name", el.attr("name")).addClass("ui-facilityautocomplete-facility-id"),
				resCon = $("<div></div>").addClass("ui-facilityautocomplete-results-container"),
				con = $("<div></div>").addClass("ui-facilityautocomplete-container").append(resCon).append(hidden).insertAfter(el);
			
			el.attr("name", el.attr("name") + "-ac");
			
			el.autocomplete({
				source: function(request, response) {
					var opts = self._getAjaxOptions();
					opts.success = function(data) {
						response(data);
					}
					$.ajax(opts);
				},
				minLength: 1,
				appendTo: resCon,
				select: function(event, ui) {
					self._selectFacility(ui.item);
					return false;
				},
				change: function(event, ui) {
					if (!ui.item) {
						if (el.val()) {
							var opts = self._getAjaxOptions();
							opts.success = function(data) {
								if (data.length == 1) {
									self._selectFacility(data[0]);
								} else {
									self._clear();
									el.val("Invalid Facility").addClass("inputerror");
								}
							}
							$.ajax(opts);
						} else {
							self._clear();
						}
					}
				}
			}).data("autocomplete")._renderItem = function(ul, item) {
				return $("<li></li>").data("item.autocomplete", item)
				.append("<a><strong>" + item.facilityName + "</strong>: " + item.facilityIdNumber + "</a>")
				.appendTo(ul);
			};
			
			if (el.val()) {
				var opts = self._getAjaxOptions();
				opts.data.facId = el.val();
				opts.success = function(data) {
					self._selectFacility(data[0]);
				}
				$.ajax(opts);
			}
		},
		
		destroy: function() {
			var el = this.element.remove("ui-facilityautocomplete-input");
			el.val(el.next().find("ui-facilityautocomplete-facility-id").val());
			el.autocomplete("destroy");
			el.next().remove();
			el.attr("name", el.attr("name").replace(/-ac$/, ""));
		},
		
		_setOption: function(option, value) {
			$.Widget.prototype._setOption.apply(this, arguments);
		},
		
		_selectFacility: function(facility) {
			var self = this,
				o = self.options,
				el = self.element,
				hidden = el.next().find(".ui-facilityautocomplete-facility-id");
			
			if (this.selectFacility) {
				this.selectFacility();
			}
			
			el.removeClass("inputerror");
			
			hidden.val(facility.id);
			el.val(facility.facilityName);
			
			el.next().find(".ui-facilityautocomplete-facility-details").remove();
			if (o.showFacilityDetails) {
				var dtl = "<div>" + facility.facilityName + " (" + facility.facilityIdNumber + ")<br/>";
				dtl = dtl + facility.addressOne + "<br/>"; 
				if (facility.addressTwo) {
					dtl = dtl + facility.addressTwo + "<br/>";
				}
				dtl = dtl + facility.city + ", " + facility.state + " " + facility.zipCode;
				if (facility.primaryPhone) {
					dtl = dtl + "<br/>" + facility.primaryPhone;
				}
				dtl = dtl + "</div>";
				$(dtl)
					.addClass("ui-facilityautocomplete-facility-details")
					.appendTo(el.next());
			}
		},
		
		_clear: function() {
			this.element.val("").removeClass("inputerror");
			this.element.next().find(".ui-facilityautocomplete-facility-id").val("")
			this.element.next().find(".ui-facilityautocomplete-facility-details").remove();
		},
		
		_getAjaxOptions: function() {
			return {
				url: context + "/facility/facility-autocomplete.action",
				dataType: "json",
				data: {
					facName: this.element.val(),
					facilityId: this.options.facilityId
				}
			};
		}
	});
})(jQuery);