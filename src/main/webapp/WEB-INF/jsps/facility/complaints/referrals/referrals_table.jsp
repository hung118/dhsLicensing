<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:set var="complaint" value="complaint" scope="request"/>
<s:set var="editable" value="%{false}"/>
<security:authorize access="hasPermission('referral-entry', 'complaint')">
	<s:if test="lstCtrl.showControls">
		<s:set var="editable" value="%{true}"/>
	</s:if>
</security:authorize>
<s:if test="#editable">
	<div class="topControls">
		<div class="mainControls">
			<s:url id="editRefUrl" action="edit-referral">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="complaintId" value="complaintId"/>
			</s:url>
			<s:a href="%{editRefUrl}" cssClass="ccl-button ajaxify {target: '#referralsSection'}">
				New Referral
			</s:a>
		</div>
	</div>
</s:if>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="referrals" class="tables">
		<display:column title="Agency">
			<s:if test="#editable">
				<s:url id="editReferralUrl" action="edit-referral" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="referral.id" value="#attr.referrals.id"/>
				</s:url>
				<s:a href="%{editReferralUrl}" cssClass="ajaxify {target: '#referralsSection'}">
					<s:property value="#attr.referrals.agency.value"/>
				</s:a>
			</s:if>
			<s:else>
				<s:property value="#attr.referrals.agency.value"/>
			</s:else>
		</display:column>
		<display:column title="Referral Date">
			<s:date name="#attr.referrals.referralDate" format="MM/dd/yyyy"/>
		</display:column>
		<s:if test="#editable">
			<display:column class="shrinkCol">
				<s:url id="deleteReferralUrl" action="delete-referral" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
					<s:param name="referral.id" value="#attr.referrals.id"/>
				</s:url>
				<s:a href="%{deleteReferralUrl}" cssClass="ajaxify {target: '#referralsSection'} ccl-action-delete">
					Delete
				</s:a>
			</display:column>
		</s:if>
	</display:table>
</s:if>