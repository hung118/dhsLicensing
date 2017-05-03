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
<s:set var="formClass">ajaxify {target: '#inspectionsBase'} ccl-action-save</s:set>
<s:set var="formAction">inspection-checklist-update</s:set>
<s:set id="actionBean" />
<s:set var="checklistId" value="#actionBean.checklistView.checklist.id" />
<A NAME="doc_top">
<div style="text-align:right"><a href="#doc_bottom">Bottom</a></div>

<legend>Inspection Checklist - View</legend>
	<s:fielderror/>
	<s:actionerror/>
<s:form id="checkListForm" action="%{formAction}" method="post" cssClass="%{formClass}">
	<s:hidden name="facilityId"/>
	<s:hidden name="complaintId"/>
	<s:hidden name="inspectionId"/>
	<s:hidden name="checklistId" />
<table>
<s:iterator value="#actionBean.checklistView.checklist.headers" var="header">
	<tr>
		<td align="right">
			<span style="font-weight:bold"><s:property value="#header.itemLabel"/>:</span> 
		</td>
		<td>
			<s:if test="#header.itemName == 'program'">
				<s:select id="programType" name="header_program" value="#header.itemValue != null ? #header.itemValue : null"
					  list="programTypes" listKey="id" listValue="value" cssClass="required"/>
			</s:if>
			<s:else>
				<input type="text" name="header_<s:property value="#header.itemName"/>" size="50" maxlength="500" value="<s:property value="#header.itemValue"/>">
			</s:else>
		</td>
	</tr>
</s:iterator>	
</table>
	
<s:iterator value="#actionBean.checklistView" var="row">
<s:set id="section" value="#row.section" />
<div>
	<table>
		<tr>
			<td colspan="2" class="sectionHeader">
				<s:property value="#section.rule.number"/>-<s:property value="#section.sectionBase"/>-<s:property value="#section.number"/>
				<s:property value="#section.title"/> 
				<s:property value="#section.name"/>
				<s:property value="#section.comment"/>
			</td>
		</tr>
	<s:if test="#row.sectionOnly == false">
		<s:iterator value="#row.results" var="subResult">
		<s:set id="sub" value="#subResult.subSection" />
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
				<s:if test="#subResult.result == 'CO'"> 
					<input type="radio" name="icrSub_<s:property value="#subResult.id"/>" value="CO" checked="checked" style="float:none;margin:0 0;height:none" > Compliant
					<input type="radio" name="icrSub_<s:property value="#subResult.id"/>" value="NC" style="float:none;margin:0 0;height:none" > Non-Compliant
					<input type="radio" name="icrSub_<s:property value="#subResult.id"/>" value="NA" style="float:none;margin:0 0;height:none" > N/A
				</s:if>
				<s:elseif test="#subResult.result == 'NC'"> 
					<input type="radio" name="icrSub_<s:property value="#subResult.id"/>" value="CO" style="float:none;margin:0 0;height:none" > Compliant
					<input type="radio" name="icrSub_<s:property value="#subResult.id"/>" value="NC" checked="checked" style="float:none;margin:0 0;height:none" > Non-Compliant
					<input type="radio" name="icrSub_<s:property value="#subResult.id"/>" value="NA" style="float:none;margin:0 0;height:none" > N/A
				</s:elseif>
				<s:elseif test="#subResult.result == 'NA'"> 
					<input type="radio" name="icrSub_<s:property value="#subResult.id"/>" value="CO" style="float:none;margin:0 0;height:none" > Compliant
					<input type="radio" name="icrSub_<s:property value="#subResult.id"/>" value="NC" style="float:none;margin:0 0;height:none" > Non-Compliant
					<input type="radio" name="icrSub_<s:property value="#subResult.id"/>" value="NA" checked="checked" style="float:none;margin:0 0;height:none" > N/A
				</s:elseif>
				<s:else>
					[<s:property value="#subResult.id"/>:<s:property value="#subResult.result"/>]
					Houston, we have a (subsectional) problem!!!
				</s:else>
					&nbsp; &nbsp;
				<span style="font-weight:">Comments</span>
				<textarea rows="1" cols="50" name="icrSubCmt_<s:property value="#subResult.id"/>" style="height:14px;width:40%;margin:0 0;"><s:property value="#subResult.comments" /> </textarea>
			</td>
		</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<s:iterator value="#row.results" var="secResult">
		<tr>
			<td class="ruleFooter">&nbsp;</td>
			<td class="ruleFooter">
				<s:if test="#secResult.result == 'CO'"> 
					<input type="radio" name="icrSec_<s:property value="#secResult.id"/>" value="CO" checked="checked" style="float:none;margin:0 0;height:none" > Compliant
					<input type="radio" name="icrSec_<s:property value="#secResult.id"/>" value="NC" style="float:none;margin:0 0;height:none" > Non-Compliant
					<input type="radio" name="icrSec_<s:property value="#secResult.id"/>" value="NA" style="float:none;margin:0 0;height:none" > N/A
				</s:if>
				<s:elseif test="#secResult.result == 'NC'"> 
					<input type="radio" name="icrSec_<s:property value="#secResult.id"/>" value="CO" style="float:none;margin:0 0;height:none" > Compliant
					<input type="radio" name="icrSec_<s:property value="#secResult.id"/>" value="NC" checked="checked" style="float:none;margin:0 0;height:none" > Non-Compliant
					<input type="radio" name="icrSec_<s:property value="#secResult.id"/>" value="NA" style="float:none;margin:0 0;height:none" > N/A
				</s:elseif>
				<s:elseif test="#secResult.result == 'NA'"> 
					<input type="radio" name="icrSec_<s:property value="#secResult.id"/>" value="CO" style="float:none;margin:0 0;height:none" > Compliant
					<input type="radio" name="icrSec_<s:property value="#secResult.id"/>" value="NC" style="float:none;margin:0 0;height:none" > Non-Compliant
					<input type="radio" name="icrSec_<s:property value="#secResult.id"/>" value="NA" checked="checked" style="float:none;margin:0 0;height:none" > N/A
				</s:elseif>
				<s:else>
					[<s:property value="#secResult.result"/>]
					Houston, we have a (sectional) problem!!!
				</s:else>
				&nbsp; &nbsp;
				<span style="font-weight:">Comments</span>
				<textarea rows="1" cols="50" name="icrSecCmt_<s:property value="#secResult.id"/>" style="height:14px;width:40%;margin:0 0;"><s:property value="#secResult.comments" /></textarea>
			</td>
		</tr>
		</s:iterator>
	</s:else>
	</table>
