<fieldset class="fileManagerFieldset">
	<legend>${parameters.legend}:</legend>
	<div class="${parameters.idPrefix}FileManagerSection">
		<input class="fileId" type="hidden" name="${parameters.name}"<#rt/>
<#if parameters.file?? && parameters.file.id??>
 value="${parameters.file.id?c}"<#rt/>
</#if>
/>
		<div class="loadingMessage" style="display: none; text-align: center;"><img src="<@s.url value="/images/earthspin.gif"/>"/></div>
		<div class="preview">
		</div>
		<button id="${parameters.idPrefix}uploadButton" class="uploadButton">Upload</button> <span class="fileLinks"><a href="#" class="removeLink">Remove</a> | <a href="#" class="downloadLink">Download</a></span>
	</div>
</fieldset>
<script type="text/javascript">
	initFileManager("${parameters.idPrefix}FileManagerSection", 
<#if parameters.file?? && parameters.file.id??>
 "${parameters.file.id?c}"<#rt/>
<#else>
 null<#rt/>
</#if>
);
</script>