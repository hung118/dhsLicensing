<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<s:if test="!lstCtrl.results.isEmpty">
	<s:url var="printLabelsUrl" action="print-labels" escapeAmp="false">
		<s:param name="personId" value="user.person.id"/>
	</s:url>
	<fieldset>
		<legend><a name="accred_exp" class="quickLink">Accreditation Expiration</a></legend>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="accredExpTopControls" name="lstCtrl" namespace="/alert/accreditation-expiration" action="list" useAjax="true" ajaxTarget="#accreditationExpirationSection">
				<s:param name="personId" value="user.person.id"/>
			</dts:listcontrols>
		</div>
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row">
				<li class="ccl-list-item clearfix <s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
					<div class="left-column">
						<ccl:facilitydetail name="facility"/>
					</div>
					<div class="right-column">
						<div><span class="label">Accreditation:</span> <s:property value="agency"/></div>
						<div><span class="label">Date Accreditation Expires:</span> <s:date name="expirationDate" format="MM/dd/yyyy"/></div>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="accredExpBottomControls" name="lstCtrl" namespace="/alert/accreditation-expiration" action="list" useAjax="true" ajaxTarget="#accreditationExpirationSection">
				<s:param name="personId" value="user.person.id"/>
			</dts:listcontrols>
		</div>
	</fieldset>
</s:if>
