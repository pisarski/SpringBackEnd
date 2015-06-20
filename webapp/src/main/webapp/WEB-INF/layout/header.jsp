<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div>
	<h1>
		<s:message code="header.welcome" />
	</h1>

	<c:if test="${hideLoginLink == null}">
		<span class="loginWrapper"> 
		
			<sec:authorize access="!isAuthenticated()">
				<s:message code="header.loggedOutMsg"/>
				<c:url value='/login' var="loginUrl" />
				<a href="${loginUrl}"><s:message code="header.logInLink"/></a>
			</sec:authorize> 
			
			<sec:authorize access="isAuthenticated()">
				<c:url value="/logout" var="logoutUrl" />
				<form:form name="logoutForm" action="${logoutUrl}" method="POST">
					<s:message code="header.loggedInAs"/> 
					<span class="usernameText">
						<sec:authentication property="principal.username" />
					</span> 
					| <a href="#" onclick="document.logoutForm.submit();"><s:message code="header.logout"/></a>
				</form:form>
			</sec:authorize>
		</span>
	</c:if>
</div>