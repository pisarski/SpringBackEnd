<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<%@ page import="com.lumesse.entity.enums.UserRight" %>

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
		<c:url var="saveUrl" value="${saveActionUrl}" />
		<form:form modelAttribute="user" action="${saveUrl}" method="POST">
		
			<div class="errorMessage marginWrapper">
				<form:errors/>
			</div>
			
			<fieldset>
				<legend><s:message code="user.legend.new"/></legend>
		
				<form:hidden path="id"/>
		
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
			
				<c:if test="${user.id == null}">
					<div class="formSection">
						<form:label path="password" cssClass="required"><s:message code="user.password"/></form:label>
						<form:password path="password" cssErrorClass="error"/>
						<form:errors path="password" cssClass="formErrorMessage"/>
					</div>					
				</c:if>
		
				<div class="formSection">
					<form:label path="rights" cssClass="required"><s:message code="user.permissionsSection"/></form:label>
					<form:errors path="rights" cssClass="formErrorMessage"/>
					<c:forEach items="<%= UserRight.values()%>" var="userRight">
						<div class="checkboxWrapper">
							<form:checkbox path="rights" value="${userRight.name()}"/> 
							<label><s:message code="${userRight.code}"/></label>
						</div>
					</c:forEach>
				</div>

				<div class="buttons">
					<button type="submit"><s:message code="${submitBtnCode}"/></button>
					<button type="button" onclick="location.href='<c:url value='/user/list'/>'"><s:message code="button.cancel"/></button>
				</div>
			</fieldset>
		</form:form>	
	</c:otherwise>
</c:choose>
