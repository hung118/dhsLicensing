<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST')">
	<s:if test="lstCtrl.showControls">
		<div class="topControls">
			<div class="mainControls">
				<s:url id="newRatingUrl" action="edit-rating">
					<s:param name="facilityId" value="facilityId"/>
				</s:url>
				<s:a href="%{newRatingUrl}" cssClass="ccl-button ajaxify {target: '#ratingsSection'}">
					New Rating
				</s:a>
			</div>
		</div>
	</s:if>
</security:authorize>
<s:if test="!lstCtrl.results.isEmpty">
	<display:table name="lstCtrl.results" id="ratings" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
		<display:column title="Rating">
			<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST')">
				<s:url id="ratingEditUrl" action="edit-rating" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="rating.id" value="#attr.ratings.id"/>
				</s:url>
				<s:a href="%{ratingEditUrl}" cssClass="ajaxify {target: '#ratingsSection'}">
					<s:property value="#attr.ratings.rating.value"/>
				</s:a>
			</security:authorize>
			<security:authorize access="!hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST')">
				<s:property value="#attr.ratings.rating.value"/>
			</security:authorize>
		</display:column>
		<display:column title="Start Date" headerClass="shrinkCol">
			<s:date name="#attr.ratings.startDate" format="MM/dd/yyyy"/>
		</display:column>
		<display:column title="End Date" headerClass="shrinkCol">
			<s:date name="#attr.ratings.endDate" format="MM/dd/yyyy"/>
		</display:column>
		<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST')">
			<s:if test="lstCtrl.showControls">
				<display:column class="shrinkCol">
					<s:url id="ratingDeleteUrl" action="delete-rating" includeParams="false">
						<s:param name="facilityId" value="facilityId"/>
						<s:param name="rating.id" value="#attr.ratings.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#ratingsSection'} ccl-action-delete" href="%{ratingDeleteUrl}">
						delete
					</s:a>
				</display:column>
			</s:if>
		</security:authorize>
	</display:table>
</s:if>