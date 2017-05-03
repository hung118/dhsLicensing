<#macro paramsAsHidden excludes>
	<#assign exList = excludes?split(',')/>
	<!-- ${excludes} -->
	<@s.iterator value="parameters.params.entrySet()">
		<#assign name = stack.findString('key')/>
		<#assign value = stack.findValue('value')!''/>
		<#assign process = true/>
		<#list exList as ex>
			<#if ex = name>
				<#assign process = false/>
				<#break/>
			</#if>
		</#list>
		<#if process>
			<#if value?is_enumerable>
				<@s.iterator value="value">
					<input type="hidden" name="${name}" value="${stack.findString('top')!''?html}"/>
				</@s.iterator>
			<#else>
				<input type="hidden" name="${name}" value="${value?html}"/>
			</#if>
		</#if>
	</@s.iterator>
</#macro>
<#assign idPrefix = parameters.id!"controls"/>
<#assign formId = "${idPrefix}_listControlsForm"/>
<#assign formClass = "listControlsForm"/>
<#if parameters.useAjax!false && parameters.ajaxTarget??>
	<#assign formClass = "${formClass} ajaxify {target: '${parameters.ajaxTarget}'}"/>
</#if>
<div id="${idPrefix}_listControlsContainer" class="listControlsContainer">
<@s.url var="formUrl" action="${parameters.action}" namespace="${parameters.namespace!''}"/>
	<form id="${formId}" action="<@s.property value="%{formUrl}"/>" class="${formClass}"<#rt/>
<#if parameters.acceptcharset??>
 acceptcharset="${parameters.acceptcharset}"<#rt/>
</#if>
<#if parameters.accessKey??>
 accessKey="${parameters.accessKey}"<#rt/>
</#if>
<#if parameters.enctype??>
 enctype="${parameters.enctype}"<#rt/>
</#if>
<#if parameters.method??>
 method="${parameters.method!'GET'}"<#rt/>
</#if>
>
<#if parameters.paramExcludes??>
	<#assign excludes = '${parameters.paramExcludes},${parameters.name}.sortBy'/>
<#else>
	<#assign excludes = '${parameters.name}.sortBy'/>
</#if>
<@paramsAsHidden excludes/>
<#assign selectName = "${parameters.name}.sortBy"/>
<#assign pageName = "${parameters.name}.page"/>
${parameters.body}
<#if parameters.enablePaging!false>
	<#assign pagerLinkClass = ""/>
	<#if parameters.useAjax!false>
		<#assign pagerLinkClass = "ajaxify"/>
	</#if>
	<#if parameters.ajaxTarget??>
		<#assign pagerLinkClass = "${pagerLinkClass} {target: '${parameters.ajaxTarget}'}"/>
	</#if>
	<#assign pageId = "${idPrefix}_page"/>
	<#assign page = parameters.nameValue.page!1/>
	<#assign pages = parameters.nameValue.pages!1/>
	<#assign begin = 1/>
	<#assign end = pages/>
		<div id="${idPrefix}_pagerContainer" class="pagerContainer">
	<#if (pages > 1)>
		<#if parameters.maxPagesToShow?? && (parameters.maxPagesToShow < pages)>
			<#if (page - (parameters.maxPagesToShow / 2)?floor <= 0)>
				<#assign end = begin + parameters.maxPagesToShow/>
			<#elseif (page + (parameters.maxPagesToShow / 2)?ceiling >= pages)>
				<#assign begin = end - parameters.maxPagesToShow/>
			<#else>
				<#assign begin = page - (parameters.maxPagesToShow /2)?floor/>
				<#assign end = begin + parameters.maxPagesToShow/>
			</#if>
		</#if>
			<div class="pageContainer">Page:
		<#if (page > 1)>
			<@s.url id="pageUrl" action="${parameters.action}" namespace="${parameters.namespace}" includeParams="all">
				<@s.param name="${pageName}" value="${page - 1}"/>
			</@s.url>
			<@s.a href="${stack.findString('pageUrl')!''?html}" cssClass="${pagerLinkClass}">&lt;</@s.a>
		</#if>
		<#if (begin > 1)>
			<@s.url id="pageUrl" action="${parameters.action}" namespace="${parameters.namespace}" includeParams="all">
				<@s.param name="${pageName}" value="1"/>
			</@s.url>
			<@s.a href="${stack.findString('pageUrl')!''?html}" cssClass="${pagerLinkClass}">1</@s.a>
			<#if (begin > 2)>
					<span>...</span>
			</#if>
		</#if>
		<#list begin..end as currentPage>
			<#if currentPage == page>
					<span><strong>${currentPage}</strong></span>
			<#else>
				<@s.url id="pageUrl" action="${parameters.action}" namespace="${parameters.namespace}" includeParams="all">
					<@s.param name="${pageName}" value="${currentPage}"/>
				</@s.url>
				<@s.a href="${stack.findString('pageUrl')!''?html}" cssClass="${pagerLinkClass}">${currentPage}</@s.a>
			</#if>
		</#list>
		<#if (end < pages)>
			<#if (end < pages - 1)>
					<span>...</span>
			</#if>
			<@s.url id="pageUrl" action="${parameters.action}" namespace="${parameters.namespace}" includeParams="all">
				<@s.param name="${pageName}" value="${pages}"/>
			</@s.url>
			<@s.a href="${stack.findString('pageUrl')!''?html}" cssClass="${pagerLinkClass}">${pages}</@s.a>
		</#if>
		<#if (page < pages)>
			<@s.url id="pageUrl" action="${parameters.action}" namespace="${parameters.namespace}" includeParams="all">
				<@s.param name="${pageName}" value="${page + 1}"/>
			</@s.url>
			<@s.a href="${stack.findString('pageUrl')!''?html}" cssClass="${pagerLinkClass}">&gt;</@s.a>
		</#if>
			</div>
	</#if>
	<#if (parameters.nameValue.numOfResults > 0)>
			<div class="resultsContainer">
				(${parameters.nameValue.numOfResults} Result<#if (parameters.nameValue.numOfResults > 1)>s</#if>)
			</div>
	</#if>
		</div>
</#if>
<#assign showSorter = parameters.showSorter!true/>
<#if showSorter && (parameters.nameValue.numOfResults < 2)>
	<#assign showSorter = false/>
</#if>
<#if showSorter>
	<#assign selectId = "${idPrefix}_sortSelect"/>
	<#setting number_format="#.#####">
		<div id="${idPrefix}_sorterContainer" class="sorterContainer">
			<label for="${selectId}">Sort By:</label>
			<select id="${selectId}" name="${selectName}" class="ccl-list-controls-submit">
	<@s.iterator value="parameters.nameValue.sortBys">
		<#assign sortOptionsKey = stack.findString('key')/>
		<#assign sortOptionsValue = stack.findString('label')/>
				<option value="${sortOptionsKey?html}"<#rt/>
		<#if tag.contains(parameters.nameValue.sortBy, sortOptionsKey) == true>
 selected="selected"<#rt/>
		</#if>
>${sortOptionsValue?html}</option><#lt/>
	</@s.iterator>
			</select>
		</div>
<#else>
	<input type="hidden" name="${selectName}" value="${parameters.nameValue.sortBy}"/>
</#if>
	</form>
</div>