<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<custom:layout title="Add New Article">
<h3>Error performing the operation:</h3>
<div class="danger">
   <c:if test="${status} != null">
    ${status}:
   </c:if>
   ${error}

</div>
<a href="${pageContext.request.contextPath}/articles">Go to  Articles ...</a>
</custom:layout>