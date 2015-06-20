<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="loginProcessingUrl" value="/login.do" />
<form:form name="loginForm" action="${loginProcessingUrl}" method="POST">
	<fieldset>
		<legend><s:message code="login.provideCredentials"/></legend>

		<c:if test="${not empty error}">
			<div class="formSection">
				<div class="formErrorMessage"><s:message code="login.error"/></div>
			</div>
		</c:if>

		<div class="formSection">
			<label for="username" class="required"><s:message code="login.username"/></label> 
			<input type="text" name="username" value="" />
		</div>

		<div class="formSection">
			<label for="password" class="required"><s:message code="login.password"/></label> 
			<input type="password" name="password" value="" />
		</div>

		<div class="formSection checkboxWrapper">
			<label for="remember_me" class="inline"><s:message code="login.rememberMe"/></label> 
			<input id="remember_me" name="remember-me" type="checkbox" />
		</div>

		<div class="buttons">
			<button type="submit"><s:message code="login.loginIn"/></button>
			<button type="button" onclick="location.href='<c:url value='/spittle/list'/>'">
				<s:message code="link.backToSpittleList"/>
			</button>
		</div>
	</fieldset>
</form:form>

<script type="text/javascript">
	document.loginForm.username.focus();
</script>