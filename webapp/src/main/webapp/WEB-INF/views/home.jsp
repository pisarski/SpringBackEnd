<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Welcome to Spittr - Signum test</h1>

Env: <c:out value="${env}"/><br/><br/>

<a href="<c:url value="/spittle/list" />">Spittles</a> |
<a href="<c:url value="/spitter/register" />">Register</a>
