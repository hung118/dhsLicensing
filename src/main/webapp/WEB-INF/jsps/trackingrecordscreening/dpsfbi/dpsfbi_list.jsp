<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<fieldset>
  <legend>DPS/FBI List</legend>

   <div class="topControls marginTop">
      <div class="dpsfbiControls">
        <security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
        
          <s:url id="newDpsFbiUrl" action="new-trsDpsFbi">
           <s:param name="screeningId" value="screeningId"/>
          </s:url>
          <s:a href="%{newDpsFbiUrl}" cssClass="ccl-button ajaxify {target: '#dpsFbiBase'}">
           New TRS DPS/FBI Record
          </s:a>
       </security:authorize>

      </div>                                                            
     <dts:listcontrols id="screeningsTopControls" name="lstCtrl" action="dpsFbi-list" namespace="/trackingrecordscreening/dpsfbi" useAjax="true" ajaxTarget="#dpsFbiBase" paramExcludes="%{'lstCtrl.range'}">
      <ccl:listrange id="screeningsTopControls" name="lstCtrl"/>
      <s:param name="screeningId" value="screeningId"/>
    </dts:listcontrols>
    </div>      
  
  <s:if test="!lstCtrl.results.isEmpty">
    <display:table name="lstCtrl.results" id="dpsfbi" class="tables">
      <display:column title="ID" headerClass="shrinkCol">
        <s:url id="editdpsfbiUrl" action="edit-trsdpsfbi" includeParams="false">
          <s:param name="trsDpsFbiId" value="#attr.dpsfbi.id"/>
          <s:param name="screeningId" value="screeningId" ></s:param>
        </s:url>
        <s:a cssClass="ajaxify {target: '#dpsFbiBase'}" href="%{editdpsfbiUrl}">
          <s:property value="#attr.dpsfbi.trackingRecordScreening.personIdentifier" />
        </s:a>
      </display:column>
      
       <display:column title="1st FBI Req">
        <s:property value="#attr.dpsfbi.firstFbiRequestDate"/>
      </display:column>


       <display:column title="2nd FBI Req">
        <s:property value="#attr.dpsfbi.secondFbiRequestDate"/>
      </display:column>

       <display:column title="To DPS for verification">
        <s:property value="#attr.dpsfbi.toDpsForVerificationDate"/>
      </display:column>

       <display:column title="DPS Verified">
        <s:property value="#attr.dpsfbi.dpsVerifiedDate"/>
      </display:column>
      
       <display:column title="Live Scan">
        <s:property value="#attr.dpsfbi.livescanDate"/>
      </display:column>
      <security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')">
      <display:column>
            <s:url id="deletedpsfbiUrl" action="delete-trsdpsfbi" includeParams="false">
            <s:param name="screeningId" value="screeningId"/>
            <s:param name="trsDpsFbiId" value="#attr.dpsfbi.id"/>
          </s:url>
          <s:a href="%{deletedpsfbiUrl}" cssClass="ajaxify {target: '#dpsFbiBase'} ccl-action-delete ccl-delete-link">
            delete
          </s:a>
        </display:column>  
      </security:authorize>                
    </display:table>

  </s:if>
</fieldset>
