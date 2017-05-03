<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>Licensing Specialist Case Load</h1>
<script type="text/javascript">
	var caseloadSortBys = <s:property value='caseloadSortBys' escapeJavaScript='false' escapeHtml='false'/>;
	var specId = <s:property value='user.person.id'/>;

	function insertAlert(alertRow, alertTitle) {
		var section = findSection(alertTitle);
		if (section == null) {
			$("fieldset.redAlerts").append("<div class=\"redAlertType\"><span class=\"alertTypeTitle\">" + alertTitle + "</span><ol class=\"ccl-list\"></ol></div>");
			section = findSection(alertTitle);
		}
		section.append(alertRow);
	}

	function findSection(alertTitle) {
		var section;
		$("fieldset.redAlerts .redAlertType span.alertTypeTitle").each(function() {
			if ($(this).html() == alertTitle) {
				section = $(this).parent().children("ol");
			}
		});
		return section;
	}
	$(document).ready(function() {
		var alerts = $(".redAlert");
		if (alerts.size() > 0) {
			$("#redAlertsSection").append("<fieldset class=\"redAlerts\"><legend>Red Alerts</legend></fieldset>");
		}
		$(".redAlert").each(function() {
			var alertTitle = $(this).parents("fieldset").children("legend").children("a").html();
			insertAlert($(this).clone(), alertTitle);
		});

		$(".redAlertType .redAlert").removeClass("odd even");
		$(".redAlertType > ol").each(function() {
			var child = 1;
			$(this).children().each(function() {
				$(this).addClass(child % 2 == 0 ? "even" : "odd");
				child++;
			});
		});

		var caseloadLinks = $(".quickLinks").siblings(".caseloadLinks");
		if (caseloadLinks.size() == 0) {
			caseloadLinks = $("<ol></ol>").addClass("ccl-list").addClass("caseloadLinks");
			$("<li></li>").addClass("ccl-list-item").appendTo(caseloadLinks);
			caseloadLinks.insertAfter(".quickLinks");
		}

		var insertPoint = null;
		if (caseloadLinks.find(".ccl-list-item div").size() == 0) {
			insertPoint = $("<div></div>").addClass("left-column");
			insertPoint.appendTo(caseloadLinks.find(".ccl-list-item"));
		} else {
			insertPoint = $("<div></div>").addClass("right-column");
			insertPoint.appendTo(caseloadLinks.find(".ccl-list-item"));
		}
		
		<s:url id="licensorFacilityDetailUrl" namespace="/reports" action="forward-report.action?destination=fac-license-detail-srch" includeParams="all" />
        $("<a href='#'>Print Licensor Caseload</a>").click(function() {
        	window.location = '<s:property value="licensorFacilityDetailUrl"/>';
        }).appendTo(insertPoint);

	});
</script>
<div id="expiredLicensesSection">
	<s:action name="list" namespace="/alert/expired-licenses" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="showWholeRegion" value="%{'false'}"/>
	</s:action>
</div>
<div id="complaintsInProgressAlertSection-SPECIALIST">
	<s:action name="list" namespace="/alert/complaints-in-progress" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="roleType" value="%{'SPECIALIST'}"/>
	</s:action>
</div>
<div id="announcedInspectionsSection">
	<s:action name="list" namespace="/alert/announced-inspections-needed" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
	</s:action>
</div>
<div id="unannouncedInspectionsSection">
	<s:action name="list" namespace="/alert/unannounced-inspections-needed" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
	</s:action>
</div>
<div id="followUpInspectionsSection-NORM">
	<s:action name="list" namespace="/alert/follow-up-inspections-needed" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="roleType" value="%{'NORM'}"/>
	</s:action>
</div>
<div id="followUpInspectionsSection-COMPL">
	<s:action name="list" namespace="/alert/follow-up-inspections-needed" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="roleType" value="%{'COMPL'}"/>
	</s:action>
</div>
<div id="ROLE_LICENSOR_SPECIALIST_conditionalFacilitiesSection">
	<s:action name="list" namespace="/alert/conditional-facilities" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="roleType" value="%{'ROLE_LICENSOR_SPECIALIST'}"/>
	</s:action>
</div>
<div id="variancesExpiringSection">
	<s:action name="list" namespace="/alert/variances-expiring" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
	</s:action>
</div>
<div id="licensorVariancesSection">
	<s:action name="licensor-variances" namespace="/home/alerts" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
	</s:action>
</div>
<div id="accreditationExpirationSection">
	<s:action name="list" namespace="/alert/accreditation-expiration" executeResult="true">
		<s:param name="person.id" value="user.person.id"/>
		<s:param name="roleType" value="%{'ROLE_LICENSOR_SPECIALIST'}"/>
	</s:action>
</div>
<script id="csld-print-dialog-tmpl" type="text/html">
	<ol class="fieldList">
		<li>
			<label for="csld-sort-by">Sort By</label>
			<select id="csld-sort-by" name="sortBy">
				{{#options}}
					<option value="{{key}}">{{label}}</option>
				{{/options}}
			</select>
		</li>
	</ol>
</script>