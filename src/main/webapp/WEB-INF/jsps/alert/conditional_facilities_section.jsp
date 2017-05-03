<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<s:if test="!lstCtrl.results.isEmpty">
	<s:url var="printLabelsUrl" action="print-labels" escapeAmp="false">
		<s:param name="personId" value="user.person.id"/>
		<s:param name="roleType" value="roleType"/>
	</s:url>
	<fieldset>
		<legend><a name="<s:property value="roleType"/>_cond_lic" class="quickLink">Facilities with Conditional Licenses/Certificates</a></legend>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="conditionalFacilitiesTopControls" name="lstCtrl" namespace="/alert/conditional-facilities" action="list" useAjax="true" ajaxTarget="#%{roleType}_conditionalFacilitiesSection">
				<s:param name="personId" value="user.person.id"/>
				<s:param name="roleType" value="roleType"/>
			</dts:listcontrols>
		</div>
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row">
				<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
					<div class="left-column">
						<ccl:facilitydetail name="facility"/>
					</div>
					<div class="right-column">
						<div><span class="label">Conditional License Start Date:</span> <s:date name="startDate" format="MM/dd/yyyy"/></div>
						<div><span class="label">Conditional License Expiration Date:</span> <s:date name="expirationDate" format="MM/dd/yyyy"/></div>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="conditionalFacilitiesBottomControls" name="lstCtrl" namespace="/alert/conditional-facilities" action="list" useAjax="true" ajaxTarget="#%{roleType}_conditionalFacilitiesSection">
				<s:param name="personId" value="user.person.id"/>
				<s:param name="roleType" value="roleType"/>
			</dts:listcontrols>
		</div>
	</fieldset>
</s:if>