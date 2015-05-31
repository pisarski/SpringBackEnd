<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ page session="false"%>
<html>
<head>
<title><s:message code="title"/></title>
<link rel="stylesheet" type="text/css" href="<s:url value="/resources/style.css" />">
</head>
<body>
	<div id="page-wrap">
		<div id="header">
			<t:insertAttribute name="header" />
		</div>
		<div id="content">
			<t:insertAttribute name="body" />
		</div>
		<div id="footer">
			<t:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>