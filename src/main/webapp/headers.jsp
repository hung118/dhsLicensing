<%@page contentType="text/html"%>
<%@page import="java.util.*"%>
<html>
	<head><title>JSP Page</title></head>
	<body>
		<table border="2">
			<tr>
				<th align="left" bgcolor="yellow">PARAMETER VARIABLE NAME</th>
				<th align="left" bgcolor="yellow">PARAMETER VARIABLE VALUE</th>
			</tr>
			<%
				Enumeration parmNames = request.getParameterNames();
				while(parmNames.hasMoreElements()) {
					String parmName = parmNames.nextElement().toString();
					out.println("<tr><td><b>"+parmName+"</b></td><td>"+request.getParameter(parmName)+"</td></tr>");
				}
			%>
		</table>
		<hr/>
		<table border="2">
			<tr>
				<th align="left" bgcolor="yellow">HEADER VARIABLE NAME</th>
				<th align="left" bgcolor="yellow">HEADER VARIABLE VALUE</th>
			</tr>
			<%
				Enumeration headerNames = request.getHeaderNames();
				while(headerNames.hasMoreElements()) {
					String headerName = headerNames.nextElement().toString();
					out.println("<tr><td><b>"+headerName+"</b></td><td>"+request.getHeader(headerName)+"</td></tr>");
				}
			%>
		</table>
		<hr/>
		<table border="2">
			<tr>
				<th align="left" bgcolor="yellow">SESSION VARIABLE NAME</th>
				<th align="left" bgcolor="yellow">SESSION VARIABLE VALUE</th>
			</tr>
			<%
				Enumeration sessionVarNames = session.getAttributeNames();
				while(sessionVarNames.hasMoreElements()) {
					String sessionVarName = sessionVarNames.nextElement().toString();
					out.println("<tr><td><b>"+sessionVarName+"</b></td><td>"+session.getAttribute(sessionVarName)+"</td></tr>");
				}
			%>
		</table>
	</body>
</html>