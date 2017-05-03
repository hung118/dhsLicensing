<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<fieldset>
	<legend>Sections</legend>
	<s:if test="!rule.sections.isEmpty">
		<s:form id="sectionOrderForm" action="reorder-sections" method="post" cssClass="ajaxify {target: '#rulesBase'} ccl-action-save">
			<s:hidden name="rule.id"/>
<%-- 			
			<div class="topControls">
				<div class="mainControls">
					<s:submit value="Save Order"/>
				</div>
			</div>
 --%>			
			<display:table name="rule.sections" id="sections" class="tables" decorator="gov.utah.dts.det.ccl.view.admin.InactiveRecordDecorator">
<%-- 				
				<display:column title="Order">
					<s:set name="orderFieldId" value="#attr.sections.id"/>
					<s:set name="sortOrder" value="#attr.sections.sortOrder"/>
					<s:textfield id="order-%{orderFieldId}" name="order-%{orderFieldId}" cssClass="orderField" maxlength="6" value="%{sortOrder}"/>
				</display:column>
 --%>				
				<display:column title="Section #" class="nowrap">
					<s:url id="subSectionsEditUrl" action="edit-subsection" includeParams="false">
						<s:param name="rule.id" value="rule.id"/>
						<s:param name="section.id" value="#attr.sections.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{subSectionsEditUrl}">
						<s:property value="#attr.rule.number"/>-<s:property value="#attr.sections.sectionBase"/>-<s:property value="#attr.sections.number"/>
					</s:a>
				</display:column>
				<display:column title="Section Name">
					<s:if test="#attr.sections.active == false">
						<div style="color:gray"><s:property value="#attr.sections.title"/>: <s:property value="#attr.sections.name"/></div>
					</s:if>
					<s:else>
						<s:property value="#attr.sections.title"/>: <s:property value="#attr.sections.name"/>
					</s:else>
					<s:if test="#attr.sections.referenceUrl != null">
						[<a href="<s:property value="#attr.sections.referenceUrl"/>" target="_blank">Link</a>]
					</s:if>
				</display:column>
				<display:column title="Version">
				
					<s:if test="#attr.sections.active == false">
						<div style="color:gray"><s:date name="#attr.sections.versionDate" format="MM/dd/yyyy"/></div>
					</s:if>
					<s:else>
						<s:date name="#attr.sections.versionDate" format="MM/dd/yyyy"/>
					</s:else>
				
				</display:column>
				<display:column title="Sub Sections">
					<s:if test="#attr.sections.active == false">
						<div style="color:gray"><s:property value="#attr.sections.subSectionCount"/></div>
					</s:if>
					<s:else>
						<s:property value="#attr.sections.subSectionCount"/>
					</s:else>
					
				</display:column>
				<display:column title="Pending #">
				
					<s:if test="#attr.sections.hasPending">
						<div style="color:red"><s:property value="#attr.sections.pendingCount"/></div>
					</s:if>
					<s:elseif test="#attr.sections.active == false">
						<div style="color:gray"><s:property value="#attr.sections.pendingCount"/></div>
					</s:elseif>
					<s:else>
						<s:property value="#attr.sections.pendingCount"/>
					</s:else>
					
				</display:column>	
				<display:column class="nowrap">
					<s:url id="sectionEditUrl" action="edit-section" includeParams="false">
						<s:param name="rule.id" value="rule.id"/>
						<s:param name="section.id" value="#attr.sections.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'}" href="%{sectionEditUrl}">
						edit
					</s:a>
					|
					<s:url id="sectionActivateUrl" action="activate-section" includeParams="false">
						<s:param name="rule.id" value="rule.id"/>
						<s:param name="section.id" value="#attr.sections.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-save" href="%{sectionActivateUrl}">
						activate
					</s:a>
					|
					<s:url id="sectionDeactivateUrl" action="deactivate-section" includeParams="false">
						<s:param name="rule.id" value="rule.id"/>
						<s:param name="section.id" value="#attr.sections.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-save" href="%{sectionDeactivateUrl}">
						deactivate
					</s:a>
					|
					 <s:url id="sectionDeleteUrl" action="delete-section" includeParams="false">
						<s:param name="rule.id" value="rule.id"/>
						<s:param name="section.id" value="#attr.sections.id"/>
					</s:url>
					<s:a cssClass="ajaxify {target: '#rulesBase'} ccl-action-delete" href="%{sectionDeleteUrl}">
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