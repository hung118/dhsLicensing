<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
  $(document).ready(function() {
    $("#ssnLastFour").focus();

    $("#birthday").datepicker({
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

    $("#birthday").mask("99/99/9999");
  });
</script>


<fieldset>
  <legend>Search</legend>
  <s:actionerror />
  <s:fielderror />
  <s:form id="trsSearchForm" action="search-results" namespace="/trackingrecordscreenig/search" method="get"
    cssClass="searchForm">
    <s:hidden name="facilityId" />
    <ol class="fieldList">
      <li><label for="ssnLastFour">SSN Last 4:</label> <s:textfield id="ssnLastFour" name="ssnLastFour"
          cssClass="ssnLastFour" maxlength="4" /></li>
      <li><label>Name:</label>
        <ol class="fieldGroup">
          <li><label for="firstName">First Name:</label> <s:textfield id="firstName" name="firstName"
              cssClass="name" /></li>
          <li><label for="lastName">Last Name:</label> <s:textfield id="lastName" name="lastName" cssClass="name" />
          </li>
        </ol></li>
        
	<security:authorize access="!hasRole('ROLE_ACCESS_PROFILE_VIEW')">
      <li><label for="birthday">Birthday:</label> <s:textfield id="birthday" name="birthday" maxlength="10"
          size="7" /></li>
	</security:authorize>

      <ol class="fieldGroup">
        <li class="submit"><s:submit value="Search" onclick="javascript:submit();" /></li>
        
	<security:authorize access="!hasRole('ROLE_ACCESS_PROFILE_VIEW')">
        <li class="submit"><s:url id="newTrackingRecordScreeningUrl" action="trackingRecordScreeningCreate"
            namespace="/trackingrecordscreening" includeParams="all">
          </s:url> <s:a href="%{newTrackingRecordScreeningUrl}" cssClass="ccl-button ">
        New Person
      </s:a></li>
    </security:authorize>
      </ol>
    </ol>
  </s:form>
</fieldset>