<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>

<s:if test="activity != null and activity.id != null">
  <s:set var="legendText">Edit Activity</s:set>
</s:if>
<s:else>
  <s:set var="legendText">Create Activity</s:set>
</s:else>
<fieldset>
  <legend>
    <s:property value="%{legendText}" />
  </legend>
  <s:actionerror />
  <s:fielderror />
  <s:form id="activityForm" action="save-activity" method="post"
    cssClass="ajaxify {target: '#activitySection'} ccl-action-save">
    <s:hidden name="screeningId" />
    <s:hidden name="activity.id" />
    <ol class="fieldList medium">
      <li>
        <label for="activityDate"><span class="redtext">* </span>Date:</label>
        <s:date id="activityDateFormatted" name="activity.activityDate" format="MM/dd/yyyy" />
        <s:textfield id="activityDate" name="activity.activityDate" value="%{activityDateFormatted}" cssClass=" datepicker" maxlength="10"/>       
      </li>
      <li class="fieldMargin" style="float:left;">
        <label for="description">Description:</label>
        <s:textarea id="description" name="activity.shortDescription"/>
        <div id="descriptionCharCount" class="charCount" style="margin-left:11em;"></div>
      </li>

      <li class="submit">
        <s:submit value="Save"/>
        <s:url id="activityCancelUrl" action="activity-list" includeParams="false">
          <s:param name="screeningId" value="screeningId"/>
        </s:url>
        <s:a href="%{activityCancelUrl}" cssClass="ajaxify {target: '#activitySection'}">
          Cancel
        </s:a>
      </li>
    </ol>
  </s:form>
</fieldset>
<script type="text/javascript">
  $("<s:property value="formSection"/> #description").charCounter(2000, {
    container : "<s:property value="formSection"/> #descriptionCharCount"
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



