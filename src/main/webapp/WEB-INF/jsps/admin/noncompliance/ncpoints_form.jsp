<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>Edit Noncompliance Points</legend>
	<s:actionmessage/>
	<s:fielderror/>
	<s:form id="noncomplianceForm" action="save-points" method="post" cssClass="ajaxify {target: '#ncPointsSection'} ccl-action-save">
		<ol class="fieldList">
			<li>
				<table class="noncompliance_matrix">
					<thead>
						<tr>
							<th></th>
							<s:iterator value="noncomplianceLevels" status="row">
								<th class="<s:if test="#row.odd == true">odd</s:if><s:else>even</s:else>"><s:property value="value"/></th>
							</s:iterator>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="findingsCategories" var="fcat" status="row">
							<tr class="<s:if test="#row.odd == true">odd</s:if><s:else>even</s:else>">
								<td class="row_title"><s:property value="#fcat.value"/></td>
								<s:iterator value="noncomplianceLevels" var="nclev" status="col">
									<s:set var="key"><s:property value="#fcat.id"/>-<s:property value="#nclev.id"/></s:set>
									<td class="<s:if test="#col.odd == true">odd</s:if><s:else>even</s:else>"><input type="text" name="pts-<s:property value='#key'/>" value="<s:property value='noncompliancePoints[#key]'/>" class="<s:if test='fieldErrors[#key]'>inputerror</s:if>"/></td>
								</s:iterator>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</li>
			<li class="submit">
				<s:submit value="Save"/>
				<s:url id="pointsEditCancelUrl" action="edit-points" includeParams="false"/>
				<s:a id="pointsEditCancel" href="%{pointsEditCancelUrl}" cssClass="ajaxify {target: '#ncPointsSection'}">
					Cancel
				</s:a>
			</li>
		</ol>
	</s:form>
</fieldset>