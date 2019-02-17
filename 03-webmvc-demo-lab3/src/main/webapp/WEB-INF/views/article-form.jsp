<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<custom:layout title="Add New Article">
    <h3>Add New Article</h3>
    <%--@elvariable id="article" type=""--%>
    <form:form method="POST" action="${pageContext.request.contextPath}/submit-article"
               modelAttribute="article" class="form-horizontal col-md-9 col-lg-6" enctype="multipart/form-data">
        <div class="form-group">
            <form:label path="title" class="col-sm-2 control-label">Title</form:label>
            <div class="col-sm-10">
                <form:input path="title" class="form-control"/>
            </div>
        </div>
        <form:errors path="title" class="errors well danger col-sm-offset-2 col-sm-10"/>

        <div class="form-group">
            <form:label path="content" class="col-sm-2 control-label">Content</form:label>
            <div class="col-sm-10">
                <form:input path="content" class="form-control"/>
            </div>
        </div>
        <form:errors path="content" class="errors well bg-danger col-sm-offset-2 col-sm-10"/>

        <div class="form-group">
            <form:label path="author" class="col-sm-2 control-label">Author</form:label>
            <div class="col-sm-10">
                <form:input path="author" class="form-control"/>
            </div>
        </div>
        <form:errors path="author" class="errors well bg-danger col-sm-offset-2 col-sm-10"/>

        <div class="form-group">
            <form:label path="created" class="col-sm-2 control-label">Date Created</form:label>
            <div class="col-sm-10">
                <form:input path="created" class="form-control"/>
            </div>
        </div>
        <form:errors path="created" class="errors well bg-danger col-sm-offset-2 col-sm-10"/>

        <div class="form-group">
            <label class="col-sm-2 control-label">Article Picture</label>
            <div class="col-sm-10">
                <input type="file" name="file" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Submit Article</button>
            </div>
        </div>
        </table>
    </form:form>
</custom:layout>