<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="errorMessage">
	<s:message code="${messageCode}" />
</div>

<div class="marginWrapper">
	<a href="<c:url value='/spittle/list'/>"><s:message code="link.backToSpittleList"/></a>
</div>