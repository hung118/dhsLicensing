<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<s:if test="usersName != null">
	<h2><strong>Viewing home page for <s:property value="usersName"/></strong></h2>
</s:if>
<script type="text/javascript">
	var recipients;

	$(document).ready(function() {
		var quickLinks = $(".quickLink");
		if (quickLinks.size() > 0) {
			var i = 0;
			quickLinks.each(function() {
				var html = "<li><a href='#" + $(this).attr("name") + "'>" + $(this).html() + "</a></li>";
				if (i < quickLinks.size() / 2) {
					$(".quickLinks .columnOne").append(html);
				} else {
					$(".quickLinks .columnTwo").append(html);
				}
				i++;
			});
		}

		$("button.mailingLabelsPrint").livequery(function() {
			$(this).click(function() {
				window.open($(this).metadata().url);
			});
		});

		var select = $("#recipientId");
		for (var i = 0; i < recipients.length; i++) {
			$("<option value='" + recipients[i].id + "'>" + recipients[i].name + "</option>").appendTo(select);
		}

		var delegateDialog = $(".delegateDialog").dialog({autoOpen: false, width: "auto", resizable: false, modal: true});

		$("#delegateForm").submit(function() {
			var data = $(this).serializeArray();
			$.ajax({
				type: "POST",
				url: $(this).attr("action"),
				data: data,
				success: function(data) {
					$("#alertsSection").empty().html(data);
					delegateDialog.dialog("close");
				}
			});
			return false;
		});

		$(".al-delegate").live("click", function(e) {
			var id = $(this).attr("id");
			id = id.substring(0, id.indexOf("-"));
			$("#alertId").val(id);
			delegateDialog.dialog("open");
			return false;
		});
	});
</script>
<div id="redAlertsSection"></div>
<fieldset>
	<legend>Quick Links</legend>
	<ol class="twoColumn quickLinks">
		<li class="column">
			<ol class="columnOne">
			</ol>
		</li>
		<li class="column">
			<ol class="columnTwo">
			</ol>
		</li>
	</ol>
</fieldset>
<s:if test="hasRole('ROLE_FACILITY_PROVIDER')">
	<div id="facilityContactSection">
		<s:action name="facility-contact-home" namespace="/home" executeResult="true">
			<s:param name="person.id" value="user.person.id"/>
		</s:action>
	</div>
</s:if>
<s:if test="isUserInternal()">
	<!-- BEGIN ALERTS PORTION OF LANDING PAGE -->
		<div id="alertsSection">
			<s:action name="list" namespace="/alert/alerts" executeResult="true">
				<s:param name="person.id" value="user.person.id"/>
			</s:action>
			<div class="delegateDialog" style="display: none;" title="Delegate Alert">
				<s:form id="delegateForm" action="delegate" namespace="/alert/alerts">
					<input id="alertId" type="hidden" name="alertId"/>
					<s:hidden name="personId" value="%{personId}"/>
					<ol class="fieldList">
						<li>
							<label for="recipientId">Recipient:</label>
							<select id="recipientId" name="recipientId">
							</select>
						</li>
						<li class="submit">
							<s:submit value="Delegate"/>
						</li>
					</ol>
				</s:form>
			</div>
		</div>
		<div id="workInProgressSection">
			<s:action name="work-in-progress" namespace="/home/alerts" executeResult="true">
				<s:param name="person.id" value="user.person.id"/>
			</s:action>
		</div>
	<!-- END OF ALERTS PORTION OF HOME -->
	<!-- BEGIN OF HOME PAGE PORTION OF LANDING PAGE -->
		<s:if test="hasRole('ROLE_OFFICE_SPECIALIST')">
			<s:action name="office-specialist" namespace="/home" executeResult="true">
				<s:param name="person.id" value="user.person.id"/>
			</s:action>
		</s:if>
		<s:if test="hasRole('ROLE_LICENSOR_SPECIALIST')">
			<s:action name="licensing-specialist" namespace="/home" executeResult="true">
				<s:param name="person.id" value="user.person.id"/>
			</s:action>
		</s:if>
		<s:if test="hasRole('ROLE_LICENSOR_MANAGER')">
			<s:action name="licensing-manager" namespace="/home" executeResult="true">
				<s:param name="person.id" value="user.person.id"/>
			</s:action>
		</s:if>
		<s:if test="hasRole('ROLE_LICENSING_DIRECTOR')">
			<s:action name="licensing-director" namespace="/home" executeResult="true">
				<s:param name="person.id" value="user.person.id"/>
			</s:action>
		</s:if>
		<s:if test="hasRole('ROLE_SUPER_ADMIN')">
			<s:action name="super-admin" namespace="/home" executeResult="true">
				<s:param name="person.id" value="user.person.id"/>
			</s:action>
		</s:if>
	<!-- END OF HOME PAGE PORTION OF LANDING PAGE -->
</s:if>