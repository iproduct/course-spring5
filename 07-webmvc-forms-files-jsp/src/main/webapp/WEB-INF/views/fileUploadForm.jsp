<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<custom:layout title="Upload File">
<h3>Add New Article</h3>
	<form:form method="POST" action="${pageContext.request.contextPath}/uploadFiles" enctype="multipart/form-data" class="form-horizontal col-md-9 col-lg-6">
			<div class="form-group">
				<label class="col-sm-2 control-label">Files</label>
				<div class="col-sm-10">
				    <input type="file" name="files" multiple class="form-control"/>
				</div>
			</div>

			<div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
				    <button type="submit" class="btn btn-primary">Upload Files</button>
				</div>
			</div>
		</table>
	</form:form>
</custom:layout>