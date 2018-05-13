<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<custom:layout title="My Blog">
    <h1>Articles List</h1>
    <h4>Articles count: ${fn:length(articles)}</h4>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Number</th>
            <th>Title</th>
            <th>Content</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="article" varStatus="status" items="${articles}">
            <tr>
                <td>${status.index}</td>
                <td>${article.title}</td>
                <td>${article.content}</td>
                <td><fmt:formatDate pattern="dd.MM.yyyy" value="${article.createdDate}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="col-sm-offset-1 col-sm-10">
        <a class="btn btn-primary" href="new-article">Add New Article</a>
    </div>
</custom:layout>