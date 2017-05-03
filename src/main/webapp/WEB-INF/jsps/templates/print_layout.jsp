<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><tiles:insertAttribute name="title"/></title>
		<s:url var="stylesDir" value="/styles" includeParams="false" encode="false"/>
		<s:url var="scriptsDir" value="/scripts" includeParams="false" encode="false"/>
		
		<link rel="stylesheet" type="text/css" media="all" href="<s:property value="scriptsDir"/>/yui/build/reset-fonts-grids/reset-fonts-grids.css"/>
		<link rel="stylesheet" type="text/css" media="all" href="<s:property value="stylesDir"/>/print<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.css"/>
		
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery-1.6.4.min.js"></script>
	</head>
	<body>
		<tiles:insertAttribute name="body" ignore="true"/>
	</body>
</html>