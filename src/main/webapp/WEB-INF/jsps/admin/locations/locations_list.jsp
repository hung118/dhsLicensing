<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<fieldset>
	<legend>Location Matrix</legend>
	<div class="topControls">
		<div class="mainControls">
			<s:form action="edit-location" method="get" cssClass="ajaxify {target: '#locationsBase'}">
				<s:submit value="New Value"/>
			</s:form>
		</div>
		<dts:listcontrols id="locationsTopControls" name="lstCtrl" action="locations-list" namespace="/admin/locations" enablePaging="true" maxPagesToShow="4" useAjax="true" ajaxTarget="#locationsBase"/>
	</div>
	<display:table name="lstCtrl.results" id="locations" class="tables">
		<display:column title="Zip Code">
			<s:property value="#attr.locations.zipCode"/>
		</display:column>
		<display:column title="City">
			<s:property value="#attr.locations.city"/>
		</display:column>
		<display:column title="County">
			<s:property value="#attr.locations.county"/>
		</display:column>
		<display:column title="State">
			<s:property value="#attr.locations.state"/>
		</display:column>
		<display:column title="Region">
			<s:property value="#attr.locations.region.name"/>
		</display:column>
		<display:column title="R &amp; R Agency">
			<s:property value="#attr.locations.rrAgency.value"/>
		</display:column>
		<display:column>
			<s:url id="locationEditUrl" action="edit-location" includeParams="false">
				<s:param name="location.id" value="#attr.locations.id"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#locationsBase'}" href="%{locationEditUrl}">
				edit
			</s:a>
			|
			<s:url id="locationDeleteUrl" action="delete-location" includeParams="false">
				<s:param name="location.id" value="#attr.locations.id"/>
			</s:url>
			<s:a cssClass="ajaxify {target: '#locationsBase'} ccl-action-delete" href="%{locationDeleteUrl}">
				delete
			</s:a>
		</display:column>
	</display:table>
	<div class="bottomControls">
		<dts:listcontrols id="locationsBottomControls" name="lstCtrl" action="locations-list" namespace="/admin/locations" enablePaging="true" maxPagesToShow="4" useAjax="true" ajaxTarget="#locationsBase"/>
	</div>
</fieldset>