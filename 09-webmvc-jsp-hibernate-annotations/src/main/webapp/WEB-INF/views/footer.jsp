<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <c:url value="/webjars/jquery/3.1.1/jquery.min.js" var="jquery" />
	<script type="text/javascript" src="${jquery}"></script>
	<c:url value="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" var="bootstrapJS" />
	<script type="text/javascript" src="${bootstrapJS}"></script>

	<spring:url value="" var="currentPage"/>
	<script type="text/javascript">
    $(document).ready(function() {
        $("#locales").change(function () {
            var selectedOption = $('#locales').val();
            if (selectedOption != ''){
                window.location.replace('${currentPage}?lang=' + selectedOption);
            }
        });
    });
    </script>
