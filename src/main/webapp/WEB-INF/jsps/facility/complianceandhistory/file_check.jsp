<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:url var="ruleUrlTmpl" action="download-file" namespace="/docs" escapeAmp="false"/>
<script id="rule-dialog-tmpl" type="text/x-jquery-tmpl">
	<object id='rule-pdf' class='embedded' width='100%' height='95%' type='application/pdf' data='{{= url}}'>You must install Adobe Reader in order to view this file.</object>
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".ccl-rule").click(function() {
			$(".ccl-generic-dialog").empty();
			if ($(this).metadata().ruleTextDocId) {
				var data = {
					url: '<s:property value='ruleUrlTmpl' escape='false'/>?fileId=' + $(this).metadata().ruleTextDocId + '&attachment=false&attachment=false#toolbar=0&navpanes=0&view=fitH'
				}
				
				$("#rule-dialog-tmpl").tmpl(data).appendTo(".ccl-generic-dialog");
			} else {
				$("<span>No text found for rule</span>").appendTo(".ccl-generic-dialog");
			}
			
			$(".ccl-generic-dialog").dialog({
				resizable: false,
				modal: true,
				closeOnEscape: false,
				width: 800,
				height: 500,
				title: $(this).html()});
			return false;
		});
	});
</script>
<s:include value="navigation.jsp"><s:param name="selectedTab">fileCheck</s:param></s:include>
<fieldset>
	<legend>File Check</legend>
	<div class="topControls">
		<dts:listcontrols id="fileCheckTopControls" name="lstCtrl" action="file-check" namespace="/facility/complianceandhistory" useAjax="true" ajaxTarget="#complianceandhistoryBase" showSorter="false" paramExcludes="%{'lstCtrl.range'}">
			<ccl:listrange id="fileCheckTopControls" name="lstCtrl"/>
			<s:param name="facilityId" value="facilityId"/>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row">
				<li>
					<div class="left-column">
						<div><span class="label">Inspection Date:</span>
							<s:url id="inspectionUrl" action="open-tab" namespace="/facility" includeParams="false">
								<s:param name="facilityId" value="facilityId"/>
								<s:param name="tab" value="%{'inspections'}"/>
								<s:param name="act" value="%{'edit-inspection-record'}"/>
								<s:param name="ns" value="%{'/inspections'}"/>
								<s:param name="inspectionId" value="inspectionId"/>
							</s:url>
							<s:a href="%{inspectionUrl}">
								<s:date name="inspectionDate" format="MM/dd/yyyy"/>
							</s:a>
						</div>
						<div><span class="label">Inspection Type(s):</span> <s:property value="inspectionTypes"/></div>
						<s:if test="#attr.complaintDate != null">
							<div><span class="label">Complaint Date:</span>
								<s:url id="complaintUrl" action="open-tab" namespace="/facility" includeParams="false">
									<s:param name="facilityId" value="facilityId"/>
									<s:param name="tab" value="%{'complaints'}"/>
									<s:param name="act" value="%{'tab'}"/>
									<s:param name="ns" value="%{'/complaints/intake'}"/>
									<s:param name="complaintId" value="complaintId"/>
								</s:url>
								<s:a href="%{complaintUrl}">
									<s:date name="complaintDate" format="MM/dd/yyyy"/>
								</s:a>
							</div>
						</s:if>
						<div><span class="label">Rule:</span> <a href="#" class="ccl-rule {ruleTextDocId: <s:if test="ruleTextDocId != null"><s:property value="ruleTextDocId"/></s:if><s:else>null</s:else>}"><ccl:rule ruleId="ruleId" format="RNUM: RDESC"/></a></div>
					</div>
					<div class="right-column">
						<div><span class="label">Finding Category:</span> <s:property value="findingCategory"/></div>
						<div><span class="label">Noncompliance Level:</span> <s:property value="noncomplianceLevel"/></div>
						<div><span class="label">Date Correction was Verified:</span> <s:if test="verificationType.name().equals('VERIFIED')"><s:date name="correctionDate" format="MM/dd/yyyy"/></s:if><s:elseif test="!verificationType.name().equals('VERIFICATION_PENDING')"><s:property value="verificationType.label"/></s:elseif></div>
					</div>
					<div class="clear">
						<div><span class="label">Finding:</span> <span class="description"><s:if test="@org.apache.commons.lang.StringUtils@isNotEmpty(declarativeStatement)"><s:property value="declarativeStatement.trim()"/></s:if></span></div>
						<div><span class="label">Additional Information:</span> <span class="description"><s:if test="@org.apache.commons.lang.StringUtils@isNotEmpty(additionalInformation)"><s:property value="additionalInformation.trim()"/></s:if></span></div>
					</div>
				</li>
			</s:iterator>
		</ol>
		<div class="bottomControls">
			<dts:listcontrols id="fileCheckBottomControls" name="lstCtrl" action="file-check" namespace="/facility/complianceandhistory" useAjax="true" ajaxTarget="#complianceandhistoryBase" showSorter="false" paramExcludes="%{'lstCtrl.range'}">
				<ccl:listrange id="fileCheckBottomControls" name="lstCtrl"/>
				<s:param name="facilityId" value="facilityId"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>