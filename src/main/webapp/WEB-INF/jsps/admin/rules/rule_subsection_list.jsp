<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>Sub Sections</legend>
	<s:if test="!section.subSections.isEmpty">
		<s:form id="subSectionOrderForm" action="reorder-subsections" method="post" cssClass="ajaxify {target: '#rulesBase'} ccl-action-save">
			<s:hidden name="rule.id"/>
			<s:hidden name="section.id"/>
			<div class="topControls" style="text-align:right;">
					<s:url id="sectionActivateAllUrl" action="activate-subsection-all" includeParams="false">
						<s:param name="rule.id" value="rule.id"/>
						<s:param name="section.id" value="section.id"/>
						<s:param name="subSection.id" value="subSection.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-save" href="%{sectionActivateAllUrl}">
						activate all
					</s:a>
					|
					<s:url id="sectionDectivateAllUrl" action="deactivate-subsection-all" includeParams="false">
						<s:param name="rule.id" value="rule.id"/>
						<s:param name="section.id" value="section.id"/>
						<s:param name="subSection.id" value="subSection.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-save" href="%{sectionDectivateAllUrl}">
						deactivate all
					</s:a>
			</div>
			<display:table name="section.subSections" id="subSections" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
<%-- 				<display:column title="Order">
					<s:set name="orderFieldId" value="#attr.subSections.id"/>
					<s:set name="sortOrder" value="#attr.subSections.sortOrder"/>
					<s:textfield id="order-%{orderFieldId}" name="order-%{orderFieldId}" cssClass="orderField" maxlength="6" value="%{sortOrder}"/>
				</display:column>
--%>
				<display:column title="#" class="nowrap">
<%-- 					<s:property value="rule.number"/>-<s:property value="section.number"/>${subSectionNumber} :  --%>
					<s:if test="#attr.subSections.category.character == 'I'">
						<div style="color:gray"><s:property value="#attr.subSections.number"/></div>
					</s:if>
					<s:else>
						<s:property value="#attr.subSections.number"/>
					</s:else>
				</display:column>
				<display:column title="Description">
					<s:if test="#attr.subSections.category.character == 'I'">
						<div style="color:gray"><s:property value="#attr.subSections.ruleContent"/></div>
					</s:if>
					<s:else>
						<s:property value="#attr.subSections.ruleContent"/>
					</s:else>
					<s:if test="#attr.subSections.referenceUrl != null">
						[<a href="<s:property value="#attr.subSections.referenceUrl"/>" target="_blank">Link</a>]
					</s:if>
				</display:column>
				<display:column title="Status">
					<s:if test="#attr.subSections.category.character == 'I'">
						<div style="color:gray"><s:property value="#attr.subSections.category"/></div>
					</s:if>
					<s:else>
						<s:property value="#attr.subSections.category"/>
					</s:else>
					
					<s:date id="versionDateFormatted" name="#attr.subSections.versionDate" format="MM/dd/yyyy" />
					<s:property value="%{versionDateFormatted}" />
					
				</display:column>
				<display:column class="nowrap">
					<s:url id="subSectionEditUrl" action="edit-subsection" includeParams="false">
						<s:param name="rule.id" value="rule.id"/>
						<s:param name="section.id" value="section.id"/>
						<s:param name="subSection.id" value="#attr.subSections.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{subSectionEditUrl}">
						edit
					</s:a>

					<s:if test="#attr.subSections.category.character == 'I' || #attr.subSections.category.character == 'P'">
						|
						<s:url id="subSectionActivateUrl" action="activate-subsection" includeParams="false">
							<s:param name="rule.id" value="rule.id"/>
							<s:param name="section.id" value="section.id"/>
							<s:param name="subSection.id" value="#attr.subSections.id"/>
						</s:url>					
						<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-save" href="%{subSectionActivateUrl}">
							activate
						</s:a>
					</s:if>
					<s:if test="#attr.subSections.category.character == 'A' || #attr.subSections.category.character == 'P'">
						|
						<s:url id="subSectionDeactivateUrl" action="deactivate-subsection" includeParams="false">
							<s:param name="rule.id" value="rule.id"/>
							<s:param name="section.id" value="section.id"/>
							<s:param name="subSection.id" value="#attr.subSections.id"/>
						</s:url>
						<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-save" href="%{subSectionDeactivateUrl}">
							deactivate
						</s:a>
					</s:if>
					<s:if test="#attr.subSections.category == 'P'">
						|
						<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-save" href="%{subSectionDeactivateUrl}">
							deactivate
						</s:a>
					</s:if>
					|
					<s:url id="subSectionDeleteUrl" action="delete-subsection" includeParams="false">
						<s:param name="rule.id" value="rule.id"/>
						<s:param name="section.id" value="section.id"/>
						<s:param name="subSection.id" value="#attr.subSections.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-delete" href="%{subSectionDeleteUrl}">
						delete
					</s:a>
				</display:column>
			</display:table>
<%-- 			
			<div class="bottomControls">
				<div class="mainControls">
					<s:submit value="Save Order"/>
				</div>
			</div>
 --%>			
		</s:form>
	</s:if>
</fieldset>