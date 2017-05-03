<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<s:if test="associatedFacility != null and associatedFacility.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Facility Association</legend>
	<s:fielderror/>
	<s:form id="associatedFacilityForm" action="save-associated-facility" method="post" cssClass="ajaxify {target: '#associatedFacilitiesSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="associatedFacility.id"/>
		<ol class="fieldList">
			<li>
				<label for="assoc-fac"><span class="redtext">* </span>Facility:</label>
				<s:textfield id="assoc-fac" name="associatedFacility.child" value="%{associatedFacility.child.id}" cssClass="required"/>
			</li>
			<li>
				<label><span class="redtext">* </span>Duration:</label>
				<ol class="fieldGroup">
					<li>
						<label for="associationBeginDate">Date Associated:</label>
						<s:date id="formattedAssociationBeginDate" name="associatedFacility.beginDate" format="MM/dd/yyyy"/>
						<s:textfield id="associationBeginDate" name="associatedFacility.beginDate" value="%{formattedAssociationBeginDate}" cssClass="required datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="associationEndDate">Date Unassociated:</label>
						<s:date id="formattedAssociationEndDate" name="associatedFacility.endDate" format="MM/dd/yyyy"/>
						<s:textfield id="associationEndDate" name="associatedFacility.endDate" value="%{formattedAssociationEndDate}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="associated-facilities-list">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#associatedFacilitiesSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="associatedFaciltiesListSection">
		<s:include value="associated_facilities_table.jsp"/>
	</div>
</fieldset>

<script type="text/javascript">
	$("#assoc-fac").facilityautocomplete({
		facilityId: facilityId
	});
</script>
	
<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that ends with 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>



