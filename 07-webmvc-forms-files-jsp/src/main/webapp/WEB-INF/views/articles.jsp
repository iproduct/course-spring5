<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<custom:layout title="My Blog">
    <h1>Articles List</h1>
    <h4>Articles count: ${fn:length(articles)}</h4>
    <form:form method="POST" action="${pageContext.request.contextPath}/select-articles"
        modelAttribute="selection" class="form-horizontal col-md-9 col-lg-6">
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th> </th>
            <th><spring:message code="lbl.number" /></th>
            <th><spring:message code="lbl.title" /></th>
            <th><spring:message code="lbl.content" /></th>
            <th><spring:message code="lbl.date" /></th>
            <th><spring:message code="lbl.picture" /></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="article" varStatus="status" items="${articles}">
            <tr>
                <td>
                    <form:checkbox path="articleIds" value="${article.id}"/>
                </td>
                <td>${status.index + 1}</td>
                <td>${article.title}</td>
                <td>${article.content}</td>
                <td><fmt:formatDate pattern="dd.MM.yyyy" value="${article.createdDate}" /></td>
                <td>
                <c:if test = "${article.pictureUrl != null}">
                    <img class="img-thumbnail" src="${pageContext.request.contextPath}/uploads/${article.pictureUrl}" /></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form:errors path="*" class="errors well bg-danger col-sm-12"/>

    <div class="col-sm-offset-1 col-sm-11">
        <button type="submit" class="btn btn-default"><spring:message code="lbl.select" /></button>
        <input type="submit" name="clear" class="btn btn-danger" value="Clear">
        <a class="btn btn-primary" href="new-article"><spring:message code="lbl.add" /></a>
    </div>
    </form:form>


    </div>
</custom:layout>
