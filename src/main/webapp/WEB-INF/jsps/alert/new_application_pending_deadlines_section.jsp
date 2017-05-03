<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<s:if test="!lstCtrl.results.isEmpty">
	<fieldset>
		<legend><a name="new_app_pend_deadline" class="quickLink">New Application Pending Deadlines</a></legend>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="newAppPendTopControls" name="lstCtrl" namespace="/alert/new-application-pending-deadlines" action="list" useAjax="true" ajaxTarget="#newApplicationPendingDeadlinesSection">
				<s:param name="personId" value="user.person.id"/>
			</dts:listcontrols>
		</div>
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row">
				<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
					<div class="left-column">
						<ccl:facilitydetail name="facility"/>
					</div>
					<div class="right-column">
						<div><span class="label">Application Received Date:</span> <s:date name="applicationReceivedDate" format="MM/dd/yyyy"/></div>
						<s:if test="letterSentDate != null">
							<div><span class="label">Letter Sent Date:</span> <s:date name="letterSentDate" format="MM/dd/yyyy"/></div>
						</s:if>
						<s:else>
							<div class="redtext">Five month letter needed</div>
						</s:else>
						<s:if test="closeFacility">
							<div class="redtext">This application has been in process for 6 months or more and has passed the deadline.  Send the Application Denial Letter and delete the in-process license.</div>
						</s:if>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="newAppPendBottomControls" name="lstCtrl" namespace="/alert/new-application-pending-deadlines" action="list" useAjax="true" ajaxTarget="#newApplicationPendingDeadlinesSection">
				<s:param name="personId" value="user.person.id"/>
			</dts:listcontrols>
		</div>
	</fieldset>
</s:if>
