<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="now" value="<%=new java.util.Date()%>" />
<h1>Spittles list (<fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${now}"/>)</h1>

<ul>
<c:forEach var="spittle" items="${spittles}">
	<li><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${spittle.time}"/> - <c:out value="${spittle.message}"/></li>
</c:forEach>
</ul>

<a href="<c:url value="/" />">Back</a>
