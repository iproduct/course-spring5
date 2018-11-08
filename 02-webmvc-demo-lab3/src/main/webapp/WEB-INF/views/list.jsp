<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<custom:layout title="Articles List">
    <table class="article-table">
        <thead>
        <th>Number</th>
        <th>Title</th>
        <th>Content</th>
        <th>Author</th>
        <th>Created</th>
        <th>Modified</th>
        </thead>
        <tbody>
        <c:forEach var="article" varStatus="status" items="${articles}">
            <tr>
                <td>
                    <c:out value="${status.index + 1}"/>
                </td>
                <td>
                    <c:out value="${article.title}"/>
                </td>
                <td>
                    <c:out value="${article.content}"/>
                </td>
                <td>
                    <c:out value="${article.author}"/>
                </td>
                <td>
                    <c:out value="${article.created}"/>
                </td>
                <td>
                    <c:out value="${article.modified}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</custom:layout>
