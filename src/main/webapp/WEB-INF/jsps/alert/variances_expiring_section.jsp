<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<s:if test="!lstCtrl.results.isEmpty">
	<s:url var="printLabelsUrl" action="print-labels" escapeAmp="false">
		<s:param name="personId" value="user.person.id"/>
	</s:url>
	<fieldset> 
		<legend><a name="variances_exp" class="quickLink">Variances Expiring</a></legend>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="variancesExpiringTopControls" name="lstCtrl" namespace="/alert/variances-expiring" action="list" useAjax="true" ajaxTarget="#variancesExpiringSection">
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
						<div>
							<s:url id="viewVarianceUrl" action="open-tab" namespace="/facility" includeParams="false">
								<s:param name="facilityId" value="facilityId"/>
								<s:param name="tab" value="%{'variances'}"/>
								<s:param name="act" value="%{'edit-variance'}"/>
								<s:param name="ns" value="%{'/variances'}"/>
								<s:param name="varianceId" value="id"/>
							</s:url>
							<span class="label">Variance Start Date:</span> 
							<s:a href="%{viewVarianceUrl}">
								<s:date name="startDate" format="MM/dd/yyyy"/>
							</s:a>
							
						</div>
						<div><span class="label">Variance End Date:</span> <s:date name="endDate" format="MM/dd/yyyy"/></div>
						<div><span class="label">Rule #:</span> 501-<s:property value="ruleNumber"/></div>
					</div>
					<div class="clear">
						<div>
							<span class="label">Director Response:</span>
							<span class="description"><s:property value="directorResponse.trim()"/></span>
						</div>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<div class="ccl-list-left-ctrls">
				<button type="button" class="mailingLabelsPrint {url: '<s:property value='%{printLabelsUrl}' escape='false'/>'}">Print Mailing Labels</button>
			</div>
			<dts:listcontrols id="variancesExpiringBottomControls" name="lstCtrl" namespace="/alert/variances-expiring" action="list" useAjax="true" ajaxTarget="#variancesExpiringSection">
				<s:param name="personId" value="user.person.id"/>
			</dts:listcontrols>
		</div>
	</fieldset>
</s:if>