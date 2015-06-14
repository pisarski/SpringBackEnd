<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="saveUrl" value="/spittle/save" />
<form:form modelAttribute="spittle" action="${saveUrl}" method="POST">
	<fieldset>
		<legend><s:message code="spittle.legend.new"/></legend>

		<div class="formSection">
			<form:label path="title"><s:message code="spittle.title"/></form:label>
			<form:input path="title" />
		</div>

		<div class="formSection">
			<form:label path="message"><s:message code="spittle.message"/></form:label>
			<form:textarea path="message" />
		</div>

		<div class="buttons">
			<button type="submit"><s:message code="button.create"/></button>
		</div>
	</fieldset>
</form:form>