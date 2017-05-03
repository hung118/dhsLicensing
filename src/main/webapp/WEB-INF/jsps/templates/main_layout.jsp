<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><tiles:insertAttribute name="title"/></title>
		
		<s:url var="stylesDir" value="/styles" includeParams="false" encode="false"/>
		<s:url var="scriptsDir" value="/scripts" includeParams="false" encode="false"/>
		
		<!-- Individual YUI CSS files -->
		<link rel="stylesheet" type="text/css" href="<s:property value="stylesDir"/>/reset-fonts-grids.css"/>
		
		<!-- Child care css files -->
		<link rel="stylesheet" type="text/css" href="<s:property value="stylesDir"/>/main<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.css"/>
		<link rel="stylesheet" type="text/css" media="print" href="<s:property value="stylesDir"/>/print<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.css"/>
		<link rel="stylesheet" type="text/css" href="<s:property value="stylesDir"/>/jquery-ui-1.8.9.custom<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.css"/>

		<!-- Individual jQuery JS files -->
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery-ui-1.8.9.custom.min.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/mustache-0.4.2.min.js"></script>
		
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.metadata-2.1.min.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.livequery-1.0.3.min.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.simplemodal-1.3.3.min.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.maskedinput-1.2.2.min.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.charcounter<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.fileupload-5.16.4<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.fileupload-fp<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.iframe-transport<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.tmpl<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.ccl<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.ccl-table<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.ui.ruleautocomplete<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/mustache-0.4.2.min.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/jquery.textchange.min.js"></script>
		
		<!-- Custom CCL javascript files -->
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/ajaxifier<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/utils<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		<script type="text/javascript" src="<s:property value="scriptsDir"/>/main<s:property value="@gov.utah.dts.det.util.SiteUtil@getResourceVersionString()"/>.js"></script>
		
		<script type="text/javascript" src="<s:url value='/scripts/date.format.js' />"></script>

		
		<script type="text/javascript">
			var context = "${pageContext.request.contextPath}";
		</script>
		
		<tiles:insertAttribute name="javascript" ignore="true"/>
	</head>
	
	<script id="ccl-table-tmpl" type="text/x-jquery-tmpl">
		{{tmpl($item.data, {position: 'top'}) '#ccl-list-controls-tmpl'}}
		{{tmpl tableTmpl}}
		{{tmpl($item.data, {position: 'bottom'}) '#ccl-list-controls-tmpl'}}
	</script>
	
	<script id="ccl-list-controls-tmpl" type="text/x-jquery-tmpl">
		<div class="ccl-list-controls ccl-list-controls-{{= $item.position}}">
			<div class="ccl-list-left-controls">
				{{if mainControlsTmpl}}{{tmpl mainControlsTmpl}}{{/if}}
			</div>
			<div class="ccl-list-right-controls">
				<form action="{{= dataUrl}}" class="ccl-list-controls-form" method="get">
					{{each formParams}}<input type="hidden" name="{{= $value.name}}" value="{{= $value.value}}"/>{{/each}}
					{{if showRange}}
						{{tmpl '#ccl-list-range-tmpl'}}
					{{/if}}
					{{if showPaging}}
						{{tmpl '#ccl-list-pager-tmpl'}}
					{{/if}}
					{{if showSorting}}
						{{tmpl '#ccl-list-sort-tmpl'}}
					{{/if}}
				</form>
			</div>
		</div>
	</script>
	
	<script id="ccl-list-range-tmpl" type="text/x-jquery-tmpl">
		<div class="ccl-list-range-container ccl-list-control">
			{{each tableData.ranges}}<input type="radio" id="ccl-range-{{= $value.key}}" name="lstCtrl.range" {{if $value.key == tableData.range}} checked="checked" {{/if}} value="{{= $value.key}}" class="ccl-list-controls-submit radio"/><label class="radioLabel">{{= $value.label}}</label>{{/each}}
		</div>
	</script>
	
	<script id="ccl-list-pager-tmpl" type="text/x-jquery-tmpl">

	</script>
	
	<script id="ccl-list-sort-tmpl" type="text/x-jquery-tmpl">
		<div class="ccl-list-sorter-container ccl-list-control">
			<label>Sort By:</label>
			<select name="lstCtrl.sortBy" class="ccl-list-controls-submit">
				{{each tableData.sortBys}}<option value="{{= $value.key}}"{{if $value.key == tableData.sortBy}}selected="selected"{{/if}}>{{= $value.label}}</option>{{/each}}
			</select>
		</div>
	</script>
	
	<script id="ccl-error-tmpl" type="text/x-jquery-tmpl">
		<ul class="ccl-error-list">{{each errors}}<li>{{= $value}}</li>{{/each}}</ul>
	</script>

	<body class="yui-skin-sam">
		<div>
			<span class="ccl-dialog ccl-loading-dialog" style="display: none;"><span style="margin-right: .5em;">Loading, please wait...</span><img src="${pageContext.request.contextPath}/images/ajax-loader.gif" style="vertical-align: middle;"/></span>
			<span class="ccl-dialog ccl-saving-dialog" style="display: none;"><span style="margin-right: .5em;">Saving, please wait...</span><img src="${pageContext.request.contextPath}/images/ajax-loader.gif" style="vertical-align: middle;"/></span>
			<span class="ccl-dialog ccl-deleting-dialog" style="display: none;"><span style="margin-right: .5em;">Deleting, please wait...</span><img src="${pageContext.request.contextPath}/images/ajax-loader.gif" style="vertical-align: middle;"/></span>
			<div class="ccl-dialog ccl-confirm-delete-dialog" style="display: none;" title="Confirm Delete">The selected item will be permanently deleted.  Are you sure you want to continue?</div>
			<div class="ccl-dialog ccl-confirm-save-dialog" style="display: none;" title="Confirm Inactivated Previous License">The selected previous license number will be inactivated.  Are you sure you want to continue?</div>
			<div class="ccl-dialog ccl-confirm-save2-dialog" style="display: none;" title="Confirm Inactivated Facility">The selected license status is closed. The facility will be inactivated.  Are you sure you want to continue?</div>
			<div class="ccl-dialog ccl-confirm-save3-dialog" style="display: none;" title="Confirm Inactivated Licenses">The selected facility status is Inactive. The facility licenses will be inactivated.  Are you sure you want to continue?</div>
			<div class="ccl-dialog ccl-generic-dialog" style="display: none;" title=""></div>
		</div>
		<div id="doc2" class="yui-t1">
			<s:if test="@gov.utah.dts.det.util.SiteUtil@getEnvironmentHeader() != null"><s:property value="@gov.utah.dts.det.util.SiteUtil@getEnvironmentHeader()" escape="false"/></s:if>
			<div class="header">
				<div class="header-inner">
					<div class="leftheader">
						<img src="${pageContext.request.contextPath}/styles/images/dept-logo.png"/>
					</div>
					<div class="rightheader">
						<img src="${pageContext.request.contextPath}/styles/images/right-banner-collage.jpg"/>
					</div>
				</div>
			</div>
			<div class="header-b">
				<div class="header-b-inner">
					<div class="username-logout">
						<s:url id="logoutUrl" namespace="/security" action="logout" includeParams="false"/>
						Welcome <span class="username"><s:property value="@gov.utah.dts.det.ccl.security.SecurityUtil@getUser().getPerson().getFirstAndLastName()"/></span>, <s:a href="%{logoutUrl}">Log Out</s:a>
						<div class="username-logout-inner">
						</div>
					</div>
				</div>
			</div>
			<div id="bd">
				<div id="yui-main">
					<tiles:insertAttribute name="leftnav" ignore="true"/>
					<div class="yui-b">
						<div id="maincontent" class="yui-g">
							<div id="heading" class="heading">
								<tiles:insertAttribute name="heading" ignore="true"/>
							</div>
							<tiles:insertAttribute name="body" ignore="true"/>
						</div>
					</div>
					<div id="ft">
						<tiles:insertAttribute name="footer" ignore="true"/>
					</div>
				</div>
			</div>
			
		</div>
	</body>
</html>