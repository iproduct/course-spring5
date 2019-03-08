<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Form Example - Register an Employee</title>
</head>
<body>
	<h3>Welcome, Enter The Employee Details</h3>

	<form:form method="POST" action="${pageContext.request.contextPath}/submit-article" modelAttribute="article">
		<table>
			<tr>
				<td><form:label path="title">Title</form:label></td>
				<td><form:input path="title" /></td>
			</tr>
			<tr>
				<td><form:label path="content">Content</form:label></td>
				<td><form:input path="content" /></td>
			</tr>
			<tr>
				<td><form:label path="createdDate">Creation Date</form:label></td>
				<td><form:input path="createdDate" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>

</body>

</html>