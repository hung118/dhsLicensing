<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:if test="complainant != null and complainant.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Complainant</legend>
	<s:fielderror/>
	<s:form id="complainantForm" action="save-complainant" method="post" cssClass="ajaxify {target: '#complainantSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="complaintId"/>
		<ol class="fieldList">
			<li>
				<label for="complainantRelationship">Relationship to Provider:</label>
				<s:select id="complainantRelationship" name="complainant.complainantRelationship" value="complainant.complainantRelationship.id"
						  list="complainantRelationships" listKey="id" listValue="value" headerKey="-1" headerValue="- Select Relationship -"
						  cssClass="required"/>
			</li>
			<li class="nm-given">
				<label>Name:</label>
				<ol class="fieldGroup">
					<li>
						<label for="complainantFirstName">First Name:</label>
						<s:textfield id="complainantFirstName" name="complainant.person.firstName" cssClass="required"/>
					</li>
					<li>
						<label for="complainantMiddleName">Middle Name:</label>
						<s:textfield id="complainantMiddleName" name="complainant.person.middleName"/>
					</li>
					<li>
						<label for="complainantLastName">Last Name:</label>
						<s:textfield id="complainantLastName" name="complainant.person.lastName" cssClass="required"/>
					</li>
				</ol>
			</li>
			<li class="nm-given nm-refused checkbox">
				<s:checkbox id="name-refused" name="nameRefused"/>
				<label for="name-refused">The complainant refused to provide his/her name.</label>
			</li>
			<li class="nm-given nm-confidential">
				<strong>Do not ask the complainant if they want to remain confidential unless they mention confidentiality.</strong> 
			</li>
			<li class="nm-given nm-confidential checkbox">
				<s:checkbox id="name-confidential" name="nameConfidential"/>
				<label for="name-confidential">The complainant wants to have his/her name remain confidential.</label>
			</li>
			<li class="nm-confidential">
				If the complainant elects to remain confidential:
			</li>
			<li class="nm-confidential radiosWithLabel">
				<label for="indefinitelyAnonymous"><span class="redtext">* </span>How long does the complainant wish to remain confidential?</label>
				<input id="conf-indef" type="radio" name="confidentialIndefinitely" value="true"<s:if test="confidentialIndefinitely"> checked="checked"</s:if>/><label for="conf-indef">Indefinitely</label>
				<input id="conf-end" type="radio" name="confidentialIndefinitely" value="false"<s:if test="!confidentialIndefinitely"> checked="checked"</s:if>/><label for="conf-end">Until the end of the investigation</label>
			</li>
			<li class="nm-refused">
				<strong>If the complainant chooses to withhold his/her name, read the following:</strong> "The Department cannot
				investigate an anonymous complaint."
			</li>
			<li class="nm-confidential">
				<div><strong>If the complainant chooses to keep his/her name confidential, read the following:</strong>
					<ul class="bulletedList">
						<li>The Department can withhold the identity of a confidential complainant from the provider being investigated.</li>
						<li>The Department may not cite a finding based on the information provided by a confidential complainant alone.
							The department must be able to verify the allegations independently for a finding to be cited.
						</li>
					</ul>
				</div>
			</li>
			<li class="nm-refused checkbox">
				<s:checkbox id="anonymousStatementRead" name="complainant.anonymousStatementRead"/>
				<label for="anonymousStatementRead">I have read the anonymous disclosure statement to the complainant.</label>
			</li>
			<li class="nm-confidential checkbox">
				<s:checkbox id="confidentialStatementRead" name="complainant.confidentialStatementRead"/>
				<label for="confidentialStatementRead">I have read the confidential disclosure statement to the complainant.</label>
			</li>
			<li class="nm-refused">
				<strong>Encourage the complainant to provide his/her name by explaining that his/her name can be kept confidential and explain
				the different types of confidential complaints:</strong>
				<ol class="bulletedList">
					<li>
						If the complainant elects to be a confidential complainant indefinitely, licensing will not reveal his/her name to the
						subject of the complaint but will need to independently corroborate the information given when deciding whether or not
						to substantiate allegations.
					</li>
					<li>
						If the complainant elects to be a confidential complainant until the completion of this investigation, licensing can
						disclose the name of the complainant at the completion of the investigation and can use the information given when
						deciding whether or not to substantiate allegations.
					</li>
				</ol>
				<br/>
				Please click the button below if the complainant continues to refuse to provide his/her name.  The complaint will be sent to
				the complaint investigator and considered an anonymous complaint.
			</li>
			<li class="nm-given">
				<label>Address:</label>
				<ccl:address id="complainantAddress" name="complainant.person.address"/>
			</li>
			<li class="nm-given">
				<label>Phone:</label>
				<ol class="fieldGroup">
					<li>
						<label for="homePhone">Home:</label>
						<s:textfield id="homePhone" name="complainant.person.homePhone" cssClass="phone required"/>
					</li>
					<li>
						<label for="workPhone">Work:</label>
						<s:textfield id="workPhone" name="complainant.person.workPhone" cssClass="phone"/>
					</li>
					<li>
						<label for="cellPhone">Cell:</label>
						<s:textfield id="cellPhone" name="complainant.person.cellPhone" cssClass="phone"/>
					</li>
					<li class="clearLeft fieldMargin">
						<label for="bestTimeToCall">Best Time to Call:</label>
						<s:textfield id="bestTimeToCall" name="complainant.bestTimeToCall"/>
					</li>
				</ol>
			</li>
			<li class="nm-given">
				<label for="complainantEmail">Email:</label>
				<s:textfield id="complainantEmail" name="complainant.person.email" cssClass="email"/>
			</li>
			<li class="submit">
				<s:submit value="Save" cssClass="nm-given nm-confidential"/>
				<div class="nm-refused" style="display: inline;">
					<s:url id="nameRefusedUrl" action="complete-anonymous-intake">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="complaintId" value="complaintId"/>
					</s:url>
					<s:a id="nm-refused-intake-complete" href="%{nameRefusedUrl}" cssClass="ccl-button ccl-action-save ccl-state-change {title: 'Submit Anonymous Complaint', onSetup: function() {serializeComplainant();}, onSuccess: function() {refreshComplaint();}}">
						Intake Complete With No Complainant Name
					</s:a>
				</div>
				<s:url id="complainantEditCancelUrl" action="view-complainant" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="complaintId" value="complaintId"/>
				</s:url>
				<s:a id="complainantEditCancel" href="%{complainantEditCancelUrl}" cssClass="ajaxify {target: '#complainantSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>
<script type="text/javascript">
	initComplainantForm();
</script>