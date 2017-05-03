<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend><s:if test="region != null and region.id != null">Edit</s:if><s:else>Create</s:else> Region</legend>
	<s:fielderror/>
	<s:actionerror/>
	<s:form id="regionForm" action="save-region" method="post" cssClass="ajaxify {target: '#regionsBase'} ccl-action-save">
		<s:hidden name="region.id"/>
		<ol class="fieldList">
			<li>
				<label for="name"><span class="redtext">* </span>Name:</label>
				<s:textfield id="name" name="region.name" cssClass="longName required"/>
			</li>
			<li class="twoColumn checkboxList">
				<span class="checkboxListLabel"><strong>Select Licensing Specialists:</strong></span>
				<s:checkboxlist id="licensingSpecialists" name="formLicensingSpecialists" value="%{formLicensingSpecialists.{id}}"
								list="licensingSpecialists" listKey="id" listValue="firstAndLastName" template="checkboxlistcolumns"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="regionEditCancelUrl" action="regions-list" includeParams="false"/>
				<s:a id="regionEditCancel" href="%{regionEditCancelUrl}" cssClass="ajaxify {target: '#regionsBase'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>	
</fieldset>