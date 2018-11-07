<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <ul>
    <c:forEach var = "article" items="${articles}">
        <li>
            <c:out value = "${article.title}"/>
        </li>
    </c:forEach>
    </ul>
</body>
</html>