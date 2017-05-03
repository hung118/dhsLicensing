<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset>
	<legend>View License</legend>
	<ol class="fieldList">
		<s:if test="license.licenseNumber != null">
		    <li>
		    	<div class="label">License Number:</div>
		    	<div class="mediumreadonly"><s:property value="license.licenseNumber"/></div>
	    	</li>
	    </s:if>
		<li>
	    	<div class="label">Status:</div>
	    	<div class="value"><s:property value="license.status.value"/></div>
		</li>
		<li>
			<div class="label">License Type:</div>
			<div class="value"><s:property value="license.subtype.value"/></div>
		</li>
		<li>
			<div class="label">Service Code:</div>
			<div class="value"><s:property value="license.serviceCode.value"/></div>
		</li>
		<li>
			<div class="label">Program Codes:</div>
			<ol class="fieldGroup" style="margin-top: 5px;">
				<s:iterator var="program" value="license.programCodeIds" status="programStatus">
					<li>
						<div class="value"><s:property value="#attr.program.value"/></div>
					</li>
					<li class="clear"></li>
				</s:iterator> 
			</ol>
		</li>
		<li>
			<div class="label">Specific Service Code:</div>
			<div class="value"><s:property value="license.specificServiceCode.value"/></div>
		</li>
		<li>
			<div class="label">Age Group:</div>
			<ol class="fieldGroup">
				<li>
					<div class="label"><strong>Group:</strong></div>
					<div class="value"><s:property value="license.ageGroup.value"/></div>
				</li>
				<li>
					<div class="label"><strong>From Age:</strong></div>
					<div class="value"><s:property value="license.fromAge"/></div>
				</li>
				<li>
					<div class="label"><strong>To Age:</strong></div>
					<div class="value"><s:property value="license.toAge"/></div>
				</li>
			</ol>
		</li>
		<li>
			<div class="label">Capacity:</div>
			<s:if test="license.ageGroup == null || license.ageGroup.value.equalsIgnoreCase('Adult & Youth') || license.ageGroup.value.equalsIgnoreCase('Adult')">
				<ol class="fieldGroup">
					<li>
						<div class="label">Adult Total:</div>
						<div class="value"><s:property value="license.adultTotalSlots"/></div>
					</li>
					<s:if test="license.ageGroup.value.equalsIgnoreCase('Adult')">
						<li>
							<div class="label"># Male:</div>
							<div class="value"><s:property value="license.adultMaleCount"/></div>
						</li>
						<li>
							<div class="label"># Female:</div>
							<div class="value"><s:property value="license.adultFemaleCount"/></div>
						</li>
					</s:if>
				</ol>
			</s:if>
			<s:if test="license.ageGroup.value.equalsIgnoreCase('Adult & Youth') || license.ageGroup.value.equalsIgnoreCase('Youth')">
				<ol class="fieldGroup">
					<li>
						<div class="label">Youth Total:</div>
						<div class="value"><s:property value="license.youthTotalSlots"/></div>
					</li>
					<s:if test="license.ageGroup.value.equalsIgnoreCase('Youth')">
						<li>
							<div class="label"># Male:</div>
							<div class="value"><s:property value="license.youthMaleCount"/></div>
						</li>
						<li>
							<div class="label"># Female:</div>
							<div class="value"><s:property value="license.youthFemaleCount"/></div>
						</li>
					</s:if>
				</ol>
			</s:if>
		</li>
		<li>
			<div class="label">Conditional / Sanction:</div>
			<div class="value"><s:property value="license.condSanc.displayName"/></div>
		</li>
		<li>
			<div class="label">Comment:</div>
			<div class="value" style="width:550px;"><s:property value="license.comment"/></div>
		</li>
		<li>
			<div class="label">License:</div>
			<ol class="fieldGroup">
				<li <s:if test="license.facility.type.character == 'F' || license.facility.type.character == 'S'">style="width:15em;"</s:if>>
					<div class="label">Start Date:</div>
					<div class="value"><s:date name="license.startDate" format="MM/dd/yyyy"/></div>
				</li>
				<li>
					<div class="label">Expiration Date:</div>
					<div class="value"><s:date name="license.expirationDate" format="MM/dd/yyyy" /></div>
				</li>
			</ol>
		</li>
		<s:if test="license.facility.type.character == 'F' || license.facility.type.character == 'S'">
			<li>
				<div class="label"></div>
				<ol class="fieldGroup">
					<li style="width:15em;">
						<div class="label">Application Received:</div>
						<div class="value"><s:date name="license.applicationReceived" format="MM/dd/yyyy" /></div>
					</li>
					<li style="width:15em;">
						<div class="label">Signature Form Received:</div>
						<div class="value"><s:date name="license.signatureFormReceived" format="MM/dd/yyyy" /></div>
					</li>
				</ol>
			</li>
			<li>
				<div class="label"></div>
				<ol class="fieldGroup">
					<li style="width:15em;">
						<div class="label">Verification of Income Received:</div>
						<div class="value"><s:date name="license.voiReceived" format="MM/dd/yyyy" /></div>
					</li>
					<li style="width:15em;">
						<div class="label">Home Study Received:</div>
						<div class="value"><s:date name="license.homeStudyReceived" format="MM/dd/yyyy" /></div>
					</li>
				</ol>
			</li>
			<li>
				<div class="label"></div>
				<ol class="fieldGroup">
					<li style="width:15em;">
						<div class="label">Medical Received:</div>
						<div class="value"><s:date name="license.medicalReceived" format="MM/dd/yyyy" /></div>
					</li>
					<li style="width:15em;">
						<div class="label">Spouse Medical Received:</div>
						<div class="value"><s:date name="license.spouseMedicalReceived" format="MM/dd/yyyy" /></div>
					</li>
				</ol>
			</li>
			<li>
				<div class="label"></div>
				<ol class="fieldGroup">
					<li style="width:15em;">
						<div class="label">Training Verified:</div>
						<div class="value"><s:date name="license.trainingVerfied" format="MM/dd/yyyy" /></div>
					</li>
					<li style="width:15em;">
						<div class="label">Spouse Training Verified:</div>
						<div class="value"><s:date name="license.spouseTrainingVerified" format="MM/dd/yyyy" /></div>
					</li>
				</ol>
			</li>
		</s:if>
		<li>
			<div class="label">2 Year:</div>
			<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(license.twoYear).displayName"/></div>
		</li>
        <li>
            <div class="label">Certificate Comment:</div>
            <div class="value" style="width:550px;"><s:property value="license.certificateComment"/></div>
        </li>
		<li>
			<div class="label">Finalized:</div>
			<div class="value"><s:property value="@gov.utah.dts.det.ccl.view.YesNoChoice@valueOfBoolean(license.finalized).displayName"/></div>
		</li>
		<li class="submit">
			<s:if test="license.isFinalized()">
				<s:url id="printCertUrl" action="print-license-cert" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="license.id" value="license.id"/>
				</s:url>
				<s:a href="%{printCertUrl}" cssClass="ccl-button">
					Print Certificate
				</s:a>
				<s:url id="printLetterUrl" action="print-license-letter" includeParams="false">
					<s:param name="facilityId" value="facilityId"/>
					<s:param name="license.id" value="license.id"/>
				</s:url>
				<s:a href="%{printLetterUrl}" cssClass="ccl-button">
					Print Letter
				</s:a>
			</s:if>
			<s:url id="cancelUrl" action="licenses-list">
				<s:param name="facilityId" value="facilityId"/>
			</s:url>
			<s:a id="cancel" href="%{cancelUrl}" cssClass="ajaxify {target: '#licensesSection'}">
				Cancel
			</s:a>
		</li>
	</ol>
</fieldset>
