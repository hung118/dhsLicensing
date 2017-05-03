<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:if test="witness != null and witness.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Witness</legend>
	<s:fielderror/>
	<s:form id="witnessForm" action="save-witness" method="post" cssClass="ajaxify {target: '#witnessesSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="complaintId"/>
		<s:hidden name="witness.id"/>
		<ol class="fieldList">
			<li>
				<label>Name:</label>
				<ol class="fieldGroup">
					<li>
						<label for="witnessFirstName"><span class="redtext">* </span>First Name:</label>
						<s:textfield id="witnessFirstName" name="witness.firstName" cssClass="required"/>
					</li>
					<li>
						<label for="witnessLastName">Last Name:</label>
						<s:textfield id="witnessLastName" name="witness.lastName"/>
					</li>
				</ol>
			</li>
			<li>
				<label>Address:</label>
				<ccl:address id="witnessAddress" name="witness.address"/>
			</li>
			<li>
				<label>Phone:</label>
				<ol class="fieldGroup">
					<li>
						<label for="homePhone">Home:</label>
						<s:textfield id="homePhone" name="witness.homePhone" cssClass="phone"/>
					</li>
					<li>
						<label for="alternatePhone">Alternate:</label>
						<s:textfield id="alternatePhone" name="witness.alternatePhone" cssClass="phone"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="witnessEditCancelUrl" action="witnesses-list" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a id="witnessEditCancel" href="%{witnessEditCancelUrl}" cssClass="ajaxify {target: '#witnessesSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="witnessesListSection">
		<s:include value="witnesses_table.jsp"/>
	</div>
</fieldset>