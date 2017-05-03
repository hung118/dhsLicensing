<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<fieldset>
	<legend>Fees Waiting for Approval</legend>
	<span id="cmp-errors"></span>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newCmpUrl" action="new-cmp">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<s:a href="%{newCmpUrl}" cssClass="ccl-button ajaxify {target: '#cmpsBase'}">
				New Fee
			</s:a>
		</div>
		<dts:listcontrols id="cmpWaitingTopControls" name="lstCtrl" action="waiting-list" namespace="/facility/cmps" useAjax="true" ajaxTarget="#cmpsWaitingSection">
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<display:table name="lstCtrl.results" id="cmps" class="tables">
			<display:column title="Received Date" headerClass="shrinkCol">
				<s:url id="editCmpUrl" action="edit-cmp" includeParams="false">
					<s:param name="transaction.id" value="#attr.cmps.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#cmpsBase'}" href="%{editCmpUrl}">
					<s:date name="#attr.cmps.date" format="MM/dd/yyyy"/>
				</s:a>
			</display:column>
			<display:column title="Check Date" headerClass="shrinkCol">
				<s:date name="#attr.cmps.checkDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Check Owner">
				<s:property value="#attr.cmps.checkOwner"/>
			</display:column>
			<display:column title="Amount" headerClass="displayHeaderCurrency shrinkCol" decorator="gov.utah.dts.det.ccl.view.admin.DisplayCurrency" style="text-align:right;">
				<s:property value="#attr.cmps.amount"/>
			</display:column>
			<display:column>
				<security:authorize access="hasRole('ROLE_SUPER_ADMIN')">
					<s:url id="deleteCmpUrl" action="delete-cmp" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="transaction.id" value="#attr.cmps.id"/>
					</s:url>
					<s:a href="%{deleteCmpUrl}" cssClass="ccl-action-delete ccl-delete-link">
						delete
					</s:a>
				</security:authorize>
			</display:column>
		</display:table>
		<div class="bottomControls">
			<dts:listcontrols id="cmpWaitingBottomControls" name="lstCtrl" action="waiting-list" namespace="/facility/cmps" useAjax="true" ajaxTarget="#cmpsWaitingSection">
				<s:param name="facilityId" value="facilityId"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>
<script type="text/javascript">
	$("#cmps").ccl("tableDelete", {errorContainer: "#cmp-errors"});
</script>