<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<fieldset>
  <legend>Background Screening Summary</legend>
  <p class="tableDescription redtext">
    The information in the far right column indicating whether an individual cleared the background screening is for <strong>internal
      information only</strong>. This information <strong>cannot</strong> be shared with the Licensee or Certificate Holder, or
    the covered individual, by anyone except the Background Clearance Unit.
  </p>
  <span id="screening-errors"></span>
  <div class="topControls marginTop">
    <div class="mainControls">
      <security:authorize
        access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
        <s:url id="newTrackRecordScreeningUrl" action="search-form" namespace="/trackingrecordscreening/search">
          <s:param name="facilityId" value="facilityId" />
        </s:url>
        <s:a href="#" cssClass="ccl-button"
          onclick="javascript:window.open('%{newTrackRecordScreeningUrl}','screening');">
          New Screening
        </s:a>
      </security:authorize>
    </div>
    <dts:listcontrols id="screeningsTopControls" name="lstCtrl" action="screenings-list"
      namespace="/facility/screenings" useAjax="true" ajaxTarget="#screeningsBase" paramExcludes="%{'lstCtrl.range'}">
      <ccl:listrange id="screeningsTopControls" name="lstCtrl" />
      <s:param name="facilityId" value="facilityId" />
    </dts:listcontrols>
  </div>
  <s:if test="!lstCtrl.results.isEmpty">
    <display:table name="lstCtrl.results" id="screenings" class="tables">
      <display:column title="">
        <s:if test="#attr.screenings.closed == true">
          <div class="trs-icon close"></div>
        </s:if>
        <s:elseif test="#attr.screenings.hasNAALetter == true">
          <div class="trs-icon denied"></div>
        </s:elseif>
        <s:elseif test="#attr.screenings.trsMain.naaDate != null">
          <div class="trs-icon denied"></div>
        </s:elseif>
        <s:elseif test="#attr.screenings.trsMain.approvalMailedDate != null">
          <div class="trs-icon final"></div>
        </s:elseif>
        <s:else>
          <div class="trs-icon progress"></div>
        </s:else>
      </display:column>

      <display:column title="Last Name">
        <security:authorize
          access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING','ROLE_ACCESS_PROFILE_VIEW')">
          <s:url id="editScreeningUrl" action="edit-tracking-record-screening" namespace="/trackingrecordscreening"
            includeParams="false" escapeAmp="false">
            <s:param name="facilityId" value="facilityId" />
            <s:param name="screeningId" value="#attr.screenings.id" />
          </s:url>
        </security:authorize>
        
        <s:if test="#editScreeningUrl != null">
          <s:a href="#" onclick="javascript:window.open('%{editScreeningUrl}','screening');">
            <s:property value="#attr.screenings.lastName" />
          </s:a>
        </s:if>
        <s:else>
          <s:property value="#attr.screenings.lastName" />
        </s:else>
      </display:column>
      <display:column title="First Name">
        <s:property value="#attr.screenings.firstName" />
      </display:column>
      <display:column title="Facility Name">
        <s:property value="#attr.screenings.facility.name" />
      </display:column>
      <display:column title="Approval">
        <s:date name="#attr.screenings.trsMain.approvalMailedDate" format="MM/dd/yyyy" />
      </display:column>
      <display:column title="Person Id">
        <s:property value="#attr.screenings.personIdentifier" />
      </display:column>
      <security:authorize
        access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
        <display:column>
          <s:url id="deleteScreeningUrl" action="delete-screening" includeParams="false">
            <s:param name="facilityId" value="facilityId" />
            <s:param name="screeningId" value="#attr.screenings.id" />
          </s:url>
          <s:a href="%{deleteScreeningUrl}" cssClass="ccl-action-delete ccl-delete-link">
            delete
          </s:a>
        </display:column>
      </security:authorize>
    </display:table>
    <div class="bottomControls">
      <dts:listcontrols id="screeningsBottomControls" name="lstCtrl" action="screenings-list"
        namespace="/facility/screenings" useAjax="true" ajaxTarget="#screeningsBase">
        <s:param name="facilityId" value="facilityId" />
      </dts:listcontrols>
    </div>
    <ul class="screenings_legend">
      <li><div class="trs-icon closed"></div>&nbsp;&nbsp; Closed.  Closed by an Administrator.</li>
      <li><div class="trs-icon denied"></div>&nbsp;&nbsp; Denied.  Did not pass background screening.</li>
      <li><div class="trs-icon final"></div>&nbsp;&nbsp; Successfully passed background screening.</li>
      <li><div class="trs-icon progress"></div>&nbsp;&nbsp; In Progress.  Application is currently in progress.</li>
    </ul>
  </s:if>
</fieldset>
<script type="text/javascript">
  $("#screenings").ccl("tableDelete", {
    errorContainer : "#screening-errors"
  });

  
</script>