<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<custom:layout title="Uploaded Files View">
<h3>Add New Article</h3>
	<h2>Submitted Files</h2>
    <table>
        <c:forEach items="${files}" var="file">
            <tr>
                <td>OriginalFileName:</td>
                <td>${file.originalFilename}</td>
            </tr>
            <tr>
                <td>Type:</td>
                <td>${file.contentType}</td>
            </tr>
        </c:forEach>
    </table>
</custom:layout>