<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<style>
<!--
input.select {}
table {
	border-bottom:0px blue dotted;
	width:100%;
	}
td {
	valign:bottom;
	padding:5px;
	}
.sectionHeader {
	background-color:#CCCCCC;
	border-top:1px #999999 solid;
	font-weight:bold;
	}
.ruleHeader {
	text-align:right;	
	}
.ruleFooter {
	background-color:#EEEEEE;
	valign:bottom;
	}
-->
</style>

<script type="text/javascript">
<!--
function checkCLForm() {
	var err = new Array();
	var checked = new Array();
	var form = document.getElementById("checkListForm");
	for (var i=0; i<form.elements.length; i++) {
		if (form.elements[i].type == "radio") {
			var obj = document.getElementById(form.elements[i].name+"_NC");
			var skip = false;
			for (var j=0; j<checked.length; j++) {
				if (checked[j] == obj.name) {
					skip = true; break;
				}
			}
			if (skip) continue;
			if (obj.checked) { 
				var id = obj.name;
				if (id.indexOf("sub_") == 0) {
					id = id.substring(4);
					var ta = document.getElementsByName("subCmt_"+id);
					if (ta[0].value == "") {
						err[err.length] = obj.name+"="+obj.value+" key="+id;
					}
				} else if (id.indexOf("sec_") == 0) {
					id = id.substring(4);	
					var ta = document.getElementsByName("secCmt_"+id);
					if (ta[0].value == "") {
						err[err.length] = obj.name+"="+obj.value+" key="+id;
					}
				}
			}
			checked[checked.length] = obj.name;
		}
	}

	if (err.length < 1) {
		form.submit();
	} else {
		var msg = err.length+" Rule(s) marked \"Non-Compliant\" and missing required comments.";
		//for (var i=0; i<err.length; i++)
		//	msg += err[i]+"\n";
		alert(msg);
	}
}
//-->
</script>

<s:set var="formClass">ajaxify {target: '#inspectionsBase'} ccl-action-save</s:set>
<s:set var="formAction">inspection-checklist-save</s:set>

<fieldset>
<legend>Inspection Checklist</legend>
	<s:fielderror/>
	<s:actionerror/>
<div style="text-align:right">
	<s:url id="insChkListPrintUrl" action="inspection-checklist-blank-print">
		<s:param name="objectId" value="inspectionId"/>
	</s:url>
	<s:a href="%{insChkListPrintUrl}" cssClass="ccl-button" target="_blank">Print</s:a>
</div>
<s:form id="checkListForm" action="%{formAction}" method="post" cssClass="%{formClass}">
	<s:hidden name="facilityId"/>
	<s:hidden name="complaintId"/>
	<s:hidden name="inspectionId"/>
<table>
<s:iterator value="checklistHeader" var="item">
	<tr>
		<td align="right">
			<span style="font-weight:bold"><s:property value="#item[1]"/>:</span> 
		</td>
		<td>
			<s:if test="#item[0] == 'program'">
				<s:select id="programType" name="header_program" value=""
							  list="programTypes" listKey="id" listValue="value" cssClass="required"/>
			</s:if>
			<s:else>
				<input type="text" name="header_<s:property value="#item[0]"/>" size="50" maxlength="500" value="<s:property value="#item[2]"/>">
			</s:else>
		</td>
	</tr>
</s:iterator>	
</table>

<s:iterator value="ruleSectionList" var="section">
	<s:if test="#section.active == true">
		<div>
			<table>
				<tr>
					<td colspan="2" class="sectionHeader">
						<s:property value="#section.rule.number"/>-<s:property value="#section.sectionBase"/>-<s:property value="#section.number"/>
						<s:property value="#section.title"/> 
						<s:property value="#section.name"/>
					</td>
				</tr>
			<s:if test="#section.subSections.size() > 0">
				<s:iterator value="#section.subSections" var="sub">
					<s:if test="#sub.category.character == 'A'">
						<tr>
							<td class="ruleHeader">
								<s:property value="#sub.number"/>
							</td>
							<td class="ruleContent">
								<s:property value="#sub.ruleContent"/>
							</td>
						</tr>
						<tr>
							<td class="ruleFooter">&nbsp;</td>
							<td class="ruleFooter">
								<input type="radio" name="sub_<s:property value="#sub.id"/>" id="sub_<s:property value="#sub.id"/>_CO" value="CO" checked="checked" style="float:none;margin:0 0;height:none" > Compliant
								<input type="radio" name="sub_<s:property value="#sub.id"/>" id="sub_<s:property value="#sub.id"/>_NC" value="NC" style="float:none;margin:0 0;height:none" > Non-Compliant
								<input type="radio" name="sub_<s:property value="#sub.id"/>" id="sub_<s:property value="#sub.id"/>_NA" value="NA" style="float:none;margin:0 0;height:none" > N/A
								&nbsp; &nbsp;
								<span style="font-weight:">Comments</span>
								<textarea rows="1" cols="50" name="subCmt_<s:property value="#sub.id"/>" style="height:14px;width:40%;margin:0 0;"></textarea>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</s:if>
			<s:else>
					<tr>
						<td class="ruleFooter">&nbsp;</td>
						<td class="ruleFooter">
							<input type="radio" name="sec_<s:property value="#section.id"/>" id="sec_<s:property value="#section.id"/>_CO" value="CO" checked="checked" style="float:none;margin:0 0;height:none" > Compliant
							<input type="radio" name="sec_<s:property value="#section.id"/>" id="sec_<s:property value="#section.id"/>_NC" value="NC" style="float:none;margin:0 0;height:none" > Non-Compliant
							<input type="radio" name="sec_<s:property value="#section.id"/>" id="sec_<s:property value="#section.id"/>_NA" value="NA" style="float:none;margin:0 0;height:none" > N/A
							&nbsp; &nbsp;
							<span style="font-weight:">Comments</span>
							<textarea rows="1" cols="50" name="secCmt_<s:property value="#section.id"/>" style="height:14px;width:40%;margin:0 0;"></textarea>
						</td>
					</tr>
			</s:else>
			</table>
		</div>
	</s:if>
</s:iterator>
<!-- 
<label>Comments:</label>
<textarea rows="1" cols="50" name="comments"></textarea>
 -->
&nbsp; &nbsp; 
<input type="submit" id="inspectionForm_0" value="Save" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" >
<s:url id="inspectionEditCancelUrl" action="inspection-checklist-sections" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="inspectionId" value="inspectionId"/>
</s:url>
<s:a href="%{inspectionEditCancelUrl}" cssClass="ccl-button ajaxify {target: '#inspectionsBase'}">
	Cancel
</s:a>

</s:form>
</fieldset>
