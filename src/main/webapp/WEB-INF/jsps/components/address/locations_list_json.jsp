<%@ page contentType="application/json" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:array name="locations" var="location" items="${locations}">
	<json:object>
		<json:property name="zipCode" value="${location.zipCode}"/>
		<json:property name="city" value="${location.city}"/>
		<json:property name="state" value="${location.state}"/>
		<json:property name="county" value="${location.county}"/>
	</json:object>
</json:array>