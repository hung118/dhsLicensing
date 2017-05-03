<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
  $(document).ready(function() {
    $("#birthdate").focus();

    $("#tabs a").each(function() {
      if ($(this).text() == "Main" && $("#trsMain").val() == "true") {
        applyCssProperties(this);
      }
      if ($(this).text() == "DPS/FBI" && $("#trsDpsFbi").val() == "true") {
        applyCssProperties(this);
      }
      if ($(this).text() == "Requests" && $("#trsRequest").val() == "true") {
        applyCssProperties(this);
      }
      if ($(this).text() == "Letters" && $("#trsLetter").val() == "true") {
        applyCssProperties(this);
      }
      if ($(this).text() == "CBS Comm" && $("#trsCbsComm").val() == "true") {
        applyCssProperties(this);
      }
      if ($(this).text() == "MIS Comm" && $("#trsMisComm").val() == "true") {
        applyCssProperties(this);
      }
      if ($(this).text() == "OSCAR" && $("#trsOscar").val() == "true") {
        applyCssProperties(this);
      }
      if ($(this).text() == "Activity" && $("#trsActivity").val() == "true") {
        applyCssProperties(this);
      }
    });

    function applyCssProperties(thisObject) {
      $(thisObject).css('border-bottom', '4px solid orange');
    }

    $("#birthdate").datepicker({
      maxDate : "-18Y",
      yearRange : "c-90:c+0",
      changeMonth : true,
      changeYear : true,
      showOtherMonths : true,
      selectOtherMonths : true,
      constrainInput : true,
      dateFormat : 'mm/dd/yy',
      showOn : "button",
      buttonImage : context + "/images/calbtn.gif",
      buttonImageOnly : true
    });

    $("#birthdate").mask("99/99/9999");
	
	$("#originalTrackingPersonButton").click(function(){
		$("#originalTrackingPerson").toggle();
	});
  });

  function markSave(tabDesc) {
    $("a:contains(" + "'" + tabDesc + "')").css('border-bottom', '4px solid orange');
  }

  function markNotSave(tabDesc) {
    $("a:contains(" + "'" + tabDesc + "')").css('border-bottom', '4px solid #666666');
  }
</script>

<s:set var="action">saveTrackingRecordScreening</s:set>
<s:set var="namespace">/trackingrecordscreening</s:set>
<s:set var="formClass">ajaxify {target: '#trackingRecordScreeningBase'} ccl-action-save</s:set>
<s:set var="cancelClass">ajaxify {target: '#trackingRecordScreeningBase'}</s:set>
<s:url id="EditCancelUrl" namespace="/trackingrecordscreening/search" action="search-form" includeParams="false" />


<div id="searchButton">
  <s:url id="trsSearchUrl" namespace="/trackingrecordscreening/search" action="search-form" includeParams="false" />
  <s:a href="%{trsSearchUrl}">Screening Search</s:a>
</div>
<s:if test="trackingRecordScreening != null and trackingRecordScreening.id != null">
  <s:set var="legendText">Edit Tracking Record Screening</s:set>
</s:if>
<s:else>
  <s:set var="legendText">Create New Tracking Record Screening</s:set>
