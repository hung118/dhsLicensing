<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend><s:if test="location != null and location.id != null">Edit</s:if><s:else>Create</s:else> Location </legend>
	<s:fielderror/>
	<s:actionerror/>
	<s:form id="locationForm" action="save-location" method="post" cssClass="ajaxify {target: '#locationsBase'} ccl-action-save">
		<s:hidden name="location.id"/>
		<ol class="fieldList">
			<li>
				<label for="zipCode"><span class="redtext">* </span>Zip Code:</label>
				<s:textfield id="zipCode" name="location.zipCode" cssClass="zipCode" maxlength="5"/>
			</li>
			<li>
				<label for="city"><span class="redtext">* </span>City:</label>
				<s:textfield id="city" name="location.city" cssClass="city"/>
			</li>
			<li>
				<label for="state"><span class="redtext">* </span>State:</label>
				<s:textfield id="state" name="location.state" cssClass="state" maxlength="2"/>
			</li>
			<li>
				<label for="county"><span class="redtext">* </span>County:</label>
				<s:textfield id="county" name="location.county" cssClass="city"/>
			</li>
			<li>
				<label for="region"><span class="redtext">* </span>Region:</label>
				<s:select id="region" name="location.region" value="location.region.id" list="regions" listKey="id" listValue="name" headerKey="-1" headerValue="--Select Region--" cssClass="required"/>
			</li>
			<li>
				<label for="rrAgency"><span class="redtext">* </span>R &amp; R Agency:</label>
				<s:select id="rrAgency" name="location.rrAgency" value="location.rrAgency.id" list="rrAgencies" listKey="id" listValue="value" headerKey="-1" headerValue="--Select R & R Agency--" cssClass="required"/>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="locationEditCancelUrl" action="locations-list" includeParams="false"/>
				<s:a cssClass="ajaxify {target: '#locationsBase'}" href="%{locationEditCancelUrl}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>