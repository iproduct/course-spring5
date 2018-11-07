<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<custom:layout title="Articles List">
    <table class="article-table">
        <c:forEach var="article" items="${articles}">
            <tr>
            <td>
                <c:out value="${article.title}"/>
            </td>
            </tr>
        </c:forEach>
    </table>
</custom:layout>
