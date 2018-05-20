<%@ attribute name="title" required="true" rtexprvalue="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <meta charset="UTF-8" />
    <jsp:include page="/WEB-INF/views/head.jspf"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jspf"/>
    <div class="container">
<jsp:doBody />
    </div>
<jsp:include page="/WEB-INF/views/footer.jspf"/>
    </div>
</body>
</html>