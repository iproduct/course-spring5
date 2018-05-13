<%@ attribute name="title" required="true" rtexprvalue="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <jsp:include page="/WEB-INF/views/head.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp"/>
    <div class="container">
<jsp:doBody />
    </div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
    </div>
</body>
</html>