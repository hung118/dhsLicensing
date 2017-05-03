<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="dts" uri="/WEB-INF/tags/dts-tags.tld"%>
<%@ taglib prefix="ccl" uri="/WEB-INF/tags/ccl-tags.tld"%>
<fieldset>
	<legend><s:property value="noteType.noteTypeName"/> Notes</legend>
	<s:actionerror/>
	<div class="ccl-list-ctrls clearfix">
		<div class="ccl-list-left-ctrls">
			<s:url id="editNoteUrl" namespace="/note" action="edit-note">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="objectId" value="objectId"/>
				<s:param name="noteType" value="noteType"/>
				<s:param name="disableRange" value="disableRange"/>
			</s:url>
			<s:a href="%{editNoteUrl}" cssClass="ccl-button ajaxify {target: '#%{noteType}_NotesSection'}">
				New Note
			</s:a>
		</div>
		<dts:listcontrols id="%{noteType}_TopNoteControls" name="lstCtrl" action="notes-list" namespace="/note" useAjax="true" ajaxTarget="#%{noteType}_NotesSection" paramExcludes="%{'lstCtrl.range'}">
			<s:param name="facilityId" value="facilityId"/>
			<s:param name="objectId" value="objectId"/>
			<s:param name="noteType" value="noteType"/>
			<s:param name="disableRange" value="disableRange"/>
			<s:if test="!disableRange">
				<ccl:listrange id="%{noteType}_TopNoteControls" name="lstCtrl"/>
			</s:if>
		</dts:listcontrols>
	</div>
	<s:if test="!lstCtrl.results.isEmpty">
		<s:set var="cutoffDate" value="cutoffDate" scope="request"/>
		<ol class="ccl-list">
			<s:iterator value="lstCtrl.results" status="row" var="note">
				<li class="ccl-list-item <s:if test="#row.odd">odd</s:if><s:else>even</s:else>">
					<div class="left-column">
						<div>
							<span class="label">Date/Time:</span>
							<s:if test="editable">
								<s:url id="editNoteUrl" action="edit-note" namespace="/note" includeParams="false">
									<s:param name="facilityId" value="facilityId"/>
									<s:param name="note.id" value="id"/>
									<s:param name="objectId" value="objectId"/>
									<s:param name="noteType" value="noteType"/>
									<s:param name="disableRange" value="disableRange"/>
								</s:url>
								<s:a cssClass="ajaxify {target: '#%{noteType}_NotesSection'}" href="%{editNoteUrl}">
									<s:date name="creationDate" format="MM/dd/yyyy hh:mm a"/>
								</s:a>
							</s:if>
							<s:else>
								<s:date name="creationDate" format="MM/dd/yyyy hh:mm a"/>
							</s:else>
						</div>
					</div>
					<div class="right-column">
						<span class="label">Entered by:</span>
						<s:property value="createdBy.firstAndLastName"/>
						<s:if test="modifiedBy.id != createdBy.id">
							(Last Modified by: <s:property value="modifiedBy.firstAndLastName"/>)
						</s:if>
					</div>
					<div class="clear">
						<span class="label">Note:</span>
						<span class="description"><s:property value="text"/></span>
					</div>
					<s:set var="note" value="%{#attr.note}" scope="request"/>
					<security:authorize access="hasRole('ROLE_SUPER_ADMIN') or isManager(request.getAttribute('note')) or (isOwner(request.getAttribute('note')) and request.getAttribute('note').getCreationDate().after(request.getAttribute('cutoffDate')))">
						<div class="ccl-list-item-ctrls clearfix">
							<span class="ccl-right-float">
								<s:url id="deleteNoteUrl" action="delete-note" includeParams="false">
									<s:param name="facilityId" value="facilityId"/>
									<s:param name="note.id" value="id"/>
									<s:param name="objectId" value="objectId"/>
									<s:param name="noteType" value="noteType"/>
									<s:param name="disableRange" value="disableRange"/>
								</s:url>
								<s:a href="%{deleteNoteUrl}" cssClass="ajaxify {target: '#%{noteType}_NotesSection'} ccl-action-delete">
									delete
								</s:a>
							</span>
						</div>
					</security:authorize>
				</li>
			</s:iterator>
		</ol>
		<div class="ccl-list-ctrls clearfix">
			<dts:listcontrols id="%{noteType}_BottomNoteControls" name="lstCtrl" action="notes-list" namespace="/note" useAjax="true" ajaxTarget="#%{noteType}_NotesSection">
				<s:param name="facilityId" value="facilityId"/>
				<s:param name="objectId" value="objectId"/>
				<s:param name="noteType" value="noteType"/>
				<s:param name="disableRange" value="disableRange"/>
			</dts:listcontrols>
		</div>
	</s:if>
</fieldset>