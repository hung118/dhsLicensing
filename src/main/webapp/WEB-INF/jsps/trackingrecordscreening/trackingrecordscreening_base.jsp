<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="tabs">
	<ul>
	<security:authorize access="hasRole('ROLE_ACCESS_PROFILE_VIEW')">
		<s:url id="mainTabUrl" action="tab" namespace="/trackingrecordscreening/main" escapeAmp="false">
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<li><a href="<s:if test="tab == 'main'">#preloaded-tab</s:if><s:else><s:property value="mainTabUrl"/></s:else>">Main</a></li>
	</security:authorize>
	
    <security:authorize access="isAuthenticated() and principal.isInternal() or hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING','ROLE_BACKGROUND_SCREENING_MANAGER')">
		<s:url id="mainTabUrl" action="tab" namespace="/trackingrecordscreening/main" escapeAmp="false">
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<li><a href="<s:if test="tab == 'main'">#preloaded-tab</s:if><s:else><s:property value="mainTabUrl"/></s:else>">Main</a></li>
		<s:url id="dpsFbiTabUrl" action="tab" namespace="/trackingrecordscreening/dpsfbi" escapeAmp="false">
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<li><a title="Department of Public Safety / Federal Bureau of Investigation" href="<s:if test="tab == 'dpsfbi'">#preloaded-tab</s:if><s:else><s:property value="dpsFbiTabUrl"/></s:else>">DPS/FBI</a></li>
		<s:url id="requestsTabUrl" action="tab" namespace="/trackingrecordscreening/requests" escapeAmp="false">
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<li><a href="<s:if test="tab == 'requests'">#preloaded-tab</s:if><s:else><s:property value="requestsTabUrl"/></s:else>">Requests</a></li>
		<s:url id="lettersTabUrl" action="tab" namespace="/trackingrecordscreening/letters" escapeAmp="false">
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<li><a href="<s:if test="tab == 'letters'">#preloaded-tab</s:if><s:else><s:property value="lettersTabUrl"/></s:else>">Letters</a></li>
		<s:url id="cbsCommTabUrl" action="tab" namespace="/trackingrecordscreening/cbscomm" escapeAmp="false">
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<li><a title="Comprehensive Review Committee" href="<s:if test="tab == 'cbscomm'">#preloaded-tab</s:if><s:else><s:property value="cbsCommTabUrl"/></s:else>">CBS Comm</a></li>
		<s:url id="miscommTabUrl" action="tab" namespace="/trackingrecordscreening/miscomm" escapeAmp="false">
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<li><a title="Adult Management Information System" href="<s:if test="tab == 'miscomm'">#preloaded-tab</s:if><s:else><s:property value="miscommTabUrl"/></s:else>">MIS Comm</a></li>
		<s:url id="oscarTabUrl" action="tab" namespace="/trackingrecordscreening/oscar" escapeAmp="false">
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<li><a title="Out of State Child Abuse Registry" href="<s:if test="tab == 'oscar'">#preloaded-tab</s:if><s:else><s:property value="oscarTabUrl"/></s:else>">OSCAR</a></li>
		<s:url id="activityTabUrl" action="tab" namespace="/trackingrecordscreening/activity" escapeAmp="false">
			<s:param name="screeningId" value="screeningId"/>
		</s:url>
		<li><a href="<s:if test="tab == 'activity'">#preloaded-tab</s:if><s:else><s:property value="activityTabUrl"/></s:else>">Activity</a></li>
    </security:authorize>
	</ul>
	<s:if test="act != null">
		<s:set var="preloadedData"><s:if test="tab == 'main'">{index: 0, url: '<s:property value="mainTabUrl"/>'}</s:if><s:elseif test="tab == 'dpsfbi'">{index: 1, url: '<s:property value="dpsfbiTabUrl"/>'}</s:elseif><s:elseif test="tab == 'requests'">{index: 2, url: '<s:property value="requestsTabUrl"/>'}</s:elseif><s:elseif test="tab == 'letters'">{index: 3, url: '<s:property value="lettersTabUrl"/>'}</s:elseif><s:elseif test="tab == 'cbscomm'">{index: 4, url: '<s:property value="cbscommTabUrl"/>'}</s:elseif><s:elseif test="tab == 'miscomm'">{index: 5, url: '<s:property value="miscommTabUrl"/>'}</s:elseif><s:elseif test="tab == 'oscar'">{index: 6, url: '<s:property value="oscarTabUrl"/>'}</s:elseif><s:elseif test="tab == 'activity'">{index: 7, url: '<s:property value="activityTabUrl"/>'}</s:elseif></s:set>
		<div id="preloaded-tab" class="<s:property value="%{preloadedData}"/>">
			<div id="<s:property value='tab'/>Base">
				<s:action name="%{act}" namespace="%{ns}" executeResult="true"/>
			</div>
		</div>
	</s:if>
</div>
