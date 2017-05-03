<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="rating != null and rating.id != null">
	<s:set var="legendText">Edit</s:set>
</s:if>
<s:else>
	<s:set var="legendText">Create</s:set>
</s:else>
<fieldset>
	<legend><s:property value="%{legendText}"/> Rating</legend>
	<s:fielderror/>
	<s:form id="facilityRatingForm" action="save-rating" method="post" cssClass="ajaxify {target: '#ratingsSection'} ccl-action-save">
		<s:hidden name="facilityId"/>
		<s:hidden name="rating.id"/>
		<ol class="fieldList">
			<li>
				<label for="rating"><span class="redtext">* </span>Rating:</label>
				<s:select id="rating" name="rating.rating" value="rating.rating.id" list="ratings" listKey="id" listValue="value" headerKey="-1" headerValue="- Select a Rating -" cssClass="required"/>
			</li>
			<li>
				<label>Duration:</label>
				<ol class="fieldGroup">
					<li>
						<label for="ratingStartDate">Start Date:</label>
						<s:date id="ratingStartDateFormatted" name="rating.startDate" format="MM/dd/yyyy" />
						<s:textfield id="ratingStartDate" name="rating.startDate" value="%{ratingStartDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
					<li>
						<label for="ratingEndDate">End Date:</label>
						<s:date id="ratingEndDateFormatted" name="rating.endDate" format="MM/dd/yyyy" />
						<s:textfield id="ratingEndDate" name="rating.endDate" value="%{ratingEndDateFormatted}" cssClass="datepicker" maxlength="10"/>
					</li>
				</ol>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="cancelUrl" action="ratings-list">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#ratingsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
	<div id="ratingsListSection">
		<s:include value="ratings_table.jsp"/>
	</div>
</fieldset>

<script type="text/javascript">
	// *** Short key ctrl-q for current date on fields that have 'Date' in id attribute. ***
	$('[id $= "Date"]').keydown(function(e) {
        //CTRL + Q keydown for current date. Key codes: http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
        if(e.ctrlKey && e.keyCode == 81){
            var currentDate = new Date();
            $(this).val(currentDate.format("mm/dd/yyyy"));
        }
    });
</script>

