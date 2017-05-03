<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<fieldset>
  <legend>Main List</legend>

   <div class="topControls marginTop">
      <div class="mainControls">
        <security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
        
          <s:url id="newMainUrl" action="new-trsMain">
           <s:param name="screeningId" value="screeningId"/>
          </s:url>
          <s:a href="%{newMainUrl}" cssClass="ccl-button ajaxify {target: '#mainBase'}">
           New TRS Main Record
          </s:a>
       </security:authorize>

      </div>
     <dts:listcontrols id="screeningsTopControls" name="lstCtrl" action="main-list" namespace="/trackingrecordscreening/main" useAjax="true" ajaxTarget="#mainBase" paramExcludes="%{'lstCtrl.range'}">
      <ccl:listrange id="screeningsTopControls" name="lstCtrl"/>
      <s:param name="screeningId" value="screeningId"/>
    </dts:listcontrols>
    </div>      
  
  <s:if test="!lstCtrl.results.isEmpty">
    <display:table name="lstCtrl.results" id="main" class="tables">
      <display:column title="ID" headerClass="shrinkCol">
        <s:url id="editMainUrl" action="edit-trsMain" includeParams="false">
          <s:param name="trsMainId" value="#attr.main.id"/>
          <s:param name="screeningId" value="screeningId" ></s:param>
        </s:url>
        <s:a cssClass="ajaxify {target: '#mainBase'}" href="%{editMainUrl}">
          <s:property value="#attr.main.trackingRecordScreening.personIdentifier" />
        </s:a>
      </display:column>
      
       <display:column title="Date Received">
        <s:property value="#attr.main.dateReceived"/>
      </display:column>


       <display:column title="Cal Cleared Date">
        <s:property value="#attr.main.calClearedDate"/>
      </display:column>

       <display:column title="Lisa Cleared Date">
        <s:property value="#attr.main.lisaClearedDate"/>
      </display:column>

       <display:column title="Amis Cleared Date">
        <s:property value="#attr.main.amisClearedDate"/>
      </display:column>
      
       <display:column title="Oscar Cleared Date">
        <s:property value="#attr.main.oscarClearedDate"/>
      </display:column>
      <security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
      <display:column>
            <s:url id="deleteMainUrl" action="delete-trsMain" includeParams="false">
            <s:param name="screeningId" value="screeningId"/>
            <s:param name="trsMainId" value="#attr.main.id"/>
          </s:url>
          <s:a href="%{deleteMainUrl}"  cssClass="ajaxify {target: '#mainBase'} ccl-action-delete ccl-delete-link" >
            delete
          </s:a>
        </display:column>  
      </security:authorize>                
    </display:table>

  </s:if>
</fieldset>
