<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="s" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
	<head>

	    <meta charset="UTF-8">
        <tiles:importAttribute name="title" />
        <title>Title Layouts</title>

		<style>
			body {
				background-color: #eee;
			}
		</style>

	</head>
	<body>

	<div class="container">

		<header class="header">
			<s:insertAttribute name="header"/>
		</header>

		<div>
			<s:insertAttribute name="content"/>
		</div>

		<footer class="footer">
			<s:insertAttribute name="footer"/>
		</footer>

	</div>

	</body>
</html>