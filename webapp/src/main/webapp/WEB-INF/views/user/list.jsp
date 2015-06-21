<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/user/list.css?appRev=${appRev}"/>">

<div class="linksContainer">
	<div class="newUserLink">
		<a href="<c:url value="/user/new"/>"><s:message code="user.list.add"/></a>
	</div>

	<div class="backLink">
		<a href="<c:url value='/spittle/list'/>"><s:message code="link.backToSpittleList" /></a>
	</div>
</div>

<table class="resultsTable">
	<tr class="tableHeader">
		<th><s:message code="user.firstName"/></th>
		<th><s:message code="user.lastName"/></th>
		<th><s:message code="user.username"/></th>
		<th>Roles</th>
	</tr>
	<c:forEach var="user" items="${users}">
		<tr>
			<td><c:out value="${user.firstName}" /></td>
			<td><c:out value="${user.lastName}" /></td>
			<td><c:out value="${user.username}" /></td>
			<td>
				<c:forEach var="role" items="${user.roles}" varStatus="status">
					<s:message code="${role.msgCode}"/>
					<c:if test="${status.index < user.roles.size()-1 }">
						, 
					</c:if>
				</c:forEach>
			</td>
		</tr>
	</c:forEach>
</table>
