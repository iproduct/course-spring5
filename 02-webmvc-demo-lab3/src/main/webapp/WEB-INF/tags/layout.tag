<%@ attribute name="title" required="true" rtexprvalue="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <meta charset="UTF-8" />
    <%@include file="/WEB-INF/fragments/head.jspf" %>
</head>
<body>
<%@include file="/WEB-INF/fragments/menu.jspf"%>
    <div class="container">
<jsp:doBody />
    </div>
<%@include file="/WEB-INF/fragments/footer.jspf" %>
</body>
</html>