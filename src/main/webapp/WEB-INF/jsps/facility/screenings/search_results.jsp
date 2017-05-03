<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<s:url var="searchAgainUrl" action="search" includeParams="all" escapeAmp="false"/>
<script type="text/javascript">
  $(document).ready(function() {
    $(".searchAgain").click(function() {
      window.location.href="<s:property value='%{searchAgainUrl}' escape='false'/>";
    });
  });
</script>
<fieldset>
	<legend>Possible Matches</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:url id="newPersonUrl" action="new-screening-form" namespace="/facility/screenings" includeParams="all"/>
			<s:a href="%{newPersonUrl}" cssClass="ccl-button ajaxify {target: '#screeningsBase'}">
				New Person
			</s:a>
		</div>
	</div>
  <s:if test="lstCtrl.results.isEmpty">
    <span class="tables">There were no results matching your search criteria.</span>
  </s:if>
  <s:else>
		<display:table name="lstCtrl.results" id="screenings" class="tables">
			<display:column title="Name">
				<s:url id="newScreeningLink" action="new-screening-form" namespace="/facility/screenings" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="personId" value="#attr.screenings.personId"/>
				</s:url>
				<s:a href="%{newScreeningLink}" cssClass="ajaxify {target: '#screeningsBase'}">
					<s:property value="#attr.screenings.firstName"/> <s:property value="#attr.screenings.lastName"/>
				</s:a>
			</display:column>
			<display:column title="Birthday">
				<s:date name="#attr.screenings.birthday" format="MM/dd/yyyy"/>
			</display:column>
		</display:table>
	</s:else>
</fieldset>