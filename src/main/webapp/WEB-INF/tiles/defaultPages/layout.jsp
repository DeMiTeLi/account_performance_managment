<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="s" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
<header class="pageHeader">
    <s:insertAttribute name="header"/>
</header>
<section class="pagesFunctionality">
    <s:insertAttribute name="content"/>
</section>
<footer class="pageFooter">
    <s:insertAttribute name="footer"/>
</footer>
</body>
</html>