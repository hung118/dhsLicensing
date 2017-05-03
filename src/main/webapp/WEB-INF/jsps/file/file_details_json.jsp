<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
	<json:property name="fileId" value="${file.id}"/>
	<json:property name="fileName" value="${file.fullFileName}"/>
	<json:property name="fileSize" value="${file.size}"/>
	<json:property name="fileContentType" value="${file.type.mimeType}"/>
</json:object>