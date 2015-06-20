<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
	
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/spittle/list.css?appRev=${appRev}"/>">

<sec:authorize access="isAuthenticated()">
	<div class="newSpittleLink">
		<a href="<c:url value="/spittle/new"/>"><s:message code="spittle.list.add"/></a>
	</div>
</sec:authorize>

<div class="spittleList">
	<c:forEach var="spittle" items="${spittles}" varStatus="status">
		<div class="spittle">
			<div class="title">
				<span class="titleText"> 
					<c:out value="${spittle.title}" />
				</span>
				<span class="spittleDate">
					<fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${spittle.time}"/>
				</span>
			</div>
			<div class="message">
				<c:out value="${spittle.message}" />
			</div>
			<hr <c:if test="${status.index == spittles.size()-1}">class="last"</c:if>/>
		</div>
	</c:forEach>
</div>

