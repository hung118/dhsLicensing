<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend>Conviction List</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newConvictionUrl" action="edit-conviction">
				<s:param name="screeningId" value="screeningId"/>
			</s:url>
			<s:a href="%{newConvictionUrl}" cssClass="ccl-button ajaxify {target: '#convictionsSection'}">
				New Conviction
			</s:a>
		</div>
	</div>
	<s:if test="!convictions.isEmpty">
		<display:table name="convictions" id="con" class="tables">
			<display:column title="Type" headerClass="shrinkCol">
				<s:url id="editConvictionUrl" action="edit-conviction" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="convictionId" value="#attr.con.id"/>
				</s:url>
				<s:a cssClass="ajaxify {target: '#convictionsSection'}" href="%{editConvictionUrl}">
					<s:property value="%{#attr.con.convictionType.value}" />
				</s:a>
			</display:column>
			<display:column title="Conviction">
				<s:property value="%{#attr.con.shortConvictionDesc}"/>
			</display:column>
			<display:column title="Date" headerClass="shrinkCol">
				<s:date name="#attr.con.convictionDate" format="MM/dd/yyyy"/>
			</display:column>
			<display:column title="Dismissed" headerClass="shrinkCol">
				<s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(#attr.con.dismissed).displayName"/>
			</display:column>
			<display:column title="Court Info">
				<s:property value="%{#attr.con.shortCourtInfo}"/>
			</display:column>
			<display:column headerClass="shrinkCol">
				<s:url id="deleteConvictionUrl" action="delete-conviction" includeParams="false">
					<s:param name="screeningId" value="screeningId"/>
					<s:param name="convictionId" value="#attr.con.id"/>
				</s:url>
				<s:a href="%{deleteConvictionUrl}" cssClass="ajaxify {target: '#convictionsSection'} ccl-action-delete ccl-delete-link">
					delete
				</s:a>
			</display:column>
		</display:table>
	</s:if>
</fieldset>