</s:else>
<s:fielderror />
<s:actionerror />
<fieldset>
  <legend>
    <s:property value="%{legendText}" />
  </legend>
  <s:form id="trsForm" action="%{action}" namespace="%{namespace}" method="post">
    <s:hidden name="screeningId" value="%{trackingRecordScreening.id}" />
    <s:hidden name="facilityId"></s:hidden>
    <s:hidden name="trsMain" value="%{trsMain}" id="trsMain" />
    <s:hidden name="trsDpsFbi" value="%{trsDpsFbi}" id="trsDpsFbi" />
    <s:hidden name="trsRequest" value="%{trsRequest}" id="trsRequest" />
    <s:hidden name="trsLetter" value="%{trsLetter}" id="trsLetter" />
    <s:hidden name="trsCbsComm" value="%{trsCbsComm}" id="trsCbsComm" />
    <s:hidden name="trsMisComm" value="%{trsMisComm}" id="trsMisComm" />
    <s:hidden name="trsOscar" value="%{trsOscar}" id="trsOscar" />
    <s:hidden name="trsActivity" value="%{trsActivity}" id="trsActivity" />

	<div id="originalTrackingPersonContainer" style="text-align: left; position: absolute; left: 424px;">
		<span id="originalTrackingPersonButton" class="trs-magifier"></span>
		<div id="originalTrackingPerson" style="display: none; width:100%;">
			Person Record Name:
			<s:property value="trackingRecordScreening.person.firstName" />
			<s:property value="trackingRecordScreening.person.lastName" />
			<s:hidden name="trackingRecordScreening.person.id" value="%{trackingRecordScreening.person.id}" />
		</div>
	</div>
	
    <ol class="fieldList">
      <li>
      	<label>Close File:</label>
        <ol class="fieldGroup">
          <li class="checkbox maintrs">
          	<s:checkbox id="closed" name="trackingRecordScreening.closed" title="Close File" disabled="%{viewOnly}" />
          </li>
        </ol>
      </li>
		  <li>
			  <label><span class="redtext">* </span>Identifiers:</label>
			  <ol class="fieldGroup">
			  	<security:authorize access="!hasRole('ROLE_ACCESS_PROFILE_VIEW')">	
				  <li>
					  <label for="birthdate">DOB:</label>
					  <s:date id="birthdateDateFormatted" name="trackingRecordScreening.birthday" format="MM/dd/yyyy" />
					  <s:textfield id="birthdate" name="trackingRecordScreening.birthday" value="%{birthdateDateFormatted}" maxlength="10" size="10" title="Date of Birth" />
				  </li>
				</security:authorize>
				  <li>
					  <label for="ssnLastFour">SSN Last four:</label>
					  <s:textfield id="ssnLastFour" name="trackingRecordScreening.ssnLastFour" cssClass="required" maxlength="4" size="10" readonly="%{viewOnly}" />
				  </li>
			  </ol>
		  </li>
      <li>
      	<label><span class="redtext">* </span>Name:</label>
        <ol class="fieldGroup">
          <li>
          	<label for="firstName">First Name:</label>
          	<s:textfield id="firstName" name="trackingRecordScreening.firstName" cssClass="required name" cssStyle="width:300px !important;" readonly="%{viewOnly}" />
          </li>
          <li>
          	<label for="lastName">Last Name:</label>
          	<s:textfield id="lastName" name="trackingRecordScreening.lastName" cssClass="required name" cssStyle="width:300px !important;" readonly="%{viewOnly}" />
          </li>
          
          <li class="clearLeft fieldMargin">
          	<label for="personAliases">Alias:</label>
          	<s:textfield id="personAliases" name="trackingRecordScreening.personAliases" cssStyle="width:300px !important;" readonly="%{viewOnly}" />
          </li>
          
        </ol>
      </li>

      <li>
      	<label for=" provider"><span class="redtext">* </span>Requesting Facility:</label>
        <s:textfield id="provider" name="trackingRecordScreening.facility" value="%{trackingRecordScreening.facility.id}" cssClass="required" readonly="%{viewOnly}" />
      </li>

      <li style="position: absolute; right: 0px; top: 51px; width: 140px; height:80%;  padding-left:20px; border-left:1px solid #ccc;">
      	<label></label>
        <ol>
          <li class="checkbox maintrs">
          	<s:checkbox id="cpf" name="trackingRecordScreening.cpf" title="Child Placing Foster" disabled="%{viewOnly}" />
          	<label for="cpf" title="Child Placing Foster">CPF</label>
          </li>
          <li class="checkbox maintrs">
          	<s:checkbox id="aw" name="trackingRecordScreening.aw" title="Adam Watch" disabled="%{viewOnly}" />
          	<label for="aw" title="Adam Watch">AW</label>
          </li>
          <li class="checkbox maintrs">
          	<s:checkbox id="relative" name="trackingRecordScreening.relative" disabled="%{viewOnly}" />
          	<label for="relative">Relative</label>
          </li>
          <li class="checkbox maintrs">
          	<s:checkbox id="nsc" name="trackingRecordScreening.nsc" title="Non State Custody (Child Placement)" disabled="%{viewOnly}" />
          	<label for="nsc" title="Non State Custody (Child Placement)">NSC</label>
          </li>
          <li class="checkbox maintrs">
          	<s:checkbox id="dcfs" name="trackingRecordScreening.adultInHome" disabled="%{viewOnly}" />
          	<label for="dcfs">Adult In Home</label>
          </li>
          <li class="checkbox maintrs">
          	<s:checkbox id="dspdSas" name="trackingRecordScreening.dspdSas" title="Division of Services for People with Disabilities / Self-Administered Services" disabled="%{viewOnly}" />
          	<label for="dspdSas" title="Division of Services for People with Disabilities / Self-Administered Services">DSPD/SAS</label>
          </li>
          <li class="checkbox maintrs">
          	<s:checkbox id="expedited" name="trackingRecordScreening.expedited" disabled="%{viewOnly}" />
          	<label for="expedited">Expedited</label>
          </li>
        </ol>
      </li>
      <security:authorize access="!hasRole('ROLE_ACCESS_PROFILE_VIEW')">
	      <ol class="fieldGroup">
	      <li class="submit"><s:submit value="Save" />
	      	<s:a href="#" onclick="javascript:window.open('%{trsSearchUrl}','screening');">Cancel</s:a>
	      </li>
	      <li class="submit"><s:url id="newTrackingRecordScreeningUrl" action="trackingRecordScreeningCreate"
	            namespace="/trackingrecordscreening" includeParams="all">
	            <s:param name="fromEditForm" value="true"></s:param>
	          </s:url> <s:a href="%{newTrackingRecordScreeningUrl}" cssClass="ccl-button ">
	        New Person
	      </s:a></li>
	      </ol>
      </security:authorize>
    </ol>
  </s:form>
  <script type="text/javascript">
      $("#provider").facilityautocomplete({
        facilityId : facilityId
      });
    </script>
</fieldset>
