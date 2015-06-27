<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<%@ page import="com.lumesse.entity.enums.UserRole" %>

<c:choose>
	<c:when test="${userLimitExceeded}">
		<div class="errorMessage">
			<s:message code="user.limitExceeded"/>
		</div>
		
		<div class="marginWrapper">
			<a href="<c:url value='/user/list'/>"><s:message code="link.backToUserList"/></a>
		</div>	
	</c:when>
	<c:otherwise>
		<c:url var="saveUrl" value="/user/save" />
		<form:form modelAttribute="user" action="${saveUrl}" method="POST">
		
			<div class="errorMessage marginWrapper">
				<form:errors/>
			</div>
			
			<fieldset>
				<legend><s:message code="user.legend.new"/></legend>
		
				<div class="formSection">
					<form:label path="firstName" cssClass="required"><s:message code="user.firstName"/></form:label>
					<form:input path="firstName" cssErrorClass="error"/>
					<form:errors path="firstName" cssClass="formErrorMessage"/>
				</div>
		
				<div class="formSection">
					<form:label path="lastName" cssClass="required"><s:message code="user.lastName"/></form:label>
					<form:input path="lastName" cssErrorClass="error"/>
					<form:errors path="lastName" cssClass="formErrorMessage"/>
				</div>
				
				<div class="formSection">
					<form:label path="username" cssClass="required"><s:message code="user.username"/></form:label>
					<form:input path="username" cssErrorClass="error"/>
					<form:errors path="username" cssClass="formErrorMessage"/>
				</div>
		
				<div class="formSection">
					<form:label path="password" cssClass="required"><s:message code="user.password"/></form:label>
					<form:password path="password" cssErrorClass="error"/>
					<form:errors path="password" cssClass="formErrorMessage"/>
				</div>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<div class="formSection">
						<form:label path="roles" cssClass="required">Role</form:label>
						<form:select path="roles" multiple="false" cssErrorClass="error">
							<c:forEach var="role" items="<%= UserRole.values() %>">
								<form:option value="${role}"><s:message code="${role.msgCode }"/></form:option>
							</c:forEach>
						</form:select>
						<form:errors path="roles" cssClass="formErrorMessage"/>
					</div>
				</sec:authorize>
				
				<div class="buttons">
					<button type="submit"><s:message code="button.create"/></button>
					<button type="button" onclick="location.href='<c:url value='/user/list'/>'"><s:message code="button.cancel"/></button>
				</div>
			</fieldset>
		</form:form>	
	</c:otherwise>
</c:choose>
