<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="print">
	<script type="text/javascript">
		$(document).ready(function() {
			window.print();
		});
	</script>
</s:if>
<h1>Licensing Specialist Caseload for <s:property value="specialist.firstAndLastName"/></h1>
<s:if test="hasActionErrors()">
	<s:actionerror/>
</s:if>
<table class="ccl-search-results">
	<thead>
		<tr>
			<th>Facility Name</th>
			<th class="shrinkCol">Facility ID#</th>
			<th>Address</th>
			<th>Phone</th>
			<th class="shrinkCol">1st Director(s)</th>
			<th class="shrinkCol">Status</th>
			<th>Type</th>
			<th class="shrinkCol">Capacity (&lt;2)</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="caseload" status="row">
			<tr class="<s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
				<td>
					<s:property value="name"/>
				</td>
				<td>
					<s:property value="idNumber"/>
				</td>
				<td>
					<s:property value="locationAddress.toString()"/>
				</td>
				<td class="shrinkCol">
					<s:property value="primaryPhone.formattedPhoneNumber"/>
				</td>
				<td>
					<s:property value="directorNames"/>
				</td>
				<td>
					<s:property value="status.label"/>
				</td>
				<td>
					<s:property value="licenseType.value"/>
				</td>
				<td>
					<s:property value="totalSlots"/> (<s:property value="slotsAgeTwo"/>)
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>