</div>
</s:iterator>
<%-- <label>Comments:</label>
<textarea rows="1" cols="50" name="comments"><s:property value="#actionBean.checklistView.checklist.comments"/></textarea>
 --%>&nbsp; &nbsp; 
 
<input type="submit" id="inspectionForm_0" value="Save" class="ui-button ui-widget ui-state-default ui-corner-all" role="button">
<s:url id="inspectionEditCancelUrl" action="view-inspection" includeParams="false">
	<s:param name="facilityId" value="facilityId"/>
	<s:param name="inspectionId" value="inspectionId"/>
</s:url>
<s:a href="%{inspectionEditCancelUrl}" cssClass="ccl-button ajaxify {target: '#inspectionsBase'}">
	Cancel
</s:a>
</s:form>

<s:if test="checklistView.checklist.attachment == null">
	<s:include value="upload_checklist_form.jsp">
		<s:param name="facilityId" value="facilityId"/>
		<s:param name="inspectionId" value="inspectionId"/>
		<s:param name="checklistId" value="#checklistView.checklist.id"/>
	</s:include>
</s:if>
<s:else>
	<fieldset>
		<legend>Inspection Checklist Attachment (PDF)</legend>
		<s:url id="viewUploadUrl" action="view-upload" includeParams="false">
			<s:param name="inspectionId" value="inspectionId"/>
			<s:param name="checklistId" value="checklistId"/>
		</s:url>
		<s:a href="%{viewUploadUrl}" cssClass="" target="_blank">View File</s:a>
	</fieldset>
</s:else>

<A NAME="doc_bottom">
<div style="text-align:right"><a href="#doc_top">Top</a></div>