<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<s:include value="../navigation.jsp"><s:param name="selectedTab">individual</s:param></s:include>
<s:set var="screening" value="screening" scope="request"/>
<fieldset>
	<legend>Individual Information</legend>
	<security:authorize access="hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER')">
		<div class="ccl-fs-ctrls">
			<ul>
				<li><span class="label">Created By:</span> <s:property value="screening.createdBy.firstAndLastName"/></li>
				<li><span class="label">Creation Date:</span> <s:date name="screening.creationDate" format="MM/dd/yyyy hh:mm a"/></li>
				<li><span class="label">Last Modified By:</span> <s:property value="screening.modifiedBy.firstAndLastName"/></li>
				<li><span class="label">Last Modified Date:</span> <s:date name="screening.modifiedDate" format="MM/dd/yyyy hh:mm a"/></li>
			</ul>
		</div>
	</security:authorize>
	<ol class="fieldList">
		<li>
			<div class="label">Name:</div>
			<div class="value">
				<ol class="fieldGroup">
					<li>
						<div class="value"><s:property value="screening.screenedAsPerson.firstName"/> <s:property value="screening.screenedAsPerson.middleName"/> <s:property value="screening.screenedAsPerson.lastName"/></div>
					</li>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.screenedAsPerson.maidenName)">
						<li class="clearLeft fieldMargin">
							<div class="label">Maiden Name:</div>
							<div class="value"><s:property value="screening.screenedAsPerson.maidenName"/></div>
						</li>
					</s:if>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.previousMarriedNames)">
						<li class="<s:if test='@org.apache.commons.lang.StringUtils@isBlank(screening.screenedPerson.maidenName)'>clearLeft </s:if>fieldMargin">
							<div class="label">Previous Married Names:</div>
							<div class="value"><s:property value="screening.previousMarriedNames"/></div>
						</li>
					</s:if>
				</ol>
			</div>
		</li>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.screenedAsPerson.aliasesString)">
			<li>
				<div class="label">Aliases:</div>
				<div class="value"><s:property value="screening.screenedAsPerson.aliasesString"/></div>
			</li>
		</s:if>
		<li>
			<div class="label">Date of Birth:</div>
			<div class="value"><s:date name="screening.screenedAsPerson.birthday" format="MM/dd/yyyy"/></div>
		</li>
		<li>
			<div class="label">Current Age:</div>
			<div class="value"><s:property value="screening.screenedAsPerson.age"/></div>
		</li>
		<li>
			<div class="label">Gender:</div>
			<div class="value"><s:property value="screening.screenedAsPerson.gender.displayName"/></div>
		</li>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.driversLicenseNumber) || @org.apache.commons.lang.StringUtils@isNotBlank(screening.driversLicenseState)">
			<li>
				<div class="label">Driver's License:</div>
				<ol class="fieldGroup">
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.driversLicenseNumber)">
						<li>
							<div class="label">License #:</div>
							<div class="value"><s:property value="screening.driversLicenseNumber"/></div>
						</li>
					</s:if>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.driversLicenseState)">
						<li>
							<div class="label">State:</div>
							<div class="value"><s:property value="screening.driversLicenseState"/></div>
						</li>
					</s:if>
				</ol>
			</li>
		</s:if>
		<li>
			<div class="label">Address:</div>
			<div class="value">
				<s:component template="addressdisplay.ftl">
					<s:param name="address" value="screening.screenedAsPerson.address"/>
				</s:component>
			</div>
		</li>
		<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.screenedAsPerson.homePhone.formattedPhoneNumber)">
			<li>
				<div class="label">Phone:</div>
				<div class="value"><s:property value="screening.screenedAsPerson.homePhone.formattedPhoneNumber"/></div>
			</li>
		</s:if>
		<li class="labelWidthMargin">
			<div class="value">This person <strong><s:if test="screening.livedInStateFiveYears">has</s:if><s:else>has not</s:else></strong> lived in utah continuously for the past 5 years.</div>
		</li>
		<li>
			<div class="label">Form Dates:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">PM Received Date:</div>
					<div class="value"><s:date name="screening.pmReceivedDate" format="MM/dd/yyyy"/></div>
				</li>
				<li>
					<div class="label">Date Sent to BCU:</div>
					<div class="value"><s:date name="screening.bcuSentDate" format="MM/dd/yyyy"/></div>
				</li>
				<li>
					<div class="label">BCU Received Date:</div>
					<div class="value"><s:date name="screening.bcuReceivedDate" format="MM/dd/yyyy"/></div>
				</li>
			</ol>
		</li>
		<s:if test="screening.returnedToProviderDate != null || screening.completeFormReceivedDate != null || @org.apache.commons.lang.StringUtils@isNotBlank(screening.note)">
			<li>
				<div class="label">Incomplete Form:</div>
				<ol class="fieldGroup">
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.returnedToProviderDate)">
						<li>
							<div class="label">Date Returned to Provider:</div>
							<div class="value"><s:date name="screening.returnedToProviderDate" format="MM/dd/yyyy"/></div>
						</li>
					</s:if>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.completeFormReceivedDate)">
						<li>
							<div class="label">Date Complete Form Received:</div>
							<div class="value"><s:date name="screening.completeFormReceivedDate" format="MM/dd/yyyy"/></div>
						</li>
					</s:if>
					<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.note)">
						<li class="<s:if test="@org.apache.commons.lang.StringUtils@isNotBlank(screening.returnedToProviderDate) || @org.apache.commons.lang.StringUtils@isNotBlank(screening.completeFormReceivedDate)">clearLeft fieldMargin</s:if>">
							<div class="label">Note:</div>
							<div class="value description"><s:property value="screening.note"/></div>
						</li>
					</s:if>
				</ol>
			</li>
		</s:if>
		<s:if test="screening.cardMailedDate != null || screening.returnDueDate != null || screening.reminderMailedDate != null || screening.reminderDueDate != null">
			<li>
				<div class="label">Fingerprint Cards:</div>
				<ol class="fieldGroup">
					<s:set var="numDates" value="0"/>
					<s:if test="screening.cardMailedDate != null">
						<li>
							<div class="label">Date Card Mailed:</div>
							<div class="value"><s:date name="screening.cardMailedDate" format="MM/dd/yyyy"/></div>
						</li>
						<s:set var="numDates" value="#attr.numDates + 1"/>
					</s:if>
					<s:if test="screening.returnDueDate != null">
						<li>
							<div class="label">Return Due Date:</div>
							<div class="value"><s:date name="screening.returnDueDate" format="MM/dd/yyyy"/></div>
						</li>
						<s:set var="numDates" value="#attr.numDates + 1"/>
					</s:if>
					<s:if test="screening.reminderMailedDate != null">
						<li class="<s:if test='#attr.numDates == 2'>clearLeft fieldMargin</s:if>">
							<div class="label">Date Reminder Mailed:</div>
							<div class="value"><s:date name="screening.reminderMailedDate" format="MM/dd/yyyy"/></div>
						</li>
						<s:set var="numDates" value="#attr.numDates + 1"/>
					</s:if>
					<s:if test="screening.reminderDueDate != null">
						<li class="<s:if test='#attr.numDates == 2'>clearLeft</s:if><s:elseif test='#attr.numDates > 2'> fieldMargin</s:elseif>">
							<div class="label">Reminder Due Date:</div>
							<div class="value"><s:date name="screening.reminderDueDate" format="MM/dd/yyyy"/></div>
						</li>
					</s:if>
				</ol>
			</li>
		</s:if>
		<li>
			<div class="label">Screening Information:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label">Screening Type:</div>
					<div class="value"><s:property value="screening.screeningType.displayName"/></div>
				</li>
				<s:if test="lastScreeningDate != null">
					<li>
						<div class="label">Last Screening Date:</div>
						<div class="value"><s:date name="lastScreeningDate" format="MM/dd/yyyy"/></div>
					</li>
				</s:if>
			</ol>
		</li>
		<security:authorize access="hasPermission('save', 'screening')">
			<li class="submit">
				<s:url id="editIndividualUrl" action="edit-individual">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="screeningId" value="screeningId"/>
				</s:url>
				<s:a href="%{editIndividualUrl}" cssClass="ccl-button ajaxify {target: '#screeningInformationSection'}">
					Edit
				</s:a>
			</li>
		</security:authorize>
	</ol>
</fieldset>