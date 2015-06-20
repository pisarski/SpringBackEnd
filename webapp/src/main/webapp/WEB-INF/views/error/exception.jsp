<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<div class="errorMessage">
	<s:message code="error.exception" arguments="${uuid}"/>
</div>

<div class="marginWrapper">
	<a href="<c:url value='/spittle/list'/>"><s:message code="link.backToSpittleList"/></a>
</div>